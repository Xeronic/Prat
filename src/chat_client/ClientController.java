package chat_client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientController {
	
	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private ClientGUI client;
	private String username;
	private LoginGUI loginGUI;
	
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
			socket = new Socket(loginGUI.getIpAddress(), 3520);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			oos.writeUTF(username);
			oos.flush();
			new RecieveMessages().start();
		} catch (IOException ex) {
			client.appendText("Could not connect to server");
		}
	}
	
	private class RecieveMessages extends Thread {
		public void run() {
			while (true) {
				System.out.println("hej");
				try {
					Object obj = ois.readObject();
					if (obj instanceof String[]) {
						client.updateList((String[]) obj);
					}
				} catch (IOException ex2) {
					ex2.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	}
}
