package chat_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PratServer extends Thread {
	private ServerSocket serverSocket;
	private String id = null;
	private ArrayList<Message> messages;
	private ArrayList<Client> clients;
	private Client tempclient;

	public PratServer(int port) {
		try {
			serverSocket = new ServerSocket(port);
			clients = new ArrayList<Client>();
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
				Client client = new Client(socket, messages);
				id = client.getUsername();
				addClient(client);
				System.out.println("Client " + id + " connected");
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}

	public void addClient(Client client) {
		clients.add(client);
		sendMessage(new Message(id), clients);
		System.out.println("Client added to client-list");
	}

	public void removeClient(String id) {
		boolean removed = false;
		for (Client client : clients) {
			if (client.getUsername() == id) {
				clients.remove(client);
				removed = true;
			} else {
			}
		}
		if (removed) {
			System.out.println("Client " + id + " removed from connections");
		} else {
			System.out.println("No client with the name " + id + " was found");
		}
	}

	public void sendMessage(Message m, ArrayList<Client> recipients) {
		for (Client recipient : recipients) {
			recipient.send(m);
		}
	}

	public void extractRecipients(Message m) {
		ArrayList<String> templist = m.getRecipients();
		if (m.all == true) {
			sendMessage(m, clients);
		} else {
			ArrayList<Client> tempClient = null;
			for (Client client : clients) {
				for (String user : templist) {
					if (user == client.getUsername()) {
						tempClient.add(client);
					}
				}
			}
			sendMessage(m, tempClient);
		}
	}

	public Client findUser(String id) {
		for (Client client : clients) {
			if (client.getUsername() == id) {
				return client;
			}
		}
		System.out.println("No client with the name " + id + " was found");
		return null;
	}
}
