package chat_client;

import javax.swing.*;

/**
 * Created by Xeronic on 15-03-12.
 */
public interface iClientGUI {
	public void appendText(String text);
	public void appendTextAndImage(String text, Icon image);
	public String[] getSelectedUsers();
	public void updateList(String[] users);
	public void showLogin();
}
