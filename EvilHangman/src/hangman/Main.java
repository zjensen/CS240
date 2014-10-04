package hangman;

import hangman.EvilHangmanGame.GuessAlreadyMadeException;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws GuessAlreadyMadeException 
	{
		myGame game = new myGame();
		if(args.length != 3)
		{
			System.out.println("Usage: java [your main class name] dictionary wordLength guesses");
			return;
		}
		File dictionary = new File(args[0]);
		int wordLength = Integer.valueOf(args[1]);
		game.startGame(dictionary, wordLength);
		int guesses = Integer.valueOf(args[2]);
		if(guesses < 1 || wordLength < 2)
		{
			System.out.println("Nah");
			return;
		}
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<wordLength;i++)
		{
			sb.append("-");
		}
		String currentWord = sb.toString();
		boolean win = false;
		ArrayList<Character> charGuesses = new ArrayList<Character>();
		while(guesses > 0)
		{
			System.out.println("You have " + guesses + " guesses left");
			System.out.println("Used letters: " + game.returnGuesses());
			System.out.println("Word: " + currentWord);
			System.out.print("Enter guess: ");
			Scanner in = new Scanner(System.in);
			String s = in.next().toLowerCase();
			while(s.length() > 1 || charGuesses.contains(s.charAt(0)) || !Character.isLetter(s.charAt(0)))
			{
				if(charGuesses.contains(s.charAt(0)))
				{
					System.out.println("You already used that letter");
				}
				else
				{
					System.out.println("Invalid input");
				}
				System.out.print("Enter guess: ");
				s = in.next().toLowerCase();
			}
			char c = s.charAt(0);
			charGuesses.add(c);
			game.makeGuess(c);
			guesses--;
			currentWord = game.myDictionary.currentWord.toString();
			int count =0;
			if(currentWord.contains("" + c))
			{
				for(int i=0;i<wordLength;i++)
				{
					if(currentWord.charAt(i) == c)
					{
						count++;
					}
				}
			}
			if(count==1)
			{
				System.out.println("Yes, there is 1 " + c);
			}
			else if(count > 1)
			{
				System.out.println("Yes, there are " + count +" "+ c + "\'s");
			}
			else
			{
				System.out.println("Sorry, there are no " + c + "\'s");
			}
			
			if(!currentWord.contains("" + "-"))
			{
				win = true;
				guesses = 0;
			}
		}
		if(win)
		{
			System.out.println("You win!");
			System.out.println("The word was: " + currentWord);
		}
		else
		{
			System.out.println("You lose!");
			System.out.println("The word was: " + game.words.get(0));
		}
	}
}
