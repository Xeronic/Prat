package chat_client;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.image.BufferedImage;
 import java.awt.image.RenderedImage;
 import java.io.ByteArrayInputStream;
 import java.io.ByteArrayOutputStream;
 import java.io.File;
 import java.io.IOException;
 import java.util.Iterator;
  
 import javax.imageio.IIOImage;
 import javax.imageio.ImageIO;
 import javax.imageio.ImageWriteParam;
 import javax.imageio.ImageWriter;
 import javax.imageio.stream.FileImageOutputStream;
 import javax.imageio.stream.ImageOutputStream;
 import javax.swing.JFrame;

import chat_server.Message;

public class InputPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField tfInput;
	private JButton btnAddImage, btnSend, btnOffline;
	private ClientController controller;
	private ImageIcon image;

	public InputPanel(ClientController controller) {
		this.controller = controller;

		tfInput = new JTextField();
		tfInput.addKeyListener(new EnterPressListener());
		btnAddImage = new JButton("Attach file...");
		btnAddImage.addActionListener(e -> addImage());
		btnSend = new JButton("Send message");
		btnSend.addActionListener((e) -> actionEvent());
		btnOffline = new JButton("Offline User");
		btnOffline
				.addActionListener((e) -> new OfflineMessagePanel(controller));

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(btnAddImage);
		buttonPanel.add(btnSend);
		buttonPanel.add(btnOffline);
		setLayout(new BorderLayout());
		add(tfInput, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.EAST);
	}

	private void addImage() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("JPEG, PNG & GIF Images",
				"jpg", "gif", "jpeg", "png"));
		fc.showDialog(null, "Välj en bildfil");
		if (fc.getSelectedFile() != null) {
			String imageFile= fc.getSelectedFile().getAbsolutePath();		
			
			try {
				BufferedImage i = ImageIO.read(new File(imageFile));
			compressAndShow(i, 0.5f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			this.image = new ImageIcon(fc.getSelectedFile().getAbsolutePath());
			int width = image.getIconWidth();
			int height = image.getIconHeight();
			while(width > 500){
				width *= 0.9;
				height *= 0.9;
			}
			Image img = image.getImage();
			Image newimg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
			this.image = new ImageIcon(newimg);
		}
	}
	
	public static void compressAndShow(BufferedImage image, float quality) throws IOException
	       {
	       // Get a ImageWriter for jpeg format.
	       Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jpeg");
	       if (!writers.hasNext()) throw new IllegalStateException("No writers found");
	       ImageWriter writer = (ImageWriter) writers.next();
	       // Create the ImageWriteParam to compress the image.
	       ImageWriteParam param = writer.getDefaultWriteParam();
	       param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	       param.setCompressionQuality(quality);
	       // The output will be a ByteArrayOutputStream (in memory)
	       ByteArrayOutputStream bos = new ByteArrayOutputStream(32768);
	       ImageOutputStream ios = ImageIO.createImageOutputStream(bos);
	       writer.setOutput(ios);
	     writer.write(null, new IIOImage(image, null, null), param);
	      ios.flush(); // otherwise the buffer size will be zero!
	       // From the ByteArrayOutputStream create a RenderedImage.
	       ByteArrayInputStream in = new ByteArrayInputStream(bos.toByteArray());
	       RenderedImage out = ImageIO.read(in);
	       int size = bos.toByteArray().length;
	       image = ImageIO.read(in);
	       // Uncomment code below to save the compressed files.
	   //    File file = new File("compressed."+quality+".jpeg");
//	       FileImageOutputStream output = new FileImageOutputStream(imgae);
//	       writer.setOutput(output); writer.write(null, new IIOImage(image, null,null), param);
	  }

	public JTextField getInputField() {
		return this.tfInput;
	}

	public void actionEvent() {
		if (tfInput.getText().length() > 0) {
			Message message = new Message();
			if (this.image != null) {
				message.setImage(this.image);
				this.image = null;
			}
			message.setText(tfInput.getText());
			if (controller.getSelectedUsers() != null) {
				message.setRecipients(controller.getSelectedUsers());
				controller.send(message);
			} else {
				message.setAll(true);
				controller.send(message);
			}
			tfInput.setText("");
		} 
		else if (this.image != null) {
			Message message = new Message();
			message.setImage(this.image);
			message.setText("");
			if (controller.getSelectedUsers() != null) {
				message.setRecipients(controller.getSelectedUsers());
				controller.send(message);
			} else {
				message.setAll(true);
				controller.send(message);
			}
			this.image = null;
		}
	}

	private class EnterPressListener implements KeyListener {
		public void keyTyped(KeyEvent e) {
		}

		public void keyReleased(KeyEvent e) {
		}

		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_ENTER) {
				actionEvent();
			}
		}
	}
}
