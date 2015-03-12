package chat_client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * This class represent the GUI for the list of online users.
 * @author Mårten, Jonas, Jerry, Anton
 */
public class UsersPanel extends JPanel {
	
	private JList<String> list;
	private DefaultListModel<String> listModel;
	private JScrollPane scrollPane;
	
	/**
	 * The constructor for the class, here is the JList and ScrollBar created. 
	 * PreferedSize, borders, and colors are set. 
	 */
	public UsersPanel() {
		listModel = new DefaultListModel<String>();
		list = new JList<String>(listModel);
		scrollPane = new JScrollPane(list);
		
		list.setFont(new Font("Sans-serif", Font.PLAIN, 16));
		list.setPreferredSize(new Dimension(200, 768));
		scrollPane.setPreferredSize(new Dimension(225,768));
		Border border = BorderFactory.createMatteBorder(0,1,0,0, Color.GRAY);
		this.setBorder(border);
		this.setBackground(Color.WHITE);
		add(scrollPane, list);
		
		/*
		 * This code is for the user to be able to choose from the list in his/her own way.
		 * So it doesn't have to be in a specific order. 
		 */
		list.setSelectionModel(new DefaultListSelectionModel() {
		    public void setSelectionInterval(int index0, int index1) {
		        if(list.isSelectedIndex(index0)) {
		            list.removeSelectionInterval(index0, index1);
		        }
		        else {
		            list.addSelectionInterval(index0, index1);
		        }
		    }
		});
	}
	
	/**
	 * This method updates the list of users. Removes all the users then loops through
	 * the received stringArray to add the users that is in the received array. 
	 * @param users - users in a stringArray.
	 */
	public void updateList(String[] users) {
		listModel.clear();
		for (String user : users) {
			listModel.addElement(user);
		}
	}
	
	/**
	 * Returns the selected users. 
	 * @return - return a string array with users.
	 */
	public String[] getSelectedUsers() {
		ArrayList<String> users = new ArrayList<String>();
		
		if (list.isSelectionEmpty()) {
			return null;
		}
				
		for (int i = 0; i < listModel.getSize(); i++) {
			if (list.isSelectedIndex(i)) {
				users.add(listModel.get(i));
			}
		}
		return (users.size() > 0) ? users.toArray(new String[users.size()]) : null;
	}
}