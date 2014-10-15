package shared.model;

public class Record 
{
	private int recordID;//key
	
	private int batchID;
	private int projectID;
	
	private int row;
	
	
	 /**
	   * Initializes the record with no parameters
	   */
	public Record()
	{
		this.recordID = -1;
		this.batchID = -1;
		this.projectID = -1;
		this.row = -1;
	}
	  /**
	   * Initializes the record if all information is available
	   * @param recordID - unique identifier of the row
	   * @param batchID - image the record comes from
	   * @param projectID - project the row is associated with
	   * @param row - the row the record appears on in the image
	   */
	public Record(int recordID, int batchID, int projectID, int row) 
	{
		super();
		this.recordID = recordID;
		this.batchID = batchID;
		this.projectID = projectID;
		this.row = row;
	}


	/**
	 * @return the recordID
	 */
	public int getRecordID() {
		return recordID;
	}
	/**
	 * @param recordID the recordID to set
	 */
	public void setRecordID(int recordID) {
		this.recordID = recordID;
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
	 * @return the row
	 */
	public int getRow() {
		return row;
	}
	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	
	
}
