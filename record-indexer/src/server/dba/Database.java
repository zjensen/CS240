package server.dba;
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
	/**
	 * Constructs database class
	 * Sets database accept objects for each model as null
	 */
	public Database() 
	{
		this.batchesDAO = null;
		this.fieldsDAO = null;
		this.projectsDAO = null;
		this.recordsDAO = null;
		this.usersDAO = null;
		this.valuesDAO = null;
	}
	
	
	
}
