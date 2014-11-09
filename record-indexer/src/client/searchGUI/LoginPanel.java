package client.searchGUI;

import static client.searchGUI.SearchGUI.*;

import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel
{
	public JTextField hostField;
	public JTextField portField;
	public JTextField usernameField;
	public JPasswordField passwordField;
	public JButton loginButton;
	public boolean valid;
	
	public LoginPanel() 
	{
		super();
		Border edge = BorderFactory.createLoweredBevelBorder();
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setBorder(edge);
		add(Box.createRigidArea(DOUBLE_HSPACE));
		this.setSize(new Dimension(800,10));
		add(new JLabel("HOST:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		hostField = new JTextField(15);
		hostField.setText("localhost");
		hostField.setMinimumSize(hostField.getPreferredSize());
		add(hostField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		add(new JLabel("PORT:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		portField = new JTextField(5);
		portField.setText("39640");
		portField.setMinimumSize(portField.getPreferredSize());
		add(portField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		add(new JLabel("Username:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		usernameField = new JTextField(15);
		usernameField.setMinimumSize(usernameField.getPreferredSize());
		add(usernameField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		add(new JLabel("Password:"));
		add(Box.createRigidArea(SINGLE_HSPACE));
		passwordField = new JPasswordField(15);
		passwordField.setMinimumSize(passwordField.getPreferredSize());
		add(passwordField);
		add(Box.createRigidArea(TRIPLE_HSPACE));
		
		loginButton = new JButton("Login");
		add(loginButton);	
		add(Box.createRigidArea(DOUBLE_HSPACE));
		
		setMaximumSize(getPreferredSize());
//		setBorder(edge);
		
	}
	
	public String getHost()
	{
		return hostField.getText();
	}
	public void setHost(String host)
	{
		hostField.setText(host);
	}
	
	public String getPort()
	{
		return portField.getText();
	}
	public void setPort(String port)
	{
		portField.setText(port);
	}
	
	public String getUsername()
	{
		return usernameField.getText();
	}
	
	public String getPassword()
	{
		char[] passwordChars = passwordField.getPassword();  
		String passwordString = new String(passwordChars); 
		return passwordString;
	}

	public LoginPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public LoginPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public LoginPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

}
