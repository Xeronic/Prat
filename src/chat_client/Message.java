package chat_client;

import java.io.Serializable;
import java.util.Date;

import javax.swing.*;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String[] recipients;
	private String text, sender;
	private Date recievedAtServer;
	private ImageIcon image;

	public boolean getAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}

	public boolean all;

	public Message() {}
	public Message(String text) {
		this.text = text;
	}
	
	public Message(String text, String[] recipients) {
		this.text = text;
		this.recipients = recipients;
	}

	public Message(String text, boolean all) {
		this.text = text;
		this.all = all;
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

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public ImageIcon getImage() {
		return this.image;
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
