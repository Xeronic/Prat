package chat_server;

import java.io.Serializable;
import java.util.Date;

import javax.swing.Icon;

public class Message implements Serializable{
	private String[] recipients;
	private String text, sender;
	private Date recievedAtServer;
	private Icon pic;
	public boolean all;
	
	public Message(String id) {
		this.text = "User " + id + " has connected at ";
	}
	
	public Message(String text, String[] recipients) {
		this.text = text;
		this.recipients = recipients;
	}

	public void setRecievedAtServer(Date date) {
		this.recievedAtServer = date;
	}
	
	public Date getRecievedAtServer() {
		return this.recievedAtServer;
	}
	
	/**
	 * @return the recipients
	 */
	public String[] getRecipients() {
		return recipients;
	}
	
	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSender() {
		return this.sender;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @param recipients the recipients to set
	 */
	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}
	
	public String toString(){
		return text;
	}
}
