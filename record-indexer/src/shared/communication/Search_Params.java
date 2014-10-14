package shared.communication;

public class Search_Params extends User_Params 
{
	private String fieldIDs;
	private String values;
	/**
	 * Parameters for searching server
	 * @param username
	 * @param password
	 * @param fieldIDs-Field id's seperate by commas
	 * @param values-Values seperated by commas
	 */
	public Search_Params(String username, String password,String fieldIDs,String values) 
	{
		super(username, password);
		this.fieldIDs=fieldIDs;
		this.values=values;
	}
	/**
	 * @return the fieldIDs
	 */
	public String getFieldIDs() {
		return fieldIDs;
	}
	/**
	 * @param fieldIDs the fieldIDs to set
	 */
	public void setFieldIDs(String fieldIDs) {
		this.fieldIDs = fieldIDs;
	}
	/**
	 * @return the values
	 */
	public String getValues() {
		return values;
	}
	/**
	 * @param values the values to set
	 */
	public void setValues(String values) {
		this.values = values;
	}
	
	
}
