package client.searchGUI;

import static client.searchGUI.SearchGUI.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import shared.communication.GetFields_Params;
import shared.communication.GetProjects_Params;
import shared.communication.Search_Params;
import shared.communication.Search_Result;
import shared.communication.Search_Result_Tuple;
import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;
import client.communication.ClientCommunicator;
import client.communication.ClientException;

@SuppressWarnings("serial")
public class SearchFrame extends JFrame 
{
	private LoginPanel loginPanel;
	private JPanel resultPanel;
	private JTextField searchField;
	private SearchParamPanel spp;
	private ClientCommunicator cc;
	public boolean loggedIn = false;
	private ArrayList<JRadioButton> fieldButtons;
	public ArrayList<String> fieldIDs = new ArrayList<String>();
	private JButton searchButton;
	private String username;
	private String password;
	private JComboBox<String> imageBox;
	private ArrayList<Search_Result_Tuple> tuples;
	private JPanel s;
	private JFrame imageFrame;
	private JButton selectButton;
	private JPanel selectPanel;
	private Map<String,String> myMap;
	private String url;
	public SearchFrame() throws HeadlessException 
	{
		super();
		myMap = new HashMap<String,String>();
		setTitle("Record Indexer - Search");
		selectPanel = new JPanel();
		selectPanel.setLayout(new FlowLayout());
		selectPanel.add(new JLabel("Images: "));
		imageBox = new JComboBox<String>();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		imageFrame = new JFrame();
		//imageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		imageFrame.setVisible(false);
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));		
		
		add(Box.createRigidArea(DOUBLE_VSPACE));
		setMinimumSize(new Dimension(800,300));
		loginPanel = new LoginPanel();
		searchButton = new JButton("SEARCH");
		selectButton = new JButton("View Image");
		spp = new SearchParamPanel();
		resultPanel = new JPanel();
		resultPanel.setLayout(new FlowLayout());
		add(loginPanel);
		loginPanel.loginButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent a) 
			{
				cc = new ClientCommunicator(loginPanel.getHost(), loginPanel.getPort());
				ValidateUser_Params params = new ValidateUser_Params(loginPanel.getUsername(),loginPanel.getPassword());
				GetProjects_Params params2 = new GetProjects_Params(loginPanel.getUsername(),loginPanel.getPassword());
				GetFields_Params params3 = new GetFields_Params(loginPanel.getUsername(),loginPanel.getPassword(),"");
				try 
				{
					ValidateUser_Result result = cc.validateUser(params);
					if(result.isValid())
					{
						username = loginPanel.getUsername();
						password = loginPanel.getPassword();
						loginPanel.loginButton.setEnabled(false);
						loginPanel.hostField.setEnabled(false);
						loginPanel.portField.setEnabled(false);
						loginPanel.usernameField.setEnabled(false);
						loginPanel.passwordField.setEnabled(false);
						loginPanel.revalidate();
						if(!loggedIn)
						{
							url = "http://"+loginPanel.getHost()+":"+loginPanel.getPort() +"/records/images/";
							Border edge = BorderFactory.createLoweredBevelBorder();
							loggedIn=true;
							JPanel p = spp.addProjects(cc.getProjects(params2).getProjects(), cc.getFields(params3).getFields());
							p.setAlignmentY(LEFT_ALIGNMENT);
							p.revalidate();
							add(p);
							fieldButtons = spp.fieldButtons;
							s = new JPanel();
							s.setSize(new Dimension(800,10));
							s.add(Box.createRigidArea(DOUBLE_HSPACE));
							
							s.setBorder(edge);
							s.add(new JLabel("Search terms\n(seperated by commas): "));
							searchField = new JTextField(52);
							s.add(searchField);
							s.add(searchButton);	
							s.add(Box.createRigidArea(new Dimension(0,20)));
							s.setMaximumSize(getPreferredSize());
							add(s);
							add(resultPanel);
							pack();
						}
					}
				} 
				catch (ClientException e) 
				{
					
				}
			}
		}
		);
		searchButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				StringBuilder sb = new StringBuilder();
				for(int i=0;i<fieldButtons.size();i++)
				{
					if(fieldButtons.get(i).isSelected())
					{
						sb.append((i+1) + ",");
					}
				}
				Search_Params params = new Search_Params(username, password, sb.toString(), searchField.getText());
				try 
				{
					cc = new ClientCommunicator(loginPanel.getHost(), loginPanel.getPort());
					Search_Result sr = cc.search(params);
					if(sr != null)
					{
						imageBox.removeAllItems();
						imageBox.setMaximumRowCount(100);
						tuples = sr.getTuples();
						for(Search_Result_Tuple tuple : tuples)
						{
							String str = tuple.getImageURL().substring(url.length());
							boolean there = false;
							ComboBoxModel<String> model = imageBox.getModel();
							for(int i=0;i<model.getSize();i++)
							{
								if(model.getElementAt(i).equals(str))
								{
									there = true;
								}
							}
							if(!there)
							{
								imageBox.addItem(str);
							}
						}
					}
				} 
				catch (ClientException e1) 
				{
				
				}
				selectPanel.add(imageBox);
				selectPanel.add(selectButton);
				add(selectPanel);
				revalidate();
			}
		});
		selectButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					InputStream input = new URL((String) url+imageBox.getSelectedItem()).openStream();
					BufferedImage image = ImageIO.read(input);
					JFrame f = new JFrame();
					ImageIcon icon = new ImageIcon(image);
					JPanel j = new JPanel();
					j.add(new JLabel(icon));
					j.revalidate();
					f.add(new JScrollPane(j));
					f.pack();
					f.setVisible(true);
					pack();
				} 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		pack();
		
	}


}
