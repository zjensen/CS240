package server.dba;
import java.util.ArrayList;

import shared.model.User;

public class UsersDAO 
{
	private Database db;

	public UsersDAO(Database db) 
	{
		this.db = db;
	}
	
	/**
	 * @return ArrayList containing all users in the database
	 */
	public ArrayList<User> getAll()
	{
		return null;
	}
	/**
	 * Searches the database for a user that matches the desired ID
	 * @param userID - the ID of the user you want
	 * @return user = the user belonging to the ID
	 */
	public User get(int userID)
	{
		
		return null;
	}
	/**
	 * Adds user to the database
	 * @param user
	 */
	public void add(User user)
	{
		
	}
	/**
	 * finds the user in the database, and replaces his previoud data with the current data
	 * @param user
	 */
	public void update(User user)
	{
		
	}
	/**
	 * removes a user from the database
	 * @param user
	 */
	public void delete(User user)
	{
		
	}
}
