package chat_client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPanel extends JPanel {

	private JTextField tfInput;
	private JButton btnAddImage, btnSend;
	private ClientController controller;
	
	public InputPanel(ClientController controller) {
		this.controller = controller;
		
		tfInput = new JTextField();
		tfInput.addKeyListener(new EnterPressListener());
		btnAddImage = new JButton("Attach file...");
		btnSend = new JButton("Send message");
		btnSend.addActionListener(new ButtonListener());
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(btnAddImage);
		buttonPanel.add(btnSend);
		
		setLayout(new BorderLayout());
		add(tfInput, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.EAST);
	}
	
	public JTextField getInputField() {
		return this.tfInput;
	}
	
	public void actionEvent() {
		if (tfInput.getText().length() > 0) {
			String[] recipients = controller.getSelectedUsers();
			controller.send(new chat_server.Message(tfInput.getText(), recipients));
			tfInput.setText("");
		}
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			actionEvent();
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
