package chat_client;

public class ClientController {
	
	private ClientGUI client;
	private String username;
	
	public ClientController() {
		new LoginGUI(this);
		
	}
	
	public void login(String username) {
		this.username = username;
		client = new ClientGUI();
		client.appendText("Trying to login..");
	}

}
