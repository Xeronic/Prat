package chat_server;

import java.io.Serializable;
import java.util.Date;

import javax.swing.*;

/**
 * Class that represents a Message sent, from client to server, or server to client.
 * @author Anton, Jerry, Mårten, Jonas
 *
 */
public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String[] recipients;
	private String text, sender;
	private Date recievedAtServer;
	private ImageIcon image;
	private boolean all;
	
	/**
	 * Constructor that dosen't take any parameters. Used for creating Messages. 
	 */
	public Message() {}
	
	/**
	 * Constructor that takes a String for creating Messages. Used for sending simple testmessages.
	 * @param text A text message
	 */
	public Message(String text) {
		this.text = text;
	}
	/**
	 * Constructor that takes a String and a recipient-list. Used for sending text for specified users.
	 * @param text A text message
	 * @param recipients A list of receivers
	 */
	public Message(String text, String[] recipients) {
		this.text = text;
		this.recipients = recipients;
	}

	/**
	 * Returns true or false, if you want to send a Message to all users online.
	 * @return true if you want to send it to all, false if not
	 */
	public boolean getAll() {
		return all;
	}
	/**
	 * Sets the all-boolean to true or false.
	 * @param all boolean to choose if you want to send you message to all or not.
	 */
	public void setAll(boolean all) {
		this.all = all;
	}
	/**
	 * Sets the time when the server recieved the message
	 * @param date time of arrival
	 */
	public void setRecievedAtServer(Date date) {
		this.recievedAtServer = date;
	}
	/**
	 * Returns the time.
	 * @return the time of arrival
	 */
	public Date getRecievedAtServer() {
		return this.recievedAtServer;
	}
	
	/**
	 * Returns the recipients in a String array
	 * @return the recipients in a String-array
	 */
	public String[] getRecipients() {
		return recipients;
	}
	
	/**
	 * Sets the sender of a Message. 
	 * @param sender of a message
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}
	/**
	 * Returns the sender
	 * @return the sender of a message
	 */
	public String getSender() {
		return this.sender;
	}
	/**
	 * Returns the text sent in a Message
	 * @return the text
	 */
	public String getText() {
		return this.text;
	}
	/**
	 * Sets the text for the message.
	 * @param text the text in a message
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Sets the image(ImageIcon) in a message.
	 * @param image a attached image in a message
	 */
	public void setImage(ImageIcon image) {
		this.image = image;
	}
	/**
	 * Returns the image(ImageIcon) to a receiver.
	 * @return the ImageIcon-object
	 */
	public ImageIcon getImage() {
		return this.image;
	}
	/**
	 * Sets the recipients, by receiving a String-array-
	 * @param recipients the recipients to set
	 */
	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}
	/**
	 * Returns the text of a Message in a string.
	 * @return text-message(String)
	 */
	public String toString(){
		return text;
	}
}
