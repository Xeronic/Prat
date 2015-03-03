package chat_client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LoginGUI {

	private String userName;
	private JTextField tfUserName;
	private JButton btnEnter;
	private JLabel lblText;
	private Font font;
	private ClientController controller;
	private JFrame frame;
	private Font lblFont, btnFont, txtFont;
	
	public LoginGUI(ClientController controller) {
		this.controller = controller;
		frame = new JFrame("UserName");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(loginPanel());
		frame.pack();	
		frame.setVisible(true);
	}
	
	public JPanel loginPanel(){
		JPanel panel = new JPanel();
		lblFont = new Font("Monospaced", Font.BOLD, 22);
		btnFont = new Font("Sanserif", Font.ROMAN_BASELINE, 22);
		txtFont = new Font("Sanserif", Font.BOLD, 16);
		
		panel.setPreferredSize(new Dimension(400,200));
		panel.setLayout(new GridLayout(3,1,5,5));
		panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		lblText = new JLabel("Select username!", JLabel.CENTER);
		lblText.setFont(lblFont);
		tfUserName = new JTextField();
		tfUserName.setFont(txtFont);
		btnEnter = new JButton("Enter username");
		btnEnter.setFont(btnFont);
		
		panel.add(lblText);
		panel.add(tfUserName);
		panel.add(btnEnter);
	
		btnEnter.addActionListener(new ButtonListener());
		
		return panel;
	}
	
	public String getUserName(){
		userName = tfUserName.getText();
		return userName;
	}
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if ((getUserName() != null) && getUserName().length() > 0) {
				controller.login(tfUserName.getText().toString());
				frame.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(null, "You must enter a username!");
			}
		}
	}
}
