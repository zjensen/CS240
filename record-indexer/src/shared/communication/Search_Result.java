package shared.communication;

import java.util.ArrayList;
/**
 * Result from searching server 
 * @author zsjensen
 *
 */
public class Search_Result 
{
	private ArrayList<Search_Result_Tuple> tuples;
	/**
	 * Constructs search results with an arraylist of tup;es
	 * @param tuples
	 */
	public Search_Result(ArrayList<Search_Result_Tuple> tuples) 
	{
		this.tuples = tuples;
	}
	
	public void updateURLs(String host, int port)
	{
		ArrayList<Search_Result_Tuple> tuples = this.getTuples();
		for(int i=0;i<tuples.size();i++)
		{
			Search_Result_Tuple t = tuples.get(i);
			t.setImageURL("http://" + host + ":" + port +"/" + t.getImageURL());
		}
		this.setTuples(tuples);
	}

	/**
	 * @return the tuples
	 */
	public ArrayList<Search_Result_Tuple> getTuples() {
		return tuples;
	}

	/**
	 * @param tuples the tuples to set
	 */
	public void setTuples(ArrayList<Search_Result_Tuple> tuples) {
		this.tuples = tuples;
	}
	@Override
	/**
	 * @return String containing Search_Result_Tuples matching the search, else FAILED
	 */
	public String toString()
	{
		if(tuples == null || tuples.isEmpty())
		{
			return "FAILED\n";
		}
		else
		{
			StringBuilder result = new StringBuilder();
			for(Search_Result_Tuple tuple:tuples)
			{
				result.append(tuple.getBatchID() + "\n");
				result.append(tuple.getImageURL() + "\n");
				result.append(tuple.getValueID() + "\n");
				result.append(tuple.getFieldID() + "\n");
			}
			return result.toString();
		}
	}
}
