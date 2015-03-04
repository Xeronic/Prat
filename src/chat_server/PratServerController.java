package chat_server;

import java.util.ArrayList;

public class PratServerController {
	private ArrayList<Client> clients;
	private Client tempclient;

	public PratServerController() {
		tempclient = null;
		clients = new ArrayList<Client>();
	}

	public void addClient(Connection connection, String id) {
		tempclient = new Client();
		tempclient.setConnection(connection);
		tempclient.setId(id);
		clients.add(tempclient);
		sendMessage(new Message(id), clients);
		System.out.println("Client added to client-list");
	}

	public void removeClient(String id) {
		boolean removed = false;
		for (Client client : clients) {
			if (client.getId() == id) {
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

	public Client findUser(String id) {
		for (Client client : clients) {
			if (client.getId() == id) {
				return client;
			}
		}
		System.out.println("No client with the name " + id + " was found");
		return null;

	}

	public void sendMessage(Message m, ArrayList<Client> recipients) {
		for (Client recipient : recipients) {
			Connection connection = recipient.getConnection();
			connection.send(m);
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
					if (user == client.getId()) {
						tempClient.add(client);
					}
				}
			}
			sendMessage(m, tempClient);
		}
	}

}
