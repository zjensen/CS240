package listem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class myGrep extends ListEm implements Grep 
{
	private Pattern stringPattern;
	private Map<File, List<String>> myMap;
	public myGrep() 
	{
		super();
		myMap = new HashMap<File, List<String>>();
	}
	@Override
	public Map<File, List<String>> grep(File directory,String fileSelectionPattern, String substringSelectionPattern,boolean recursive) 
	{
		myMap.clear();
		substringSelectionPattern = ".*" + substringSelectionPattern + ".*";
		Pattern p = Pattern.compile(fileSelectionPattern);
		stringPattern = Pattern.compile(substringSelectionPattern);
		super.processDirectory(directory, p, recursive);
		return myMap;
	}
	@Override
	void processFile(File file)
	{
		List<String> ls = new ArrayList<String>();
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(scan.hasNextLine())
		{
			String s = scan.nextLine();
			if(stringPattern.matcher(s).matches())
			{
				ls.add(s);
			}
		}
		if(!ls.isEmpty())
		{
			myMap.put(file, ls);
		}
		scan.close();
		return;
	}
}
