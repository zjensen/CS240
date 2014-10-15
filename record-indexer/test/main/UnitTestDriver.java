package main;

public class UnitTestDriver 
{

	public static void main(String[] args) 
	{
		String[] testClasses = new String[] {
				"server.dba.BatchesDAOTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);

	}

}
