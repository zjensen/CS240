package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import client.gui.state.BatchState;
import client.gui.state.BatchStateListener;
import client.gui.state.Cell;

@SuppressWarnings("serial")
public class HelpTabs extends JTabbedPane 
{
	private BatchState bs;
	private JScrollPane fieldHelpPanel;
	private ArrayList<String> fields;
	private JEditorPane editor;
	private JPanel panel;
	private int field;
	public HelpTabs(BatchState bs)
	{
		super();
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		this.bs = bs;
		fieldHelpPanel = new JScrollPane();
		fieldHelpPanel.getVerticalScrollBar().setValue(bs.getHelpVScroll());
		fieldHelpPanel.validate();
		if(this.bs.getBatch()!=null)
		{
			field = this.bs.getCurrentCell().getColumn();
			fields = this.bs.getFieldHelps();
			setFieldHelp();
		}
		panel.add(fieldHelpPanel, BorderLayout.CENTER);
		panel.validate();
		panel.setVisible(true);
		add("Field Help", panel);
		JPanel empty = new JPanel();
		empty.setBackground(new Color(0,0,0));
		add("Image Navigator",empty);
		this.bs.addListener(bsListener);
		this.validate();
	}
	public void setFieldHelp()
	{
		if(this.fields == null)
		{
			this.bs.setFieldHelps(null);
			this.fields = this.bs.getFieldHelps();
			
		}
		fieldHelpPanel.setViewportView(null);
		field = this.bs.getCurrentCell().getColumn();
		//new JEditorPane(fields.get(field));
		editor = new JEditorPane();
		editor.setContentType("text/html");
		if(field==-1)
		{
			field = 0;
		}
		String text = fields.get(field);
		editor.setEditable(false);
		editor.setText(text);
		editor.setPreferredSize(new Dimension(500,300));
		fieldHelpPanel.setViewportView(editor);
		fieldHelpPanel.validate();
	}
	
	public void saveScroll()
	{
		bs.setHelpVScroll(fieldHelpPanel.getVerticalScrollBar().getValue());
	}
	
	private BatchStateListener bsListener = new BatchStateListener()
	{

		@Override
		public void valueChanged(Cell cell, String newValue) 
		{
			
		}

		@Override
		public void currentCellChanged(Cell cell) 
		{
			setFieldHelp();
		}
		
	};
}
