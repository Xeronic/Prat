package chat_client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPanel extends JPanel implements ActionListener {

	private JTextField tfInput;
	private JButton btnAddImage, btnSend;
	private ClientController controller;
	
	public InputPanel(ClientController controller) {
		this.controller = controller;
		tfInput = new JTextField();
		btnAddImage = new JButton("Attach file...");
		btnSend = new JButton("Send message");
		btnSend.addActionListener(this);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(btnAddImage);
		buttonPanel.add(btnSend);
		
		setLayout(new BorderLayout());
		add(tfInput, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.EAST);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSend) {
			if (controller.getSelectedUsers() == null) {
				controller.sendMessage(new chat_server.Message(tfInput.getText()));
			}
		}
	}
}
