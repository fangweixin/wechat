package com.team2.bean;

public class MsgItem {
	private int contactId;
	private String contactIcon;
	private String contactName;
	private String msgSimpleContent;
	private String msgTime;
	private boolean msgNotDisturb;
	private int unReadMessage;


	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	
	public String getContactIcon() {
		return contactIcon;
	}

	public void setContactIcon(String contactIcon) {
		this.contactIcon = contactIcon;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getMsgSimpleContent() {
		return msgSimpleContent;
	}

	public void setMsgSimpleContent(String msgSimpleContent) {
		this.msgSimpleContent = msgSimpleContent;
	}

	public String getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(String msgTime) {
		this.msgTime = msgTime;
	}

	public boolean isMsgNotDisturb() {
		return msgNotDisturb;
	}

	public void setMsgNotDisturb(boolean msgNotDisturb) {
		this.msgNotDisturb = msgNotDisturb;
	}

	public int getUnReadMessage() {
		return unReadMessage;
	}

	public void setUnReadMessage(int unReadMessage) {
		this.unReadMessage = unReadMessage;
	}

}
