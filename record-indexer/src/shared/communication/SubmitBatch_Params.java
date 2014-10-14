package shared.communication;

import java.util.ArrayList;

import shared.model.Record;

public class SubmitBatch_Params extends User_Params 
{
	private int batchID;
	private ArrayList<Record> records;
	public SubmitBatch_Params(String username, String password,int batchID,ArrayList<Record> records) 
	{
		super(username, password);
		this.batchID=batchID;
		this.records=records;
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
	public ArrayList<Record> getRecords() {
		return records;
	}
	/**
	 * @param records the records to set
	 */
	public void setRecords(ArrayList<Record> records) {
		this.records = records;
	}

	
}
