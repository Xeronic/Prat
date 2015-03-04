package chat_server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Client extends Thread {
	private Connection connection;
	private String username;
	private ArrayList<Message> messages;
	private PratServer pratServer;
	
	public Client(Socket socket, ArrayList<Message> messages, PratServer pratServer) {
		this.messages = messages;
		this.connection = new Connection(socket);
		this.start();
		this.pratServer = pratServer;
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
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String id) {
		this.username = id;
	}
	
	public void run(){
		while(true){
			recieve();
		}
	}
	
	public void recieve() {
		try {
			Object obj = connection.getInputStream().readObject();
			if (obj instanceof Message) {
				Message m = (Message) obj;
				messages.add(m);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			pratServer.removeClient(this);
		}
	}

	public void send(Message m) throws IOException {
			connection.getOutputStream().writeObject(m);
			connection.getOutputStream().flush();
	}
}
