package chat_client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.Border;

public class UsersPanel extends JPanel {
	
	private JList<String> list;
	private DefaultListModel<String> listModel;
	private JScrollPane scrollPane;
	
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
		list.setSelectionModel(new DefaultListSelectionModel() 
		{
		    @Override
		    public void setSelectionInterval(int index0, int index1) 
		    {
		        if(list.isSelectedIndex(index0)) 
		        {
		            list.removeSelectionInterval(index0, index1);
		        }
		        else 
		        {
		            list.addSelectionInterval(index0, index1);
		        }
		    }
		});
	}
	
	public void updateList(String[] users) {
		listModel.clear();
		for (String user : users) {
			listModel.addElement(user);
		}
	}

	public String[] getSelectedUsers() {
		
		if (list.isSelectionEmpty()) {
			return null;
		}
		
		String[] users = new String[listModel.getSize()];
		
		for (int i = 0; i < listModel.getSize(); i++) {
			if (list.isSelectedIndex(i)) {
				users[i] = listModel.get(i);
			}
		}
		return users;
	}
}
