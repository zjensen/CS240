package shared.model;


public class Batch
{
	private int batchID;//key
	
	private int projectID;
	
	private String file;
	
	private boolean completed;
	private int userID;
	
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
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + batchID;
		result = prime * result + (completed ? 1231 : 1237);
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + projectID;
		result = prime * result + userID;
		return result;
	}
	/**
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Batch other = (Batch) obj;
		if (batchID != other.batchID)
			return false;
		if (completed != other.completed)
			return false;
		if (file == null) {
			if (other.file != null)
				return false;
		} else if (!file.equals(other.file))
			return false;
		if (projectID != other.projectID)
			return false;
		if (userID != other.userID)
			return false;
		return true;
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
	public boolean getCompleted() {
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
