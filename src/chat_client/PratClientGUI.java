package chat_client;

import java.awt.BorderLayout;

import javax.swing.*;

public class PratClientGUI {
	
	PratUsersPanel usersPanel = new PratUsersPanel();
	
	public PratClientGUI() {
		JFrame window = new JFrame("Prat - klient");
		window.setLayout(new BorderLayout());
		
		window.add(new JTextArea(), BorderLayout.CENTER);
		window.add(usersPanel, BorderLayout.EAST);
		
		String[] users = {"Jerry", "Mårten", "Anton", "Jonas"};
		usersPanel.updateList(users);
		
		window.pack();
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void updateList(String[] users) {
		usersPanel.updateList(users);
	}

}
