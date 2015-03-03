package chat_server;

import java.net.Socket;
import java.util.ArrayList;

public class PratServerController {
	private ArrayList<Client> clients = null;

	public PratServerController(){
		
	}
	public void addClient(Socket s, String id){
		
	}
	public void removeClient(Client c){
		
	}
	public Client findUser(String id){
		
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
