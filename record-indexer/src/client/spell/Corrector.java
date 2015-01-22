package client.spell;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import shared.communication.DownloadFile_Params;
import shared.communication.DownloadFile_Result;
import client.communication.ClientException;
import client.gui.state.BatchState;

public class Corrector implements SpellCorrector
{
	public MyTrie Dictionary;
	private BatchState bs;
	
	public Corrector(BatchState bs)
	{
		Dictionary = new MyTrie();
		this.bs = bs;
	}
	/**
	 * Constructor for testing purposes, no batchstate needed
	 */
	public Corrector()
	{
		Dictionary = new MyTrie();
	}
	
	@Override
	public void useDictionary(String dictionaryFileName) throws IOException 
	{
		String in = "";
		try 
		{
			DownloadFile_Result result = this.bs.getCc().downloadFile(new DownloadFile_Params(dictionaryFileName));
			in = new String(result.getFileBytes());
		} 
		catch (ClientException e) 
		{
			return;
		}
		String[] words = in.split(",");
		for(String word: words)
		{
			if(word.matches("[a-z,A-Z,-, ]+"));
			{
				Dictionary.add(word.trim());
			}
		}
	}
	
	@Override
	public void useTestDictionary(String dictionary) throws IOException 
	{
		Dictionary = null;
		Dictionary = new MyTrie();
		String[] words = dictionary.split(",");
		for(String word: words)
		{
			if(word.matches("[a-z,A-Z,-, ]+"));
			{
				Dictionary.add(word.trim());
			}
		}
	}

	@Override
	public ArrayList<String> suggestSimilarWord(String inputWord) throws NoSimilarWordFoundException
	{
		inputWord = inputWord.trim().toLowerCase();
		ArrayList<String> edits = new ArrayList<String>();
		ArrayList<String> suggestions = new ArrayList<String>();
		
		if(Dictionary.find(inputWord) != null)
		{
			suggestions.add(inputWord);
			return suggestions;
		}
		
		edits.add(inputWord);
		edit(edits);
		
		edit(edits);
		for(int i = 0; i < edits.size(); i++)
		{
			if(Dictionary.find(edits.get(i)) != null)
			{
				if(!suggestions.contains(edits.get(i)))
				{
					suggestions.add(edits.get(i));
				}
			}
		}
		
		if(suggestions.size() > 0)
		{
			Collections.sort(suggestions);
			return suggestions;
		}
		
		throw new NoSimilarWordFoundException();
	}
	
	private void edit(ArrayList<String> edits)
	{
		int k = edits.size();
		for(int i = 0; i < k; i++)
		{
			deletion(edits.get(i), edits);
			transpostion(edits.get(i), edits);
			alteration(edits.get(i), edits);
			addition(edits.get(i), edits);
		}
	}
	
	private void deletion(String word, ArrayList<String> edits)
	{
		for(int i = 0; i < word.length(); i++)
		{
			StringBuilder sb = new StringBuilder(word);
			sb.deleteCharAt(i);
			edits.add(sb.toString());
		}
	}
	
	private void transpostion(String word, ArrayList<String> edits)
	{
		for(int i = 0; i < word.length() - 1; i++)
		{
			StringBuilder sb = new StringBuilder(word);
			char c = sb.charAt(i);
			sb.deleteCharAt(i);
			sb.insert(i + 1, c);
			edits.add(sb.toString());
		}
	}
	
	private void alteration(String word, ArrayList<String> edits)
	{
		for(int i = 0; i < 28; i++)
		{
			char c = 'a';
			c += i;
			if(i==26) //white space
			{
				c = ' ';
			}
			else if(i==27) //dash
			{
				c = '-';
			}
			for(int j = 0; j < word.length(); j++)
			{
				StringBuilder sb = new StringBuilder(word);
				sb.deleteCharAt(j);
				sb.insert(j, c);
				edits.add(sb.toString());
			}
		}
	}
	
	private void addition(String word, ArrayList<String> edits)
	{
		for(int i = 0; i < 28; i++)
		{
			char c = 'a';
			c += i;
			if(i==26) //white space
			{
				c = ' ';
			}
			else if(i==27) //dash
			{
				c = '-';
			}
			for(int j = 0; j <= word.length(); j++)
			{
				StringBuilder sb = new StringBuilder(word);
				sb.insert(j, c);
				edits.add(sb.toString());
			}
		}
	}
	
	@Override
	public boolean foundWord(String inputWord) 
	{
		inputWord = inputWord.trim();
		if(inputWord.matches("[a-z,A-Z,-, ]+"));
		{
			if(Dictionary.find(inputWord) == null)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}
}
