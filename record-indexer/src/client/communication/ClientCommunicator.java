package client.communication;

import shared.communication.*;
/**
 * Facilitates communication between client and server
 * @author zsjensen
 *
 */
public class ClientCommunicator 
{

	public ClientCommunicator() 
	{
		
	}
	/**
	 * @param validateUser_Params - username and password
	 * @return result of checking if user is valid
	 */
	public ValidateUser_Result validateUser(ValidateUser_Params params)
	{
		return null;
	}
	
	/**
	 * returns the specified project
	 * @param params
	 * @return
	 */
	public GetProjects_Result getProjects(GetProjects_Params params)
	{
		return null;
	}
	
	/**
	 * Returns a sample image for the user to see
	 * @param params
	 * @return
	 */
	public GetSampleImage_Result getSampleImage(GetSampleImage_Params params)
	{
		return null;
	}
	
	/**
	 * Downloads a batch for the user to index
	 * @param params
	 * @return
	 */
	public DownloadBatch_Result downloadBatch(DownloadBatch_Params params)
	{
		return null;
	}
	
	/**
	 * Submits a batch
	 * @param submitbatch params
	 * @return submit batch results
	 */
	public SubmitBatch_Result submitBatch(SubmitBatch_Params params)
	{
		return null;
	}
	
	/**
	 * Returns information about all of the fields for the specified project
	 * If no project is specified, returns information about all of the fields for all projects in the system
	 * @param getFields params
	 * @return get fields results
	 */
	public GetFields_Result getFields(GetFields_Params params)
	{
		return null;
	}
	
	/**
	 * Returns results from a search
	 * @param Search_Params 
	 * @return Search_Result
	 */
	public Search_Result search(Search_Params params)
	{
		return null;
	}

	/**
	 * downloads file - takes in url, returns bytes
	 * @param downloadFile_Params
	 * @return download_File result
	 */
	public DownloadFile_Result downloadFile(DownloadFile_Params params)
	{
		return null;
	}

}
