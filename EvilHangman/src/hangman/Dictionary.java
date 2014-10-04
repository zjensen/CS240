package hangman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Dictionary 
{
	public ArrayList<String> words;
	public int wordLength;
	public StringBuilder currentWord;
	public Dictionary(ArrayList<String> s, int i) 
	{
		words = s;
		wordLength = i;
		StringBuilder sb = new StringBuilder();
		for(int j=0;j<i;j++)
		{
			sb.append("-");
		}
		currentWord = sb;
	}

	public void bestList(Map<String, ArrayList<String>> myMap, char c)
	{
		int maxVal = 0;
		ArrayList<String> topValue = new ArrayList<String>();
		String topKey = new String();
		ArrayList<Integer> topLetters = new ArrayList<Integer>();
		for(Entry<String, ArrayList<String>> entry: myMap.entrySet())
		{
			ArrayList<String> value = entry.getValue();
			String key = entry.getKey();
			ArrayList<Integer> letters = new ArrayList<Integer>();
			if(value.size() > maxVal)
			{
				maxVal = value.size();
				for(int i=0;i<wordLength;i++)
				{
					if(key.charAt(i) == c)
					{
						letters.add(i);
					}
				}
				topValue = value;
				topKey = key;
				topLetters = letters;
			}
			else if(value.size() == maxVal)
			{
				for(int i=0;i<wordLength;i++)
				{
					if(key.charAt(i) == c)
					{
						letters.add(i);
					}
				}
				if(letters.size() < topLetters.size())
				{
					topValue = value;
					topKey = key;
					topLetters = letters;
				}
				else if(letters.size() == topLetters.size())
				{
					for(int j=letters.size();j>-1;j--)
					{
						if(letters.get(j) > topLetters.get(j))
						{
							topValue = value;
							topKey = key;
							topLetters = letters;
							j=-1;
						}
						else if(letters.get(j) < topLetters.get(j))
						{
							j=-1;
						}
					}	
				}
			}
		}
		for(int j=0;j<topKey.length();j++)
		{
			if (topKey.charAt(j) == c)
			{
				currentWord.deleteCharAt(j);
				currentWord.insert(j, c);
			}
		}
		words.clear();
		words = topValue;
	}
	
	public Set<String> guess(char c)
	{
		Map<String, ArrayList<String>> myMap = new HashMap<String, ArrayList<String>>();
		for(int i=0;i<words.size();i++)
		{
			String key = new String();
			StringBuilder keySB = new StringBuilder();
			String s = words.get(i);
			for(int j=0;j<wordLength;j++)
			{
				if(s.charAt(j) == c)
				{
					keySB.append(c);
				}
				else
				{
					keySB.append("-");
				}
			}
			key = keySB.toString();
			ArrayList<String> value = new ArrayList<String>();
			if(myMap.containsKey(key))
			{
				value = myMap.get(key);
				value.add(s);
				myMap.replace(key, value);
			}
			else
			{
				value.add(s);
				myMap.put(key, value);
			}
		}
		bestList(myMap, c);
		HashSet<String> toReturn = new HashSet<String>(words);
		return toReturn;
	}
}
