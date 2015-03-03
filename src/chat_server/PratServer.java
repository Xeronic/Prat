package chat_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PratServer extends Thread{
	private PratServerController controller;
	private ServerSocket serverSocket;
	
	public PratServer(int port){
		try {
			serverSocket = new ServerSocket(port);
			this.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		System.out.println("Server running");
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				controller.addClient(new ClientHandler(socket, id));
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}
	
	private class ClientHandler extends Thread{
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		private String id;
		
		public ClientHandler(Socket socket, String id) {
			this.id = id;
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());
				System.out.println("Client " + id + " connected");
			} catch (IOException e) {
			}
		}
	}
}
