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
	  * Constructs project with no parameters
	  */
	public Project()
	{
		this.projectID = -1;
		this.title = null;
		this.recordsPerImage = -1;
		this.firstYCoord = -1;
		this.recordHeight = -1;
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
	
	/** (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + firstYCoord;
		result = prime * result + projectID;
		result = prime * result + recordHeight;
		result = prime * result + recordsPerImage;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	/** (non-Javadoc)
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
		Project other = (Project) obj;
		if (firstYCoord != other.firstYCoord)
			return false;
		if (projectID != other.projectID)
			return false;
		if (recordHeight != other.recordHeight)
			return false;
		if (recordsPerImage != other.recordsPerImage)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
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
