package server.dba;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.DatabaseException;
import shared.model.User;

public class UsersDAO_Test 
{

	private Database db;
	private UsersDAO uDAO;
	
	@Before
	public void setup() throws DatabaseException 
	{
		db = new Database();
		db.initialize();
		db.startTransaction();
		uDAO = db.getUsersDAO();
		ArrayList<User> userList = uDAO.getAll();
		for(int i=0;i<userList.size();i++)
		{
			uDAO.delete(userList.get(i));
		}
	}
	
	@After
	public void teardown() throws DatabaseException 
	{
		db.endTransaction(false);
		db = null;
		uDAO = null;
	}
	
	@Test
	public void testAdd() throws DatabaseException 
	{
		User u1 = new User();
		User u2 = new User(0,"username","password","firstName","lastName","email",8987,99);
		int i = uDAO.add(u1);
		int j = uDAO.add(u2);
		u1.setUserID(i);
		u2.setUserID(j);
		u2.setBatchID(99);
		ArrayList<User> userList = uDAO.getAll();
		assertEquals(userList.size(), 2);
		assertEquals(uDAO.get(i),u1);
		assertEquals(uDAO.get(j),u2);
	}
	
	@Test
	public void testUpdate() throws DatabaseException
	{
		User u1 = new User();
		User u2 = new User(0,"username","password","firstName","lastName","email",434,9999);
		int i = uDAO.add(u1);
		int j = uDAO.add(u2);
		u1.setUserID(i);
		u2.setUserID(j);
		u1.setUsername("Changed 1");
		u1.setIndexedRecords(3);
		u2.setEmail("Changed 2");
		u2.setBatchID(3);
		uDAO.update(u1);
		uDAO.update(u2);
		ArrayList<User> userList = uDAO.getAll();
		assertEquals(userList.size(), 2);
		assertEquals(uDAO.get(i),u1);
		assertEquals(uDAO.get(j),u2);
	}
	
	@Test
	public void testDelete() throws DatabaseException
	{
		User u1 = new User();
		User u2 = new User(0,"username","password","firstName","lastName","email",434,9999);
		int i = uDAO.add(u1);
		int j = uDAO.add(u2);
		u1.setUserID(i);
		u2.setUserID(j);
		uDAO.delete(u1);
		uDAO.delete(u2);
		ArrayList<User> userList = uDAO.getAll();
		assertEquals(userList.size(), 2);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException 
	{
		User u1 = new User();
		uDAO.add(u1);
		uDAO.delete(u1);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException 
	{
		User u1 = new User();
		uDAO.update(u1);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException 
	{
		User u1 = new User();
		uDAO.delete(u1);
	}

}
