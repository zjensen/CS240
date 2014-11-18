package client.gui.state;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import client.communication.ClientCommunicator;
import client.communication.ClientException;
import client.gui.IndexingWindow;
import client.gui.LoginWindow;
import shared.communication.*;
import shared.model.*;

class Cell{
	int record;
	int field;
}
public class BatchState 
{
	//private List<BatchStateListener> listeners;
	private ClientCommunicator cc;
	
	//views
	private LoginWindow loginWindow;
	private IndexingWindow indexingWindow;
	
	//Synchronization
	private boolean inverted;
	private boolean highlightsVisible;
	private int zoomLevel;
	private User user;
	private String[][] dataValues;
	private Cell currentCell;
	private Batch batch;
	private ArrayList<Field> fields;
	private Project project;
	
	/**
	 * Starts the GUI
	 * @param host
	 * @param port
	 */
	public BatchState(String host, String port)
	{
		cc = new ClientCommunicator(host,port);
		loginWindow = new LoginWindow(this);
		loginWindow.setVisible(true);
	}
	
	/**
	 * Attempts to login the user
	 * @param username
	 * @param password
	 * @return User object. Is null if login credentials are incorrect
	 */
	public User login(String username, String password)
	{
		ValidateUser_Params params = new ValidateUser_Params(username,password);
		try 
		{
			ValidateUser_Result result = cc.validateUser(params);
			if(result.isValid())
			{
				setupIndexer();
				this.user = result.getUser();
				return this.user;
			}
			else
			{
				return null;
			}
		} 
		catch (ClientException e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(indexingWindow,
			"Well this is embarassing.....\nI can't seem to connect to the server right now.\n\nLet's try again in a little bit!",
			"Ooops!",
			JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Shows the indexing window and removes the login window
	 */
	public void viewIndexer()
	{
		loginWindow.setVisible(false);
		indexingWindow.setVisible(true);
	}
	
	/**
	 * Sets up a fresh indexing window
	 */
	public void setupIndexer()
	{
		indexingWindow = new IndexingWindow(this);
		indexingWindow.setVisible(false);
	}
	
	/**
	 * logs the current user out, and displays the login window
	 */
	public void logout()
	{
		indexingWindow.dispose();
		indexingWindow = null;
		loginWindow = new LoginWindow(this);
		loginWindow.setVisible(true);
	}
	
	/**
	 * Saves the current state of the indexing program to a local folder
	 */
	public void save()
	{
		//field values
		//zoom level
		//scroll position
		//highlightVisible
		//inverted
		//
	}
	
	
	/**
	 * toggles whether or not image is inverted
	 */
	public void invertImage() 
	{
		this.inverted = !this.inverted;
	}
	
	
	/**
	 * Submits the batch the user is currently working on, and resets indexing window
	 */
	public void submit()
	{
		try 
		{
			SubmitBatch_Result result = cc.submitBatch(new SubmitBatch_Params(this.user.getUsername(),this.user.getPassword(),Integer.toString(this.user.getBatchID()),",,,;,,,;,,,;,,,;,,,;,,,;,,,;,,,;"));
			result.isSubmitted();
		} 
		catch (ClientException e) 
		{
			e.printStackTrace();
		}
		this.user.setBatchID(-1);
	}
	
	
	/**
	 * zooms the image in or out
	 * @param true if zooming in, false if zooming out
	 */
	public void zoom(boolean in)
	{
		if(in) //zoom in
		{
			
		}
		else //zoom out
		{
			
		}
	}
	
	
	/**
	 * Toggles the highlight setting for the image
	 */
	public void toggleHighlights()
	{
		this.highlightsVisible = !this.highlightsVisible;
	}
	
	
	/**
	 * Returns all the projects the server has stored
	 * @return arrayList of projects
	 */
	public ArrayList<Project> getProjects()
	{
		try 
		{
			return cc.getProjects(new GetProjects_Params(this.user.getUsername(),this.user.getPassword())).getProjects();
		} 
		catch (ClientException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * returns sample image
	 * @param projectID
	 * @return buffered image that is a sample from the selected project
	 */
	public BufferedImage getSampleImage(int projectID)
	{
		GetSampleImage_Params params= new GetSampleImage_Params(this.user.getUsername(),this.user.getPassword(), Integer.toString(projectID));
		GetSampleImage_Result result;
		try 
		{
			result = cc.getSampleImage(params);
			InputStream input = new URL(result.getImageURL()).openStream();
			BufferedImage image = ImageIO.read(input);
			return image;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}

	
	/**
	 * Downloads new batch for user, and displays it in indexing window
	 * @param projectID
	 */
	public void downloadBatch(int projectID)
	{
		try 
		{
			User u = this.user;
			DownloadBatch_Result result = cc.downloadBatch(new DownloadBatch_Params(this.user.getUsername(),this.user.getPassword(),Integer.toString(projectID)));
			if(result.getBatchID() == -1)
			{
				JOptionPane.showMessageDialog(indexingWindow,
						"There are no batches currently available for the selected project.\nSorry!",
						"No Batch Available",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.user.setBatchID(result.getBatchID());
			this.batch=result.getBatch();
			this.project = result.getProject();
			this.fields = result.getFields();
			this.inverted = false;
			this.highlightsVisible = true;
			this.currentCell = new Cell();
			this.currentCell.field = 1;
			this.currentCell.record = 1;
			//this.dataValues = new String[this.fields.size()][this.project.getRecordsPerImage()]; //fields are x-coord, records are y
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(indexingWindow,
			"Well this is embarassing......\nI Can't seem to connect to the server.\n\nLet's try again in a little bit!",
			"Ooops!",
			JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public User getUser() {
		return user;
	}

	
	public void setUser(User user) {
		this.user = user;
	}
	
	
	public Batch getBatch() {
		return batch;
	}

	
	
	
	//current cell - not static
	//indexed data values - not static
	// listeners from entries, image, field help, etc.
	// listen to AND from
	//synchronization.java
	/*
	 * 
	 * 
	 * 
	 */
}
interface BatchStateListener
{
	public void valueChange(Cell cell, String newValue);
	public void currentCellChanged(Cell newCurrentCell);
}
