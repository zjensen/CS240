package shared.model;
/**
 * 
 * @author zsjensen
 * User object, containing all information to a user of the record-indexer
 */
public class User 
{
	private int userID;//key
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private int indexedRecords;
	private Batch currentBatch;
	
	 /**
	   * Initializes the user with no data
	   */
	public User()
	{
		userID = -1;
		username = null;
		password = null;
		firstName = null;
		lastName = null;
		email = null;
		indexedRecords = 0;
		currentBatch = null;
	}
	 /**
	   * Initializes the user if ALL information is available
	   * @param userID - unique identifier of the user
	   * @param username - name user uses to log in
	   * @param password - users password
	   * @param firstName - user's first name
	   * @param lastName - user's last name
	   * @param email - user's email address
	   * @param indexedRecords - number of records indexed by user
	   * @param currentBatch - current image user is working on
	   */
	public User(int userID, String username, String password, String firstName,String lastName, String email, int indexedRecords,Batch currentBatch) 
	{
		super();
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.indexedRecords = indexedRecords;
		this.currentBatch = currentBatch;
	}




	/**
	 * @return the userID
	 */
	public int getUserID() 
	{
		return userID;
	}
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) 
	{
		this.userID = userID;
	}
	/**
	 * @return the username
	 */
	public String getUsername() 
	{
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) 
	{
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() 
	{
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) 
	{
		this.password = password;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() 
	{
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() 
	{
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() 
	{
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) 
	{
		this.email = email;
	}
	/**
	 * @return the indexedRecords
	 */
	public int getIndexedRecords() 
	{
		return indexedRecords;
	}
	/**
	 * @param indexedRecords the indexedRecords to set
	 */
	public void setIndexedRecords(int indexedRecords) 
	{
		this.indexedRecords = indexedRecords;
	}
	/**
	 * @return the currentBatch
	 */
	public Batch getCurrentBatch() 
	{
		return currentBatch;
	}
	/**
	 * @param currentBatch the currentBatch to set
	 */
	public void setCurrentBatch(Batch currentBatch) 
	{
		this.currentBatch = currentBatch;
	}
	
}
