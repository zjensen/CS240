package client.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import client.gui.state.BatchState;

@SuppressWarnings("serial")
public class ButtonBar extends JPanel implements ActionListener
{
	private BatchState bs;
	private JButton zoomInButton;
	private JButton zoomOutButton;
	private JButton invertButton;
	private JButton highlightButton;
	private JButton saveButton;
	private JButton submitButton;
	public ButtonBar(BatchState bs)
	{
		super();
		this.bs = bs;
		this.setBackground(new Color(253,95,0));
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setSize(1000,50);
		setButtons();
	}
	public void setButtons()
	{
		zoomInButton = new JButton("Zoom In");
		zoomInButton.addActionListener(this);
		add(zoomInButton);
		
		zoomOutButton = new JButton("Zoom Out");
		zoomOutButton.addActionListener(this);
		add(zoomOutButton);
		
		invertButton = new JButton("Invert Image");
		invertButton.addActionListener(this);
		add(invertButton);
		
		highlightButton = new JButton("Toggle Highlights");
		highlightButton.addActionListener(this);
		add(highlightButton);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(this);
		add(saveButton);
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		add(submitButton);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent a) 
	{
		if(a.getSource() == zoomInButton)
		{
			bs.zoom(true);
		}
		else if(a.getSource() == zoomOutButton)
		{
			bs.zoom(false);
		}
		else if(a.getSource() == invertButton)
		{
			bs.invertImage();
		}
		else if(a.getSource() == highlightButton)
		{
			bs.toggleHighlights();
		}
		else if(a.getSource() == saveButton)
		{
			bs.save();
		}
		else if(a.getSource() == submitButton)
		{
			if(bs.getUser().getBatchID() != -1)
			{
				bs.submit();
			}
			else
			{
				JOptionPane.showMessageDialog(this,
				"You must download a batch before submitting one.",
				"No Batch",
				JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
