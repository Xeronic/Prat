package chat_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Connection {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	ArrayList<Message> messages;
	private Socket socket;

	public Connection(Socket socket) {
		try {
			this.socket = socket;
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {}
	}

	public String getID() {
		try {
			return ois.readUTF();
		} catch (IOException ex) {
			return null;
		}
	}
	
	public ObjectOutputStream getOutputStream() {
		return oos;
	}
	
	public ObjectInputStream getInputStream() {
		return ois;
	}

	public void socketClose() {
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}