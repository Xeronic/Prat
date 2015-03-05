package chat_client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class ClientGUI implements ActionListener {
	
	private UsersPanel usersPanel = new UsersPanel();
	private InputPanel inputPanel = new InputPanel(this);
	private JTextPane tpChatArea = new JTextPane();
	private JScrollPane scroll;
	private ClientController controller;
	
	public ClientGUI(ClientController controller) {
		this.controller = controller;
		JFrame window = new JFrame("Prat - klient");
		window.setLayout(new BorderLayout());
		
		tpChatArea.setEditable(false);
		scroll = new JScrollPane(tpChatArea);
		scroll.setPreferredSize(new Dimension(500,500));
		window.add(scroll, BorderLayout.CENTER);
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
	
	public void appendText(String text) {
		tpChatArea.setForeground(Color.ORANGE);
		String temp = tpChatArea.getText();
		tpChatArea.setText(temp + "\n" + text);
	}

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