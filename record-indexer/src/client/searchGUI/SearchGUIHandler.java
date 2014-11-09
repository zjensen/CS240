package client.searchGUI;

import java.util.ArrayList;

import shared.communication.GetProjects_Params;
import shared.communication.ValidateUser_Params;
import shared.communication.ValidateUser_Result;
import shared.model.Project;
import client.communication.ClientCommunicator;
import client.communication.ClientException;

public class SearchGUIHandler 
{
	ClientCommunicator cc;
	private String host;
	private String port;
	private String username;
	private String password;
	public SearchGUIHandler() 
	{
		setHost(null);
		setPort(null);
	}
	
	public boolean login(String host, String port,String username, String password)
	{
		this.setHost(host);
		this.setPort(port);
		this.setPassword(password);
		this.setUsername(username);
		cc = new ClientCommunicator(host, port);
		ValidateUser_Params params = new ValidateUser_Params(username,password);
		try 
		{
			ValidateUser_Result result = cc.validateUser(params);
			if(result.isValid())
			{
				return true;
			}
			else
			{
				return false;
			}
		} 
		catch (ClientException e) 
		{
			return false;
		}
	}
	
	public ArrayList<Project> getProjects()
	{
		GetProjects_Params params = new GetProjects_Params(this.getUsername(),this.getPassword());
		try 
		{
			return cc.getProjects(params).getProjects();
		} 
		catch (ClientException e) 
		{
			return null;
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
