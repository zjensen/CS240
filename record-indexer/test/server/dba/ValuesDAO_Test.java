package server.dba;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.DatabaseException;
import shared.model.Value;

public class ValuesDAO_Test 
{
	private Database db;
	private ValuesDAO vDAO;
	
	@Before
	public void setup() throws DatabaseException 
	{
		db = new Database();
		db.initialize();
		db.startTransaction();
		vDAO = db.getValuesDAO();
		ArrayList<Value> valueList = vDAO.getAll();
		for(int i=0;i<valueList.size();i++)
		{
			vDAO.delete(valueList.get(i));
		}
	}
	
	@After
	public void teardown() throws DatabaseException 
	{
		db.endTransaction(false);
		db = null;
		vDAO = null;
	}
	
	@Test
	public void testAdd() throws DatabaseException 
	{
		Value v1 = new Value(1,2,3,4,5,null);
		Value v2 = new Value(1,32,2,5,21,"data2");
		int i = vDAO.add(v1);
		int j = vDAO.add(v2);
		v1.setValueID(i);
		v2.setValueID(j);
		ArrayList<Value> valueList = vDAO.getAll();
		assertEquals(valueList.size(), 2);
		assertEquals(vDAO.get(i),v1);
		assertEquals(vDAO.get(j),v2);
	}
	
	@Test
	public void testUpdate() throws DatabaseException
	{
		Value v1 = new Value(1,2,3,4,5,null);
		Value v2 = new Value(1,32,2,5,21,"data2");
		int i = vDAO.add(v1);
		int j = vDAO.add(v2);
		v1.setValueID(i);
		v2.setValueID(j);
		v1.setBatchID(2);
		v1.setData("v1 data");
		v2.setProjectID(99);
		v2.setData(null);
		vDAO.update(v1);
		vDAO.update(v2);
		ArrayList<Value> valueList = vDAO.getAll();
		assertEquals(valueList.size(), 2);
		assertEquals(vDAO.get(i),v1);
		assertEquals(vDAO.get(j),v2);
	}
	
	@Test
	public void testDelete() throws DatabaseException
	{
		Value v1 = new Value(1,2,3,4,5,null);
		Value v2 = new Value(1,32,2,5,21,"data2");
		int i = vDAO.add(v1);
		int j = vDAO.add(v2);
		v1.setValueID(i);
		v2.setValueID(j);
		vDAO.delete(v1);
		vDAO.delete(v2);
		ArrayList<Value> valueList = vDAO.getAll();
		assertEquals(valueList.size(), 0);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException 
	{
		Value v1 = new Value();
		vDAO.add(v1);
		vDAO.delete(v1);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException 
	{
		Value v1 = new Value();
		vDAO.update(v1);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException 
	{
		Value v1 = new Value();
		vDAO.delete(v1);
	}
}
