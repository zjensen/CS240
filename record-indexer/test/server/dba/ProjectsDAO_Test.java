package server.dba;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.DatabaseException;
import shared.model.Project;

public class ProjectsDAO_Test 
{
	private Database db;
	private ProjectsDAO pDAO;
	
	@Before
	public void setup() throws DatabaseException 
	{
		db = new Database();
		db.initialize();
		db.startTransaction();
		pDAO = db.getProjectsDAO();
		ArrayList<Project> projectList = pDAO.getAll();
		for(int i=0;i<projectList.size();i++)
		{
			pDAO.delete(projectList.get(i));
		}
	}
	
	@After
	public void teardown() throws DatabaseException 
	{
		db.endTransaction(false);
		db = null;
		pDAO = null;
	}
	
	@Test
	public void testAdd() throws DatabaseException 
	{
		Project p1 = new Project(1,"p1",3,1,5);
		Project p2 = new Project(2,"p2",4,2,5);
		int i = pDAO.add(p1);
		int j = pDAO.add(p2);
		p1.setProjectID(i);
		p2.setProjectID(j);
		ArrayList<Project> projectList = pDAO.getAll();
		assertEquals(projectList.size(), 2);
		assertEquals(pDAO.get(i),p1);
		assertEquals(pDAO.get(j),p2);
	}
	
	@Test
	public void testUpdate() throws DatabaseException
	{
		Project p1 = new Project(1,"p1",3,1,5);
		Project p2 = new Project(2,"p2",4,2,5);
		int i = pDAO.add(p1);
		int j = pDAO.add(p2);
		p1.setProjectID(i);
		p2.setProjectID(j);
		p1.setTitle("Changed 1");
		p2.setTitle("Changed 2");
		pDAO.update(p1);
		pDAO.update(p2);
		ArrayList<Project> projectList = pDAO.getAll();
		assertEquals(projectList.size(), 2);
		assertEquals(pDAO.get(i),p1);
		assertEquals(pDAO.get(j),p2);
	}
	
	@Test
	public void testDelete() throws DatabaseException
	{
		Project p1 = new Project(1,"p1",3,1,5);
		Project p2 = new Project(2,"p2",4,2,5);
		int i = pDAO.add(p1);
		int j = pDAO.add(p2);
		p1.setProjectID(i);
		p2.setProjectID(j);
		pDAO.delete(p1);
		pDAO.delete(p2);
		ArrayList<Project> projectList = pDAO.getAll();
		assertEquals(projectList.size(), 0);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidAdd() throws DatabaseException 
	{
		Project p1 = new Project();
		pDAO.add(p1);
		pDAO.delete(p1);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidUpdate() throws DatabaseException 
	{
		Project p1 = new Project();
		pDAO.update(p1);
	}
	
	@Test(expected=DatabaseException.class)
	public void testInvalidDelete() throws DatabaseException 
	{
		Project p1 = new Project();
		pDAO.delete(p1);
	}

}
