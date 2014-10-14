package server.dba;

import java.util.ArrayList;

import shared.model.Batch;
import shared.model.Project;

public class BatchesDAO 
{
	private Database db;

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
	 * @param project - project you want values for
	 * @returns - ArrayList containing all batches in the project
	 */
	public ArrayList<Batch> getAll(Project project)
	{	
		return null;
	}
	/**
	 * Searches the database for a batch that matches the desired ID
	 * @param batchID - the ID of the batch you want
	 * @return batch = the batch belonging to the ID
	 */
	public Batch get(int batchID)
	{
		
		return null;
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
