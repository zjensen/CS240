package shared.communication;

public class GetFields_Params extends User_Params 
{
	private int projectID;
	/**
	 * Parameters for getting fields
	 * @param username
	 * @param password
	 * @param projectID - can be empty if looking for all projects
	 */
	public GetFields_Params(String username, String password, int projectID) 
	{
		super(username, password);
		this.projectID=projectID;
	}

}
