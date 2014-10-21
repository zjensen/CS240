package server.dba;

import java.sql.*;
import java.util.ArrayList;

import server.DatabaseException;
import shared.model.Field;
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
	 * @throws DatabaseException 
	 */
	public ArrayList<Field> getAll() throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Field> fieldList = new ArrayList<Field>();
		try
		{
			String sql = "SELECT * FROM fields";
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				Field field = new Field();
				field.setFieldID(rs.getInt(1));
				field.setProjectID(rs.getInt(2));
				field.setTitle(rs.getString(3));
				field.setXCoord(rs.getInt(4));
				field.setWidth(rs.getInt(5));
				field.setHelpHTML(rs.getString(6));
				field.setKnownData(rs.getString(7));
				field.setColumn(rs.getInt(8));
				fieldList.add(field);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException();
		}
		return fieldList;
	}
	/**
	 * gets all fields in the specified project
	 * @return an Array List containing all fields in the project
	 * @param projectID - project you want to find fields for
	 * @throws DatabaseException 
	 */
	public ArrayList<Field> getAll(int projectID) throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Field> fieldList = new ArrayList<Field>();
		try
		{
			String sql = "SELECT * FROM fields WHERE projectID = " + Integer.toString(projectID);
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				Field field = new Field();
				field.setFieldID(rs.getInt(1));
				field.setProjectID(rs.getInt(2));
				field.setTitle(rs.getString(3));
				field.setXCoord(rs.getInt(4));
				field.setWidth(rs.getInt(5));
				field.setHelpHTML(rs.getString(6));
				field.setKnownData(rs.getString(7));
				field.setColumn(rs.getInt(8));
				fieldList.add(field);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException();
		}
		return fieldList;
	}
	/**
	 * Searches the database for a field that matches the desired ID
	 * @param fieldID - the ID of the field you want
	 * @return field = the field belonging to the ID
	 * @throws DatabaseException 
	 */
	public Field get(int fieldID) throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Field field = new Field();
		try
		{
			String sql = "SELECT * FROM fields WHERE fieldID = " + Integer.toString(fieldID);
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				field.setFieldID(rs.getInt(1));
				field.setProjectID(rs.getInt(2));
				field.setTitle(rs.getString(3));
				field.setXCoord(rs.getInt(4));
				field.setWidth(rs.getInt(5));
				field.setHelpHTML(rs.getString(6));
				field.setKnownData(rs.getString(7));
				field.setColumn(rs.getInt(8));
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException();
		}
		return field;
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
			String sql = "INSERT INTO fields (projectID, title, xCoord, width, helpHTML, knownData, \"column\") VALUES (?,?,?,?,?,?,?)";
			
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
			System.out.println( e.getClass().getName() + ": " + e.getMessage() );
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
		return fieldID;
	}
	/**
	 * finds the field in the database, and replaces it's previous data with the current data
	 * @param field
	 * @throws DatabaseException 
	 */
	public void update(Field field) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try
		{
			String sql = 	"UPDATE fields " + 
							"set projectID = ?, title = ?, xCoord = ?, " + 
							"width = ?, helpHTML = ?, knownData = ?, \"column\" = ? " +
							"WHERE fieldID = ?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, field.getProjectID());
			stmt.setString(2, field.getTitle());
			stmt.setInt(3, field.getXCoord());
			stmt.setInt(4, field.getWidth());
			stmt.setString(5, field.getHelpHTML());
			stmt.setString(6, field.getKnownData());
			stmt.setInt(7, field.getColumn());
			stmt.setInt(8, field.getFieldID());
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
			System.out.println( e.getClass().getName() + ": " + e.getMessage() );
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
	 * removes a field from the database
	 * @param field
	 * @throws DatabaseException 
	 */
	public void delete(Field field) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try
		{
			String sql = "DELETE FROM fields WHERE fieldID = " + Integer.toString(field.getFieldID());
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
