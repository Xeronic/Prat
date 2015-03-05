package chat_client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String[] recipients = controller.getSelectedUsers();
			controller.send(new chat_server.Message(tfInput.getText(), recipients));
		}
	}
}
