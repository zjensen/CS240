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
	public GetFields_Params(String username, String password, String projectID) 
	{
		super(username, password);
		if(!projectID.isEmpty())
		{
			this.projectID = Integer.valueOf(projectID);
		}
		else
		{
			this.projectID = -1;
		}
	}
	/**
	 * @return the projectID
	 */
	public int getProjectID() {
		return projectID;
	}
	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
	

}
