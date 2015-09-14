package com.likejian.websocket;
import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

import com.likejian.thread.ChatThread;

@ClientEndpoint
@ServerEndpoint(value="/websocket/")
public class EventSocket{
	
	private static Logger logger = Logger.getLogger(EventSocket.class.getName());
	
	@OnOpen
	public void onOpen(Session session) {
		String nick = (String)session.getRequestParameterMap().get("nick").get(0);
		//是否可以在这里进行一个用户合法性的验证
		logger.debug("Client connected sessionId is " + session.getId());
		logger.debug("nick = " + nick);
		session.getUserProperties().put("nick", nick);
		//session.setMaxIdleTimeout(2000);		//连接最大空闲时间，也就是连接多久没用就会被干掉		
		ChatThread.sessionSet.add(session);
		String content = "["+ nick+"]进入聊天室";
		ChatThread.contentQueue.add(content);
	}
	
	@OnMessage
	public void onMessage(String message, Session session) throws IOException,
			InterruptedException {
		logger.debug("Received: [" + message + "] from sessionId=" + session.getId());
		String nick = (String)session.getUserProperties().get("nick");
		String content = "["+ nick +"]说:" + message;
		ChatThread.contentQueue.add(content);
	}

	@OnClose
	public void onClose(Session session, CloseReason reason) {
		logger.debug("Connection closed sessionId is " + session.getId());
		String nick = (String)session.getUserProperties().get("nick");
		String content = "["+ nick +"]退出聊天室";
		ChatThread.contentQueue.add(content);
		ChatThread.sessionSet.remove(session);
	}

	@OnError
	public void onError(Session session, Throwable t) {
		t.printStackTrace();
		logger.debug("System Error " + session.getId());
	}
}
