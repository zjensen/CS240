package server;

import org.junit.* ;

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
		//assertEquals("OK", "OK");
		//assertTrue(true);
		//assertFalse(false);
	}

	public static void main(String[] args) 
	{
		String[] testClasses = new String[] 
		{
			"server.ServerUnitTests",
			"server.dba.ProjectsDAO_Test",
			"server.dba.BatchesDAO_Test",
			"server.dba.FieldsDAO_Test",
			"server.dba.RecordsDAO_Test",
			"server.dba.ValuesDAO_Test",
			"server.dba.UsersDAO_Test"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

