package chat_server;

import javax.swing.*;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class ClientListener extends Thread {
	private Connection connection;
	private String username;
	private PratServer controller;
	
	public ClientListener(Socket socket, PratServer controller) {
		this.controller = controller;
		this.connection = new Connection(socket);
	}
	
	public String waitForInitialMessage() {
		do {
			try {
				username = connection.getInputStream().readUTF();
			} catch (IOException ex) {}
		} while (username == null);
		return username;
	}
	
	public Connection getConnection() {
		return connection;
	}

	public String getUsername() {
		return this.username;
	}
	
	public void run() {
		while(!Thread.interrupted()) {
			recieve();
		}
	}
	
	public void recieve() {
		try {
			Object obj = connection.getInputStream().readObject();
			if (obj instanceof Message) {
				Message m = (Message) obj;
				m.setSender(this.username);
				m.setRecievedAtServer(new Date());
				controller.extractRecipients(m);
			}
		} catch (EOFException e) {
			controller.removeClient(this);
			this.interrupt();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			
		}
	}

	public void send(Message m) throws IOException {
			connection.getOutputStream().writeObject(m);
			connection.getOutputStream().flush();
	}
}
