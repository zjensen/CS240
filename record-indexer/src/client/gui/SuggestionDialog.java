package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import client.gui.state.BatchState;
import client.gui.state.Cell;
import client.spell.Corrector;
import client.spell.SpellCorrector.NoSimilarWordFoundException;

@SuppressWarnings("serial")
public class SuggestionDialog extends JDialog implements ActionListener 
{
	private BatchState bs;
	private JButton cancelButton;
	private JButton useButton;
	private JScrollPane listPane;
	private Cell cell;
	private JList<String> list;
	
	public SuggestionDialog(BatchState bs, Cell cell)
	{
		super(bs.getIndexingWindow(), "Download Batch");
		this.setModal(true);
		
	    this.setLayout(new BorderLayout());
		this.bs = bs;
		this.cell = cell;
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		useButton = new JButton("Use Suggestion");
		useButton.addActionListener(this);
		useButton.setEnabled(false);

		
		this.setSize(300, 400);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setResizable(false);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(cancelButton);
		buttonPanel.add(useButton);
		this.add(buttonPanel,BorderLayout.SOUTH);
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		addList();
		
		list.setBackground(new Color(255,0,213,100));
		
		listPane = new JScrollPane(list);
		
		this.add(listPane);
		
		this.validate();
	}
	
	public void addList()
	{
		Corrector c = bs.getCorrectors().get(cell.getColumn());
		DefaultListModel<String> model = new DefaultListModel<String>();
		list = new JList<String>(model);
		try 
		{
			ArrayList<String> suggestions = c.suggestSimilarWord(bs.getDataValues()[cell.getRecord()][cell.getColumn()]);
			for(String s : suggestions)
			{
				model.addElement(s);
			}
		} 
		catch (NoSimilarWordFoundException e) 
		{

		}
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.addMouseListener(mouseAdapter);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==cancelButton)
		{
			this.dispose();
		}
		else if(e.getSource()==useButton)
		{
			String value = list.getSelectedValue();
			bs.valueChanged(cell, value);
			this.dispose();
		}
	}
	
	private MouseAdapter mouseAdapter = new MouseAdapter() 
	{
		@Override
		public void mousePressed(MouseEvent e) 
		{
			if(e.getSource() == list)
			{
				list.getModel().getElementAt(list.locationToIndex(e.getPoint()));
				useButton.setEnabled(true);
			}
		}
	};
}
