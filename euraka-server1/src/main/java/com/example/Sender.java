package com.example;

import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Sender implements ConfirmCallback{
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public String send() {
        String context = "hello " + new Date();
        System.out.println("Sender : " + context);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        rabbitTemplate.setConfirmCallback(this);
        sendMsg(context);
        return "sendHelloMQ";
    }
    
    public String sendHi() {
        String context = "hi " + new Date();
        System.out.println("Sender : " + context);
        rabbitTemplate.setConfirmCallback(this);
        sendHiMsg(context);
        return "sendHiMQ";
    }
    
    public void sendHiMsg(String context) {  
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString()); 
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.HI_ROUTINGKEY, context,correlationId);  
    } 
    
    public void sendMsg(String context) {  
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString()); 
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.ROUTINGKEY, context,correlationId);  
    }  
    
    /**  
     * 回调  
     */  
    @Override  
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {  
        System.out.println(" 回调id:" + correlationData);  
        if (ack) {  
            System.out.println("消息成功消费");  
        } else {  
            System.out.println("消息消费失败:" + cause);  
        }  
    } 
}
