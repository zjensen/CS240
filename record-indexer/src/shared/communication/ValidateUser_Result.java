package shared.communication;

import shared.model.User;

public class ValidateUser_Result 
{
	private User user;
	private boolean valid;
	
	/**
	 * Results for checking if user is valid
	 * @param user
	 * @param valid
	 */
	public ValidateUser_Result(User user, boolean valid) 
	{
		this.user = user;
		this.valid = valid;
	}
	/**
	 * if user isnt found, just return false
	 * @param valid
	 */
	public ValidateUser_Result(boolean valid) 
	{
		this.user = null;
		this.valid = valid;
	}
	/**
	 * @return TRUE and user's first name, last name, and indexed records if valid user. Else FALSE
	 */
	@Override
	public String toString()
	{
		if(valid)
		{
			return "TRUE\n"+user.getFirstName()+"\n"+user.getLastName()+"\n"+user.getIndexedRecords()+"\n";
		}
		else
		{
			return "FALSE\n";
		}
	}
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the valid
	 */
	public boolean isValid() {
		return valid;
	}
	/**
	 * @param valid the valid to set
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}
}
