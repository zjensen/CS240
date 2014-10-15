package shared.model;


public class Batch
{
	private int batchID;//key
	
	private int projectID;
	private int userID;
	private boolean completed;
	private String file;
	
	/**
	 * Constructs batch with no information
	 */
	public Batch() 
	{
		this.batchID = -1;
		this.projectID = -1;
		this.userID = -1;
		this.completed = false;
		this.file = null;
	}
	 /**
	   * Initializes the batch if ALL information is available
	   * @param batchID - unique identifier of the batch
	   * @param projectID - project batch is from
	   * @param userID - ID of user if batch is in progress
	   * @param completed - is batch completed?
	   * @param file - file address of image
	   */
	public Batch(int batchID, int projectID, int userID,boolean completed, String file) 
	{
		this.batchID = batchID;
		this.projectID = projectID;
		this.userID = userID;
		this.completed = completed;
		this.file = file;
	}
	/**
	 * @return the batchID
	 */
	public int getBatchID() {
		return batchID;
	}
	/**
	 * @param batchID the batchID to set
	 */
	public void setBatchID(int batchID) {
		this.batchID = batchID;
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
	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	/**
	 * @return the completed
	 */
	public boolean isCompleted() {
		return completed;
	}
	/**
	 * @param completed the completed to set
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	/**
	 * @return the file
	 */
	public String getFile() {
		return file;
	}
	/**
	 * @param file the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}
}
