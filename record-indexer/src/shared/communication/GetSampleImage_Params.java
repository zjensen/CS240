package shared.communication;

public class GetSampleImage_Params extends User_Params
{	
	private int projectID;
	/**
	 * All information needed to pull a sample image
	 * @param username
	 * @param password
	 * @param projectID
	 */
	public GetSampleImage_Params(String username, String password,int projectID) 
	{
		super(username, password);
		this.projectID = projectID;
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
