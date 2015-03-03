package chat_client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class PratClientGUI implements ActionListener {
	
	private PratUsersPanel usersPanel = new PratUsersPanel();
	private PratInputPanel inputPanel = new PratInputPanel(this);
	
	public PratClientGUI() {
		JFrame window = new JFrame("Prat - klient");
		window.setLayout(new BorderLayout());
		
		window.add(new JTextArea(), BorderLayout.CENTER);
		
		window.add(usersPanel, BorderLayout.EAST);
		window.add(inputPanel, BorderLayout.SOUTH);
		
		String[] users = {"Jerry", "Mårten", "Anton", "Jonas"};
		usersPanel.updateList(users);
		
		window.pack();
		window.setVisible(true);
		window.setSize(1024, 768);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	
	public void updateList(String[] users) {
		usersPanel.updateList(users);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String[] users = usersPanel.getSelectedUsers();
		
		if (users == null) {
			System.out.println("HEJ");
			// boolean ska sättas till true i meddelande
		} else {
			// Sätt recipeints till users
		}
		JOptionPane.showMessageDialog(null, usersPanel.getSelectedUsers());
	}
	
	

}
