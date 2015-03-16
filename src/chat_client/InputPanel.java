package chat_client;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import chat_server.Message;

/**
 * This class represents the inputGUI "Send message, attach file, Offline User and
 * a textField for writing messages.
 * @author Mårten, Jonas, Jerry, Anton
 */
public class InputPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tfInput;
	private JButton btnAddImage, btnSend, btnOffline;
	private ClientController controller;
	private ImageIcon image;
	
	/**
	 * Constructor that create buttons and textfield. Also Creates a panel to place it on
	 * and its layout. 
	 * @param controller - ClientController
	 */
	public InputPanel(ClientController controller) {
		this.controller = controller;

		tfInput = new JTextField();
		tfInput.addKeyListener(new EnterPressListener());
		btnAddImage = new JButton("Attach file");
		btnAddImage.addActionListener(e -> addImage());
		btnSend = new JButton("Send message");
		btnSend.addActionListener((e) -> actionEvent());
		btnOffline = new JButton("Offline User");
		btnOffline.addActionListener((e) -> new OfflineMessagePanel(controller));

		JPanel buttonPanel = new JPanel();
		setLayout(new BorderLayout());
		buttonPanel.add(btnAddImage);
		buttonPanel.add(btnSend);
		buttonPanel.add(btnOffline);
		add(tfInput, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.EAST);
	}
	
	/**
	 * This method uses JFileChooser to let the user choose a image
	 * thats approved. Then scale it to fit the JTextPane that displays it.
	 */
	private void addImage() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("JPEG, PNG & GIF Images",
				"jpg", "gif", "jpeg", "png"));
		fc.showDialog(null, "Välj en bildfil");
		if (fc.getSelectedFile() != null) {
			this.image = new ImageIcon(fc.getSelectedFile().getAbsolutePath());
			int width = image.getIconWidth();
			int height = image.getIconHeight();
			while(width > 500){
				width *= 0.9;
				height *= 0.9;
			}
			Image img = image.getImage();
			Image newimg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
			this.image = new ImageIcon(newimg);
		}
	}
	
	/**
	 * Method for retrieving textField.
	 * @return - textField
	 */
	public JTextField getInputField() {
		return this.tfInput;
	}
	
	/**
	 * This method checks if there is a image in the message or if its only text,
	 * also checks for who to send the message to.
	 */
	public void actionEvent() {
		if (tfInput.getText().length() > 0) {
			Message message = new Message();
			if (this.image != null) {
				message.setImage(this.image);
				this.image = null;
			}
			message.setText(tfInput.getText());
			if (controller.getSelectedUsers() != null) {
				message.setRecipients(controller.getSelectedUsers());
				controller.send(message);
			} else {
				message.setAll(true);
				controller.send(message);
			}
			tfInput.setText("");
		} 
		else if (this.image != null) {
			Message message = new Message();
			message.setImage(this.image);
			message.setText("");
			if (controller.getSelectedUsers() != null) {
				message.setRecipients(controller.getSelectedUsers());
				controller.send(message);
			} else {
				message.setAll(true);
				controller.send(message);
			}
			this.image = null;
		}
	}
	
	/**
	 * InnerClass that listens for Keyboard buttons to be pressed.
	 * @author Mårten, Jerry, Anton, Jonas
	 */
	private class EnterPressListener implements KeyListener {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
		
		/**
		 * If enter button is pressed on keyboard the actionEvent method
		 * is called.
		 */
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_ENTER) {
				actionEvent();
			}
		}
	}
}