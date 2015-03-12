package chat_client;

import javax.swing.*;

/**
 * This class starts the client. 
 * @author Mårten, Jonas, Jerry, Anton
 *
 */
public class StartClient {
	
	/**
	 * Using the invokeLater method for threads that updates GUI. 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ClientController();
			}
		});
	}

}
