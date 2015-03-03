package chat_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class PratServer extends Thread {
	private PratServerController controller;
	private ServerSocket serverSocket;
	private String id = null;

	public PratServer(PratServerController controller, int port) {
		try {
			this.controller = controller;
			serverSocket = new ServerSocket(port);
			this.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		System.out.println("Server running");
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				Connection connection = new Connection(socket);
				id = connection.getID();
				controller.addClient(connection, id);
				System.out.println("Client " + id + " connected");
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}
}
