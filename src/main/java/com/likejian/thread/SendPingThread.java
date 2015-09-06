package com.likejian.thread;

import java.nio.ByteBuffer;

import javax.websocket.Session;

import org.apache.log4j.Logger;

public class SendPingThread extends Thread {
	
	private static Logger logger = Logger.getLogger(SendPingThread.class.getName());
	@Override
	public void run() {
		while (true) {
			try {
				//给每个客户端发一ping帧，干掉已经主动断开链接的客户端
				for(Session session : ChatThread.sessionSet){
					logger.debug("sessionId=" + session.getId() + " isOpen()=" +session.isOpen());
					ByteBuffer buffer = ByteBuffer.allocate(0);  
					session.getBasicRemote().sendPing(buffer);
				}
				Thread.sleep(1000 * 5);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
