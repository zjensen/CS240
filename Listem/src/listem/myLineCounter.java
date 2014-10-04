package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class myLineCounter extends ListEm implements LineCounter 
{
	private Map<File, Integer> myMap;
	public myLineCounter() 
	{
		super();
		myMap = new HashMap<File, Integer>();
	}
	@Override
	public Map<File, Integer> countLines(File directory,String fileSelectionPattern, boolean recursive) 
	{
		myMap.clear();
		Pattern p = Pattern.compile(fileSelectionPattern);
		super.processDirectory(directory, p, recursive);
		return myMap;
	}
	@Override
	void processFile(File file) 
	{
		int count = 0;
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(scan.hasNextLine())
		{
			count++;
			scan.nextLine();
		}
		scan.close();
		myMap.put(file, count);
		return;
	}
}
