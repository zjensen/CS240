package servertester.controllers;

import java.util.*;

import client.communication.ClientCommunicator;
import client.communication.ClientException;
import servertester.views.*;
import shared.communication.*;

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() 
	{
		ClientCommunicator cc = new ClientCommunicator(_view.getHost(), _view.getPort(),this);
		String[] inputParams = _view.getParameterValues();
		ValidateUser_Params params = new ValidateUser_Params(inputParams[0],inputParams[1]);
		try 
		{
			ValidateUser_Result result = cc.validateUser(params);
			_view.setResponse(result.toString());
		} 
		catch (ClientException e) 
		{
			_view.setResponse("FAILED\n");
		}
	}
	
	private void getProjects() 
	{
		ClientCommunicator cc = new ClientCommunicator(_view.getHost(), _view.getPort(),this);
		String[] inputParams = _view.getParameterValues();
		GetProjects_Params params = new GetProjects_Params(inputParams[0],inputParams[1]);
		try 
		{
			GetProjects_Result result = cc.getProjects(params);
			_view.setResponse(result.toString());
		} 
		catch (ClientException e) 
		{
			_view.setResponse("FAILED\n");
		}
	}
	
	private void getSampleImage() 
	{
		ClientCommunicator cc = new ClientCommunicator(_view.getHost(), _view.getPort(),this);
		String[] inputParams = _view.getParameterValues();
		GetSampleImage_Params params = new GetSampleImage_Params(inputParams[0],inputParams[1],inputParams[2]);
		try 
		{
			GetSampleImage_Result result = cc.getSampleImage(params);
			_view.setResponse(result.toString());
		} 
		catch (ClientException e) 
		{
			_view.setResponse("FAILED\n");
		}
	}
	
	private void downloadBatch() 
	{
		ClientCommunicator cc = new ClientCommunicator(_view.getHost(), _view.getPort(),this);
		String[] inputParams = _view.getParameterValues();
		DownloadBatch_Params params = new DownloadBatch_Params(inputParams[0],inputParams[1],inputParams[2]);
		try 
		{
			DownloadBatch_Result result = cc.downloadBatch(params);
			_view.setResponse(result.toString());
		} 
		catch (ClientException e) 
		{
			_view.setResponse("FAILED\n");
		}
	}
	
	private void getFields() 
	{
		ClientCommunicator cc = new ClientCommunicator(_view.getHost(), _view.getPort(),this);
		String[] inputParams = _view.getParameterValues();
		GetFields_Params params = new GetFields_Params(inputParams[0],inputParams[1],inputParams[2]);
		try 
		{
			GetFields_Result result = cc.getFields(params);
			_view.setResponse(result.toString());
		} 
		catch (ClientException e) 
		{
			_view.setResponse("FAILED\n");
		}
	}
	
	private void submitBatch() 
	{
		ClientCommunicator cc = new ClientCommunicator(_view.getHost(), _view.getPort(),this);
		String[] inputParams = _view.getParameterValues();
		SubmitBatch_Params params = new SubmitBatch_Params(inputParams[0],inputParams[1],inputParams[2],inputParams[3]);
		try 
		{
			SubmitBatch_Result result = cc.submitBatch(params);
			_view.setResponse(result.toString());
		} 
		catch (ClientException e) 
		{
			_view.setResponse("FAILED\n");
		}
	}
	
	private void search() 
	{
		ClientCommunicator cc = new ClientCommunicator(_view.getHost(), _view.getPort(),this);
		String[] inputParams = _view.getParameterValues();
		if(inputParams[2].isEmpty()||inputParams[3].isEmpty())
		{
			_view.setResponse("FAILED\n");
			return;
		}
		Search_Params params = new Search_Params(inputParams[0],inputParams[1],inputParams[2],inputParams[3]);
		try 
		{
			Search_Result result = cc.search(params);
			_view.setResponse(result.toString());
		} 
		catch (ClientException e) 
		{
			_view.setResponse("FAILED\n");
		}
	}

}

