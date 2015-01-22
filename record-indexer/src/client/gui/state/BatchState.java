package client.gui.state;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import client.communication.ClientCommunicator;
import client.communication.ClientException;
import client.gui.IndexingWindow;
import client.gui.LoginWindow;
import client.spell.Corrector;
import shared.communication.*;
import shared.model.*;

public class BatchState 
{
	// GENERAL
	private ArrayList<BatchStateListener> listeners;
	private ClientCommunicator cc;
	private Map<Integer,Corrector> correctors;
	
	// views
	private LoginWindow loginWindow;
	private IndexingWindow indexingWindow;
	
	//Synchronization
	private boolean inverted;
	private boolean highlightsVisible;
	private double zoomLevel;
	private User user;
	private String[][] dataValues;
	private Cell currentCell;
	private Batch batch;
	private ArrayList<Field> fields;
	private Project project;
	private int windowHeight;
	private int windowWidth;
	private Point windowPostion;
	private int horzontalDividerLocation;
	private int verticalDividerLocation;
	private int imageX;
	private int imageY;
	private ArrayList<String> fieldHelps;
	private int tableVScroll;
	private int tableHScroll;
	private int listVScroll;
	private int listHScroll;
	private int formVScroll;
	private int formHScroll;
	private int helpVScroll;
	private boolean[][] cellQuality;

	
	/**
	 * Starts the GUI
	 * @param host
	 * @param port
	 */
	public BatchState(String host, String port)
	{
		this.cc = new ClientCommunicator(host,port);
		listeners = new ArrayList<BatchStateListener>();
		this.windowHeight = 700;
		this.windowWidth = 1000;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.windowPostion = new Point(dim.width/2-500, dim.height/2-300);
		this.imageX=-250;
		this.imageY=0;
		this.zoomLevel = 0.5;
		this.currentCell = new Cell(0,0);
		this.verticalDividerLocation = 300;
		this.horzontalDividerLocation = 500;
		this.loginWindow = new LoginWindow(this);
		this.loginWindow.setVisible(true);
		tableVScroll=0;
		tableHScroll=0;
		listVScroll=0;
		listHScroll=0;
		formVScroll=0;
		formHScroll=0;
		helpVScroll=0;
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
				this.user = result.getUser();
				new BatchStateLoader(this);
				setupIndexer();
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
		this.save();
		this.clear();
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
		this.indexingWindow.getDataTabs().getDataForm().saveScroll();
		this.indexingWindow.getDataTabs().getDataTable().saveScroll();
		this.indexingWindow.getHelpTabs().saveScroll();
		new BatchStateSaver(this);
	}
	
	
	/**
	 * toggles whether or not image is inverted
	 */
	public void invertImage() 
	{
		this.inverted = !this.inverted;
		this.indexingWindow.invertImage();
	}
	
	
	/**
	 * Submits the batch the user is currently working on, and resets indexing window
	 */
	public void submit()
	{
		try 
		{
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<getProject().getRecordsPerImage();i++) //columns
			{
				for(int j=0;j<getFields().size();j++) //rows
				{
					sb.append(dataValues[i][j] + ",");
				}
				sb.deleteCharAt(sb.length()-1);
				sb.append(";");
			}
			sb.deleteCharAt(sb.length()-1); //removes last semicolon
			SubmitBatch_Result result = cc.submitBatch(new SubmitBatch_Params(this.user.getUsername(),this.user.getPassword(),Integer.toString(this.user.getBatchID()),sb.toString()));
			if(result.isSubmitted())
			{
				this.user.setBatchID(-1);
				this.batch = null;
				this.project = null;
				this.fields = null;
				this.fieldHelps = null;
				this.listeners.clear();
				File toDelete = new File(this.user.getUsername());
				toDelete.delete();
				this.verticalDividerLocation = this.indexingWindow.getVerticalDividerLocation();
				this.horzontalDividerLocation = this.indexingWindow.getHorzontalDividerLocation();
				this.indexingWindow.reset(this);
			}
			
		} 
		catch (ClientException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	/**
	 * zooms the image in or out
	 * @param true if zooming in, false if zooming out
	 */
	public void zoom(boolean in)
	{
		if(in && this.zoomLevel <= 2.25) //zoom in
		{
			zoomLevel += 0.25;
		}
		else if(!in && this.zoomLevel >= .5 ) //zoom out
		{
			zoomLevel -= 0.25;
		}
		this.indexingWindow.repaintImage();
	}
	
	
	/**
	 * Toggles the highlight setting for the image
	 */
	public void toggleHighlights()
	{
		this.highlightsVisible = !this.highlightsVisible;
		indexingWindow.getImagePanel().drawHighlights();
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
			DownloadBatch_Result result = cc.downloadBatch(new DownloadBatch_Params(this.user.getUsername(),this.user.getPassword(),Integer.toString(projectID)));
			if(result.getBatchID() == -1)
			{
				JOptionPane.showMessageDialog(indexingWindow,
						"There are no batches currently available for the selected project.\nSorry!",
						"No Batch Available",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.horzontalDividerLocation = this.getIndexingWindow().getHorzontalDividerLocation();
			this.verticalDividerLocation = this.getIndexingWindow().getVerticalDividerLocation();
			this.user.setBatchID(result.getBatchID());
			this.batch=result.getBatch();
			this.batch.setFile(result.getImageURL()); //updating image url from server
			this.project = result.getProject();
			this.fields = this.getFields();
			this.inverted = false;
			this.highlightsVisible = true;
			this.currentCell = new Cell(0,0);
			this.imageX=0;
			this.dataValues = new String[this.project.getRecordsPerImage()][this.fields.size()+1]; //fields are x-coord, records are y
			this.cellQuality = new boolean[this.project.getRecordsPerImage()][this.fields.size()];
			
			for(int i=0;i<getProject().getRecordsPerImage();i++) //columns
			{
				for(int j=0;j<getFields().size();j++) //rows
				{
					cellQuality[i][j] = true;
				}
			}
			
			this.correctors = new HashMap<Integer,Corrector>();
			for(int j=0;j<this.fields.size();j++)
			{
				if(this.fields.get(j).getKnownData()!=null)
				{
					Corrector c = new Corrector(this);
					c.useDictionary(this.fields.get(j).getKnownData());
					this.correctors.put(j, c);
				}
			}
			
			this.indexingWindow.reset(this);
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
	
	/**
	 * clears current users info
	 */
	public void clear()
	{
		this.inverted = false;
		this.highlightsVisible = false;
		this.zoomLevel = 0;
		this.user = null;
		this.dataValues = null;
		this.currentCell = null;
		this.batch = null;
		this.fields = null;
		this.project = null;
		this.listeners.clear();
	}

	/**
	 * Updates values, checks if word is valid, then allerts batch state listeners
	 * @param cell
	 * @param value
	 */
	public void valueChanged(Cell cell, String value)
	{
		value = value.trim();
		this.dataValues[cell.getRecord()][cell.getColumn()] = value;
		if(!value.isEmpty())
		{
			if(this.correctors.containsKey(cell.getColumn()))
			{
				Corrector c = this.correctors.get(cell.getColumn());
				this.cellQuality[cell.getRecord()][cell.getColumn()] = c.foundWord(value);
			}
		}
		// check
		for(BatchStateListener l : listeners)
		{
			l.valueChanged(cell, value);
		}
	}
	
	/**
	 * updates cell and alerts batch state listeners
	 * @param cell
	 */
	public void cellChanged(Cell cell)
	{
		this.currentCell = cell;
		for(BatchStateListener l : listeners)
		{
			l.currentCellChanged(cell);
		}
	}
	
	
	
	// GETTERS AND SETTERS
	
	
	/**
	 * Adds listener to array
	 * @param listener
	 */
	public void addListener(BatchStateListener listener)
	{
		listeners.add(listener);
	}
	
	/**
	 * @return the cc
	 */
	public ClientCommunicator getCc() {
		return cc;
	}

	/**
	 * @param cc the cc to set
	 */
	public void setCc(ClientCommunicator cc) {
		this.cc = cc;
	}

	/**
	 * @return the loginWindow
	 */
	public LoginWindow getLoginWindow() {
		return loginWindow;
	}

	/**
	 * @param loginWindow the loginWindow to set
	 */
	public void setLoginWindow(LoginWindow loginWindow) {
		this.loginWindow = loginWindow;
	}

	/**
	 * @return the indexingWindow
	 */
	public IndexingWindow getIndexingWindow() {
		return indexingWindow;
	}

	/**
	 * @param indexingWindow the indexingWindow to set
	 */
	public void setIndexingWindow(IndexingWindow indexingWindow) {
		this.indexingWindow = indexingWindow;
	}

	/**
	 * @return the inverted
	 */
	public boolean isInverted() {
		return inverted;
	}

	/**
	 * @param inverted the inverted to set
	 */
	public void setInverted(boolean inverted) {
		this.inverted = inverted;
	}

	/**
	 * @return the highlightsVisible
	 */
	public boolean isHighlightsVisible() {
		return highlightsVisible;
	}

	/**
	 * @param highlightsVisible the highlightsVisible to set
	 */
	public void setHighlightsVisible(boolean highlightsVisible) {
		this.highlightsVisible = highlightsVisible;
	}

	/**
	 * @return the zoomLevel
	 */
	public double getZoomLevel() {
		return zoomLevel;
	}

	/**
	 * @param zoomLevel the zoomLevel to set
	 */
	public void setZoomLevel(double zoomLevel) {
		this.zoomLevel = zoomLevel;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the dataValues
	 */
	public String[][] getDataValues() {
		return dataValues;
	}

	/**
	 * @param dataValues the dataValues to set
	 */
	public void setDataValues(String[][] dataValues) {
		this.dataValues = dataValues;
	}

	/**
	 * @return the currentCell
	 */
	public Cell getCurrentCell() {
		return currentCell;
	}
	
	/**
	 * @param currentCell the currentCell to set
	 */
	public void setCurrentCell(Cell currentCell) {
		this.currentCell = currentCell;
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
	 * @return the fields
	 */
	public ArrayList<Field> getFields() 
	{
		if(this.project!=null && this.fields==null)
		{
			try 
			{
				this.fields = cc.getFields(new GetFields_Params(this.user.getUsername(), this.user.getPassword(), Integer.toString(this.project.getProjectID()))).getFields();
			} catch (ClientException e) 
			{
				e.printStackTrace();
				return null;
			}
		}
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
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

	/**
	 * @return the windowHeight
	 */
	public int getWindowHeight() {
		return windowHeight;
	}

	/**
	 * @param windowHeight the windowHeight to set
	 */
	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}

	/**
	 * @return the windowWidth
	 */
	public int getWindowWidth() {
		return windowWidth;
	}

	/**
	 * @param windowWidth the windowWidth to set
	 */
	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}

	/**
	 * @return the windowPostion
	 */
	public Point getWindowPostion() {
		return windowPostion;
	}

	/**
	 * @param windowPostion the windowPostion to set
	 */
	public void setWindowPostion(Point windowPostion) {
		this.windowPostion = windowPostion;
	}

	/**
	 * @return the horzontalDividerLocation
	 */
	public int getHorzontalDividerLocation() {
		return horzontalDividerLocation;
	}

	/**
	 * @param horzontalDividerLocation the horzontalDividerLocation to set
	 */
	public void setHorzontalDividerLocation(int horzontalDividerLocation) {
		this.horzontalDividerLocation = horzontalDividerLocation;
	}

	/**
	 * @return the verticalDividerLocation
	 */
	public int getVerticalDividerLocation() {
		return verticalDividerLocation;
	}

	/**
	 * @param verticalDividerLocation the verticalDividerLocation to set
	 */
	public void setVerticalDividerLocation(int verticalDividerLocation) {
		this.verticalDividerLocation = verticalDividerLocation;
	}

	/**
	 * @return the imageX
	 */
	public int getImageX() {
		return imageX;
	}

	/**
	 * @param imageX the imageX to set
	 */
	public void setImageX(int imageX) {
		this.imageX = imageX;
	}

	/**
	 * @return the imageY
	 */
	public int getImageY() {
		return imageY;
	}

	/**
	 * @param imageY the imageY to set
	 */
	public void setImageY(int imageY) {
		this.imageY = imageY;
	}

	public ArrayList<String> getFieldHelps() {
		return fieldHelps;
	}

	public void setFieldHelps(ArrayList<String> fieldHelps) {
		if(fieldHelps == null && fields != null)
		{
			this.fieldHelps = new ArrayList<String>();
			for(Field field : fields)
			{
				try 
				{
					DownloadFile_Result result = cc.downloadFile(new DownloadFile_Params(field.getHelpHTML()));
					String help = new String(result.getFileBytes());
					this.fieldHelps.add(help);
				} 
				catch (ClientException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//this.fieldHelps = fieldHelps;
	}

	/**
	 * @return the listeners
	 */
	public ArrayList<BatchStateListener> getListeners() {
		return listeners;
	}

	/**
	 * @param listeners the listeners to set
	 */
	public void setListeners(ArrayList<BatchStateListener> listeners) {
		this.listeners = listeners;
	}

	/**
	 * @return the tableVScroll
	 */
	public int getTableVScroll() {
		return tableVScroll;
	}

	/**
	 * @param tableVScroll the tableVScroll to set
	 */
	public void setTableVScroll(int tableVScroll) {
		this.tableVScroll = tableVScroll;
	}

	/**
	 * @return the tableHScroll
	 */
	public int getTableHScroll() {
		return tableHScroll;
	}

	/**
	 * @param tableHScroll the tableHScroll to set
	 */
	public void setTableHScroll(int tableHScroll) {
		this.tableHScroll = tableHScroll;
	}

	/**
	 * @return the listVScroll
	 */
	public int getListVScroll() {
		return listVScroll;
	}

	/**
	 * @param listVScroll the listVScroll to set
	 */
	public void setListVScroll(int listVScroll) {
		this.listVScroll = listVScroll;
	}

	/**
	 * @return the listHScroll
	 */
	public int getListHScroll() {
		return listHScroll;
	}

	/**
	 * @param listHScroll the listHScroll to set
	 */
	public void setListHScroll(int listHScroll) {
		this.listHScroll = listHScroll;
	}

	/**
	 * @return the formVScholl
	 */
	public int getFormVScroll() {
		return formVScroll;
	}

	/**
	 * @param formVScholl the formVScholl to set
	 */
	public void setFormVScroll(int formVScholl) {
		this.formVScroll = formVScholl;
	}

	/**
	 * @return the formHScroll
	 */
	public int getFormHScroll() {
		return formHScroll;
	}

	/**
	 * @param formHScroll the formHScroll to set
	 */
	public void setFormHScroll(int formHScroll) {
		this.formHScroll = formHScroll;
	}

	/**
	 * @return the helpVScroll
	 */
	public int getHelpVScroll() {
		return helpVScroll;
	}

	/**
	 * @param helpVScroll the helpVScroll to set
	 */
	public void setHelpVScroll(int helpVScroll) {
		this.helpVScroll = helpVScroll;
	}

	/**
	 * @return the cellQuality
	 */
	public boolean[][] getCellQuality() {
		return cellQuality;
	}

	/**
	 * @param cellQuality the cellQuality to set
	 */
	public void setCellQuality(boolean[][] cellQuality) {
		this.cellQuality = cellQuality;
	}

	/**
	 * @return the correctors
	 */
	public Map<Integer, Corrector> getCorrectors() {
		return correctors;
	}

	/**
	 * @param correctors the correctors to set
	 */
	public void setCorrectors(Map<Integer, Corrector> correctors) {
		this.correctors = correctors;
	}
}
