package chat_server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class PratServer extends Thread {
	private ServerSocket serverSocket;
	private String id = null;
	private ArrayList<Message> pendingMessages, messages;
	private ArrayList<Client> clients;
	private Client tempclient;

	public PratServer(int port) {
		pendingMessages = new ArrayList<Message>();
		messages = new ArrayList<Message>();
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
				id = client.waitForInitialMessage();
				addClient(client);
				sendUserlist(client, clients);
				System.out.println("Client " + id + " connected");
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}

	private void sendUserlist(Client client, ArrayList<Client> clients2) {
		String[] str = new String[clients.size()];
		for (int i = 0; i < clients.size(); i++) {
			str[i] = clients.get(i).getUsername();
		}
		for (Client user : clients) {
			try {
				user.getConnection().getOutputStream().writeObject(str);
			} catch (IOException e) {
				System.out.println("Could not send user list to "
						+ user.getUsername());
				e.printStackTrace();
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
			sendMessage(m, recipient);
		}
	}

	public void sendMessage(Message m, Client client) {
		try {
			client.send(m);

		} catch (SocketException ex) {
			pendingMessages.add(m);
			clients.remove(client);
		} catch (IOException ex) {
			pendingMessages.add(m);
			clients.remove(client);
		}
	}

	public void extractRecipients(Message m) {
		if (m.all == true) {
			sendMessage(m, clients);
		} else {
			for (String recipent : m.getRecipients()) {
				for (Client client : clients) {
					if (recipent.equals(client.getUsername())) {
						sendMessage(m, client);
					}
				}
			}
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
