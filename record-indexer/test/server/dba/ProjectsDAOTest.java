package server.dba;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.DatabaseException;
import shared.model.Project;

public class ProjectsDAOTest 
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
		ArrayList<Project> bl = pDAO.getAll();
		assertEquals(bl.size(), 2);
		assertEquals(pDAO.get(i),p1);
		assertEquals(pDAO.get(j),p2);
	}

}
