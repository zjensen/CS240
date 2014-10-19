package server.dba;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.DatabaseException;
import shared.model.Batch;
import shared.model.Field;
import shared.model.Project;
import shared.model.Record;
import shared.model.Value;
/**
 * Database access object for values
 * @author zsjensen
 *
 */
public class ValuesDAO 
{
	private Database db;
	/**
	 * constructs object and sets database
	 * @param db
	 */
	public ValuesDAO(Database db) 
	{
		this.db = db;
	}
	
	/**
	 * Searches for the specified project then returns its values
	 * @param project - the project you are searching in
	 * @return all values within that project
	 */
	public ArrayList<Value> getAll(Project project)
	{
		
		return null;
	}
	/**
	 * Searches for the specified field then returns its values
	 * @param field - the field you are searching in
	 * @return all values from that field
	 */
	public ArrayList<Value> getAll(Field field)
	{
		return null;
	}
	/**
	 * Searches for the specified batch then returns its values
	 * @param batch - the batch you are searching in
	 * @return all values from that batch
	 */
	public ArrayList<Value> getAll(Batch batch)
	{
		return null;
	}
	/**
	 * Searches for the specified record then returns its values
	 * @param record - the record you are searching in
	 * @return all values from that record
	 */
	public ArrayList<Value> getAll(Record record)
	{
		return null;
	}
	/**
	 * returns all values in the database
	 * @return arrayList of values
	 * @throws DatabaseException 
	 */
	public ArrayList<Value> getAll() throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Value> valueList = new ArrayList<Value>();
		try
		{
			String sql = "SELECT * FROM values";
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				Value value = new Value();
				value.setValueID(rs.getInt(1));
				value.setRecordID(rs.getInt(2));
				value.setFieldID(rs.getInt(3));
				value.setBatchID(rs.getInt(4));
				value.setProjectID(rs.getInt(5));
				value.setData(rs.getString(6));
				valueList.add(value);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException();
		}
		return valueList;
	}
	/**
	 * Searches the database for a value that matches the desired ID
	 * @param valueID - the ID of the value you want
	 * @return value = the value belonging to the ID
	 * @throws DatabaseException 
	 */
	public Value get(int valueID) throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Value value = new Value();
		try
		{
			String sql = "SELECT * FROM values WHERE valueID = " + Integer.toString(valueID);
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				value.setValueID(rs.getInt(1));
				value.setRecordID(rs.getInt(2));
				value.setFieldID(rs.getInt(3));
				value.setBatchID(rs.getInt(4));
				value.setProjectID(rs.getInt(5));
				value.setData(rs.getString(6));
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException();
		}
		return value;
	}
	/**
	 * Adds value to the database
	 * @param value
	 * @throws DatabaseException 
	 */
	public int add(Value value) throws DatabaseException
	{
		PreparedStatement stmt = null;
		Statement keyStmt = null; 
		ResultSet keyRS = null;
		int valueID;
		try
		{
			String sql = "INSERT INTO values (recordID, fieldID, batchID, projectID, data) VALUES (?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, value.getRecordID());
			stmt.setInt(2, value.getFieldID());
			stmt.setInt(3, value.getBatchID());
			stmt.setInt(4, value.getProjectID());
			stmt.setString(5, value.getData());
			if (stmt.executeUpdate() == 1) 
			{
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()"); 
				keyRS.next();
				valueID = keyRS.getInt(1);
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
					keyStmt.close();
					keyRS.close();
				}
				catch(SQLException e)
				{
					throw new DatabaseException();
				}
			}
		}
		return valueID;
	}
	/**
	 * finds the value in the database, and replaces it's previous data with the current data
	 * @param value
	 * @throws DatabaseException 
	 */
	public void update(Value value) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try
		{	
			String sql = 	"UPDATE values" + 
							"set recordID = ? fieldID = ?, batchID = ?, projectID = ?, data = ? " +
							"WHERE valueID = ?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, value.getRecordID());
			stmt.setInt(2, value.getFieldID());
			stmt.setInt(3, value.getBatchID());
			stmt.setInt(4, value.getProjectID());
			stmt.setString(5, value.getData());
			stmt.setInt(6, value.getValueID());
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
	 * removes a value from the database
	 * @param value
	 * @throws DatabaseException 
	 */
	public void delete(Value value) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try
		{
			String sql = "DELETE FROM values WHERE valueID = " + Integer.toString(value.getValueID());
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
