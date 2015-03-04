package chat_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection extends Thread {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String id;

	public Connection(Socket socket) {
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			id = ois.readUTF();
		} catch (IOException e) {
		}
	}

	public String getID() {
		return id;
	}
	
	public void send(Message m){
		try {
			oos.writeObject(m);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}