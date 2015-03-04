package chat_client;

import java.io.*;
import java.net.*;

public class ClientController {
	
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private ClientGUI client;
	private String username;
	
	public ClientController() {
		new LoginGUI(this);
		
	}
	
	public void login(String username) {
		this.username = username;
		client = new ClientGUI();
		client.appendText("Trying to login..");
		connect();
	}
	
	public void connect() {
		try {
			socket = new Socket("127.0.0.1", 3520);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			oos.writeUTF(username);
			oos.flush();
		} catch (IOException ex) {
			client.appendText("Could not connect to server");
		}
		client = new ClientGUI(this);
		client.appendText("Trying to login...");
}
