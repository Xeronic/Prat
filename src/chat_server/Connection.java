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

	public Connection(Socket socket) {
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {}
	}
<<<<<<< HEAD

	public String getID() {
		try {
			return ois.readUTF();
		} catch (IOException ex) {
			return null; 
		}
//		return id;
=======
	
	public ObjectOutputStream getOutputStream() {
		return oos;
	}
	
	public ObjectInputStream getInputStream() {
		return ois;
>>>>>>> jerry
	}
}