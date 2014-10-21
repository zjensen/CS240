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
	 * @throws DatabaseException 
	 */
	public ArrayList<Project> getAll() throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Project> projectList = new ArrayList<Project>();
		try
		{
			String sql = "SELECT * FROM projects";
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				Project project = new Project();
				project.setProjectID(rs.getInt(1));
				project.setTitle(rs.getString(2));
				project.setRecordsPerImage(rs.getInt(3));
				project.setFirstYCoord(rs.getInt(4));
				project.setRecordHeight(rs.getInt(5));
				projectList.add(project);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException();
		}
		return projectList;
	}
	/**
	 * Searches the database for a project that matches the desired ID
	 * @param projectID - the ID of the project you want
	 * @return project = the project belonging to the ID
	 * @throws DatabaseException 
	 */
	public Project get(int projectID) throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Project project = new Project();
		try
		{
			String sql = "SELECT * FROM projects WHERE projectID = " + Integer.toString(projectID);
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				project.setProjectID(rs.getInt(1));
				project.setTitle(rs.getString(2));
				project.setRecordsPerImage(rs.getInt(3));
				project.setFirstYCoord(rs.getInt(4));
				project.setRecordHeight(rs.getInt(5));
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException();
		}
		return project;
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
			//System.out.println( e.getClass().getName() + ": " + e.getMessage() );
			throw new DatabaseException();
		}
		finally
		{
			if(stmt != null)
			{
				try
				{
					stmt.close();
					keyStmt.close();
					keyRS.close();
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
	 * @throws DatabaseException 
	 */
	public void update(Project project) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try
		{
			String sql = 	"UPDATE projects " + 
							"set title = ?, recordsPerImage = ?, firstYCoord = ?, recordHeight = ? " + 
							"WHERE projectID = ?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString(1, project.getTitle());
			stmt.setInt(2, project.getRecordsPerImage());
			stmt.setInt(3, project.getFirstYCoord());
			stmt.setInt(4, project.getRecordHeight());
			stmt.setInt(5, project.getProjectID());
			if (stmt.executeUpdate() == 1) 
			{
				//works
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
	}
	/**
	 * removes a project from the database
	 * @param project
	 * @throws DatabaseException 
	 */
	public void delete(Project project) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try
		{
			String sql = "DELETE FROM projects WHERE projectID = " + Integer.toString(project.getProjectID());
			stmt = db.getConnection().prepareStatement(sql);
			if (stmt.executeUpdate() == 1) 
			{
				//It worked!
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
	}
}
