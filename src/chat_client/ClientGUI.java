package chat_client;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * This class combine the panels to create one joint GUI. 
 * @author Jerry, Anton, Mårten, Jonas
 */
public class ClientGUI {
	
	private UsersPanel usersPanel;
	private InputPanel inputPanel;
	private JTextPane tpChatArea;
	private JScrollPane scroll;
	private Dimension dim;
	private ClientController controller;
	private StyledDocument document;
	
	/**
	 * Creates a window to place panels at. sets size, layout and border.
	 * Place the window in the middle of the screen. 
	 * @param controller - ClientController
	 */
	public ClientGUI(ClientController controller) {
		this.controller = controller;
		usersPanel = new UsersPanel();
		inputPanel = new InputPanel(controller);
		
		JFrame window = new JFrame(controller.getUserName() + " | Prat");
		window.setLayout(new BorderLayout());
		
		tpChatArea = new JTextPane();
		tpChatArea.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		scroll = new JScrollPane(tpChatArea);
		document = tpChatArea.getStyledDocument();


		window.add(scroll, BorderLayout.CENTER);
		window.add(usersPanel, BorderLayout.EAST);
		window.add(inputPanel, BorderLayout.SOUTH);
		
		window.pack();
		window.setVisible(true);
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
	
	/**
	 * Calls the updateList in usersPanel to update the list with
	 * online clients. 
	 * @param users -String array with users.
	 */
	public void updateList(String[] users) {
		usersPanel.updateList(users);
	}
	
	/**
	 * This method appends text to the textPane that displays conversations. 
	 * @param text - text to be appended.
	 */
	public void appendText(String text) {
		try {
			document.insertString(document.getLength(), text + "\n", null);
		} catch (BadLocationException exe) {
			System.err.println(exe.getMessage());
		}
		tpChatArea.setCaretPosition(tpChatArea.getDocument().getLength());
	}
	
	/**
	 * This method is for appending both text and Image (Or only image).
	 * @param text - text to be appended.
	 * @param image - image to be appended.
	 */
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
	
	/**
	 * This method uses the getSelectedUsers method in the usersPanel class.
	 * @return - returns a String array with the users that are selected.
	 */
	public String[] getSelectedUsers() {
		return usersPanel.getSelectedUsers();
	}
}