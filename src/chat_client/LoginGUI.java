package chat_client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class LoginGUI {

	private String userName, ipAddress;
	private JTextField tfUserName, tfIpAddress;
	private JButton btnEnter;
	private JLabel lblText;
	private ClientController controller;
	private JFrame frame;
	private Font btnFont, borderFont;
	private JLabel inputIpAddress, inputUserName;
	
	public LoginGUI(ClientController controller) {
		this.controller = controller;
		frame = new JFrame("Login window");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(loginPanel());
		frame.pack();	
		frame.setVisible(true);
		frame.setLocation(400, 200);
	}
	
	public JPanel loginPanel(){
		JPanel panel = new JPanel();
		btnFont = new Font("Sanserif", Font.ROMAN_BASELINE, 22);
		borderFont = new Font("Sanserif", Font.ROMAN_BASELINE, 28);
		panel.setPreferredSize(new Dimension(400,200));
		panel.setLayout(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		btnEnter = new JButton("Enter");
		btnEnter.setFont(btnFont);
	
		panel.add(btnEnter, BorderLayout.SOUTH);
		panel.add(centrePanel(), BorderLayout.CENTER);
		panel.setBackground(Color.ORANGE);
	
		btnEnter.addActionListener(new ButtonListener());
		
		return panel;
	}
	
	public JPanel centrePanel(){
		JPanel centrePanel = new JPanel();
		JPanel userNamePanel = new JPanel(new BorderLayout());
		JPanel ipAddressPanel = new JPanel(new BorderLayout());
		centrePanel.setLayout(new GridLayout(2,1));
		centrePanel.setBackground(Color.ORANGE);
		inputIpAddress = new JLabel("Ip address:");
		inputIpAddress.setBackground(Color.ORANGE);
		inputIpAddress.setOpaque(true);
		inputUserName = new JLabel("UserName:");
		inputUserName.setBackground(Color.ORANGE);
		inputUserName.setOpaque(true);
		tfUserName = new JTextField();
		tfUserName.setSize(new Dimension(1009,2000));
		tfIpAddress = new JTextField("127.0.0.1");
		centrePanel.setBorder(BorderFactory.createTitledBorder(null, "Login", TitledBorder.DEFAULT_JUSTIFICATION, 
				TitledBorder.DEFAULT_POSITION, borderFont, Color.BLACK));
		userNamePanel.add(inputUserName,BorderLayout.WEST);
		userNamePanel.add(tfUserName, BorderLayout.CENTER);
		ipAddressPanel.add(inputIpAddress, BorderLayout.WEST);
		ipAddressPanel.add(tfIpAddress, BorderLayout.CENTER);
		centrePanel.add(userNamePanel);
		centrePanel.add(ipAddressPanel);
		
		return centrePanel;
	}
	
	public String getUserName(){
		userName = tfUserName.getText();
		return userName;
	}
	
	public String getIpAddress(){
		ipAddress = tfIpAddress.getText();
		return ipAddress;
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
