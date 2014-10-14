package shared.communication;

public class User_Params 
{	
	private String username;
	private String password;
	
	/**
	 * Holds values used to validate users in other communication classes
	 * @param username
	 * @param password
	 */
	public User_Params(String username, String password) 
	{
		this.username = username;
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
