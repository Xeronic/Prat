package chat_client;

import javax.swing.*;

public class StartClient {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ClientGUI client = new ClientGUI();
				Controller controller = new Controller(client);

			}
		});
	}

}
