package shared.communication;


public class SubmitBatch_Params extends User_Params 
{
	private int batchID;
	private String values;
	public SubmitBatch_Params(String username, String password,String batchID,String values) 
	{
		super(username, password);
		this.batchID=Integer.valueOf(batchID);
		this.values=values;
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
	 * @return the records
	 */
	public String getValues() {
		return values;
	}
	/**
	 * @param records the records to set
	 */
	public void setValues(String values) {
		this.values = values;
	}

	
}
