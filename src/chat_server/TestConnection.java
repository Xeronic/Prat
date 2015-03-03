package chat_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.Icon;

public class TestConnection {
	private Socket socket;
	public TestConnection(String ip, int port){
		try {
			socket = new Socket(ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Connection().start();
	}
	
	public class Connection extends Thread {
		private Icon icon;
		private ObjectOutputStream oos;

		/**
		 * constructor that starts a new input stream using the previous created
		 * socket
		 */
		public Connection() {
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
			}
		}

		/**
		 * The client starts a thread that loops for ever and sets the icon to
		 * the in coming object then notifies the listeners (the viewer)
		 */
		public void run() {
			while (true) {
				try {
					oos.writeUTF("Jonas");
				} catch (Exception e) {
				}
			}
		}
	}
}
