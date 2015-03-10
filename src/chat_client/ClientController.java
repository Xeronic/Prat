package chat_client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;

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

	public String getUserName() {
		return username;
	}

	public String[] getSelectedUsers() {
		return client.getSelectedUsers();
	}

	public void send(chat_server.Message m) {
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			System.out.println("Could not send file: " + e.getMessage());
		}
	}

	public void appendText(chat_server.Message m) {

		m.setText((m.getSender() != null) ? (new SimpleDateFormat("HH:mm:ss")
				.format(m.getRecievedAtServer()) + " " + m.getSender() + "> " + m
				.getText()) : m.getText());

		if (m.getImage() == null) {
			client.appendText(m.getText());
		} else {
			client.appendTextAndImage(m.getText(), m.getImage());
		}
	}

	private class RecieveMessages extends Thread {
		public void run() {
			while (true) {
				try {
					Object obj = ois.readObject();
					if (obj instanceof String[]) {
						client.updateList((String[]) obj);
					} else if (obj instanceof chat_server.Message) {
						chat_server.Message m = (chat_server.Message) obj;
						appendText(m);
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
