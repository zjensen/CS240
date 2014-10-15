package server.dba;

import java.util.ArrayList;

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
	 */
	public ArrayList<Value> getAll()
	{
		return null;
	}
	/**
	 * Searches the database for a value that matches the desired ID
	 * @param valueID - the ID of the value you want
	 * @return value = the value belonging to the ID
	 */
	public Value get(int valueID)
	{
		
		return null;
	}
	/**
	 * Adds value to the database
	 * @param value
	 */
	public void add(Value value)
	{
		
	}
	/**
	 * finds the value in the database, and replaces it's previous data with the current data
	 * @param value
	 */
	public void update(Value value)
	{
		
	}
	/**
	 * removes a value from the database
	 * @param value
	 */
	public void delete(Value value)
	{
		
	}
	
}
