package client;

import org.junit.*;
import static org.junit.Assert.*;

public class ClientUnitTests {
	
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}
	
	@Test
	public void test_1() {		
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}

	public static void main(String[] args) {

		String[] testClasses = new String[] {
				"client.ClientUnitTests",
				"client.gui.QualityChecker_Test"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}

