package server.dba;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import server.DatabaseException;
import shared.model.Batch;
import shared.model.Project;
import shared.model.Record;
/**
 * database access object for records
 * @author zsjensen
 *
 */
public class RecordsDAO 
{
	private Database db;

	public RecordsDAO(Database db) 
	{
		this.db = db;
	}
	
	/**
	 * gets all records in the database
	 * @return an Array List containing all records in the database
	 * @throws DatabaseException 
	 */
	public ArrayList<Record> getAll() throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Record> recordList = new ArrayList<Record>();
		try
		{
			String sql = "SELECT * FROM records";
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				Record record = new Record();
				record.setRecordID(rs.getInt(1));
				record.setBatchID(rs.getInt(2));
				record.setProjectID(rs.getInt(3));
				record.setRow(rs.getInt(4));
				recordList.add(record);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException();
		}
		return recordList;
	}
	/**
	 * gets all records belonging to a specific project
	 * @param project - project you want records from
	 * @return an Array List containing all records for that project
	 */
	public ArrayList<Record> getAll(Project project)
	{
		return null;
	}
	/**
	 * gets all records belonging to a specific Batch
	 * @param batch - image you want records for
	 * @return an Array List containing all records for the batch
	 */
	public ArrayList<Record> getAll(Batch batch)
	{
		return null;
	}
	/**
	 * Searches the database for a record that matches the desired ID
	 * @param recordID - the ID of the record you want
	 * @return record = the record belonging to the ID
	 * @throws DatabaseException 
	 */
	public Record get(int recordID) throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Record record = new Record();
		try
		{
			String sql = "SELECT * FROM records WHERE recordID = " + Integer.toString(recordID);
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				record.setRecordID(rs.getInt(1));
				record.setBatchID(rs.getInt(2));
				record.setProjectID(rs.getInt(3));
				record.setRow(rs.getInt(4));
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException();
		}
		return record;
	}
	/**
	 * Adds record to the database
	 * @param record
	 * @throws DatabaseException 
	 */
	public int add(Record record) throws DatabaseException
	{
		PreparedStatement stmt = null; 
		Statement keyStmt = null; 
		ResultSet keyRS = null;
		int recordID;
		try
		{
			String sql = "INSERT INTO records (batchID, projectID, row) VALUES (?,?,?)";
			
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1,record.getBatchID());
			stmt.setInt(2,record.getProjectID());
			stmt.setInt(3, record.getRow());
			if (stmt.executeUpdate() == 1) 
			{
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()"); 
				keyRS.next();
				recordID = keyRS.getInt(1);
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
		return recordID;
	}
	/**
	 * finds the record in the database, and replaces it's previous data with the current data
	 * @param record
	 * @throws DatabaseException 
	 */
	public void update(Record record) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try
		{
			String sql = 	"UPDATE records " + 
							"set batchID = ?, projectID = ?, row = ? " + 
							"WHERE recordID = ?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setInt(1, record.getBatchID());
			stmt.setInt(2, record.getProjectID());
			stmt.setInt(3, record.getRow());
			stmt.setInt(4, record.getRecordID());
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
	 * removes a record from the database
	 * @param record
	 * @throws DatabaseException 
	 */
	public void delete(Record record) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try
		{
			String sql = "DELETE FROM records WHERE recordID = " + Integer.toString(record.getRecordID());
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
