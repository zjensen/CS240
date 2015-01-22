package client.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import shared.model.Field;
import client.gui.state.BatchState;
import client.gui.state.BatchStateListener;
import client.gui.state.Cell;

@SuppressWarnings("serial")
public class DataForm extends JPanel implements ActionListener
{
	private BatchState bs;
	private JList<Integer> list;
	private ArrayList<JLabel> fieldLabels;
	private ArrayList<JTextField> valueFields;
	private JPanel valuePanel;
	private JScrollPane valuePane;
	private JScrollPane recordPane;
	private Cell currentCell;
	private GridBagLayout gridbag;
	private ArrayList<Field> fields;
	private JMenuItem suggestionMenuItem;
	private Cell misspelledCell;
	
	public DataForm(BatchState bs)
	{
		this.bs = bs;
		this.currentCell=bs.getCurrentCell();
		setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		gridbag = new GridBagLayout();
		list = new JList<Integer>();
		
		suggestionMenuItem = new JMenuItem("See suggestions");
		suggestionMenuItem.addActionListener(this);
		
		recordPane = new JScrollPane();
		recordPane.setBackground(new Color(255,255,0));
		
		valuePanel = new JPanel();
		//valuePanel.setLayout(new BoxLayout(valuePanel,BoxLayout.Y_AXIS));
		valuePanel.setBackground(new Color(115,255,0));
	    valuePanel.setLayout(gridbag);
		valuePane = new JScrollPane(valuePanel);
		this.add(recordPane);
		this.add(valuePane);
		
		recordPane.getHorizontalScrollBar().setValue(bs.getListHScroll());
		recordPane.getVerticalScrollBar().setValue(bs.getListVScroll());
		recordPane.validate();
		
		valuePane.getHorizontalScrollBar().setValue(bs.getFormHScroll());
		valuePane.getVerticalScrollBar().setValue(bs.getFormHScroll());
		valuePane.validate();
		
		
		
		fieldLabels = new ArrayList<JLabel>();
		valueFields = new ArrayList<JTextField>();
		this.bs.addListener(bsListener);
		this.addMouseListener(mouseAdapter);
		this.addKeyListener(kListener);
		this.addFocusListener(fListener);
		if(this.bs.getBatch()!=null)
		{
			this.fields = this.bs.getFields();
			addList();
			recordPane.setViewportView(list);
			set();
			updateValues();
		}
	}
	public void set()
	{
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = 1;
		c.ipady = 10;
		c.weightx = 1;
		c.gridx=0;
		c.gridy=1;
		c.insets = new Insets(10,0,10,0);  //top padding
		int j = 1;
		if(fields==null)
		{
			fields = this.bs.getFields();
		}
		for(Field f : fields)
		{
			JLabel l = new JLabel(f.getTitle());
			l.setSize(100, 30);
			fieldLabels.add(l);
			
			JTextField t = new JTextField(15);
			t.setMinimumSize(new Dimension(150,20));
			t.setSize(150, 20);
			t.addMouseListener(mouseAdapter);
			t.addFocusListener(fListener);
			t.addKeyListener(kListener);
			t.setFocusTraversalKeysEnabled(false);
			
			valueFields.add(t);
			
			c.gridx = 0;
			c.gridy = j;
			valuePanel.add(l,c);
			
			c.gridx = 1;
			c.gridy = j;
			valuePanel.add(t,c);
			
			j++;
		}
		valuePanel.validate();
		
	}
	
