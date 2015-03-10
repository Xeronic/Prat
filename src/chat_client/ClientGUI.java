package chat_client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class ClientGUI {
	
	private UsersPanel usersPanel;
	private InputPanel inputPanel;
	private JTextPane tpChatArea;
	private JScrollPane scroll;
	private Dimension dim;
	private ClientController controller;
	
	public ClientGUI(ClientController controller) {
		this.controller = controller;
		usersPanel = new UsersPanel(controller);
		inputPanel = new InputPanel(controller);
		
		JFrame window = new JFrame("Prat - klient");
		window.setLayout(new BorderLayout());
		
		tpChatArea = new JTextPane();
		tpChatArea.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		tpChatArea.setEditable(false);
		scroll = new JScrollPane(tpChatArea);
		
		window.add(scroll, BorderLayout.CENTER);
		window.add(usersPanel, BorderLayout.EAST);
		window.add(inputPanel, BorderLayout.SOUTH);
		
		window.pack();
		window.setVisible(true);
		window.setSize(824, 568);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);
		
		window.addWindowListener(new WindowAdapter(){ 
			  public void windowOpened( WindowEvent e){ 
			    inputPanel.getInputField().requestFocus();
			  } 
			}); 
	}
	
	public void updateList(String[] users) {
		usersPanel.updateList(users);
	}
	
	public void appendText(String text) {
		String temp = tpChatArea.getText();
		tpChatArea.setText(temp + "\n" + text);
	}
	
	public String[] getSelectedUsers() {
		return usersPanel.getSelectedUsers();
	}
}