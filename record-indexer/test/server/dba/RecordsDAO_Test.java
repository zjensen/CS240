package server.dba;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.DatabaseException;
import shared.model.Record;

public class RecordsDAO_Test 
{

	private Database db;
	private RecordsDAO rDAO;
	
	@Before
	public void setup() throws DatabaseException 
	{
		Database.initialize();
		db = new Database();
		db.startTransaction();
		rDAO = db.getRecordsDAO();
		ArrayList<Record> recordList = rDAO.getAll();
		for(int i=0;i<recordList.size();i++)
		{
			rDAO.delete(recordList.get(i));
		}
	}
	
	@After
	public void teardown() throws DatabaseException 
	{
		db.endTransaction(false);
		db = null;
		rDAO = null;
	}
	
	@Test
	public void testAdd() throws DatabaseException 
	{
		Record r1 = new Record(3,6,3,56);
		Record r2 = new Record(2,6,865,3);
		int i = rDAO.add(r1);
		int j = rDAO.add(r2);
		r1.setRecordID(i);
		r2.setRecordID(j);
		ArrayList<Record> recordList = rDAO.getAll();
		assertEquals(recordList.size(), 2);
		assertEquals(rDAO.get(i),r1);
		assertEquals(rDAO.get(j),r2);
	}
	
	@Test
	public void testUpdate() throws DatabaseException
	{
		Record r1 = new Record(1,3,4,2);
		Record r2 = new Record(1,5,2,66);
		int i = rDAO.add(r1);
		int j = rDAO.add(r2);
		r1.setRecordID(i);
		r2.setRecordID(j);
		r1.setRow(2);
		r1.setBatchID(3);
		r2.setProjectID(99);
		r2.setRow(8);
		rDAO.update(r1);
		rDAO.update(r2);
		ArrayList<Record> recordList = rDAO.getAll();
		assertEquals(recordList.size(), 2);
		assertEquals(rDAO.get(i),r1);
		assertEquals(rDAO.get(j),r2);
	}
	
	@Test
	public void testDelete() throws DatabaseException
	{
		Record r1 = new Record(1,2,3,4);
		Record r2 = new Record(1,3,2,5);
		int i = rDAO.add(r1);
		int j = rDAO.add(r2);
		r1.setRecordID(i);
		r2.setRecordID(j);
		rDAO.delete(r1);
		rDAO.delete(r2);
		ArrayList<Record> recordList = rDAO.getAll();
		assertEquals(recordList.size(), 0);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException 
	{
		Record r1 = new Record();
		rDAO.add(r1);
		rDAO.delete(r1);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException 
	{
		Record r1 = new Record();
		rDAO.update(r1);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException 
	{
		Record r1 = new Record();
		rDAO.delete(r1);
	}
}
