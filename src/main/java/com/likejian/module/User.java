package com.likejian.module;

public class User {

	private String sessionId;
	private String nick;
	
	public User(String sessionId, String nick) {
		super();
		this.sessionId = sessionId;
		this.nick = nick;
	}
	
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
}
