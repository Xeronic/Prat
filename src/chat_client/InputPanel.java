package chat_client;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InputPanel extends JPanel {

	private JTextField tfInput;
	private JButton btnAddImage, btnSend;
	
	public InputPanel(ActionListener al) {
		
		tfInput = new JTextField();
		btnAddImage = new JButton("Attach file...");
		btnSend = new JButton("Send message");
		btnSend.addActionListener(al);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(btnAddImage);
		buttonPanel.add(btnSend);
		
		setLayout(new BorderLayout());
		add(tfInput, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.EAST);
	}
}
