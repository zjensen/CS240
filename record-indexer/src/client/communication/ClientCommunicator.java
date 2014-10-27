package client.communication;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import servertester.controllers.Controller;
import shared.communication.*;
/**
 * Facilitates communication between client and server
 * @author zsjensen
 *
 */
public class ClientCommunicator 
{
	private String host;
	private int port;
	private Controller controller;
	
	private Logger logger = Logger.getLogger("ClientCommunicator");
	
	public ClientCommunicator(String host, String port) 
	{
		this.host = host;
		this.port = Integer.valueOf(port);
		controller = null;
	}
	
	public ClientCommunicator(String host, String port, Controller controller) 
	{
		this.host = host;
		this.port = Integer.valueOf(port);
		this.controller = controller;
	}

	/**
	 * @param validateUser_Params - username and password
	 * @return result of checking if user is valid
	 * @throws ClientCommunicatorException 
	 */
	public ValidateUser_Result validateUser(ValidateUser_Params params) throws ClientException
	{
		logger.info("CC-ValidateUser");
		return (ValidateUser_Result)doPost("http://" + host + ":" + port +"/validateUser",params);
	}
	
	/**
	 * returns the specified project
	 * @param params
	 * @return
	 * @throws ClientCommunicatorException 
	 */
	public GetProjects_Result getProjects(GetProjects_Params params) throws ClientException
	{
		logger.info("CC-getProjects");
		return (GetProjects_Result)doPost("http://" + host + ":" + port +"/getProjects",params);
	}
	
	/**
	 * Returns a sample image for the user to see
	 * @param params
	 * @return
	 * @throws ClientCommunicatorException 
	 */
	public GetSampleImage_Result getSampleImage(GetSampleImage_Params params) throws ClientException
	{
		logger.info("CC-getSampleImage");
		GetSampleImage_Result result = (GetSampleImage_Result)doPost("http://" + host + ":" + port +"/getSampleImage",params);
		if(result.getImageURL()!=null && !result.getImageURL().isEmpty())
		{
			result.setImageURL("http://" + host + ":" + port +"/" + result.getImageURL());
		}
		return result;
	}
	
	/**
	 * Downloads a batch for the user to index
	 * @param params
	 * @return
	 * @throws ClientCommunicatorException 
	 */
	public DownloadBatch_Result downloadBatch(DownloadBatch_Params params) throws ClientException
	{
		logger.info("CC-downloadBatch");
		DownloadBatch_Result result = (DownloadBatch_Result)doPost("http://" + host + ":" + port +"/downloadBatch",params);
		if(result!=null && result.getImageURL() != null)
		{
			result.updateURLs(host, port);
		}
		return result;
	}
	
	/**
	 * Submits a batch
	 * @param submitbatch params
	 * @return submit batch results
	 * @throws ClientCommunicatorException 
	 */
	public SubmitBatch_Result submitBatch(SubmitBatch_Params params) throws ClientException
	{
		logger.info("CC-submitBatch");
		return (SubmitBatch_Result)doPost("http://" + host + ":" + port +"/submitBatch",params);
	}
	
	/**
	 * Returns information about all of the fields for the specified project
	 * If no project is specified, returns information about all of the fields for all projects in the system
	 * @param getFields params
	 * @return get fields results
	 * @throws ClientCommunicatorException 
	 */
	public GetFields_Result getFields(GetFields_Params params) throws ClientException
	{
		logger.info("cc-getFields");
		return (GetFields_Result)doPost("http://" + host + ":" + port +"/getFields",params);
	}
	
	/**
	 * Returns results from a search
	 * @param Search_Params 
	 * @return Search_Result
	 * @throws ClientCommunicatorException 
	 */
	public Search_Result search(Search_Params params) throws ClientException
	{
		logger.info("cc-search");
		Search_Result result = (Search_Result)doPost("http://" + host + ":" + port +"/search",params);
		if(!result.getTuples().isEmpty() && result.getTuples()!=null)
		{
			result.updateURLs(host, port);
		}
		return result;
	}

	/**
	 * downloads file - takes in url, returns bytes
	 * @param downloadFile_Params
	 * @return download_File result
	 * @throws ClientException 
	 */
	public DownloadFile_Result downloadFile(DownloadFile_Params params) throws ClientException
	{
		logger.info("cc-downloadFile");
		return new DownloadFile_Result(doGet("http://" + host + ":" + port + File.separator + params.getUrl()));
	}
	/**
	 * does POST operations
	 * @param urlPath
	 * @param params
	 * @return
	 * @throws ClientException
	 */
	private Object doPost(String urlPath, Object params) throws ClientException
	{ 
		XStream xs = new XStream(new DomDriver());
		Object result = null;
		try
		{
			URL url = new URL(urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.connect();
			OutputStream request = connection.getOutputStream();
			xs.toXML(params, request);
			if(controller!=null)
			{
				controller.getView().setRequest(request.toString());
			}
			request.close();
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) //200
			{
				InputStream response = connection.getInputStream();
				result = xs.fromXML(response);
				response.close();
			}
		}
		catch(Exception e)
		{
			logger.severe("doPost catch: " + e.getMessage() + " : " +e);
			throw new ClientException(e);
		}
		logger.info("completed doPost");
		return result;
	}
	/**
	 * does GET opperations
	 * @param urlPath
	 * @return
	 * @throws ClientException
	 */
	public byte[] doGet(String urlPath) throws ClientException
	{
		byte[] result = null;
		try
		{
			URL url = new URL(urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.connect();
			if(controller!=null)
			{
				controller.getView().setRequest(connection.getRequestMethod().toString());
			}
			if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) //200
			{
				InputStream response = connection.getInputStream();
				result = IOUtils.toByteArray(response);
				response.close();
			}
		}
		catch(Exception e)
		{
			logger.severe("doGet catch: " + e.getMessage() + " : " +e);
			throw new ClientException(e);
		}
		logger.info("completed doGet");
		return result;
	}
}
