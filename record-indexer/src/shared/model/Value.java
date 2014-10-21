package shared.model;

public class Value 
{
	private int valueID;//key
	
	private int recordID;
	private int fieldID;
	private int batchID;
	private int projectID;
	
	private String data;
	
	
	 /**
	   * Initializes the Value with no parameters
	   */
	public Value() 
	{
		super();
		this.valueID = -1;
		this.recordID = -1;
		this.fieldID = -1;
		this.batchID = -1;
		this.projectID = -1;
		this.data = null;
	}
	 /**
	   * Initializes the Value if ALL information is available
	   * @param valueID - unique identifier of the value
	   * @param recordID - record the value comes from(row)
	   * @param fieldID - field the value is associated with(column)
	   * @param batchID - image the value comes from
	   * @param projectID - project the value is associated with
	   * @param data - actual text or 'Value'
	   */
	public Value(int valueID, int recordID, int fieldID, int batchID, int projectID, String data) 
	{
		this.valueID = valueID;
		this.recordID = recordID;
		this.fieldID = fieldID;
		this.batchID = batchID;
		this.projectID = projectID;
		this.data = data;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + batchID;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + fieldID;
		result = prime * result + projectID;
		result = prime * result + recordID;
		result = prime * result + valueID;
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
		Value other = (Value) obj;
		if (batchID != other.batchID)
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (fieldID != other.fieldID)
			return false;
		if (projectID != other.projectID)
			return false;
		if (recordID != other.recordID)
			return false;
		if (valueID != other.valueID)
			return false;
		return true;
	}
	/**
	 * @return the valueID
	 */
	public int getValueID() {
		return valueID;
	}
	/**
	 * @param valueID the valueID to set
	 */
	public void setValueID(int valueID) {
		this.valueID = valueID;
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
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	
	
	

}
