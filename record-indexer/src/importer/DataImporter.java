package importer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import server.DatabaseException;
import server.dba.Database;
import shared.model.Project;

public class DataImporter 
{	
	private Database database;
	public DataImporter() throws DatabaseException
	{
		database = new Database();
		database.initialize();
		
	}

	public static void main(String[] args) throws DatabaseException, IOException 
	{
		DataImporter importer = new DataImporter();
		File emptyDB = new File("database/empty_indexer_server");
		File fullDB = new File("database/indexer_server");
		Files.copy(emptyDB.toPath(), fullDB.toPath(), StandardCopyOption.REPLACE_EXISTING);
		File toImport = new File(args[0]);
	}
	public void parseFile(File file) throws ParserConfigurationException, SAXException, IOException, DatabaseException
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
			p.setTitle(title_Element.getTextContent());
			p.setFirstYCoord(Integer.parseInt(firstYCoord_Element.getTextContent()));
			p.setRecordHeight(Integer.parseInt(recordHeight_Element.getTextContent()));
			p.setRecordsPerImage(Integer.parseInt(recordsPerImage_Element.getTextContent()));
			database.startTransaction();
			database.getProjectsDAO().add(p);//add support to have add project return project ID
			database.endTransaction(true);
			//parse fields
		}
	}
	

}
