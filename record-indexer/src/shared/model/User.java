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
	private int batchID; //current batch
	
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
		batchID = -1;
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
	public User(int userID, String username, String password, String firstName,String lastName, String email, int indexedRecords,int batchID) 
	{
		super();
		this.userID = userID;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.indexedRecords = indexedRecords;
		this.batchID = batchID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + batchID;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + indexedRecords;
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + userID;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (batchID != other.batchID)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (indexedRecords != other.indexedRecords)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userID != other.userID)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
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
	public int getBatchID() 
	{
		return batchID;
	}
	/**
	 * @param currentBatch the currentBatch to set
	 */
	public void setBatchID(int batchID) 
	{
		this.batchID = batchID;
	}
	
}
