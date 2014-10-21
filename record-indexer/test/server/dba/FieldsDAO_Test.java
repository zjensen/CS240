package server.dba;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.DatabaseException;
import shared.model.*;

public class FieldsDAO_Test 
{

	private Database db;
	private FieldsDAO fDAO;
	
	@Before
	public void setup() throws DatabaseException 
	{
		db = new Database();
		db.initialize();
		db.startTransaction();
		fDAO = db.getFieldsDAO();
		ArrayList<Field> fieldList = fDAO.getAll();
		for(int i=0;i<fieldList.size();i++)
		{
			fDAO.delete(fieldList.get(i));
		}
	}
	
	@After
	public void teardown() throws DatabaseException 
	{
		db.endTransaction(false);
		db = null;
		fDAO = null;
	}
	
	@Test
	public void testAdd() throws DatabaseException 
	{
		Field f1 = new Field(0,2,6,"f1",3,4,"help",null);
		Field f2 = new Field(0,99,7,"f2",3,5,"help2","known2");
		int i = fDAO.add(f1);
		int j = fDAO.add(f2);
		f1.setFieldID(i);
		f2.setFieldID(j);
		ArrayList<Field> fieldList = fDAO.getAll();
		assertEquals(fieldList.size(), 2);
		assertEquals(fDAO.get(i),f1);
		assertEquals(fDAO.get(j),f2);
	}
	
	@Test
	public void testUpdate() throws DatabaseException
	{
		Field f1 = new Field(0,2,6,"f1",3,4,"help",null);
		Field f2 = new Field(0,99,7,"f2",3,5,"help2","known2");
		int i = fDAO.add(f1);
		int j = fDAO.add(f2);
		f1.setFieldID(i);
		f2.setFieldID(j);
		f1.setTitle("Changed 1");
		f1.setColumn(3);
		f2.setHelpHTML("Changed 2");
		f2.setKnownData(null);
		fDAO.update(f1);
		fDAO.update(f2);
		ArrayList<Field> fieldList = fDAO.getAll();
		assertEquals(fieldList.size(), 2);
		assertEquals(fDAO.get(i),f1);
		assertEquals(fDAO.get(j),f2);
	}
	
	@Test
	public void testDelete() throws DatabaseException
	{
		Field f1 = new Field(0,2,6,"f1",3,4,"help",null);
		Field f2 = new Field(0,99,7,"f2",3,5,"help2","known2");
		int i = fDAO.add(f1);
		int j = fDAO.add(f2);
		f1.setFieldID(i);
		f2.setFieldID(j);
		fDAO.delete(f1);
		fDAO.delete(f2);
		ArrayList<Field> fieldList = fDAO.getAll();
		assertEquals(fieldList.size(), 0);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException 
	{
		Field f1 = new Field();
		fDAO.add(f1);
		fDAO.delete(f1);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException 
	{
		Field f1 = new Field();
		fDAO.update(f1);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException 
	{
		Field f1 = new Field();
		fDAO.delete(f1);
	}
}
