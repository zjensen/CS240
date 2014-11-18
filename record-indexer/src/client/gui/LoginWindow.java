package client.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import shared.model.User;
import client.gui.state.BatchState;

@SuppressWarnings("serial")
public class LoginWindow extends JFrame implements ActionListener
{
	
	private BatchState bs;
	
	private JButton loginButton;
	private JButton exitButton;
	
	private JTextField usernameField;
	private JPasswordField passwordField;
	
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	
	private GridBagLayout gridbag;
	
	private User user;
	
	private JButton okayButton;
	
	private JDialog welcome;
	
	public LoginWindow(BatchState bs)
	{
		super("Login to Indexer");
		user = null;
		gridbag = new GridBagLayout();
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.HORIZONTAL;
	    setLayout(gridbag);
		this.bs = bs;
		setSize(new Dimension(400,150));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		usernameLabel = new JLabel("Username: ");
		c.gridwidth = 1;
		c.ipadx = 0;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		add(usernameLabel,c);
		usernameField = new JTextField("test1");
		c.gridwidth = 4;
		c.gridx = 1;
		c.gridy = 0;
		add(usernameField,c);
		
		passwordLabel = new JLabel("Password: ");
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		add(passwordLabel, c);
		passwordField = new JPasswordField("test1");
		c.gridwidth = 4;
		c.gridx = 1;
		c.gridy = 1;
		add(passwordField, c);
		
		loginButton = new JButton("Login");
		loginButton.addActionListener(this);
		c.insets = new Insets(10,0,0,0);
		c.anchor = GridBagConstraints.SOUTH;
		c.gridwidth = 1;
		c.gridx = 1;
		c.gridy = 3;
		add(loginButton,c);
		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);
		c.gridx = 2;
		c.gridy = 3;
		add(exitButton,c);
	}
	public void actionPerformed(ActionEvent a)
	{
	    if ( a.getSource() == loginButton )
	    { 
	    	char[] passwordChars = passwordField.getPassword();  
			String passwordString = new String(passwordChars);
			this.user = bs.login(usernameField.getText(), passwordString);
			if(this.user != null)
			{
				loggedIn(user);
			}
			else
			{
				failedLogin();
			}
	    }
	    else if(a.getSource() == exitButton)
	    {
	    	System.exit(0);
	    }
	    else if(a.getSource() == okayButton)
	    {
	    	bs.viewIndexer();
	    }
	}
	
	public void loggedIn(User user)
	{
		welcome = new JDialog(this,"Welcome to Indexer");
		GridBagLayout g2 = new GridBagLayout();
	    GridBagConstraints c = new GridBagConstraints();
	    welcome.setLayout(g2);
	    
		welcome.setResizable(false);
		welcome.setModal(true);
		welcome.setSize(300, 100);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		welcome.setLocation(dim.width/2-welcome.getSize().width/2, dim.height/2-welcome.getSize().height/2);
		welcome.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JLabel l1 = new JLabel("Welcome, " + user.getFirstName() + " " + user.getLastName() + ".");
		c.gridwidth = 1;
		c.ipadx = 1;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		welcome.add(l1,c);
		
		JLabel l2 = new JLabel("You have indexed " + user.getIndexedRecords() + " records.");
		c.gridy = 1;
		welcome.add(l2,c);
		
		okayButton = new JButton("Okay");
		okayButton.addActionListener(this);
		c.ipady = 2;
		c.gridy = 2;
		welcome.add(okayButton,c);
		
		welcome.addWindowListener(new WindowAdapter() 
		{
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	bs.viewIndexer();
		    }
		});
		
		welcome.setVisible(true);
	}
	public void failedLogin()
	{
		JOptionPane.showMessageDialog(this,
			    "Invalid username and/or password.",
			    "Login Failed",
			    JOptionPane.ERROR_MESSAGE);
	}
}
