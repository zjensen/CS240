package shared.communication;

public class DownloadBatch_Params extends User_Params
{
	private int projectID;
	/**
	 * All information needed to download a batch
	 * @param username
	 * @param password
	 * @param projectID
	 */
	public DownloadBatch_Params(String username, String password,String projectID) 
	{
		super(username, password);
		this.projectID = Integer.valueOf(projectID);
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
