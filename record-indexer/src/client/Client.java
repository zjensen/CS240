package client;
import java.awt.EventQueue;

import client.gui.state.BatchState;

public class Client 
{
	public static void main(String[] args) 
	{
		if(args.length != 2)
		{
			return;
		}
		String h = args[0];
		String p = args[1];
		if(h.isEmpty())
		{
			h = "localhost";
		}
		if(p.isEmpty())
		{
			p = "39640";
		}
		final String host = h;
		final String port = p;
		EventQueue.invokeLater
		(		
			new Runnable() 
			{
				public void run() 
				{
					new BatchState(host,port);
				}
			}
		);
	}
}
