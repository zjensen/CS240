package server.dba;
import java.util.ArrayList;

import shared.model.Batch;
import shared.model.Project;
import shared.model.Record;

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
	 */
	public ArrayList<Record> getAll()
	{
		return null;
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
	 */
	public Record get(int recordID)
	{
		
		return null;
	}
	/**
	 * Adds record to the database
	 * @param record
	 */
	public void add(Record record)
	{
		
	}
	/**
	 * finds the record in the database, and replaces it's previous data with the current data
	 * @param record
	 */
	public void update(Record record)
	{
		
	}
	/**
	 * removes a record from the database
	 * @param record
	 */
	public void delete(Record record)
	{
		
	}
}
