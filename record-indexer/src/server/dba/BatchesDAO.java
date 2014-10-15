package server.dba;
import java.sql.*;
import java.util.ArrayList;

import server.DatabaseException;
import shared.model.Batch;
import shared.model.Project;
/**
 * Database Access Object for batches
 * @author zsjensen
 *
 */
public class BatchesDAO 
{
	private Database db;
	/**
	 * Constructs object with database
	 * @param db
	 */
	public BatchesDAO(Database db) 
	{
		this.db = db;
	}
	
	/**
	 * Returns all batches in database
	 * @returns - ArrayList containing all Batches in the database
	 */
	public ArrayList<Batch> getAll()
	{
		return null;
	}
	/**
	 * Returns all batches in the specified project
	 * @param projectID - id of the project you want batches for
	 * @returns - ArrayList containing all batches in the project
	 */
	public ArrayList<Batch> getAll(int projectID)
	{	
		return null;
	}
	/**
	 * Searches the database for a batch that matches the desired ID
	 * @param batchID - the ID of the batch you want
	 * @return batch = the batch belonging to the ID
	 * @throws DatabaseException 
	 */
	public Batch get(int batchID) throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Batch batch = new Batch();
		try
		{
			String sql = "select * from batches where batchID = ?";
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				batch.setBatchID(rs.getInt(1));
				batch.setProjectID(rs.getInt(2));
				batch.setFile(rs.getString(3));
				batch.setCompleted(rs.getBoolean(4));
				batch.setUserID(rs.getInt(5));
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException();
		}
		return batch;
	}
	/**
	 * Adds batch to the database
	 * @param batch
	 */
	public void add(Batch batch)
	{
		
	}
	/**
	 * finds the batch in the database, and replaces it's previous data with the current data
	 * @param batch
	 */
	public void update(Batch batch)
	{
		
	}
	/**
	 * removes a batch from the database
	 * @param batch
	 */
	public void delete(Batch batch)
	{
		
	}
}
