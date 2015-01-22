package client.gui.state;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import client.spell.Corrector;
import shared.model.*;

public class BatchStateLoader 
{
	/**
	 * Loads all the users saved information
	 * @param bs
	 */
	public BatchStateLoader(BatchState bs)
	{
		File file = new File(bs.getUser().getUsername());
		if(!file.exists())
		{
			return;
		}
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int first = scan.nextInt();
		
		// THERE IS A BATCH
		if(first!=-1)
		{
			// BATCH 
			Batch batch = new Batch();
			batch.setBatchID(first);
			batch.setCompleted(scan.nextBoolean());
			batch.setFile(scan.next());
			batch.setProjectID(scan.nextInt());
			batch.setUserID(scan.nextInt());
			bs.setBatch(batch);
			
			// PROJECT
			Project p = new Project();
			p.setFirstYCoord(scan.nextInt());
			p.setProjectID(scan.nextInt());
			p.setRecordHeight(scan.nextInt());
			p.setRecordsPerImage(scan.nextInt());
			scan.nextLine();
			p.setTitle(scan.nextLine());
			bs.setProject(p);
			String[][] data = new String[p.getRecordsPerImage()][bs.getFields().size()+1];
			bs.getFields();
			bs.setFieldHelps(null);
			
			// TABLE
			int column = scan.nextInt();
			int record = scan.nextInt();
			bs.setCurrentCell(new Cell(record,column));
			scan.nextLine();
			String[] recordStrings = scan.nextLine().split(";");
			int i = 0;
			int j = 0;
			for(String recordString : recordStrings)
			{
				String[] valueStrings = recordString.split(",");
				j=0;
				for(String valueString : valueStrings)
				{
					data[i][j] = valueString;
					j++;
				}
				i++;
			}
			bs.setDataValues(data);
			
			// QUALITY CHECKER
			Map<Integer,Corrector> correctors = new HashMap<Integer,Corrector>();
			ArrayList<Field> fields = bs.getFields();
			for(int k=0;k<fields.size();k++)
			{
				if(fields.get(k).getKnownData()!=null)
				{
					Corrector c = new Corrector(bs);
					try 
					{
						c.useDictionary(fields.get(k).getKnownData());
					} 
					catch (IOException e) 
					{
						
					}
					correctors.put(k, c);
				}
			}
			bs.setCorrectors(correctors);
			
			boolean[][] cellQuality = new boolean[p.getRecordsPerImage()][fields.size()];
			for(i=0;i<p.getRecordsPerImage();i++) //columns
			{
				for(j=0;j<fields.size();j++) //rows
				{
					String word = data[i][j];
					if(word != null && !word.isEmpty())
					{
						cellQuality[i][j] = correctors.get(j).foundWord(data[i][j]);
					}
					else
					{
						cellQuality[i][j] = true;
					}
				}
			}
			bs.setCellQuality(cellQuality);
			
		}
		
		// WINDOW LOCATION AND SIZE
		bs.setWindowHeight(scan.nextInt());
		bs.setWindowWidth(scan.nextInt());
		bs.setWindowPostion(new Point((int)scan.nextDouble(),(int) scan.nextDouble()));
		
		// DIVIDER LOCATION
		bs.setHorzontalDividerLocation(scan.nextInt());
		bs.setVerticalDividerLocation(scan.nextInt());
		
		// IMAGE
		bs.setInverted(scan.nextBoolean());
		bs.setHighlightsVisible(scan.nextBoolean());
		bs.setZoomLevel(scan.nextDouble());
		bs.setImageX(scan.nextInt());
		bs.setImageY(scan.nextInt());
		
		// SCROLL POSITIONS
		bs.setTableVScroll(scan.nextInt());
		bs.setTableHScroll(scan.nextInt());
		bs.setListVScroll(scan.nextInt());
		bs.setListHScroll(scan.nextInt());
		bs.setFormVScroll(scan.nextInt());
		bs.setFormHScroll(scan.nextInt());
		bs.setHelpVScroll(scan.nextInt());
		
		
	}
}
