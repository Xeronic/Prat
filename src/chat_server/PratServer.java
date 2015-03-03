package chat_server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PratServer extends Thread{
	private PratServerController controller;
	private ServerSocket serverSocket;
	private String id = null;
	
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
				ClientHandler tempClient = new ClientHandler(socket);
				id = tempClient.ois.readUTF();
				controller.addClient(tempClient, id);
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}
	
	public class ClientHandler extends Thread{
		private ObjectOutputStream oos;
		private ObjectInputStream ois;
		
		public ClientHandler(Socket socket) {
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
			}
		}
	}
}
