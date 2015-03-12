package chat_client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * This class represents the loginPanelGUI for the system.
 * @author Mårten, Anton, Jonas, Jerry
 *
 */
public class LoginGUI {

	private String userName, ipAddress;
	private JTextField tfUserName, tfIpAddress;
	private JButton btnEnter;
	private Controller controller;
	private JFrame frame;
	private Font btnFont, borderFont;
	private JLabel inputIpAddress, inputUserName;
	private Dimension dim;
	private Color ORANGE = Color.ORANGE;
	
	/**
	 * Creates a frame to place panels at. And set to display at center of the screen.
	 * @param controller - Controller
	 */
	public LoginGUI(Controller controller) {
		this.controller = controller;
		frame = new JFrame("Login window");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(loginPanel());
		frame.pack();	
		frame.setVisible(true);
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2-frame.getSize().width/2, 
				dim.height/2-frame.getSize().height/2); //Place window center of screen.
	}
	
	/**
	 * This method creates a panel and set size and layout. Creates border and font.
	 * @return JPanel
	 */
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
		panel.setBackground(ORANGE);
		
		tfUserName.addKeyListener(new EnterPress());
		tfIpAddress.addKeyListener(new EnterPress());
		btnEnter.addActionListener(new ButtonListener());
		
		return panel;
	}
	
	/**
	 * Creates a panel that is to be placed at center of a window. 
	 * @return JPanel
	 */
	public JPanel centrePanel(){
		JPanel centrePanel = new JPanel();
		JPanel userNamePanel = new JPanel(new BorderLayout());
		JPanel ipAddressPanel = new JPanel(new BorderLayout());
		centrePanel.setLayout(new GridLayout(2, 1));
		centrePanel.setBackground(ORANGE);
		inputIpAddress = new JLabel("Ip address:");
		inputIpAddress.setBackground(ORANGE);
		inputIpAddress.setOpaque(true);
		inputUserName = new JLabel("UserName:");
		inputUserName.setBackground(ORANGE);
		inputUserName.setOpaque(true);
		tfUserName = new JTextField();
		tfIpAddress = new JTextField("127.0.0.1");
		tfIpAddress.addFocusListener(new FocusTextListener());
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
	
	/**
	 * Method for retrieving userNames.
	 * @return userName
	 */
	public String getUserName(){
		userName = tfUserName.getText();
		return userName;
	}
	
	/**
	 * Method for retrieving ipAddresses.
	 * @return ipAddress
	 */
	public String getIpAddress(){
		ipAddress = tfIpAddress.getText();
		return ipAddress;
	}
	
	/**
	 * Checks if a userName is written if a name is written the login method in
	 * the controller class will be called. Else a JOptionPane will be displayed
	 * and ask the user for a userName.
	 */
	public void actionEvent(){
		if ((getUserName() != null) && getUserName().length() > 0) {
			controller.login(tfUserName.getText().toString());
			frame.setVisible(false);
		} else {
			JOptionPane.showMessageDialog(null, "You must enter a username!");
		}
	}
	
	/**
	 * InnerClass that listens if the button is clicked.
	 * @author Mårten, Jerry, Jonas, Anton
	 */
	private class ButtonListener implements ActionListener {
		
		/**
		 * If enter button is clicked, run the actionEvent method.
		 */
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnEnter){
				actionEvent();
			}
		}
	}
	
	/**
	 * InnerClass that listens for the keyboard button Enter is pressed.
	 * @author Mårten, Anton, Jonas, Jerry
	 */
	private class EnterPress implements KeyListener {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
		
		/**
		 * This method is performed if the enter button on the keyboard is pressed
		 * then the actionEvent method is called. 
		 */
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_ENTER){
				actionEvent();
			}	
		}
	}
	
	/**
	 * InnerClass that listen for a textField to be focused.
	 * @author Jonas, Anton, Jerry, Mårten
	 */
	private class FocusTextListener implements FocusListener {
		public void focusLost(FocusEvent e) {}
		
		/**
		 * If a textField get activated the textField calls selectAll method
		 * and if there is any text in the textField it will be marked.
		 */
		public void focusGained(FocusEvent e) {
			tfIpAddress.selectAll();			
		}
	}
}