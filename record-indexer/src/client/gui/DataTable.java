package client.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import shared.model.Field;
import shared.model.Project;
import client.gui.state.BatchState;
import client.gui.state.BatchStateListener;
import client.gui.state.Cell;

@SuppressWarnings("serial")
public class DataTable extends JScrollPane implements ActionListener
{
	private BatchState bs;
	private JTable table;
	private ArrayList<Field> fields;
	private Project project;
	private JMenuItem suggestionMenuItem;
	private Cell misspelledCell;
	
	public DataTable(BatchState bs)
	{
		this.bs = bs;
		fields = bs.getFields();
		project = bs.getProject();
		table = null;
		suggestionMenuItem = new JMenuItem("See suggestions");
		suggestionMenuItem.addActionListener(this);
		
		if(bs.getBatch()!=null)
		{
			generateTable();
			table.changeSelection(this.bs.getCurrentCell().getRecord(), this.bs.getCurrentCell().getColumn()+1, false, false);
			this.bs.addListener(bsListener);
		}
		this.setViewportView(table);
		this.getVerticalScrollBar().setValue(bs.getTableVScroll());
		this.getHorizontalScrollBar().setValue(bs.getTableHScroll());
		this.validate();
	}
	public void generateTable()
	{
		String[] columnTitles = new String[fields.size()+1];
		columnTitles[0] = "Record Number";
		for(int i=0;i<fields.size();i++)
		{
			columnTitles[i+1]=fields.get(i).getTitle();
		}
		String[][] storedData = bs.getDataValues();
		String[][] data = new String[project.getRecordsPerImage()][fields.size()+1];
		
		for(int i=0;i<project.getRecordsPerImage();i++) //columns
		{
			for(int j=0;j<fields.size()+1;j++) //rows
			{
				if(j==0)
				{
					data[i][j] = Integer.toString(i+1);
				}
				else
				{
					if(storedData!=null && storedData[i][j-1]!=null)
					{
						data[i][j] = storedData[i][j-1];
					}
					else
					{
						data[i][j] = "";
					}
				}
			}
		}
		String[][] tempData = new String[project.getRecordsPerImage()][fields.size()+1];
		for(int i=0;i<project.getRecordsPerImage();i++) //columns
		{
			for(int j=1;j<fields.size()+1;j++) //rows
			{
				tempData[i][j-1] = data[i][j];
			}
		}
		this.bs.setDataValues(tempData);
		table = new JTable(data,columnTitles)
		{
			@Override
			public boolean isCellEditable(int row, int column)
			{
				if(column == 0)
				{
					return false;
				}
				else
				{
					return true;
				}
			}
		};
		for(int i=0;i<=fields.size();i++)
		{
			table.getTableHeader().getColumnModel().getColumn(i).setMinWidth(100);;
			table.getTableHeader().getColumnModel().getColumn(i).setResizable(false);
		}
		table.setCellSelectionEnabled(true);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setGridColor(new Color(0,0,0));
		table.addMouseListener(mouseAdapter);
		table.addKeyListener(kListener);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		table.setSelectionBackground(new Color(0,0,255,150));
		table.setDefaultRenderer(Object.class, new CustomRenderer());	
	}
	
	private MouseAdapter mouseAdapter = new MouseAdapter() 
	{		
		@Override
		public void mousePressed(MouseEvent e) 
		{
			if(table.columnAtPoint(e.getPoint())==0) //record numbers shouldnt call anything
			{
				return;
			}
			Cell cell = new Cell(table.rowAtPoint(e.getPoint()),table.columnAtPoint(e.getPoint())-1);
			if(e.getButton() == MouseEvent.BUTTON3) //right click
			{
				if(!bs.getCellQuality()[cell.getRecord()][cell.getColumn()])
				{
					misspelledCell = cell;
					SuggestionPopUp menu = new SuggestionPopUp();
			        menu.show(e.getComponent(), e.getX(), e.getY());
				}
				return;
			}
			Cell oldCell = bs.getCurrentCell();
			if(!cell.equals(bs.getCurrentCell()))
			{
				String value = (String)table.getValueAt(oldCell.getRecord(), oldCell.getColumn()+1);
				if(value != null)
					bs.valueChanged(oldCell, value);
			}
			bs.cellChanged(cell);
		}
	};
	
	private BatchStateListener bsListener = new BatchStateListener()
	{

		@Override
		public void valueChanged(Cell cell, String newValue) 
		{
			table.setValueAt(newValue,cell.getRecord(),cell.getColumn()+1);
		}

		@Override
		public void currentCellChanged(Cell cell) 
		{
			table.changeSelection(cell.getRecord(), cell.getColumn()+1, false, false);
		}
		
	};
	
	private KeyAdapter kListener = new KeyAdapter()
	{
		@Override
		public void keyReleased(KeyEvent e)
		{
			if(table.getSelectedColumn() == 0) //record numbers shouldnt call anything
			{
				return;
			}
			int k = e.getKeyCode();
			if(k == KeyEvent.VK_TAB || k==KeyEvent.VK_LEFT || k==KeyEvent.VK_RIGHT || k==KeyEvent.VK_UP ||k==KeyEvent.VK_DOWN)
			{
				Cell cell = new Cell(table.getSelectedRow(),table.getSelectedColumn()-1);
				String value = (String)table.getValueAt(bs.getCurrentCell().getRecord(), bs.getCurrentCell().getColumn()+1);
				if(value != null)
				{
					bs.valueChanged(bs.getCurrentCell(), value);
				}
				bs.cellChanged(cell);
			}
		}
	};
	
	public void tabSwitch()
	{
		if (table.isEditing())
		{
		    table.getCellEditor().stopCellEditing();
		}
		Cell cell = new Cell(table.getSelectedRow(),table.getSelectedColumn());
		String value = (String)table.getValueAt(cell.getRecord(), cell.getColumn());
		if(value != null)
		{
			bs.valueChanged(bs.getCurrentCell(), value);
		}
		value = (String)table.getValueAt(bs.getCurrentCell().getRecord(), bs.getCurrentCell().getColumn()+1);
		return;
	}

	public JTable getTable() {
		return table;
	}
	public void setTable(JTable table) {
		this.table = table;
	}
	
	public void saveScroll()
	{
		bs.setTableHScroll(this.getHorizontalScrollBar().getValue());
		bs.setTableVScroll(this.getVerticalScrollBar().getValue());
	}
	
	public class CustomRenderer extends DefaultTableCellRenderer 
	{
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	    {
	        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        if(column!=0)
	        {
		        if(!bs.getCellQuality()[row][column-1])
		        {
		        	 c.setBackground(new Color(255, 0, 0));
		        }
		        else
		        {
		        	if (isSelected)
			        {
			            c.setBackground(table.getSelectionBackground());
			        }
		        	else
		        	{
		        		c.setBackground(null);
		        	}
		        }
	        }
	        else
	        {
	        	c.setBackground(null);
	        }
	        return c;
	    }
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
