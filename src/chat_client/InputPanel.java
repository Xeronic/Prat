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

public class InputPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tfInput;
	private JButton btnAddImage, btnSend, btnOffline;
	private Controller controller;
	private ImageIcon image;

	public InputPanel(Controller controller) {
		this.controller = controller;

		tfInput = new JTextField();
		tfInput.addKeyListener(new EnterPressListener());
		btnAddImage = new JButton("Attach file");
		btnAddImage.addActionListener(e -> addImage());
		btnSend = new JButton("Send message");
		btnSend.addActionListener((e) -> actionEvent());
		btnOffline = new JButton("Offline User");
		btnOffline
				.addActionListener((e) -> new OfflineMessagePanel(controller));

		JPanel buttonPanel = new JPanel();
		setLayout(new BorderLayout());
		buttonPanel.add(btnAddImage);
		buttonPanel.add(btnSend);
		buttonPanel.add(btnOffline);
		add(tfInput, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.EAST);
	}

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

	public JTextField getInputField() {
		return this.tfInput;
	}

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

	private class EnterPressListener implements KeyListener {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}

		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_ENTER) {
				actionEvent();
			}
		}
	}
}