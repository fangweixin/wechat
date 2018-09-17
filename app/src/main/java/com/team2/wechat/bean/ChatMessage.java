package com.team2.wechat.bean;

import java.util.Date;

public class ChatMessage {

	private String content;
	private Type type;
	private Date date;
	
	public enum Type{
		RECEIVE,SEND
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	
	
}
