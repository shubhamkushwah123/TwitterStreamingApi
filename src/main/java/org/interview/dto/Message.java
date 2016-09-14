package org.interview.dto;


import java.util.Comparator;

public class Message implements Comparable<Message>,Comparator<Message> {
	
	long messageId;
	
	long creationDate;
	
	String messageText;
	
	String author;

	long userId;
	
	String userName;
	
	String screenName;
	
	long userCreatedAt;

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public long getUserCreatedAt() {
		return userCreatedAt;
	}

	public void setUserCreatedAt(long userCreatedAt) {
		this.userCreatedAt = userCreatedAt;
	}

	@Override
	public int compareTo(Message msg) {
		if(this.getUserCreatedAt()==msg.getUserCreatedAt())
			return 0;
		else if(this.getUserCreatedAt()>msg.getUserCreatedAt())
			return 1;
		else 
			return -1;
	}

	@Override
	public int compare(Message msg1, Message msg2) {
		if(msg1.getCreationDate()==msg2.getCreationDate())
			return 0;
		else if(msg1.getCreationDate()>msg2.getCreationDate())
			return 1;
		else
			return -1;
	}
	
	
	

}
