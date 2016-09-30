<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
<title>Testing websockets</title>
</head>
<body>
	<div>
		<input id="msg" type="text" />
		<input id="button" type="button" value="发送" onclick="send();" />
	</div>
	<div id="messages"></div>
	<script type="text/javascript">
		var userId = Math.random();
		var webSocket = new WebSocket('ws://localhost:8080/testwebsocket?userId='+ userId);
		//var webSocket = new WebSocket('ws://115.159.49.154:8080/testwebsocket?userId='+ userId);

		webSocket.onerror = function(event) {
			onError(event);
		};

		webSocket.onopen = function(event) {
			onOpen(event);
		};

		webSocket.onmessage = function(event) {
			onMessage(event);
		};

		function onMessage(event) {
			document.getElementById('messages').innerHTML += '<br />'
					+ event.data;
		}

		function onOpen(event) {
			document.getElementById('messages').innerHTML = 'Connection established';
		}

		function onError(event) {
			alert(event.data);
		}

		function send() {
			var text = document.getElementById("msg").value;
			webSocket.send(text);
			return false;
		}
	</script>
</body>
</html>