package shared.communication;
/**
 * Result tuples from a search
 * @author zsjensen
 */
public class Search_Result_Tuple 
{
	private int batchID;
	private String imageURL;
	private int valueID;
	private int fieldID;
	/**
	 * Constucts Search_Result_Tuple, response to a search
	 * @param batchID
	 * @param imageURL
	 * @param valueID
	 * @param fieldID
	 */
	public Search_Result_Tuple(int batchID, String imageURL, int valueID,int fieldID) 
	{
		this.batchID = batchID;
		this.imageURL = imageURL;
		this.valueID = valueID;
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
	 * @return the imageURL
	 */
	public String getImageURL() {
		return imageURL;
	}
	/**
	 * @param imageURL the imageURL to set
	 */
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
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
}
