package shared.model;



public class Project 
{
	private int projectID;//key
	
	private String title;
	private int recordsPerImage;
	private int firstYCoord;
	private int recordHeight;
	
	// ------------------------------------------------------------------------------------
	// CONSTUCTORS
	// ------------------------------------------------------------------------------------
	 /**
	   * Initializes the project if there is NOT an ID delegated yet
	   * @param title - Project's title
	   * @param recordsPerImage - number of records(rows of values) per image in project
	   * @param firstYCoord - Y coordinate of the first record in the image
	   * @param recordHeight - height of each record(row) in the images
	   */
	public Project(String title, int recordsPerImage,int firstYCoord, int recordHeight)
	{
		this.projectID = -1;
		this.title = title;
		this.recordsPerImage = recordsPerImage;
		this.firstYCoord = firstYCoord;
		this.recordHeight = recordHeight;
	}
	 /**
	   * Initializes the project if there IS an ID delegated 
	   * @param projectID - unique identifier key
	   * @param title - Project's title
	   * @param recordsPerImage - number of records(rows of values) per image in project
	   * @param firstYCoord - Y coordinate of the first record in the image
	   * @param recordHeight - height of each record(row) in the images
	   */
	public Project(int projectID, String title, int recordsPerImage,int firstYCoord, int recordHeight) 
	{
		this.projectID = projectID;
		this.title = title;
		this.recordsPerImage = recordsPerImage;
		this.firstYCoord = firstYCoord;
		this.recordHeight = recordHeight;
	}
	
	// ------------------------------------------------------------------------------------
	// GETTERS AND SETTERS - PUBLIC ACCESS
	// ------------------------------------------------------------------------------------
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the recordsPerImage
	 */
	public int getRecordsPerImage() {
		return recordsPerImage;
	}
	/**
	 * @param recordsPerImage the recordsPerImage to set
	 */
	public void setRecordsPerImage(int recordsPerImage) {
		this.recordsPerImage = recordsPerImage;
	}
	/**
	 * @return the firstYCoord
	 */
	public int getFirstYCoord() {
		return firstYCoord;
	}
	/**
	 * @param firstYCoord the firstYCoord to set
	 */
	public void setFirstYCoord(int firstYCoord) {
		this.firstYCoord = firstYCoord;
	}
	/**
	 * @return the recordHeight
	 */
	public int getRecordHeight() {
		return recordHeight;
	}
	/**
	 * @param recordHeight the recordHeight to set
	 */
	public void setRecordHeight(int recordHeight) {
		this.recordHeight = recordHeight;
	}
}
