package client.communication;

import static org.junit.Assert.*;
import importer.DataImporter;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import server.dba.*;
import shared.communication.*;
import shared.model.Batch;
import shared.model.User;

public class ClientCommunicator_Test 
{
	private ClientCommunicator cc;
	private Database db;
	
	@BeforeClass
	public static void setUpClass() throws Exception
	{
		String[] args = {"/Users/zsjensen/Downloads/Records/Records.xml"};
		DataImporter.main(args);
	}
	@Before
	public void setUp() throws Exception 
	{
		Database.initialize();
		db = new Database();
		cc = new ClientCommunicator("localhost","39640");
	}

	@After
	public void tearDown() throws Exception 
	{
		db=null;
		cc=null;
	}

	@Test
	public void testValidateUser() throws Exception 
	{
		ValidateUser_Params params1 = new ValidateUser_Params("test1","test1");
		ValidateUser_Result result1 = cc.validateUser(params1);
		assertEquals(result1.toString(),"TRUE\nTest\nOne\n0\n");
		
		ValidateUser_Params params2 = new ValidateUser_Params("test2","test2");
		ValidateUser_Result result2 = cc.validateUser(params2);
		assertEquals(result2.toString(),"TRUE\nTest\nTwo\n0\n");
		
		ValidateUser_Params paramsFail1 = new ValidateUser_Params("test1","test2");
		ValidateUser_Result resultFail1 = cc.validateUser(paramsFail1);
		assertEquals(resultFail1.toString(),"FALSE\n");
		
		ValidateUser_Params paramsFail2 = new ValidateUser_Params("","test2");
		ValidateUser_Result resultFail2 = cc.validateUser(paramsFail2);
		assertEquals(resultFail2.toString(),"FALSE\n");
		
		ValidateUser_Params paramsFail3 = new ValidateUser_Params("test1","");
		ValidateUser_Result resultFail3 = cc.validateUser(paramsFail3);
		assertEquals(resultFail3.toString(),"FALSE\n");
		
		ValidateUser_Params paramsFail4 = new ValidateUser_Params("","");
		ValidateUser_Result resultFail4 = cc.validateUser(paramsFail4);
		assertEquals(resultFail4.toString(),"FALSE\n");
		
		ValidateUser_Params paramsFail5 = new ValidateUser_Params("!@#$%^&*","!@#$%^&*())?><MNBGVFCDXSERTYU");
		ValidateUser_Result resultFail5 = cc.validateUser(paramsFail5);
		assertEquals(resultFail5.toString(),"FALSE\n");
	}
	@Test
	public void testGetProjects() throws Exception 
	{
		GetProjects_Params params1 = new GetProjects_Params("test1","test1");
		GetProjects_Result result1 = cc.getProjects(params1);
		assertEquals(result1.toString(),"1\n1890 Census\n2\n1900 Census\n3\nDraft Records\n");
		
		GetProjects_Params paramsFail1 = new GetProjects_Params("test2","test1");
		GetProjects_Result resultFail1 = cc.getProjects(paramsFail1);
		assertEquals(resultFail1.toString(),"FAILED\n");
		
	}
	@Test
	public void testGetSampleImage() throws Exception 
	{
		GetSampleImage_Params params1 = new GetSampleImage_Params("test1","test1","1");
		GetSampleImage_Result result1 = cc.getSampleImage(params1);
		assertEquals(result1.toString(),"http://localhost:39640/records/images/1890_image0.png\n"); //correct - toString

		GetSampleImage_Params params2 = new GetSampleImage_Params("test1","test1","2");
		GetSampleImage_Result result2 = cc.getSampleImage(params2);
		assertEquals(result2.toString(),"http://localhost:39640/records/images/1900_image0.png\n"); //correct - toString
		
		GetSampleImage_Params params3 = new GetSampleImage_Params("test1","test1","3");
		GetSampleImage_Result result3 = cc.getSampleImage(params3);
		assertEquals(result3.toString(),"http://localhost:39640/records/images/draft_image0.png\n"); //correct - toString
		
		GetSampleImage_Params params4 = new GetSampleImage_Params("test","test2","1");
		GetSampleImage_Result result4 = cc.getSampleImage(params4);
		assertEquals(result4.toString(),"FAILED\n"); //incorrect user

		GetSampleImage_Params params5 = new GetSampleImage_Params("test1","test1","4");
		GetSampleImage_Result result5 = cc.getSampleImage(params5);
		assertEquals(result5.toString(),"FAILED\n"); //incorrect project
	}
	@Test
	public void testDownloadBatch() throws Exception 
	{	
		DownloadBatch_Params params1 = new DownloadBatch_Params("test1","test1","1");
		DownloadBatch_Result result1 = cc.downloadBatch(params1);
		assertEquals(result1.getBatchID(),1); //correct - toString
		
		DownloadBatch_Params params2 = new DownloadBatch_Params("test1","test1","3");
		DownloadBatch_Result result2 = cc.downloadBatch(params2);
		assertEquals(result2.toString(),"FAILED\n");
		
		db.startTransaction();
		Batch batch = db.getBatchesDAO().get(1);
		User user = db.getUsersDAO().get(1);
		
		assertEquals(batch.getUserID(),user.getBatchID()); //updated batchIDs
		assertEquals(user.getUserID(),batch.getBatchID()); //updated userIDs
		
		user.setBatchID(2);
		batch.setUserID(3);
		db.getBatchesDAO().update(batch);
		db.getUsersDAO().update(user);
		db.endTransaction(true);
		
		DownloadBatch_Params params3 = new DownloadBatch_Params("test1","test1","1");
		DownloadBatch_Result result3 = cc.downloadBatch(params3);
		assertEquals(result3.toString(),"FAILED\n");
		
		DownloadBatch_Params params4 = new DownloadBatch_Params("test1","test2","1");
		DownloadBatch_Result result4 = cc.downloadBatch(params4);
		assertEquals(result4.toString(),"FAILED\n");
		
		DownloadBatch_Params params5 = new DownloadBatch_Params("test2","test2","4");
		DownloadBatch_Result result5 = cc.downloadBatch(params5);
		assertEquals(result5.toString(),"FAILED\n");
		
		DownloadBatch_Params params6 = new DownloadBatch_Params("test2","test2","2");
		DownloadBatch_Result result6 = cc.downloadBatch(params6);
		String output = "21\n2\nhttp://localhost:39640/records/images/1900_image0.png\n204\n62\n10\n5\n"
						+ "5\n0\nGender\nhttp://localhost:39640/records/fieldhelp/gender.html\n45\n205\nhttp://localhost:39640/records/knowndata/genders.txt\n"
						+ "6\n1\nAge\nhttp://localhost:39640/records/fieldhelp/age.html\n250\n120\n"
						+ "7\n2\nLast Name\nhttp://localhost:39640/records/fieldhelp/last_name.html\n370\n325\nhttp://localhost:39640/records/knowndata/1900_last_names.txt\n"
						+ "8\n3\nFirst Name\nhttp://localhost:39640/records/fieldhelp/first_name.html\n695\n325\nhttp://localhost:39640/records/knowndata/1900_first_names.txt\n"
						+ "9\n4\nEthnicity\nhttp://localhost:39640/records/fieldhelp/ethnicity.html\n1020\n450\nhttp://localhost:39640/records/knowndata/ethnicities.txt\n";
		assertEquals(result6.toString(),output);
	}

