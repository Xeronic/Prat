package chat_server;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
/**
 *Represents a client to the server.
 * @author Anton, Jerry, Mårten, Jonas
 * 
 */
public class ClientListener extends Thread {
	private Connection connection;
	private String username;
	private Server controller;
	
	/**
	 * Constructs a "client" with a socket and a serverconnection.
	 * @param socket Socket
	 * @param controller Server
	 */
	public ClientListener(Socket socket, Server controller) {
		this.controller = controller;
		this.connection = new Connection(socket);
	}
	/**
	 * Waits for the username, from the inputStream.
	 * @return Username
	 */
	public String waitForInitialMessage() {
		do {
			try {
				username = connection.getInputStream().readUTF();
			} catch (IOException ex) {}
		} while (username == null);
		return username;
	}
	/**
	 * Returns the connection.
	 * @return connection
	 */
	public Connection getConnection() {
		return connection;
	}
	/**
	 * Returns the Username
	 * @return username
	 */
	public String getUsername() {
		return this.username;
	}
	/**
	 * Checks for new input.
	 */
	public void run() {
		while(!Thread.interrupted()) {
			recieve();
		}
	}
	/**
	 * Recieves information.
	 */
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
	/**
	 * Sends information and message.
	 * @param m Message
	 * @throws IOException
	 */
	public void send(Message m) throws IOException {
			connection.getOutputStream().writeObject(m);
			connection.getOutputStream().flush();
	}
}
