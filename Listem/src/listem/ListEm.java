package listem;

import java.io.File;
import java.util.regex.Pattern;

public abstract class ListEm 
{
	public ListEm(){}
	public void processDirectory(File dir, Pattern p, boolean recursive)
	{
		if(!dir.canRead() || !dir.isDirectory())
		{
			return;
		}
		for(File file : dir.listFiles())
		{
			if(file.isDirectory() && recursive)
			{
				processDirectory(dir,p,recursive);
			}
			else if(file.isFile())
			{
				if(p.matcher(file.getName()).matches())
				{
					processFile(file);
				}
			}
		}
	}
	abstract void processFile(File file);
}
