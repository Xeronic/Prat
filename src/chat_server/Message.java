package chat_server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.Icon;

public class Message implements Serializable{
	private String[] recipients;
	private String text;
	private Icon pic;
	public boolean all;
	
	public Message(String id) {
		this.text = "User " + id + " has connected at ";
	}
	
	public Message(String text, String[] recipients) {
		this.text = text;
		this.recipients = recipients;
	}

	/**
	 * @return the recipients
	 */
	public String[] getRecipients() {
		return recipients;
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