	@Test
	public void testSubmitBatch() throws Exception 
	{
		SubmitBatch_Params params1 = new SubmitBatch_Params("test1", "test1", "1", "1,1,1,1;2,2,2,2;3,3,3,3;4,4,4,4;5,5,5,5;6,6,6,6;7,7,7,7;8,8,8,8"); //correct
		SubmitBatch_Result result1 = cc.submitBatch(params1);
		assertEquals(result1.toString(),"TRUE\n");
		
		SubmitBatch_Params params2 = new SubmitBatch_Params("test1", "test1", "2", "1,1,1;2,2,2,2;,3,,4,4,4;5,5,5,5;6,,6;7,7,,7,8,8,"); //incorrect
		SubmitBatch_Result result2 = cc.submitBatch(params2);
		assertEquals(result2.toString(),"FALSE\n");
		
		SubmitBatch_Params params3 = new SubmitBatch_Params("test1", "test1", "4", ",1,,;,,2,;,3,,;,,,;,,,;,,,;,,,;,,,"); //correct
		SubmitBatch_Result result3 = cc.submitBatch(params3);
		assertEquals(result3.toString(),"TRUE\n");
		
		SubmitBatch_Params params6 = new SubmitBatch_Params("sheila", "parker", "5", ",,,;,,,;,,,;,,,;,,,;,,,;,,,;,,,"); //correct
		SubmitBatch_Result result6 = cc.submitBatch(params6);
		assertEquals(result6.toString(),"TRUE\n");
		
		SubmitBatch_Params params4 = new SubmitBatch_Params("test1", "test1", "25", "1,1,1,1;2,2,2,2;3,3,3,3;4,4,4,4;5,5,5,5;6,6,6,6;7,7,7,7;8,8,8,8"); //incorrect field number
		SubmitBatch_Result result4 = cc.submitBatch(params4);
		assertEquals(result4.toString(),"FALSE\n");
		
		SubmitBatch_Params params5 = new SubmitBatch_Params("test2", "test2", "3", "1,1,1,1;2,2,2,2;3,3,3,3;4,4,4,4;5,5,5,5;6,6,6,6;7,7,7,7;8,8,8,8"); //correct, check user and batch
		SubmitBatch_Result result5 = cc.submitBatch(params5);
		assertEquals(result5.toString(),"TRUE\n");
		db.startTransaction();
		User user = db.getUsersDAO().get(1);
		User user3 = db.getUsersDAO().get(3);
		Batch batch1 = db.getBatchesDAO().get(1);
		Batch batch4 = db.getBatchesDAO().get(4);
		Batch batch5 = db.getBatchesDAO().get(5);
		assertEquals(user.getBatchID(),-1);
		assertEquals(user.getIndexedRecords(),16);
		assertEquals(user3.getIndexedRecords(),8);
		assertEquals(batch1.getCompleted(),true);
		assertEquals(batch4.getCompleted(),false);
		assertEquals(batch5.getCompleted(),false);
		db.endTransaction(false);
		
	}
	@Test
	public void testGetFields() throws Exception 
	{
		GetFields_Params params1= new GetFields_Params("test1", "test1", ""); //correct
		GetFields_Result result1 = cc.getFields(params1);
		assertEquals(result1.getFields().size(),13);
		
		GetFields_Params params2= new GetFields_Params("test1", "test1", "2"); // correct
		GetFields_Result result2 = cc.getFields(params2);
		assertEquals(result2.getFields().size(),5);
		
		GetFields_Params params3= new GetFields_Params("test1", "test1", "3"); //correct - toString
		GetFields_Result result3 = cc.getFields(params3);
		assertEquals(result3.toString(),"3\n10\nLast Name\n3\n11\nFirst Name\n3\n12\nAge\n3\n13\nEthnicity\n");
		
		GetFields_Params params4= new GetFields_Params("test1", "test1", "4"); //incorrect project
		GetFields_Result result4 = cc.getFields(params4);
		assertEquals(result4.toString(),"FAILED\n");
		
		GetFields_Params params5= new GetFields_Params("test2", "test1", "2"); //incorrect user
		GetFields_Result result5 = cc.getFields(params5);
		assertEquals(result5.toString(),"FAILED\n");
	}
	@Test
	public void testSearch() throws Exception 
	{
		Search_Params params1 = new Search_Params("test1","test1","10,11,12,13","gOrDoN"); //checking different case
		Search_Result result1 = cc.search(params1);
		assertEquals(result1.getTuples().size(),2);
		
		Search_Params params2 = new Search_Params("test1","test1","12,13","19"); //correct
		Search_Result result2 = cc.search(params2);
		assertEquals(result2.getTuples().size(),15);
		
		Search_Params params3 = new Search_Params("test2","test2","12,1,7,8","WHITE"); //correct value, wrong fields
		Search_Result result3 = cc.search(params3);
		assertEquals(result3.getTuples().size(),0);
		
		Search_Params params4 = new Search_Params("test2","test2","12,1,7,8","WHITE"); //correct value, wrong fields - checking string
		Search_Result result4 = cc.search(params4);
		assertEquals(result4.toString(),"FAILED\n");
		
		Search_Params params5 = new Search_Params("test2","test1","13","WHITE"); //incorrect user
		Search_Result result5 = cc.search(params5);
		assertEquals(result5.toString(),"FAILED\n");
		
		Search_Params params6 = new Search_Params("test2","test2","10,12,13,","HiSpAnIC,26"); //multiple fields, multiple results
		Search_Result result6 = cc.search(params6);
		assertEquals(result6.getTuples().size(),28);
		
		Search_Params params7 = new Search_Params("test2","test2","11,999999999,13","ZACHARY"); //incorrect user
		Search_Result result7 = cc.search(params7);
		assertEquals(result7.toString(),"57\nhttp://localhost:39640/records/images/draft_image16.png\n6\n11\n"); //correct - toString
	}
	@Test
	public void testDownloadFile() throws Exception 
	{
		DownloadFile_Params params = new DownloadFile_Params("records/images/1890_image0.png");
		DownloadFile_Result result = cc.downloadFile(params);
		Path path = new File("records/images/1890_image0.png").toPath();
		assertTrue(Arrays.equals(result.getFileBytes(), Files.readAllBytes(path)));
	}

}
