package server.dba;

import java.util.ArrayList;

import shared.model.Field;
import shared.model.Project;

public class FieldsDAO 
{
	private Database db;

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
	 * @param project - project you want to find fields for
	 */
	public ArrayList<Field> getAll(Project project)
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
	 */
	public void add(Field field)
	{
		
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
