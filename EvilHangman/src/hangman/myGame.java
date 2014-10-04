package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Set;

public class myGame implements EvilHangmanGame 
{
	public ArrayList<String> words;
	public Dictionary myDictionary;
	public ArrayList<Character> guesses;
	
	public myGame() 
	{
		words = new ArrayList<String>();
		guesses = new ArrayList<Character>();
	}

	public String returnGuesses()
	{
		StringBuilder sb = new StringBuilder();
		Collections.sort(guesses);
		for(int i=0;i<guesses.size();i++)
		{
			sb.append(guesses.get(i) + " ");
		}
		return sb.toString();
	}

	@Override
	public void startGame(File dictionary, int wordLength) 
	{
		Scanner scan = null;
		try {
			scan = new Scanner(dictionary);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(scan.hasNext())
		{
			String s = scan.next().toLowerCase();
			if(s.length() == wordLength)
			{
				words.add(s);
			}
		}
		scan.close();
		myDictionary = new Dictionary(words,wordLength);
	}

	@Override
	public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException 
	{
		Set<String> toReturn;
		if(guesses.contains(guess))
		{
			throw new GuessAlreadyMadeException();
		}
		guesses.add(guess);
		toReturn = myDictionary.guess(guess);
		words.clear();
		words.addAll(toReturn);
		return toReturn;
	}

}
