package client.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import client.gui.state.BatchState;

@SuppressWarnings("serial")
public class IndexingWindow extends JFrame implements ActionListener
{
	private JSplitPane verticalSplitPane; //bottom and top of screen
	private JSplitPane horizontalSplitPane; //left and right of bottom
	private BatchState bs;
	private ButtonBar bb;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem downloadBatchItem;
	private JMenuItem logoutItem;
	private JMenuItem exitItem;
	private DataTabs dataTabs;
	private HelpTabs helpTabs;
	private DownloadBatchDialog dbDialog;
	private ImagePanel imagePanel;
	
	public IndexingWindow(BatchState bs)
	{
		super("Indexer");
		this.bs = bs;
	    setLayout(new BorderLayout());
		setSize(new Dimension(bs.getWindowWidth(),bs.getWindowHeight()));
		setLocation(bs.getWindowPostion());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(true);
		addMenu();
		bb = new ButtonBar(this.bs);
		this.add(bb,BorderLayout.PAGE_START);
		
		dataTabs = new DataTabs(this.bs);
		helpTabs = new HelpTabs(this.bs);
		imagePanel = new ImagePanel(this.bs);
		
		
		horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		horizontalSplitPane.setBackground(new Color(225, 0, 255));
		horizontalSplitPane.setLeftComponent(dataTabs); //left
		horizontalSplitPane.setRightComponent(helpTabs); //right
		horizontalSplitPane.setDividerLocation(bs.getHorzontalDividerLocation());
		horizontalSplitPane.setResizeWeight(0.5);
		
		
		verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		verticalSplitPane.setBackground(new Color(125,249,255));
		verticalSplitPane.setLeftComponent(imagePanel); //image
		verticalSplitPane.setRightComponent(horizontalSplitPane); //horizontal split pane
		verticalSplitPane.setDividerLocation(bs.getVerticalDividerLocation());
		verticalSplitPane.setResizeWeight(0.5);
		
		this.add(verticalSplitPane,BorderLayout.CENTER);
		
		this.addWindowListener(wListener);
		
		revalidate();
	}
	
	public void addMenu()
	{
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		
		downloadBatchItem = new JMenuItem("Download Batch");
		downloadBatchItem.addActionListener(this);
		fileMenu.add(downloadBatchItem);
		
		logoutItem = new JMenuItem("Logout");
		fileMenu.add(logoutItem);
		logoutItem.addActionListener(this);
		
		exitItem = new JMenuItem("Exit");
		fileMenu.add(exitItem);
		exitItem.addActionListener(this);
		
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);
	}
	
	@Override
	public void actionPerformed(ActionEvent a) 
	{
		if(a.getSource() == downloadBatchItem)
		{
			if(bs.getUser().getBatchID() == -1) //no current batch
			{
				dbDialog = new DownloadBatchDialog(this,this.bs);
				dbDialog.setVisible(true);
			}
			else
			{
				JOptionPane.showMessageDialog(this,
				"You must submit your current batch before downloading another.",
				"Already have an assigned batch",
				JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(a.getSource() == logoutItem)
		{
			bs.logout();
		}
		else if(a.getSource() == exitItem)
		{
			bs.save(); //save it maybe????
			System.exit(0);
		}
	}
	
	public JMenuItem getDownloadBatchItem()
	{
		return this.downloadBatchItem;
	}
	
	public void reset(BatchState bs)
	{
		this.bs = bs;
		this.imagePanel = new ImagePanel(this.bs);
		this.dataTabs = new DataTabs(this.bs);
		this.helpTabs = new HelpTabs(this.bs);
		
		horizontalSplitPane.setLeftComponent(dataTabs); //left
		horizontalSplitPane.setRightComponent(helpTabs); //right
		horizontalSplitPane.setDividerLocation(bs.getHorzontalDividerLocation());
		horizontalSplitPane.validate();
		
		verticalSplitPane.setLeftComponent(imagePanel); //image
		verticalSplitPane.setRightComponent(horizontalSplitPane); //horizontal split pane
		verticalSplitPane.setDividerLocation(bs.getVerticalDividerLocation());
		verticalSplitPane.validate();
		
		this.validate();
		
	}
	
	
	
	private WindowListener wListener = new WindowListener()
	{
		@Override
	    public void windowClosing(WindowEvent e) {
	    	bs.save();
	    	System.exit(0);
	    }

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public void invertImage()
	{
		this.imagePanel.invertImage();
	}
	
	public int getVerticalDividerLocation()
	{
		return verticalSplitPane.getDividerLocation();
	}
	
	public int getHorzontalDividerLocation()
	{
		return horizontalSplitPane.getDividerLocation();
	}
	public void repaintImage()
	{
		this.imagePanel.zoomImage();
	}
	public ImagePanel getImagePanel()
	{
		return this.imagePanel;
	}
	public DataTabs getDataTabs()
	{
		return this.dataTabs;
	}

	/**
	 * @return the helpTabs
	 */
	public HelpTabs getHelpTabs() {
		return helpTabs;
	}
}
