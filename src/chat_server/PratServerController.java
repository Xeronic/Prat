package chat_server;

import java.util.ArrayList;

public class PratServerController {
	private ArrayList<Client> clients;
	private Client tempclient;

	public PratServerController(){
		tempclient = null;
		clients = null;
	}
	public void addClient(Connection connection, String id){
		Connection c1 = connection;
		tempclient.setConnection(c1);
		tempclient.setId(id);
		clients.add(tempclient);
	}
	public void removeClient(Client c){
		
	}
	public Client findUser(String id){
		return null;
	}
	public void sendMessage(Message m, ArrayList<Client> clients){
		
	}
	public void extractRecipients(Message m){
		if(m.all==true){
			sendMessage(m, clients);
		}else{
			
		}
	}
	
}
