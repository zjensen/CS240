package client.gui;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import client.gui.state.BatchState;

@SuppressWarnings("serial")
public class DataTabs extends JTabbedPane
{
	private BatchState bs;
	private DataTable tableEntry;
	private DataForm formEntry;
	public DataTabs(BatchState bs)
	{
		super();
		this.bs = bs;
		tableEntry = new DataTable(this.bs);
		formEntry = new DataForm(this.bs);
		this.add("Table Entry",tableEntry);
		this.add("Form Entry",formEntry);
		this.addChangeListener(cListener);
		this.revalidate();
	}
	/**
	 * @return the tableEntry
	 */
	public DataTable getTableEntry() {
		return tableEntry;
	}
	/**
	 * @return the formEntry
	 */
	public DataForm getFormEntry() {
		return formEntry;
	}
	public DataTable getDataTable()
	{
		return tableEntry;
	}
	public DataForm getDataForm()
	{
		return formEntry;
	}
	
	ChangeListener cListener = new ChangeListener() 
	{
		@Override
		public void stateChanged(ChangeEvent e) 
		{
			JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
			int index = sourceTabbedPane.getSelectedIndex();
			if(index == 1) //switched to form view
			{
				tableEntry.tabSwitch();
				formEntry.tabSwitchTo();
			}
			else if(index==0) //switch to table view
			{
				formEntry.tabSwitchFrom();
			}
		}
	};
	
}
