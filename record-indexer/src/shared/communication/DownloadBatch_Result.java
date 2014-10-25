package shared.communication;
import shared.model.*;

import java.util.ArrayList;

public class DownloadBatch_Result 
{
	private Batch batch;
	private Project project;
	private int batchID;
	private int projectID;
	private String imageURL;
	private int firstYCoord;
	private int recordHeight;
	private int numRecords;
	private int numFields;
	private ArrayList<Field> fields;
	public DownloadBatch_Result(int batchID,int projectID, String imageURL,int firstYCoord, int recordHeight, int numRecords, int numFields,ArrayList<Field> fields) 
	{
		this.batchID = batchID;
		this.projectID = projectID;
		this.imageURL = imageURL;
		this.firstYCoord = firstYCoord;
		this.recordHeight = recordHeight;
		this.numRecords = numRecords;
		this.numFields = numFields;
		this.fields = fields;
	}
	public DownloadBatch_Result() 
	{
		this.batchID = -1;
		this.projectID = -1;
		this.imageURL = null;
		this.firstYCoord = -1;
		this.recordHeight = -1;
		this.numRecords = -1;
		this.numFields = -1;
		this.fields = null;
	}
	public DownloadBatch_Result(Batch batch, Project project, ArrayList<Field> fields)
	{
		this.batchID = batch.getBatchID();
		this.projectID = project.getProjectID();
		this.imageURL = batch.getFile();
		this.firstYCoord = project.getFirstYCoord();
		this.recordHeight = project.getRecordHeight();
		this.numRecords = project.getRecordsPerImage();
		this.numFields = fields.size();
		this.fields = fields;
	}
	
	public void updateURLs(String host, int port)
	{
		this.setImageURL("http://" + host + ":" + port +"/" + this.getImageURL());
		ArrayList<Field> fields = this.getFields();
		if(fields != null)
		{
			for(int i=0;i<fields.size();i++)
			{
				Field field = fields.get(i);
				if(field.getKnownData() != null)
				{
					field.setKnownData("http://" + host + ":" + port +"/" + field.getKnownData());
				}
				field.setHelpHTML("http://" + host + ":" + port +"/" + field.getHelpHTML());
			}
		}
		this.setFields(fields);
	}
	public String toString()
	{
		if(batchID == -1)
		{
			return "FAILED\n";
		}
		else
		{
			StringBuilder sb = new StringBuilder();
			sb.append(Integer.toString(batchID)+"\n");
			sb.append(Integer.toString(projectID)+"\n");
			sb.append(imageURL+"\n");
			sb.append(Integer.toString(firstYCoord)+"\n");
			sb.append(Integer.toString(recordHeight)+"\n");
			sb.append(Integer.toString(numRecords)+"\n");
			sb.append(Integer.toString(numFields)+"\n");
			for(Field f : fields)
			{
				sb.append(Integer.toString(f.getFieldID())+"\n");
				sb.append(Integer.toString(f.getColumn())+"\n");
				sb.append(f.getTitle()+"\n");
				sb.append(f.getHelpHTML()+"\n");
				sb.append(Integer.toString(f.getXCoord())+"\n");
				sb.append(Integer.toString(f.getWidth())+"\n");
				if(f.getKnownData()!=null)
				{
					sb.append(f.getKnownData()+"\n");
				}
			}
			return sb.toString();
		}
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
	/**
	 * @return the numRecords
	 */
	public int getNumRecords() {
		return numRecords;
	}
	/**
	 * @param numRecords the numRecords to set
	 */
	public void setNumRecords(int numRecords) {
		this.numRecords = numRecords;
	}
	/**
	 * @return the numFields
	 */
	public int getNumFields() {
		return numFields;
	}
	/**
	 * @param numFields the numFields to set
	 */
	public void setNumFields(int numFields) {
		this.numFields = numFields;
	}
	/**
	 * @return the fields
	 */
	public ArrayList<Field> getFields() {
		return fields;
	}
	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}
	/**
	 * @return the batch
	 */
	public Batch getBatch() {
		return batch;
	}
	/**
	 * @param batch the batch to set
	 */
	public void setBatch(Batch batch) {
		this.batch = batch;
	}
	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}
	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}
}
