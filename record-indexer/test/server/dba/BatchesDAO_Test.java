package server.dba;
import java.util.ArrayList;

import org.junit.*;

import server.DatabaseException;
import shared.model.Batch;
import static org.junit.Assert.* ;


public class BatchesDAO_Test 
{
	private Database db;
	private BatchesDAO bDAO;
	
	@Before
	public void setup() throws DatabaseException 
	{
		Database.initialize();
		db = new Database();
		db.startTransaction();
		bDAO = db.getBatchesDAO();
		ArrayList<Batch> batchList = bDAO.getAll();
		for(int i=0;i<batchList.size();i++)
		{
			bDAO.delete(batchList.get(i));
		}
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
		Batch b1 = new Batch(0,2,"b1",true,5);
		Batch b2 = new Batch(0,99,"b2",false,5);
		int i = bDAO.add(b1);
		int j = bDAO.add(b2);
		b1.setBatchID(i);
		b2.setBatchID(j);
		ArrayList<Batch> bl = bDAO.getAll();
		assertEquals(bl.size(), 2);
		assertEquals(bDAO.get(i),b1);
		assertEquals(bDAO.get(j),b2);
	}
	
	@Test
	public void testUpdate() throws DatabaseException
	{
		Batch b1 = new Batch(0,2,"b1",true,5);
		Batch b2 = new Batch(0,99,"b2",false,5);
		int i = bDAO.add(b1);
		int j = bDAO.add(b2);
		b1.setBatchID(i);
		b2.setBatchID(j);
		b1.setFile("Changed 1");
		b2.setFile("Changed 2");
		bDAO.update(b1);
		bDAO.update(b2);
		ArrayList<Batch> bl = bDAO.getAll();
		assertEquals(bl.size(), 2);
		assertEquals(bDAO.get(i),b1);
		assertEquals(bDAO.get(j),b2);
	}
	
	@Test
	public void testDelete() throws DatabaseException
	{
		Batch b1 = new Batch(0,2,"b1",true,5);
		Batch b2 = new Batch(0,99,"b2",false,5);
		int i = bDAO.add(b1);
		int j = bDAO.add(b2);
		b1.setBatchID(i);
		b2.setBatchID(j);
		bDAO.delete(b1);
		bDAO.delete(b2);
		ArrayList<Batch> bl = bDAO.getAll();
		assertEquals(bl.size(), 0);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException 
	{
		Batch b1 = new Batch();
		bDAO.add(b1);
		bDAO.delete(b1);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException 
	{
		Batch b1 = new Batch();
		bDAO.update(b1);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException 
	{
		Batch b1 = new Batch();
		bDAO.delete(b1);
	}
}
