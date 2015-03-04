package chat_server;

import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.Icon;

public class Message {
	private ArrayList<String> recipients;
	private String text;
	private Icon pic;
	public boolean all;
	
	public Message(String id) {
		this.text = "User " + id + " has connected at " + Calendar.HOUR_OF_DAY;
	}
}
