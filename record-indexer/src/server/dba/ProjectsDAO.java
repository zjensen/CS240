package server.dba;

import java.sql.*;
import java.util.ArrayList;

import server.DatabaseException;
import shared.model.Project;
/**
 * Database access object for projects
 * @author zsjensen
 *
 */
public class ProjectsDAO 
{
	private Database db;
	/**
	 * constructs object and sets database 
	 * @param db
	 */
	public ProjectsDAO(Database db) 
	{
		this.db = db;
	}
	
	/**
	 * gets all projects in the database
	 * @return an Array List containing all projects in the database
	 */
	public ArrayList<Project> getAll()
	{
		return null;
	}
	/**
	 * Searches the database for a project that matches the desired ID
	 * @param projectID - the ID of the project you want
	 * @return project = the project belonging to the ID
	 */
	public Project get(int projectID)
	{
		
		return null;
	}
	/**
	 * Adds project to the database
	 * @param project
	 * @throws DatabaseException 
	 */
	public int add(Project project) throws DatabaseException
	{
		PreparedStatement stmt = null; 
		Statement keyStmt = null; 
		ResultSet keyRS = null;
		int projectID;
		try
		{
			String sql = "INSERT INTO projects (title, recordsPerImage, firstYCoord, recordHeight) VALUES (?,?,?,?)";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString(1, project.getTitle());
			stmt.setInt(2, project.getRecordsPerImage());
			stmt.setInt(3, project.getFirstYCoord());
			stmt.setInt(4, project.getRecordHeight());
			if (stmt.executeUpdate() == 1) 
			{
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()"); 
				keyRS.next();
				projectID = keyRS.getInt(1);
			} 
			else
			{
				throw new DatabaseException();
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException();
		}
		finally
		{
			if(stmt != null)
			{
				try
				{
					stmt.close();
				}
				catch(SQLException e)
				{
					throw new DatabaseException();
				}
			}
		}
		return projectID;
	}
	/**
	 * finds the project in the database, and replaces it's previous data with the current data
	 * @param project
	 */
	public void update(Project project)
	{
		
	}
	/**
	 * removes a project from the database
	 * @param project
	 */
	public void delete(Project project)
	{
		
	}
}
