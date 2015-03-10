package chat_client;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class InputPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tfInput;
	private JButton btnAddImage, btnSend;
	private ClientController controller;
	private ImageIcon image;
	
	public InputPanel(ClientController controller) {
		this.controller = controller;
		
		tfInput = new JTextField();
		tfInput.addKeyListener(new EnterPressListener());
		btnAddImage = new JButton("Attach file...");
		btnAddImage.addActionListener(e -> addImage());
		btnSend = new JButton("Send message");
		btnSend.addActionListener((e) -> actionEvent());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(btnAddImage);
		buttonPanel.add(btnSend);
		setLayout(new BorderLayout());
		add(tfInput, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.EAST);
	}

	private void addImage() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("JPEG, PNG & GIF Images", "jpg", "gif", "jpeg", "png"));
		fc.showDialog(null, "Välj en bildfil");
		if (fc.getSelectedFile() != null) {
			this.image = new ImageIcon(fc.getSelectedFile().getAbsolutePath());
		}
	}

	public JTextField getInputField() {
		return this.tfInput;
	}
	
	public void actionEvent() {
		if (tfInput.getText().length() > 0) {
			chat_server.Message message = new chat_server.Message();
			if (this.image != null) {
				message.setImage(this.image);
				this.image = null;
			}
			message.setText(tfInput.getText());
			if(controller.getSelectedUsers() != null){
				message.setRecipients(controller.getSelectedUsers());
				controller.send(message);
			}else{
				message.setAll(true);
				controller.send(message);
			}
			tfInput.setText("");
		}
	}
	
	private class EnterPressListener implements KeyListener {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
		
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_ENTER){
				actionEvent();
			}	
		}
	}
}
