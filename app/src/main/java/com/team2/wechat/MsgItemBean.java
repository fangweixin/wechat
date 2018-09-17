package com.team2.wechat;

public class MsgItemBean {
	private String contactIcon;
	private String contactName;
	private String msgSimpleContent;
	private String msgTime;
	private boolean msgNotDisturb;

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

}