	private void addList()
	{
		int recordsPerImage = this.bs.getProject().getRecordsPerImage();
		
		DefaultListModel<Integer> model = new DefaultListModel<Integer>();
		
		for(int i=0;i<recordsPerImage;i++)
		{
			model.addElement(i+1);
		}
		
		list = new JList<Integer>(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setSelectedIndex(currentCell.getRecord());
		list.setBackground(new Color(255,255,0));
		list.addMouseListener(mouseAdapter);
	}
	
	private void updateValues()
	{
		String data[][] = bs.getDataValues();
		if(valueFields.size()==0)
		{
			this.set();
		}
		for(int i=0;i<valueFields.size();i++)
		{
			String value = data[currentCell.getRecord()][i];
			valueFields.get(i).setText(value);
			if(!bs.getCellQuality()[currentCell.getRecord()][i])
			{
				valueFields.get(i).setBackground(new Color(255,0,0));
			}
			else
			{
				valueFields.get(i).setBackground(new Color(255,255,255));
			}
		}
		int focused = currentCell.getColumn();
		valueFields.get(focused).requestFocusInWindow();
		this.validate();
	}
	
	public void tabSwitchTo()
	{
		valueFields.get(currentCell.getColumn()).requestFocusInWindow();
	}
	
	public void tabSwitchFrom()
	{
		String s = valueFields.get(currentCell.getColumn()).getText();
		bs.valueChanged(currentCell, s);
	}

	private MouseAdapter mouseAdapter = new MouseAdapter() 
	{
		@Override
		public void mousePressed(MouseEvent e) 
		{
			if(e.getSource() == list)
			{
				int record = list.locationToIndex(e.getPoint()); //record they selected in the list
				if(record!=currentCell.getRecord())
				{
					currentCell.setRecord(record);
					bs.cellChanged(currentCell);
				}
			}
			else if(e.getSource().getClass().equals(JTextField.class))
			{
				Component c = e.getComponent();
				for(int i=0;i<valueFields.size();i++)
				{
					if(valueFields.get(i).equals(c))
					{
						if(e.getButton() == MouseEvent.BUTTON3) //right click
						{
							if(!bs.getCellQuality()[currentCell.getRecord()][i])
							{
								misspelledCell = new Cell(currentCell.getRecord(),i);
								SuggestionPopUp menu = new SuggestionPopUp();
						        menu.show(e.getComponent(), e.getX(), e.getY());
							}
							return;
						}
						String s = valueFields.get(currentCell.getColumn()).getText();
						bs.valueChanged(currentCell, s); //TODO wait for release
						currentCell.setColumn(i);
						bs.cellChanged(currentCell);
					}
				}
			}
			
		}
	};
	
	private FocusListener fListener = new FocusListener()
	{
		@Override
		public void focusGained(FocusEvent e) 
		{
			Component c = e.getComponent();
			for(int i=0;i<valueFields.size();i++)
			{
				if(valueFields.get(i).equals(c))
				{
					currentCell.setColumn(i);
					bs.cellChanged(currentCell);
				}
			}
		}

		@Override
		public void focusLost(FocusEvent e) 
		{
			
		}
		
	};
	
	private BatchStateListener bsListener = new BatchStateListener()
	{

		@Override
		public void valueChanged(Cell cell, String newValue) 
		{
			updateValues();
		}

		@Override
		public void currentCellChanged(Cell cell) 
		{
			currentCell = cell;
			list.setSelectedIndex(cell.getRecord());
			//valueFields.get(cell.getColumn()).requestFocusInWindow();
			updateValues();
		}
		
	};
	
	private KeyAdapter kListener = new KeyAdapter()
	{
		@Override
		public void keyReleased(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_TAB)
			{
				Cell cell = currentCell;
				String s = valueFields.get(cell.getColumn()).getText();
				if(!s.isEmpty())
				{
					bs.valueChanged(cell, s);
				}
				cell.setColumn(cell.getColumn()+1);
				if(cell.getColumn()==fields.size())
				{
					cell.setColumn(0);
				}
				bs.cellChanged(cell);
			}
		}
	};

	public void saveScroll()
	{
		bs.setListHScroll(recordPane.getHorizontalScrollBar().getValue());
		bs.setListVScroll(recordPane.getVerticalScrollBar().getValue());
		
		bs.setFormHScroll(valuePane.getHorizontalScrollBar().getValue());
		bs.setFormHScroll(valuePane.getVerticalScrollBar().getValue());
	}
	
	class SuggestionPopUp extends JPopupMenu {
	    JMenuItem anItem;
	    public SuggestionPopUp(){
	        add(suggestionMenuItem);
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == suggestionMenuItem)
		{
			new SuggestionDialog(bs,misspelledCell).setVisible(true);;
		}
	}
}
