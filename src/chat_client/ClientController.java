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

	public void login(String username) {
		this.username = username;
		client = new ClientGUI(this);
		client.appendText("Trying to login..");
		connect();
	}

	public void connect() {
		try {
			Socket socket = new Socket(loginGUI.getIpAddress(), 3520);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			oos.writeUTF(username);
			oos.flush();
			new ReceiveMessages().start();
		} catch (IOException ex) {
			client.appendText("Could not connect to server");
		}
	}

	public String getUserName() {
		return username;
	}

	public String[] getSelectedUsers() {
		return client.getSelectedUsers();
	}

	public void send(Message m) {
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			System.err.println("Couldn't send message...");
		}
	}

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

	private class ReceiveMessages extends Thread {
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
