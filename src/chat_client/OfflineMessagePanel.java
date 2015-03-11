package chat_client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.filechooser.FileNameExtensionFilter;

public class OfflineMessagePanel extends JPanel {

	private JPanel panel;
	private Dimension dim;
	private JButton btnSend, btnAttach, btnCancel;
	private JFrame frame;
	private JTextPane tpMessage;
	private ImageIcon image;
	private JScrollPane scroll;
	private ClientController controller;
	private JTextField tfToUser;
	private ImageIcon icon;

	public OfflineMessagePanel(ClientController controller) {
		this.controller = controller;
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(thePanel());
		frame.pack();
		frame.setVisible(true);
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - panel.getSize().width / 2, dim.height
				/ 2 - panel.getSize().height / 2);
	}

	public JPanel thePanel() {
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(400, 300));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
		panel.setBackground(Color.ORANGE);

		panel.add(topPanel(), BorderLayout.NORTH);
		panel.add(centrePanel(), BorderLayout.CENTER);
		panel.add(bottomPanel(), BorderLayout.SOUTH);

		btnSend.addActionListener(e -> sendEvent());
		btnAttach.addActionListener(e -> attachEvent());
		btnCancel.addActionListener(e -> cancelEvent());
		tpMessage.addKeyListener(new EnterPressListener());

		return panel;
	}

	public JPanel topPanel() {

		JPanel topPanel = new JPanel();
		JLabel lblSendTo = new JLabel("Send to:");
		tfToUser = new JTextField();

		lblSendTo.setBackground(Color.ORANGE);
		lblSendTo.setOpaque(true);
		topPanel.setLayout(new BorderLayout());
		topPanel.add(lblSendTo, BorderLayout.WEST);
		topPanel.add(tfToUser, BorderLayout.CENTER);

		return topPanel;
	}

	public JPanel centrePanel() {

		JPanel centrePanel = new JPanel();
		JLabel lblMessage = new JLabel("Message:");
		tpMessage = new JTextPane();
		scroll = new JScrollPane(tpMessage);
		centrePanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 30, 10));
		lblMessage.setBorder(BorderFactory.createEmptyBorder(0, 0, 160, 10));
		tpMessage.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 0));
		centrePanel.setBackground(Color.ORANGE);

		centrePanel.setLayout(new BorderLayout());
		centrePanel.add(lblMessage, BorderLayout.WEST);
		centrePanel.add(scroll, BorderLayout.CENTER);

		return centrePanel;
	}

	public JPanel bottomPanel() {

		JPanel bottomPanel = new JPanel();
		btnSend = new JButton("Send");
		btnAttach = new JButton("Attach file");
		btnCancel = new JButton("Cancel");

		bottomPanel.setBackground(Color.ORANGE);
		bottomPanel.setLayout(new GridLayout(1, 3, 5, 5));
		bottomPanel.add(btnSend);
		bottomPanel.add(btnAttach);
		bottomPanel.add(btnCancel);

		return bottomPanel;
	}

	private ImageIcon addImage() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("JPEG, PNG & GIF Images",
				"jpg", "gif", "jpeg", "png"));
		fc.showDialog(null, "Välj en bildfil");
		if (fc.getSelectedFile() != null) {
			image = new ImageIcon(fc.getSelectedFile().getAbsolutePath());
		}
		return image;
	}
	
	public void sendEvent(){
		chat_server.Message message = new chat_server.Message();
		if (this.icon != null) {
			message.setImage(this.icon);
			this.icon = null;
		}
		message.setText(tpMessage.getText());
		if(tfToUser.getText() != null){
			String[] person = new String[1];
			person[0] = tfToUser.getText();
			message.setRecipients(person);
			controller.send(message);
		}
		frame.setVisible(false);
	}
	
	public void attachEvent(){
		icon = addImage();
		if (icon != null) {
			tpMessage.insertIcon(icon);
		}
	}
	
	public void cancelEvent(){
		frame.setVisible(false);
	}

	private class EnterPressListener implements KeyListener {
		public void keyTyped(KeyEvent e) {
		}

		public void keyReleased(KeyEvent e) {
		}

		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_ENTER) {
				sendEvent();
			}
		}
	}
}
