package server.dba;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import server.DatabaseException;

/**
 * Database class. Stores Database object classes.
 * Will open connection to DB
 * @author zsjensen
 *
 */
public class Database 
{
	private BatchesDAO batchesDAO;
	private FieldsDAO fieldsDAO;
	private ProjectsDAO projectsDAO;
	private RecordsDAO recordsDAO;
	private UsersDAO usersDAO;
	private ValuesDAO valuesDAO;
	private Connection connection;
	/**
	 * Constructs database class
	 * Sets database accept objects for each model as null
	 */
	public Database() 
	{
		this.batchesDAO = new BatchesDAO(this);
		this.fieldsDAO = new FieldsDAO(this);
		this.projectsDAO = new ProjectsDAO(this);
		this.recordsDAO = new RecordsDAO(this);
		this.usersDAO = new UsersDAO(this);
		this.valuesDAO = new ValuesDAO(this);
		connection = null;
	}
	/**
	 * Initializes the database
	 * @throws DatabaseException 
	 */
	public void initialize() throws DatabaseException
	{
		String driver = "org.sqlite.JDBC";
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			//cant load database driver
			throw new DatabaseException();
		}
	}
	
	/**
	 * @return the batchesDAO
	 */
	public BatchesDAO getBatchesDAO() {
		return batchesDAO;
	}
	/**
	 * @param batchesDAO the batchesDAO to set
	 */
	public void setBatchesDAO(BatchesDAO batchesDAO) {
		this.batchesDAO = batchesDAO;
	}
	/**
	 * @return the fieldsDAO
	 */
	public FieldsDAO getFieldsDAO() {
		return fieldsDAO;
	}
	/**
	 * @param fieldsDAO the fieldsDAO to set
	 */
	public void setFieldsDAO(FieldsDAO fieldsDAO) {
		this.fieldsDAO = fieldsDAO;
	}
	/**
	 * @return the projectsDAO
	 */
	public ProjectsDAO getProjectsDAO() {
		return projectsDAO;
	}
	/**
	 * @param projectsDAO the projectsDAO to set
	 */
	public void setProjectsDAO(ProjectsDAO projectsDAO) {
		this.projectsDAO = projectsDAO;
	}
	/**
	 * @return the recordsDAO
	 */
	public RecordsDAO getRecordsDAO() {
		return recordsDAO;
	}
	/**
	 * @param recordsDAO the recordsDAO to set
	 */
	public void setRecordsDAO(RecordsDAO recordsDAO) {
		this.recordsDAO = recordsDAO;
	}
	/**
	 * @return the usersDAO
	 */
	public UsersDAO getUsersDAO() {
		return usersDAO;
	}
	/**
	 * @param usersDAO the usersDAO to set
	 */
	public void setUsersDAO(UsersDAO usersDAO) {
		this.usersDAO = usersDAO;
	}
	/**
	 * @return the valuesDAO
	 */
	public ValuesDAO getValuesDAO() {
		return valuesDAO;
	}
	/**
	 * @param valuesDAO the valuesDAO to set
	 */
	public void setValuesDAO(ValuesDAO valuesDAO) {
		this.valuesDAO = valuesDAO;
	}
	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}
	/**
	 * @param connection the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * opens connection to database and starts transaction
	 * @throws DatabaseException 
	 */
	public void startTransaction() throws DatabaseException
	{
		String databaseName = "database" + File.separator + "indexer_server.sqlite";
		String connectionURL = "jdbc:sqlite:" + databaseName;
		connection = null;
		try 
		{
		    connection = DriverManager.getConnection(connectionURL);
		    connection.setAutoCommit(false); //don't auto commit, will commit at end of transaction
		}
		catch (SQLException e) 
		{
			throw new DatabaseException();
		}
	}
	
	/**
	 * Commits or rolls back transaction, then closes connection
	 * @param commit
	 * @throws DatabaseException 
	 */
	public void endTransaction(boolean commit) throws DatabaseException 
	{
		try
		{
			if(commit)
			{
				connection.commit(); //commit changes to database
			}
			else
			{
				connection.rollback(); //rollback changes
			}
		}
		catch(SQLException e)
		{
			throw new DatabaseException(); //or nah
		}
		finally
		{
			try
			{
				connection.close(); //close connection to database
			}
			catch(SQLException e)
			{
				throw new DatabaseException(); //or nah
			}
		}
		connection = null; //after connection closed or failed, reset as null
	}
}
