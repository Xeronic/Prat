package chat_server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.Icon;

public class Message implements Serializable{
	private ArrayList<String> recipients;
	private String text;
	private Icon pic;
	public boolean all;
	
	public Message(String id) {
		this.text = "User " + id + " has connected at ";
	}

	/**
	 * @return the recipients
	 */
	public ArrayList<String> getRecipients() {
		return recipients;
	}

	/**
	 * @param recipients the recipients to set
	 */
	public void setRecipients(ArrayList<String> recipients) {
		this.recipients = recipients;
	}
}
