package server.dba;
import java.sql.*;
import java.util.ArrayList;

import server.DatabaseException;
import shared.model.User;
/**
 * database access object for users
 * @author zsjensen
 *
 */
public class UsersDAO 
{
	private Database db;

	public UsersDAO(Database db) 
	{
		this.db = db;
	}
	
	/**
	 * @return ArrayList containing all users in the database
	 * @throws DatabaseException 
	 */
	public ArrayList<User> getAll() throws DatabaseException
	{
		ArrayList<User> userList = new ArrayList<User>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM users";
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				User user = new User();
				user.setUserID(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setFirstName(rs.getString(4));
				user.setLastName(rs.getString(5));
				user.setEmail(rs.getString(6));
				user.setIndexedRecords(rs.getInt(7));
				user.setBatchID(rs.getInt(8));
				userList.add(user);
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException();
		}
		return userList;
	}
	/**
	 * Searches the database for a user that matches the desired ID
	 * @param userID - the ID of the user you want
	 * @return user = the user belonging to the ID
	 * @throws DatabaseException 
	 */
	public User get(int userID) throws DatabaseException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user = new User();
		try
		{
			String sql = "SELECT * FROM users WHERE userID = " + Integer.toString(userID);
			stmt = db.getConnection().prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				user.setUserID(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setFirstName(rs.getString(4));
				user.setLastName(rs.getString(5));
				user.setEmail(rs.getString(6));
				user.setIndexedRecords(rs.getInt(7));
				user.setBatchID(rs.getInt(8));
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException();
		}
		return user;
	}
	/**
	 * Adds user to the database
	 * @param user
	 * @throws DatabaseException 
	 */
	public int add(User user) throws DatabaseException
	{
		PreparedStatement stmt = null;
		Statement keyStmt = null; 
		ResultSet keyRS = null;
		int userID;
		try
		{
			String sql = "INSERT INTO users (username, password, firstName, lastName, email) VALUES (?, ?, ?, ?, ?)";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstName());
			stmt.setString(4, user.getLastName());
			stmt.setString(5, user.getEmail());
			if (stmt.executeUpdate() == 1) 
			{
				keyStmt = db.getConnection().createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()"); 
				keyRS.next();
				userID = keyRS.getInt(1);
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
		return userID;
	}
	/**
	 * finds the user in the database, and replaces his previoud data with the current data
	 * @param user
	 * @throws DatabaseException 
	 */
	public void update(User user) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try
		{
			String sql = 	"UPDATE users " +
							"username = ?, password = ?, firstName = ?, " + 
							"lastName = ?, email = ?, indexedRecords = ?, batchID = ? " +
							"WHERE userID = ?";
			stmt = db.getConnection().prepareStatement(sql);
			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstName());
			stmt.setString(4, user.getLastName());
			stmt.setString(5, user.getEmail());
			stmt.setInt(6, user.getIndexedRecords());
			stmt.setInt(7, user.getBatchID());
			stmt.setInt(8, user.getUserID());
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
	 * removes a user from the database
	 * @param user
	 * @throws DatabaseException 
	 */
	public void delete(User user) throws DatabaseException
	{
		PreparedStatement stmt = null;
		try
		{
			String sql = "DELETE FROM users WHERE userID = " + Integer.toString(user.getUserID());
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
