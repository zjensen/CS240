package client.gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import client.gui.state.BatchState;

@SuppressWarnings("serial")
public class HelpTabs extends JTabbedPane 
{
	private BatchState bs;
	private JPanel fieldHelpPanel;
	public HelpTabs(BatchState bs)
	{
		super();
		this.bs = bs;
		fieldHelpPanel = new JPanel();
		add("Field Help", fieldHelpPanel);
	}
}
