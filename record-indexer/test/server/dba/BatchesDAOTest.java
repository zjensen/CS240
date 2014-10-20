package server.dba;
import java.util.ArrayList;

import org.junit.*;

import server.DatabaseException;
import shared.model.Batch;
import static org.junit.Assert.* ;


public class BatchesDAOTest 
{
	private Database db;
	private BatchesDAO bDAO;
	@Before
	public void setup() throws DatabaseException 
	{
		db = new Database();
		db.initialize();
		db.startTransaction();
		bDAO = db.getBatchesDAO();
	}
	
	@After
	public void teardown() throws DatabaseException 
	{
		db.endTransaction(false);
		db = null;
		bDAO = null;
	}
	
	@Test
	public void testAdd() throws DatabaseException 
	{
		Batch b1 = new Batch(1,2,3,true,"trueURL");
		Batch b2 = new Batch(2,3,4,false,"falseURL");
		bDAO.add(b1);
		bDAO.add(b2);
		ArrayList<Batch> bl = bDAO.getAll();
		assertEquals(bl.size(), 2);
		assertEquals(bDAO.get(1),b1);
		assertEquals(bDAO.get(2),b2);
	
		assertEquals("OK", "OK");
		assertTrue(true);
		assertFalse(false);
	}
}
