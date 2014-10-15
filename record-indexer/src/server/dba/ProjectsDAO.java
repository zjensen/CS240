package server.dba;

import java.util.ArrayList;

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
	 */
	public ArrayList<Project> getAll()
	{
		return null;
	}
	/**
	 * Searches the database for a project that matches the desired ID
	 * @param projectID - the ID of the project you want
	 * @return project = the project belonging to the ID
	 */
	public Project get(int projectID)
	{
		
		return null;
	}
	/**
	 * Adds project to the database
	 * @param project
	 */
	public void add(Project project)
	{
		
	}
	/**
	 * finds the project in the database, and replaces it's previous data with the current data
	 * @param project
	 */
	public void update(Project project)
	{
		
	}
	/**
	 * removes a project from the database
	 * @param project
	 */
	public void delete(Project project)
	{
		
	}
}
