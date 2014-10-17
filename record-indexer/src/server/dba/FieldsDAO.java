package server.dba;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.DatabaseException;
import shared.model.Field;
import shared.model.Project;
/**
 * Database access object for fields
 * @author zsjensen
 *
 */
public class FieldsDAO 
{
	private Database db;
	/**
	 * Constructs object and sets database
	 * @param db
	 */
	public FieldsDAO(Database db) 
	{
		this.db = db;
	}
	
	/**
	 * gets all fields in the database
	 * @return an Array List containing all fields in the database
	 */
	public ArrayList<Field> getAll()
	{
		return null;
	}
	/**
	 * gets all fields in the specified project
	 * @return an Array List containing all fields in the project
	 * @param projectID - project you want to find fields for
	 */
	public ArrayList<Field> getAll(int projectID)
	{
		return null;
	}
	/**
	 * Searches the database for a field that matches the desired ID
	 * @param fieldID - the ID of the field you want
	 * @return field = the field belonging to the ID
	 */
	public Field get(int fieldID)
	{
		
		return null;
	}
	/**
	 * Adds field to the database
	 * @param field
	 * @throws DatabaseException 
	 */
	public int add(Field field) throws DatabaseException
	{
		PreparedStatement stmt = null; 
		Statement keyStmt = null; 
		ResultSet keyRS = null;
		int fieldID;
		try
		{
			String sql = "INSERT INTO projects (projectID, title, xCoord, width, helpHTML, knownData, column) VALUES (?,?,?,?,?,?,?)";
			
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1,field.getProjectID());
			stmt.setString(2, field.getTitle());
			stmt.setInt(3, field.getXCoord());
			stmt.setInt(4, field.getWidth());
			stmt.setString(5, field.getHelpHTML());
			stmt.setString(6, field.getKnownData());
			stmt.setInt(7, field.getColumn());
			if (stmt.executeUpdate() == 1) 
			{
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()"); 
				keyRS.next();
				fieldID = keyRS.getInt(1);
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
		return fieldID;
	}
	/**
	 * finds the field in the database, and replaces it's previous data with the current data
	 * @param field
	 */
	public void update(Field field)
	{
		
	}
	/**
	 * removes a field from the database
	 * @param field
	 */
	public void delete(Field field)
	{
		
	}
}
