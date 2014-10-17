package server;

import org.junit.* ;
import static org.junit.Assert.* ;

public class ServerUnitTests 
{
	
	@Before
	public void setup() 
	{
		
	}
	
	@After
	public void teardown() 
	{
		
	}
	
	@Test
	public void test_1() 
	{
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	public static void main(String[] args) 
	{
		System.out.println("here");
		String[] testClasses = new String[] 
		{
			"server.ServerUnitTests",
			"test/server.dba.BatchesDAOTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

