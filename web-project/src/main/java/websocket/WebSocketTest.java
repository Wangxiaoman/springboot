package websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

/*
 * DEMO测试时使用的websocket类，状态数据直接cache在进程内
 */
@ServerEndpoint("/websockettest")  
@Component
public class WebSocketTest {
    private static final Logger LOGGER = Logger.getLogger(WebSocketTest.class);
    // key为房间号，List为房间中用户
    private static final Map<String, User> onlineUsers = new ConcurrentHashMap<String, User>();
    private static final Set<String> onlineSeats = Collections.newSetFromMap(new HashMap<String, Boolean>());

    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        // Print the client message for testing purposes
        LOGGER.info("Received: " + message + ", session id:" + session.getId());
        if (!session.isOpen()) {
            try {
                session.close();
            } catch (IOException e) {
                LOGGER.info("", e);
            }
            cleanupSessionData(session);
            System.out.println(onlineUsers.size());
            return;
        }

        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(message);
        } catch (Exception e) {
            LOGGER.warn("invalid msg forma?  msg:" + message, e);
            return;
        }
//        String type = jsonObject.getString("t");
//        if ("add".equals(type)) {
//            // 抢座
//            synchronized (onlineSeats) {
//                if (onlineSeats.size() >= MAX_SEAT_NUM) {
//                    return;
//                }
//                onlineSeats.add(session.getId());
//                putStatusDataIntoJson(jsonObject);
//                message = jsonObject.toString();
//            }
//        } else if ("leave".equals(type)) {
//            // 离座
//            synchronized (onlineSeats) {
//                if (!onlineSeats.contains(session.getId())) {
//                    return;
//                }
//                onlineSeats.remove(session.getId());
//                putStatusDataIntoJson(jsonObject);
//                message = jsonObject.toString();
//            }
//        } 
//        broadcastMsg(message);
    }

    @OnOpen
  public void onOpen(Session session) {
    Map<String, List<String>> paramMap = session.getRequestParameterMap();
    List<String> userIds = paramMap.get("userId");
    String userId = "无名氏";
    if (userIds != null && userIds.size() > 0) {
      userId = userIds.get(0);
    }
    User user = new User(userId, session);
    onlineUsers.put(session.getId(), user);
    System.out.println("current user count:" + onlineUsers.size() + ",Client connected");
    // 发送当前座位列表
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("t", "s");
    putStatusDataIntoJson(jsonObject);
    synchronized (session) {
      try {
        session.getBasicRemote().sendText(jsonObject.toString());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

    @OnClose
    public void onClose(Session session) {
        cleanupSessionData(session);
        System.out.println("current user count:" + onlineUsers.size() + ",Connection closed");
    }

  private void broadcastMsg(String message) {
    Iterator<Entry<String, User>> iterator = onlineUsers.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, User> entry = iterator.next();
      try {
        if (entry.getValue().getSession().isOpen()) {
          synchronized (entry.getValue().getSession()) {
            entry.getValue().getSession().getBasicRemote().sendText(message);
          }
        } else {
          iterator.remove();
          entry.getValue().getSession().close();
          // cleanup session data ?
        }
      } catch (IOException e) {
        iterator.remove();
        try {
          entry.getValue().getSession().close();
        } catch (IOException e1) {
        }
        // cleanup session data ?
      }
    }
  }

    private void putStatusDataIntoJson(JSONObject jsonObject) {
        Set<String> uids = new HashSet<String>();
        synchronized (onlineSeats) {
            for (String sid : onlineSeats) {
                if (onlineUsers.containsKey(sid)) {
                    uids.add(onlineUsers.get(sid).getUserId());
                }
            }
        }
    }

    private void cleanupSessionData(Session session) {
        if (session == null || session.getId() == null) {
            return;
        }
        synchronized (onlineSeats) {
            onlineSeats.remove(session.getId());
        }
        // check the result and broadcast if nessesarry ?
        onlineUsers.remove(session.getId());
    }
}

class User {
    private String userId;
    private Session session;

    public User(String userId, Session session) {
        this.userId = userId;
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
