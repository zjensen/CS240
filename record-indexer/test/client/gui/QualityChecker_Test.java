package client.gui;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.spell.Corrector;
import client.spell.SpellCorrector.NoSimilarWordFoundException;

public class QualityChecker_Test 
{
	private Corrector corrector;
	
	@Before
	public void setUp() throws Exception 
	{
		corrector = new Corrector();
	}

	@After
	public void tearDown() throws Exception
	{
		corrector = null;
	}

	@Test
	public void testSuggestions1() throws IOException, NoSimilarWordFoundException 
	{
		String dictionary = "a,b,c";
		corrector.useTestDictionary(dictionary);
		
		// Word is found
		assertTrue(corrector.foundWord("b"));
		
		// Word is NOT found
		assertFalse(corrector.foundWord("d"));
		
		// Suggested word is correct
		assertEquals(corrector.suggestSimilarWord("d").get(0),"a");
		assertEquals(corrector.suggestSimilarWord("acd").get(1),"c");
		assertEquals(corrector.suggestSimilarWord("cab").size(),3);
	}
	
	@Test
	public void testSuggestions2() throws IOException, NoSimilarWordFoundException 
	{
		String dictionary = "first-second, ThIrD FoUrTh ,fifth - SIXTH,a,b,c,d,aa,ab,ac,ad";
		corrector.useTestDictionary(dictionary);
		
		// WHITESPACE and DASHES
		assertEquals(corrector.suggestSimilarWord("thirfourth").get(0),"third fourth"); //edit 2
		assertEquals(corrector.suggestSimilarWord("fifth  SiXtH").get(0),"fifth - sixth"); //edit 1
		assertEquals(corrector.suggestSimilarWord("fifth-SiXtH").get(0),"fifth - sixth"); //edit 2
		
		assertEquals(corrector.suggestSimilarWord("th-ird-fourth").get(0),"third fourth"); //edit 2
		assertEquals(corrector.suggestSimilarWord("fifth---SiXtH").get(0),"fifth - sixth"); //edit 1
		assertEquals(corrector.suggestSimilarWord("fi-rst-seCOND- ").get(0),"first-second"); //edit 2
		
		// MULTIPLE SUGGESTIONS
		assertEquals(corrector.suggestSimilarWord("e").size(),8); //edit 1 and 2
		assertEquals(corrector.suggestSimilarWord("cab").get(3),"ac"); //edit 2 alphabetical
		assertEquals(corrector.suggestSimilarWord("ab").size(),1); //returns self	
	}
	
	@Test(expected = NoSimilarWordFoundException.class)
	public void testSuggestions3() throws IOException, NoSimilarWordFoundException 
	{
		String dictionary = "a,b,c,d";
		corrector.useTestDictionary(dictionary);
		
		// No words found
		corrector.suggestSimilarWord("a- d");
	}
	
	@Test(expected = NoSimilarWordFoundException.class)
	public void testSuggestions4() throws IOException, NoSimilarWordFoundException 
	{
		String dictionary = "a b,c-d";
		corrector.useTestDictionary(dictionary);
		
		// No words found
		corrector.suggestSimilarWord("de-");
	}
	
	@Test
	public void testWord() throws IOException 
	{
		String dictionary = "a,b,c-d,e f, g h ";
		corrector.useTestDictionary(dictionary);
		
		// Word is found
		assertTrue(corrector.foundWord("b"));
		assertTrue(corrector.foundWord("c-d"));
		assertTrue(corrector.foundWord("e f"));
		assertTrue(corrector.foundWord(" g h"));
		assertTrue(corrector.foundWord("g h "));
		
		// Word is NOT found
		assertFalse(corrector.foundWord("d"));
		assertFalse(corrector.foundWord("a-b"));
		assertFalse(corrector.foundWord(" "));
		assertFalse(corrector.foundWord("a-"));
		assertFalse(corrector.foundWord("g"));
		assertFalse(corrector.foundWord("e  f"));
		assertFalse(corrector.foundWord("cd"));
		assertFalse(corrector.foundWord("c - d"));
	}

}
