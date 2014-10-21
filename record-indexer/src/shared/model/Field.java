package shared.model;

public class Field
{
	private int fieldID;//key
	
	private int projectID;
	
	private String title;
	private int xCoord;
	private int width;
	private String helpHTML;
	private String knownData;
	private int column;
	
	 /**
	   * Initializes the field with no data set
	   */
	public Field()
	{
		this.fieldID = -1;
		this.projectID = -1;
		this.column = -1;
		this.title = null;
		this.xCoord = -1;
		this.width = -1;
		this.helpHTML = null;
		this.knownData = null;
	}
	 /**
	   * Initializes the field if ALL information is available
	   * @param fieldID - unique identifier of the field
	   * @param projectID - project the field is associated with
	   * @param column - column in each image where field is located
	   * @param title - title of the column
	   * @param xCoord - where the field begins in the image
	   * @param width - width of field
	   * @param helpHTML - HTML link to help, if available
	   * @param knownData - location of text file containing known values
	   */
	public Field(int fieldID, int projectID, int column, String title, int xCoord, int width, String helpHTML, String knownData) 
	{
		this.fieldID = fieldID;
		this.projectID = projectID;
		this.column = column;
		this.title = title;
		this.xCoord = xCoord;
		this.width = width;
		this.helpHTML = helpHTML;
		this.knownData = knownData;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + fieldID;
		result = prime * result
				+ ((helpHTML == null) ? 0 : helpHTML.hashCode());
		result = prime * result
				+ ((knownData == null) ? 0 : knownData.hashCode());
		result = prime * result + projectID;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + width;
		result = prime * result + xCoord;
		return result;
	}
	/* (non-Javadoc)
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
		Field other = (Field) obj;
		if (column != other.column)
			return false;
		if (fieldID != other.fieldID)
			return false;
		if (helpHTML == null) {
			if (other.helpHTML != null)
				return false;
		} else if (!helpHTML.equals(other.helpHTML))
			return false;
		if (knownData == null) {
			if (other.knownData != null)
				return false;
		} else if (!knownData.equals(other.knownData))
			return false;
		if (projectID != other.projectID)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (width != other.width)
			return false;
		if (xCoord != other.xCoord)
			return false;
		return true;
	}
	/**
	 * @return the fieldID
	 */
	public int getFieldID() {
		return fieldID;
	}
	/**
	 * @param fieldID the fieldID to set
	 */
	public void setFieldID(int fieldID) {
		this.fieldID = fieldID;
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
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}
	/**
	 * @param column the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
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
	 * @return the xCoord
	 */
	public int getXCoord() {
		return xCoord;
	}
	/**
	 * @param xCoord the xCoord to set
	 */
	public void setXCoord(int xCoord) {
		this.xCoord = xCoord;
	}
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return the helpHTML
	 */
	public String getHelpHTML() {
		return helpHTML;
	}
	/**
	 * @param helpHTML the helpHTML to set
	 */
	public void setHelpHTML(String helpHTML) {
		this.helpHTML = helpHTML;
	}
	/**
	 * @return the knownData
	 */
	public String getKnownData() {
		return knownData;
	}
	/**
	 * @param knownData the knownData to set
	 */
	public void setKnownData(String knownData) {
		this.knownData = knownData;
	}

}
