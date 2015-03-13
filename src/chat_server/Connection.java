package chat_server;

import java.io.*;
import java.net.Socket;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Connection {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public Connection(Socket socket) {
		try {
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
}