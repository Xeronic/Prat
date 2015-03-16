package chat_client;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ClientGUI implements iClientGUI {
	
	private UsersPanel usersPanel;
	private InputPanel inputPanel;
	private JTextPane tpChatArea;
	private JScrollPane scroll;
	private Dimension dim;
	private StyledDocument document;
	private Controller controller;
	private LoginGUI loginGUI;
	private JFrame window;

	public ClientGUI() {
		this.controller = new Controller(this);
		controller.start();
		usersPanel = new UsersPanel();
		inputPanel = new InputPanel(controller);
		
		window = new JFrame("Not connected | Prat");
		window.setLayout(new BorderLayout());
		
		tpChatArea = new JTextPane();
		tpChatArea.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		scroll = new JScrollPane(tpChatArea);
		document = tpChatArea.getStyledDocument();

		window.add(scroll, BorderLayout.CENTER);
		window.add(usersPanel, BorderLayout.EAST);
		window.add(inputPanel, BorderLayout.SOUTH);
		
		window.pack();

		window.setSize(824, 568);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width/2-window.getSize().width/2,
				dim.height/2-window.getSize().height/2); //place window center of screen.
		
		window.addWindowListener(new WindowAdapter(){ 
			  public void windowOpened( WindowEvent e){ 
				  inputPanel.getInputField().requestFocus();
			  } 
		});
	}
	
	public void updateList(String[] users) {
		usersPanel.updateList(users);
	}

	public void showLogin() {
		loginGUI = new LoginGUI(this.controller);
	}

	public void showChatWindow() {
		loginGUI.hide();
		window.setVisible(true);
	}
	
	public void appendText(String text) {
		try {
			document.insertString(document.getLength(), text + "\n", null);
		} catch (BadLocationException exe) {
			System.err.println(exe.getMessage());
		}
		tpChatArea.setCaretPosition(tpChatArea.getDocument().getLength());
	}

	public void appendTextAndImage(String text, Icon image) {
		try {
			document.insertString(document.getLength(), text + "\n", null);

			Style style = document.addStyle("StyleName", null);
			StyleConstants.setIcon(style, image);
			document.insertString(document.getLength(), "\n", style);

		} catch (BadLocationException exe) {
			System.err.println(exe.getMessage());
		}
		tpChatArea.setCaretPosition(tpChatArea.getDocument().getLength());
	}
	
	public String[] getSelectedUsers() {
		return usersPanel.getSelectedUsers();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new ClientGUI();
			}
		});
	}
}