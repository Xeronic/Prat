package chat_client;

import java.awt.*;

import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.filechooser.FileNameExtensionFilter;

import chat_server.Message;

/**
 * Panel that appears when "Offline User" button is selected.
 * @author Anton, Jerry, Jonas, Mårten
 *
 */
public class OfflineMessagePanel extends JPanel {

	private JPanel panel;
	private JButton btnSend, btnAttach, btnCancel;
	private JFrame frame;
	private JTextPane tpMessage;
	private ClientController controller;
	private JTextField tfToUser;
	private ImageIcon icon;
	private Color ORANGE = Color.ORANGE;

	/**
	 * Creates a frame that is placed in the middle of the screen.
	 * The frame display the placed panel(s).
	 * @param controller - ClientController
	 */
	public OfflineMessagePanel(ClientController controller) {
		this.controller = controller;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(thePanel());
		frame.pack();
		frame.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - panel.getSize().width / 2, dim.height
				/ 2 - panel.getSize().height / 2);	// set window to centre of screen.
	}

	/**
	 * This is a method that place out panels. The mainpanel i created in this method
	 * and the layout and preferedsize is set. Also border and color is set in this method.
	 * @return - returns the mainpanel to be added on the frame.
	 */
	public JPanel thePanel() {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(400, 300));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
		panel.setBackground(ORANGE);

		panel.add(northPanel(), BorderLayout.NORTH);
		panel.add(centrePanel(), BorderLayout.CENTER);
		panel.add(southPanel(), BorderLayout.SOUTH);

		btnSend.addActionListener(e -> sendEvent());
		btnAttach.addActionListener(e -> attachEvent());
		btnCancel.addActionListener(e -> cancelEvent());
		tpMessage.addKeyListener(new EnterPressListener());

		return panel;
	}

	/**
	 * Creates a panel and add one JLabel and one JTextField.
	 * @return - returns the panel that is to be placed in the north.
	 */
	public JPanel northPanel() {

		JPanel northPanel = new JPanel();
		JLabel lblSendTo = new JLabel("Send to:");
		tfToUser = new JTextField();

		lblSendTo.setBackground(ORANGE);
		lblSendTo.setOpaque(true);
		northPanel.setLayout(new BorderLayout());
		northPanel.add(lblSendTo, BorderLayout.WEST);
		northPanel.add(tfToUser, BorderLayout.CENTER);

		return northPanel;
	}

	/**
	 * Creates a panel and add one JLabel and a JTextPane.
	 * @return - returns the panel that is to be placed in the centre.
	 */
	public JPanel centrePanel() {

		JPanel centrePanel = new JPanel();
		JLabel lblMessage = new JLabel("Message:");
		tpMessage = new JTextPane();
		JScrollPane scroll = new JScrollPane(tpMessage);
		centrePanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
		lblMessage.setBorder(BorderFactory.createEmptyBorder(0, 0, 160, 10));
		tpMessage.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 0));
		centrePanel.setBackground(ORANGE);

		centrePanel.setLayout(new BorderLayout());
		centrePanel.add(lblMessage, BorderLayout.WEST);
		centrePanel.add(scroll, BorderLayout.CENTER);

		return centrePanel;
	}
	
	/**
	 * 
	 * @return - returns 
	 */
	public JPanel southPanel() {

		JPanel southPanel = new JPanel();
		btnSend = new JButton("Send");
		btnAttach = new JButton("Attach file");
		btnCancel = new JButton("Cancel");

		southPanel.setBackground(ORANGE);
		southPanel.setLayout(new GridLayout(1, 3, 5, 5));
		southPanel.add(btnSend);
		southPanel.add(btnAttach);
		southPanel.add(btnCancel);

		return southPanel;
	}

	private void addImage() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("JPEG, PNG & GIF Images",
				"jpg", "gif", "jpeg", "png"));
		fc.showDialog(null, "Välj en bildfil");
		if (fc.getSelectedFile() != null) {
			icon = new ImageIcon(fc.getSelectedFile().getAbsolutePath());
		}
	}
	
	public void sendEvent(){
		Message message = new Message();
		if (this.icon != null) {
			message.setImage(this.icon);
			this.icon = null;
		}
		message.setText(tpMessage.getText());
		if(tfToUser.getText() != null){
			String[] person = new String[1];
			person[0] = tfToUser.getText();
			message.setRecipients(person);
			message.setSender(controller.getUserName());
			controller.send(message);
		}
		frame.setVisible(false);
	}
	
	public void attachEvent(){
		addImage();
		if (icon != null) {
			tpMessage.insertIcon(icon);
		}
	}
	
	public void cancelEvent(){
		frame.setVisible(false);
	}

	private class EnterPressListener implements KeyListener {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}

		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_ENTER) {
				sendEvent();
			}
		}
	}
}
