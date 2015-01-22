package client.gui.state;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import shared.model.*;

public class BatchStateSaver 
{
	public BatchStateSaver(BatchState bs)
	{
		File file = new File(bs.getUser().getUsername());
		StringBuilder sb = new StringBuilder();
		Batch b = bs.getBatch();
		Project p = bs.getProject();
		String n = "\n";
		
		// IF THERE IS NO BATCH
		if(b == null)
		{
			sb.append(-1 + n);
		}
		else
		{
			// BATCH
			sb.append(b.getBatchID()+n);
			sb.append(b.getCompleted()+n);
			sb.append(b.getFile()+n);
			sb.append(b.getProjectID()+n);
			sb.append(b.getUserID()+n);
			sb.append(n);
			
			// PROJECT
			sb.append(p.getFirstYCoord()+n);
			sb.append(p.getProjectID()+n);
			sb.append(p.getRecordHeight()+n);
			sb.append(p.getRecordsPerImage()+n);
			sb.append(p.getTitle()+n);
			sb.append(n);
			
			// TABLE
			sb.append(bs.getCurrentCell().getColumn()+n);
			sb.append(bs.getCurrentCell().getRecord()+n);
			String[][] data = bs.getDataValues();
			StringBuilder sb2 = new StringBuilder();
			for(int i=0;i<bs.getProject().getRecordsPerImage();i++) //columns
			{
				for(int j=0;j<bs.getFields().size();j++) //rows
				{
					sb2.append(data[i][j] + ",");
				}
				sb2.deleteCharAt(sb2.length()-1);
				sb2.append(";");
			}
			sb2.deleteCharAt(sb2.length()-1);
			sb.append(sb2 + n);
		}
		sb.append(n);
		
		// WINDOW SIZE AND LOCATION
		sb.append(bs.getIndexingWindow().getHeight()+n);
		sb.append(bs.getIndexingWindow().getWidth()+n);
		sb.append(bs.getIndexingWindow().getLocationOnScreen().getX()+n);
		sb.append(bs.getIndexingWindow().getLocationOnScreen().getY()+n);
		sb.append(n);
		
		// DIVIDER LOCATIONS
		sb.append(bs.getIndexingWindow().getHorzontalDividerLocation()+n);
		sb.append(bs.getIndexingWindow().getVerticalDividerLocation()+n);
		sb.append(n);
		
		// IMAGE
		sb.append(bs.isInverted()+n);
		sb.append(bs.isHighlightsVisible()+n);
		sb.append(bs.getZoomLevel()+n);
		sb.append(bs.getImageX()+n);
		sb.append(bs.getImageY()+n);
		sb.append(n);
		
		// SCROLL POSITIONS
		sb.append(bs.getTableVScroll()+n);
		sb.append(bs.getTableHScroll()+n);
		sb.append(bs.getListVScroll()+n);
		sb.append(bs.getListHScroll()+n);
		sb.append(bs.getFormVScroll()+n);
		sb.append(bs.getFormHScroll()+n);
		sb.append(bs.getHelpVScroll()+n);
		
		
		PrintWriter out;
		try {
			out = new PrintWriter(file);
			out.println(sb);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
