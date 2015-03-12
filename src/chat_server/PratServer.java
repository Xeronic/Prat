package chat_server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class PratServer extends Thread {
	private ServerSocket serverSocket;
	private String id = null;
	private ArrayList<Message> pendingMessages;
	private ArrayList<ClientListener> clients;
	private final static Logger LOGGER = Logger.getLogger("PratServerLogg");

	public PratServer(int port) {
		try {
			createLoggFile();
			pendingMessages = new ArrayList<Message>();
			clients = new ArrayList<ClientListener>();
			serverSocket = new ServerSocket(port);
			this.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createLoggFile() throws IOException {
		String filename = "logfile_"
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(new Date());
		File f = new File("./loggs/" + filename);
		if (!f.getParentFile().exists()) {
			f.getParentFile().mkdirs();
		}

		FileHandler fh = new FileHandler("./loggs/" + filename + ".txt");// Kom
																			// ihåg
																			// att
																			// ändra
																			// fildestination
		LOGGER.setUseParentHandlers(false);
		LOGGER.addHandler(fh);
		SimpleFormatter formatter = new SimpleFormatter();
		fh.setFormatter(formatter);
	}

	public void removeClient(ClientListener client) {
		clients.remove(client);
		sendMessage(new Message(client.getUsername() + " disconnected"),
				clients);
		LOGGER.info(client.getUsername() + " DISCONNECTED");
		sendUserlist();
		client = null;
	}

	public void run() {
		System.out.println("Server running");
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				ClientListener client = new ClientListener(socket, this);
				id = client.waitForInitialMessage();
				addClient(client);
			} catch (IOException e) {
				System.err.println(e);
			}
		}
	}

	private void checkPendingMessages(ClientListener client) {
		Message message_to_be_deleted = null;
		for (Message pending : pendingMessages) {
			if (pending.getRecipients() == null) {
				message_to_be_deleted = pending;
				break;
			}
			if (pending.getRecipients()[0].equals(client.getUsername())) {
				sendMessage(pending, client);
				message_to_be_deleted = pending;
				break;
			}
		}
		if (message_to_be_deleted != null) {
			pendingMessages.remove(message_to_be_deleted);
		}
	}

	public void sendUserlist() {
		String[] clientList = new String[clients.size()];
		for (int i = 0; i < clients.size(); i++) {
			clientList[i] = clients.get(i).getUsername();
		}

		for (ClientListener client : clients) {
			try {
				client.getConnection().getOutputStream()
						.writeObject(clientList);
			} catch (IOException e) {
				System.out.println("Could not send user list to "
						+ client.getUsername());
				e.printStackTrace();
			}
		}
	}

	public void addClient(ClientListener client) {
		ArrayList<ClientListener> temp = new ArrayList<ClientListener>();
		sendMessage(
				new Message(
						client.getUsername()
								+ " connected at "
								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
										.format(new Date())), clients);
		clients.add(client);
		sendUserlist();
		client.start();
		System.out.println("Client " + id + " connected");
		temp.add(client);
		sendMessage(new Message("Connected"), temp);
		checkPendingMessages(client);
	}

	public void sendMessage(Message m, ArrayList<ClientListener> recipients) {
		for (ClientListener recipient : recipients) {
			sendMessage(m, recipient);
		}
	}

	public void sendMessage(Message m, ClientListener client) {
		try {
			client.send(m);
			LOGGER.info(m.getSender() + " -> " + client.getUsername() + ": "
					+ m.toString());
		} catch (SocketException ex) {
			pendingMessages.add(m);
			clients.remove(client);
		} catch (IOException ex) {
			pendingMessages.add(m);
			clients.remove(client);
		} catch (NullPointerException ex) {
			System.err.println(m.getRecipients().toString());
			System.err.println(client.getUsername());
		}
	}

	public void extractRecipients(Message m) {
		boolean temp = false;
		if (m.all) {
			sendMessage(m, clients);
		} else {
			for (String recipient : m.getRecipients()) {
//				try {
					for (ClientListener client : clients) {
						if (recipient.equals(client.getUsername())) {
							sendMessage(m, client);
							temp = true;
						}
					}
//				} catch (Exception e) {
//					pendingMessages.add(m);
//				}
			}
			if(!temp)
				pendingMessages.add(m);
			sendMessage(m, findUser(m.getSender()));
		}
	}

	private ClientListener findUser(String sender) {
		for (ClientListener client : clients) {
			if (client.getUsername().equals(sender)) {
				return client;
			}
		}
		return null;
	}
}
