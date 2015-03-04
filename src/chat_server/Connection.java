package chat_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Connection extends Thread {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String id;
	ArrayList<Message> messages;

	public Connection(Socket socket) {
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			id = ois.readUTF();
			this.start();
		} catch (IOException e) {
		}
	}

	public void setMessagesRef(ArrayList<Message> messages){
		this.messages = messages;
	}
	
	public String getID() {
		return id;
	}

	public void run() {
		while (true) {
			recieve();
		}
	}

	public void recieve() {
		try {
			Message m = (Message) ois.readObject();
			messages.add(m);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void send(Message m) {
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}