package importer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import server.DatabaseException;
import server.dba.Database;
import shared.model.*;

public class DataImporter 
{	
	private Database database;
	public DataImporter() throws DatabaseException
	{
		database = new Database();
		database.initialize();
		
	}

	/**
	 * 
	 * @param args
	 * @throws DatabaseException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public static void main(String[] args) throws DatabaseException, IOException, ParserConfigurationException, SAXException 
	{
		DataImporter importer = new DataImporter();
		File emptyDB = new File("database/empty_indexer_server");
		File fullDB = new File("database/indexer_server");
		Files.copy(emptyDB.toPath(), fullDB.toPath(), StandardCopyOption.REPLACE_EXISTING); //replace full db with empty one to start fresh
		File toImport = new File(args[0]); //xml file
		File recordsFile = new File("records");
		Files.copy(toImport.toPath(), recordsFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		importer = new DataImporter();
		importer.parseUsers(toImport);
		importer.parseProjects(toImport);
	}
	
	/**
	 * 
	 * @param file
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws DatabaseException
	 */
	public void parseProjects(File file) throws ParserConfigurationException, SAXException, IOException, DatabaseException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		NodeList projectList = doc.getElementsByTagName("project");
		
		for(int i=0;i<projectList.getLength();i++)
		{
			Element project_Element = (Element)projectList.item(i);	
			Element title_Element = (Element)project_Element.getElementsByTagName("title").item(0);
			Element firstYCoord_Element = (Element)project_Element.getElementsByTagName("firstycoord").item(0);
			Element recordHeight_Element = (Element)project_Element.getElementsByTagName("recordheight").item(0);
			Element recordsPerImage_Element = (Element)project_Element.getElementsByTagName("recordsperimage").item(0);
			
			Project p = new Project();
			int projectID = -1;
			p.setTitle(title_Element.getTextContent());
			p.setFirstYCoord(Integer.valueOf(firstYCoord_Element.getTextContent()));
			p.setRecordHeight(Integer.valueOf(recordHeight_Element.getTextContent()));
			p.setRecordsPerImage(Integer.valueOf(recordsPerImage_Element.getTextContent()));
			
			database.startTransaction();
			try
			{
				projectID = database.getProjectsDAO().add(p);
				database.endTransaction(true);
			}
			catch(DatabaseException e)
			{
				database.endTransaction(true);
				throw new DatabaseException();
			}
			
			ArrayList<Integer> fieldIDs = parseFields(project_Element, projectID);
			parseBatches(project_Element, projectID, Integer.valueOf(recordsPerImage_Element.getTextContent()), fieldIDs);
		}
	}
	
	/**
	 * 
	 * @param values_Element
	 * @param projectID
	 * @param batchID
	 * @param recordID
	 * @param fieldIDs
	 * @throws DatabaseException
	 */
	public void parseValues(Element values_Element, int projectID, int batchID, int recordID, ArrayList<Integer> fieldIDs) throws DatabaseException
	{
		NodeList valueList = values_Element.getElementsByTagName("value");
		
		for(int i=0;i<valueList.getLength();i++)
		{
			Element value_Element = (Element)valueList.item(i);	
			Value v = new Value();
			v.setBatchID(batchID);
			v.setFieldID(fieldIDs.get(i));
			v.setProjectID(projectID);
			v.setRecordID(recordID);
			v.setData(value_Element.getTextContent());
			database.startTransaction();
			try
			{
				database.getValuesDAO().add(v);
				database.endTransaction(true);
			}
			catch(DatabaseException e)
			{
				database.endTransaction(true);
				throw new DatabaseException();
			}
		}
	}
	
	/**
	 * 
	 * @param records_Element
	 * @param projectID
	 * @param batchID
	 * @param fieldIDs
	 * @throws DatabaseException
	 */
	public void parseRecords(Element records_Element, int projectID, int batchID, ArrayList<Integer> fieldIDs) throws DatabaseException
	{
		NodeList recordList = records_Element.getElementsByTagName("record");
		
		for(int i=0;i<recordList.getLength();i++)
		{
			Element record_Element = (Element)recordList.item(i);	
			Element values_Element = (Element)record_Element.getElementsByTagName("values").item(0);
			Record r = new Record();
			int recordID = -1;
			r.setBatchID(batchID);
			r.setProjectID(projectID);
			r.setRow(i);
			database.startTransaction();
			try
			{
				recordID = database.getRecordsDAO().add(r);
				database.endTransaction(true);
			}
			catch(DatabaseException e)
			{
				database.endTransaction(true);
				throw new DatabaseException();
			}
			parseValues(values_Element,projectID,batchID,recordID,fieldIDs);
		}
	}
	
	/**
	 * 
	 * @param project_Element
	 * @param projectID
	 * @param recordsPerImage
	 * @param fieldIDs
	 * @throws DatabaseException
	 */
	public void parseBatches(Element project_Element, int projectID, int recordsPerImage, ArrayList<Integer> fieldIDs) throws DatabaseException
	{
		NodeList batchList = project_Element.getElementsByTagName("image");
		
		for(int i=0;i<batchList.getLength();i++)
		{
			Element batch_Element = (Element)batchList.item(i);
			Element file_Element = (Element)batch_Element.getElementsByTagName("file").item(0);
			Element records_Element = (Element)batch_Element.getElementsByTagName("records").item(0);
			
			Batch b = new Batch();
			int batchID = -1;
			b.setProjectID(projectID);
			b.setFile(file_Element.getTextContent());
			
			NodeList records = records_Element.getElementsByTagName("record");
			if(records.getLength() == recordsPerImage)
			{
				b.setCompleted(true);
			}
			else
			{
				b.setCompleted(false);
			}
			
			database.startTransaction();
			try
			{
				batchID = database.getBatchesDAO().add(b);
				database.endTransaction(true);
			}
			catch(DatabaseException e)
			{
				database.endTransaction(true);
				throw new DatabaseException();
			}
			
			if(records_Element != null)//there are records associated with batch
			{
				parseRecords(records_Element, projectID, batchID, fieldIDs);
			}
		}
	}
	
	/**
	 * 
	 * @param project_Element
	 * @param projectID
	 * @return
	 * @throws DatabaseException
	 */
	public ArrayList<Integer> parseFields(Element project_Element, int projectID) throws DatabaseException
	{
		ArrayList<Integer> fieldIDs = new ArrayList<Integer>();
		NodeList fieldList = project_Element.getElementsByTagName("field");
		
		for(int i=0;i<fieldList.getLength();i++)
		{
			Element field_Element = (Element)fieldList.item(i);	
			Element title_Element = (Element)field_Element.getElementsByTagName("title").item(0);
			Element xCoord_Element = (Element)field_Element.getElementsByTagName("xcoord").item(0);
			Element width_Element = (Element)field_Element.getElementsByTagName("width").item(0);
			Element helpHTML_Element = (Element)field_Element.getElementsByTagName("helphtml").item(0);
			Element knownData_Element = (Element)field_Element.getElementsByTagName("knowndata").item(0);
			
			Field f = new Field();
			int fieldID = -1;
			f.setProjectID(projectID);
			f.setTitle(title_Element.getTextContent());
			f.setXCoord(Integer.valueOf(xCoord_Element.getTextContent()));
			f.setWidth(Integer.valueOf(width_Element.getTextContent()));
			f.setHelpHTML(helpHTML_Element.getTextContent());
			if(knownData_Element != null) //optional filepath
			{
				f.setKnownData(knownData_Element.getTextContent());
			}
			f.setColumn(i);
			
			database.startTransaction();
			try
			{
				fieldID = database.getFieldsDAO().add(f);
				database.endTransaction(true);
			}
			catch(DatabaseException e)
			{
				database.endTransaction(true);
				throw new DatabaseException();
			}
			fieldIDs.add(fieldID);
		}
		return fieldIDs;
	}
	
	/**
	 * Adds users to database
	 * @param file
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws DatabaseException
	 */
	public void parseUsers(File file) throws ParserConfigurationException, SAXException, IOException, DatabaseException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		NodeList userList = doc.getElementsByTagName("user");
		
		for(int i=0;i<userList.getLength();i++)
		{
			Element user_Element = (Element)userList.item(i);	
			Element username_Element = (Element)user_Element.getElementsByTagName("username").item(0);
			Element password_Element = (Element)user_Element.getElementsByTagName("password").item(0);
			Element firstName_Element = (Element)user_Element.getElementsByTagName("firstname").item(0);
			Element lastName_Element = (Element)user_Element.getElementsByTagName("lastname").item(0);
			Element email_Element = (Element)user_Element.getElementsByTagName("email").item(0);
			Element indexedRecords_Element = (Element)user_Element.getElementsByTagName("indexedrecords").item(0);
			
			User u = new User();
			u.setUsername(username_Element.getTextContent());
			u.setPassword(password_Element.getTextContent());
			u.setFirstName(firstName_Element.getTextContent());
			u.setLastName(lastName_Element.getTextContent());
			u.setEmail(email_Element.getTextContent());
			u.setIndexedRecords(Integer.valueOf(indexedRecords_Element.getTextContent()));
			
			database.startTransaction();
			try
			{
				database.getUsersDAO().add(u);
				database.endTransaction(true);
			}
			catch(DatabaseException e)
			{
				database.endTransaction(true);
				throw new DatabaseException();
			}
		}
	}

}
