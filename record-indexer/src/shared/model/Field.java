package shared.model;

public class Field
{
	private int fieldID;//key
	
	private int projectID;
	
	private int column;
	private String title;
	private int xCoord;
	private int width;
	private String helpHTML;
	private String knownData;
	
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
	public int getxCoord() {
		return xCoord;
	}
	/**
	 * @param xCoord the xCoord to set
	 */
	public void setxCoord(int xCoord) {
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
