package server.facade;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Logger;

import server.ServerException;
import server.DatabaseException;
import server.dba.*;
import shared.communication.*;
import shared.model.*;

import org.apache.commons.io.*;

public class Server_Facade 
{
	private Logger logger = Logger.getLogger("Server_Facade");
	public static void initialize() throws ServerException
	{
		try 
		{
			Database.initialize();
		} 
		catch (DatabaseException e) 
		{
			throw new ServerException();
		}
	}

	public Server_Facade()
	{
		
	}
	/**
	 * validates if a user is in the database
	 * @param params
	 * @return
	 * @throws DatabaseException
	 */
	public ValidateUser_Result validateUser(ValidateUser_Params params) throws DatabaseException
	{
		logger.info("SF-validateUser");
		Database db = new Database();
		try
		{
			db.startTransaction();
			ArrayList<User> users = db.getUsersDAO().getAll();
			db.endTransaction(true);
			boolean valid = false;
			User user = null;
			for(User u : users)
			{
				if(params.getUsername().equals(u.getUsername()))
				{
					if(params.getPassword().equals(u.getPassword()))
					{
						user = u;
						valid = true;
					}
				}
			}
			return new ValidateUser_Result(user,valid);
		}
		catch(DatabaseException e)
		{
			logger.info(e.getMessage());
			db.endTransaction(false);
			throw new DatabaseException();
		}
	}
	/**
	 * returns results with all projects from database
	 * @param params
	 * @return project info
	 * @throws DatabaseException
	 */
	public GetProjects_Result getProjects(GetProjects_Params params) throws DatabaseException
	{
		logger.info("SF-getProjects");
		Database db = new Database();
		ArrayList<Project> projects = new ArrayList<Project>();
		ValidateUser_Params vup = new ValidateUser_Params(params.getUsername(),params.getPassword());
		if(validateUser(vup).isValid())
		{
			try 
			{
				db.startTransaction();
				projects = db.getProjectsDAO().getAll();
				db.endTransaction(false);
			} 
			catch (DatabaseException e) 
			{
				logger.info(e.getMessage());
				db.endTransaction(false);
				throw new DatabaseException();
			}
		}
		return new GetProjects_Result(projects);
	}
	/**
	 * returns first image in selected project
	 * @param params
	 * @return image url
	 * @throws DatabaseException
	 */
	public GetSampleImage_Result getSampleImage(GetSampleImage_Params params) throws DatabaseException 
	{
		logger.info("SF-getSampleImage");
		Database db = new Database();
		String url = null;
		ValidateUser_Params vup = new ValidateUser_Params(params.getUsername(),params.getPassword());
		if(validateUser(vup).isValid())
		{
			try 
			{
				db.startTransaction();
				ArrayList<Batch> batches = db.getBatchesDAO().getAll(params.getProjectID()); //all batches in project
				db.endTransaction(false);
				if(!batches.isEmpty())
				{
					url = batches.get(0).getFile(); //sets url to url of first image in selected project
				}
			} 
			catch (DatabaseException e) 
			{
				logger.info(e.getMessage());
				db.endTransaction(false);
				throw new DatabaseException();
			}
		}
		return new GetSampleImage_Result(url);
	}
	/**
	 * Downloads a batch and assigns the batch and user to eachother
	 * @param params
	 * @return batches information
	 * @throws DatabaseException
	 */
	public DownloadBatch_Result downloadBatch(DownloadBatch_Params params) throws DatabaseException 
	{
		logger.info("SF-downloadBatch");
		DownloadBatch_Result result= new DownloadBatch_Result();
		Database db = new Database();
		ValidateUser_Params vup = new ValidateUser_Params(params.getUsername(),params.getPassword());
		ValidateUser_Result vur = validateUser(vup);
		if(vur.isValid() && vur.getUser().getBatchID() == -1) //user is valid and doesn't have assigned batch
		{
			try 
			{
				db.startTransaction();
				Project project = db.getProjectsDAO().get(params.getProjectID());
				ArrayList<Field> fields = db.getFieldsDAO().getAll(params.getProjectID());
				ArrayList<Batch> batches = db.getBatchesDAO().getAll(params.getProjectID());
				for(Batch batch : batches)
				{
					if(batch.getUserID() == -1 && !batch.getCompleted())
					{
						User user = vur.getUser();
						user.setBatchID(batch.getBatchID());
						batch.setUserID(user.getUserID());
						db.getBatchesDAO().update(batch); //sets batch as assigned to the user
						db.getUsersDAO().update(user); //sets users assigned batch
						db.endTransaction(false);
						return new DownloadBatch_Result(batch,project,fields); 
					}
				}
				db.endTransaction(false);
			} 
			catch (DatabaseException e) 
			{
				logger.info(e.getMessage());
				db.endTransaction(false);
				throw new DatabaseException();
			}
		}
		return result;
	}
	/**
	 * Submits a batch to the server
	 * @param params
	 * @return boolean whether or not submit was a success
	 * @throws DatabaseException
	 */
	public SubmitBatch_Result submitBatch(SubmitBatch_Params params) throws DatabaseException
	{
		logger.info("SF-submitBatch");
		Database db = new Database();
		ValidateUser_Params vup = new ValidateUser_Params(params.getUsername(),params.getPassword());
		ValidateUser_Result vur = validateUser(vup);
		if(vur.isValid())
		{
			try
			{
				db.startTransaction();
				
				ValuesDAO vDAO = db.getValuesDAO();
				
				User user = vur.getUser(); //get user that was working on batch
				Batch batch = db.getBatchesDAO().get(params.getBatchID());
				Project project = db.getProjectsDAO().get(batch.getProjectID());
				int batchID = batch.getBatchID();
				int projectID = project.getProjectID();
				
				ArrayList<Record> records = (db.getRecordsDAO().getAll(batch));
				ArrayList<Field> fields = db.getFieldsDAO().getAll(projectID);
				
				String[] recordStrings = params.getValues().split(";", records.size()); //split input into records
				if(recordStrings.length != project.getRecordsPerImage()) //too many records submitted
				{
					db.endTransaction(false);
					return new SubmitBatch_Result(false);
				}
				int row = 0; //to keep track of what record were looking at
				for(String recordString : recordStrings)
				{
					String[] valueStrings = recordString.split(",",fields.size()); //split records into values
					
					if(valueStrings.length != fields.size() && valueStrings.length !=0)
					{
						db.endTransaction(false);
						return new SubmitBatch_Result(false);
					}
					
					int column = 0; //to keep track of what field were looking at
					
					ArrayList<Value> values = vDAO.getAll(records.get(row));
					
					for(String valueString:valueStrings) //for each value in the record
					{
						if(valueString.isEmpty())
						{
							valueString = null;
						}
						if(values.get(column) == null)
						{
							Value v = new Value(-1,records.get(row).getRecordID(),fields.get(column).getFieldID(),batchID,projectID,valueString);
							vDAO.add(v);
						}
						else
						{
							Value v = values.get(column);
							v.setData(valueString);
							vDAO.update(v);
						}
						column++;
					}
					row++;
				}
				
				user.setBatchID(-1); //reset users assigned batch
				user.setIndexedRecords(user.getIndexedRecords()+row); //updates users number of indexed records
				batch.setUserID(-1);
				boolean completed = db.getValuesDAO().checkCompletion(batchID);
				batch.setCompleted(completed);
				db.getBatchesDAO().update(batch);
				db.getUsersDAO().update(user);
				db.endTransaction(true);
			}
			catch(DatabaseException e)
			{
				logger.severe("SF-Submit Batch CATCH: "+e.getMessage());
				db.endTransaction(false);
				throw new DatabaseException();
			}
			return new SubmitBatch_Result(true);
		}
		return new SubmitBatch_Result(false);
	}
	/**
	 * Returns list of fields
	 * @param params
	 * @return
	 * @throws DatabaseException
	 */
	public GetFields_Result getFields(GetFields_Params params) throws DatabaseException 
	{
		logger.info("SF-getFields");
		Database db = new Database();
		ValidateUser_Params vup = new ValidateUser_Params(params.getUsername(),params.getPassword());
		ArrayList<Field> fields = new ArrayList<Field>();
		if(validateUser(vup).isValid()) //user is valid and doesn't have assigned batch
		{
			try 
			{
				db.startTransaction();
				if(params.getProjectID() == -1)
				{
					fields = db.getFieldsDAO().getAll();
				}
				else
				{
					fields = db.getFieldsDAO().getAll(params.getProjectID());
				}
				db.endTransaction(false);
			} 
			catch (DatabaseException e) 
			{
				logger.info(e.getMessage());
				db.endTransaction(false);
				throw new DatabaseException();
			}
		}
		return new GetFields_Result(fields);
	}
	/**
	 * Searches selected fields for search values
	 * @param params
	 * @return
	 * @throws DatabaseException
	 */
	public Search_Result search(Search_Params params) throws DatabaseException
	{
		Database db = new Database();
		ArrayList<Search_Result_Tuple> tuples = new ArrayList<Search_Result_Tuple>();
		ValidateUser_Params vup = new ValidateUser_Params(params.getUsername(),params.getPassword());
		if(validateUser(vup).isValid()) //user is valid and doesn't have assigned batch
		{
			ValuesDAO vDAO = db.getValuesDAO();
			BatchesDAO bDAO = db.getBatchesDAO();
			RecordsDAO rDAO = db.getRecordsDAO();
			
			String[] searchValueStrings = params.getValues().split("[,]");
			String[] fieldIDStrings = params.getFieldIDs().split("[,]");
			
			for(String fieldIDString : fieldIDStrings)
			{
				int fieldID = Integer.valueOf(fieldIDString);
				try
				{
					db.startTransaction();
					ArrayList<Value> values = vDAO.getAllByFieldID(fieldID);
					for(Value value:values)
					{
						for(String searchValueString:searchValueStrings)
						{
							if(value.getData()!=null && value.getData().toLowerCase().equals(searchValueString.toLowerCase()))
							{
								Record record = rDAO.get(value.getRecordID());
								Batch batch = bDAO.get(value.getBatchID());
								Search_Result_Tuple tuple = new Search_Result_Tuple(value.getBatchID(),batch.getFile(),record.getRow(),fieldID);
								tuples.add(tuple);
							}
						}
					}
					db.endTransaction(false);
				}
				catch(DatabaseException e)
				{
					logger.severe("SF-Search CATCH: "+e.getMessage());
					db.endTransaction(false);
					throw new DatabaseException();
				}
			}
		}
		return new Search_Result(tuples);
	}
	/**
	 * Returns bite array with data from download
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public DownloadFile_Result downloadFile(DownloadFile_Params params) throws IOException 
	{
		logger.info("SF-downloadFile");
		InputStream is = new FileInputStream(params.getUrl());
		byte[] data = null;
		data = IOUtils.toByteArray(is);
		is.close();
		return new DownloadFile_Result(data);
	}
}
