package chat_server;

import java.io.*;
import java.net.Socket;
/**
 * Class that represents the connection from the client to the server.
 * @author Anton, Jerry, Mårten, Jonas 
 */
public class Connection {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	
	/**
	 * The constructor takes a socket, and uses it to create an Input and an Output-steam. 
	 * @param socket A socket
	 */

	public Connection(Socket socket) {
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {}
	}
	/**
	 * Returns the ID of the client connected.
	 * @return the ID of the client
	 */
	public String getID() {
		try {
			return ois.readUTF();
		} catch (IOException ex) {
			return null;
		}
	}
	
	/**
	 * Returns the OutputStream
	 * @return oos the OutputStream
	 */
	public ObjectOutputStream getOutputStream() {
		return oos;
	}
	
	/**
	 * Returns the InputStream
	 * @return ois the InputStream
	 */
	public ObjectInputStream getInputStream() {
		return ois;
	}
}