package chat_client;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;

/**
 * Controller class for the Client.
 * @author Anton, Jerry, Jonas, Mårten
 */

import chat_server.Message;

import javax.swing.*;

/**
 * Controller class for Clients. 
 * @author Jonas, Anton, Jerry, Mårten
 */
public class ClientController {

	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private ClientGUI client;
	private String username;
	private LoginGUI loginGUI;

	/**
	 * Constructor. Creates and shows a Login screen.
	 */
	public ClientController() {
		loginGUI = new LoginGUI(this);
	}
	
	/**
	 * Stores the userName for a client, then try to make a connection.
	 * @param username - clients userName.
	 */
	public void login(String username) {
		this.username = username;
		client = new ClientGUI(this);
		client.appendText("Trying to login..");
		connect();
	}
	
	/**
	 * Creates a new socket with the ipAddress that the user enter in the
	 * log in GUI, and a port thats available. Then uses the socket connection 
	 * for input and outputStream. Then i writes the userName to the server and
	 * then starts ReceiveMessage thread. 
	 */
	public void connect() {
		try {
			Socket socket = new Socket(loginGUI.getIpAddress(), 3520);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			oos.writeUTF(username);
			oos.flush();
			new ReceiveMessages().start();
		} catch (IOException ex) {
			System.err.println(ex.getMessage());
			client.appendText("Could not connect to server");
		}
	}

	/**
	 * Method for retrieving userName(s).
	 * @return userName(s)
	 */
	public String getUserName() {
		return username;
	}

	/**
	 * Method for retrieving selectedUser(s).
	 * @return selectedUser(s)
	 */
	public String[] getSelectedUsers() {
		return client.getSelectedUsers();
	}

	/**
	 * Sends a message object.
	 * @param m - a object of the Message class
	 */
	public void send(Message m) {
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			System.err.println("Couldn't send message...");
		}
	}

	/**
	 * This method formats the message, it appends "sender + timeStamp + > ". 
	 * Then the method checks if its only text to be appended after ">" or if there
	 * is an image asWell. If there is an image in the message object it will be
	 * directed to the "appendTextAndImage". 
	 * @param m a object of the Message class
	 */
	public void appendText(Message m) {
		m.setText((m.getSender() != null) ? (new SimpleDateFormat("HH:mm:ss")
				.format(m.getRecievedAtServer()) + " " + m.getSender() + "> " + m
				.getText()) : m.getText());

		if (m.getImage() == null) {
			client.appendText(m.getText());
		} else {
			client.appendTextAndImage(m.getText(), m.getImage());
		}
	}

	public void sendOfflineMessage(String toUser, String inputMessage, ImageIcon icon) {
		Message message = new Message();
		if (icon != null) {
			message.setImage(icon);
		}
		message.setText(inputMessage);
		if(toUser != null){
			String[] person = new String[1];
			person[0] = toUser;
			message.setRecipients(person);
			message.setSender(this.getUserName());
			this.send(message);
		}
	}

	public void sendMessage(String inputMessage, ImageIcon image) {
		if (inputMessage.length() > 0) {
			Message message = new Message();
			if (image != null) {
				message.setImage(image);
				image = null;
			}
			message.setText(inputMessage);
			if (this.getSelectedUsers() != null) {
				message.setRecipients(this.getSelectedUsers());
				this.send(message);
			} else {
				message.setAll(true);
				this.send(message);
			}
			client.clearInputField();
		}
		else if (image != null) {
			Message message = new Message();
			message.setImage(image);
			message.setText("");
			if (this.getSelectedUsers() != null) {
				message.setRecipients(this.getSelectedUsers());
				this.send(message);
			} else {
				message.setAll(true);
				this.send(message);
			}
			image = null;
		}
	}

	/**
	 * This class receives messages from the server.
	 * @author Mårten, Jerry, Anton, Jonas
	 */
	private class ReceiveMessages extends Thread {
		
		/**
		 * The run method reads an object and checks if its a String array or
		 * if its a Message object. If its a String array the method "updateList" 
		 * if called, because then its a list of users. Else if its a Message object
		 * the "appendText" method is called. 
		 */
		public void run() {
			while (!Thread.interrupted()) {
				try {
					Object obj = ois.readObject();
					if (obj instanceof String[]) {
						client.updateList((String[]) obj);
					} else if (obj instanceof Message) {
						Message m = (Message) obj;
						appendText(m);
					}
				} catch (IOException ex) {
					System.err.println("Connection lost...");
					JOptionPane.showMessageDialog(null, "Connection lost...");
					this.interrupt();
				} catch (ClassNotFoundException e) {
					System.err.println("Can't find received class");
				}
			}
		}
	}
}