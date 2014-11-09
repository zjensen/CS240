package client.searchGUI;

import java.awt.Dimension;
import java.awt.EventQueue;


public class SearchGUI 
{
	public static final int HSPACING = 5;
	public static final int VSPACING = 5;
	
	public static final Dimension SINGLE_HSPACE = new Dimension(HSPACING, 0);
	public static final Dimension DOUBLE_HSPACE = new Dimension(HSPACING * 2, 0);
	public static final Dimension TRIPLE_HSPACE = new Dimension(HSPACING * 3, 0);
	public static final Dimension MAX_HSPACE = new Dimension(10000, 0);
	
	public static final Dimension SINGLE_VSPACE = new Dimension(0, VSPACING);
	public static final Dimension DOUBLE_VSPACE = new Dimension(0, VSPACING * 2);
	public static final Dimension TRIPLE_VSPACE = new Dimension(0, VSPACING * 3);
	public static final Dimension MAX_VSPACE = new Dimension(0, 10000);
	
	public SearchGUI()
	{
		
	}
	
	public static void main(String[] args) 
	{
		EventQueue.invokeLater
		(		
			new Runnable() 
			{
				public void run() 
				{
					SearchFrame frame = new SearchFrame();
					frame.setVisible(true);
				}
			}
		);
	}
	
}
