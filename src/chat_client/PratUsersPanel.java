package chat_client;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

public class PratUsersPanel extends JPanel {
	
	private JList<String> list;
	private DefaultListModel<String> listModel;
	
	public PratUsersPanel() {
		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		
		this.setPreferredSize(new Dimension(200, 500));
		add(list);
	}
	
	public void updateList(String[] users) {
		for (String user : users) {
			listModel.addElement(user);
		}
	}

}
