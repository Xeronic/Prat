package chat_server;

import java.util.ArrayList;

public class Client {
	private Connection connection;
	private String id;
	
	public Client(Connection connection, ArrayList<Message> messages) {
		this.connection = connection;
		connection.setMessagesRef(messages);
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
