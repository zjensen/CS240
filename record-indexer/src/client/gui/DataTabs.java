package client.gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import client.gui.state.BatchState;

@SuppressWarnings("serial")
public class DataTabs extends JTabbedPane
{
	private BatchState bs;
	private JPanel tableEntry;
	private JPanel formEntry;
	private JTable table;
	public DataTabs(BatchState bs)
	{
		super();
		this.bs = bs;
		tableEntry = new JPanel();
		formEntry = new JPanel();
		this.add("Table Entry",tableEntry);
		this.add("Form Entry",formEntry);
		if(bs.getBatch() != null) //there is a batch!
		{
			setTable();
			setForm();
		}
	}
	
	public void setTable()
	{
		table = new JTable();
		this.add(table);
	}
	
	public void setForm()
	{
		
	}
}
