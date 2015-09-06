package com.likejian.thread;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.websocket.Session;

import org.apache.log4j.Logger;

import com.likejian.module.User;

public class ChatThread extends Thread {

	private static Logger logger = Logger.getLogger(ChatThread.class.getName());
	public static ConcurrentLinkedQueue<String> contentQueue = new ConcurrentLinkedQueue<String>();
	public static Set<Session> sessionSet = Collections.synchronizedSet(new HashSet<Session>());
	//每一个sessionId对应的用户信息
	public static ConcurrentHashMap<String, User> onlineUserMap  = new ConcurrentHashMap<String, User>();
	
	@Override
	public void run() {
		while (true) {
			try {
				if (contentQueue.isEmpty()) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					String info = contentQueue.poll();
					if (info != null) {
						//给每一个在线用户发送信息
						for(Session session : sessionSet){
							if(session.isOpen()){
								session.getBasicRemote().sendText(info);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
