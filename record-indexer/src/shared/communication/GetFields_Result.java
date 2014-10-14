package shared.communication;

import java.util.ArrayList;

import shared.model.*;

public class GetFields_Result 
{
	private ArrayList<Field> fields;

	/**
	 * Results from getting project fields
	 * @param fields
	 */
	public GetFields_Result(ArrayList<Field> fields) 
	{
		this.fields = fields;
	}

	/**
	 * @return the fields
	 */
	public ArrayList<Field> getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}
	/**
	 * @return FAILED if no fields, else project ID, fieldID, and field title for all fields
	 */
	@Override
	public String toString()
	{
		if(fields==null || fields.isEmpty())
		{
			return "FAILED/n";
		}
		else
		{
			StringBuilder result = new StringBuilder();
			for(Field f : fields)
			{
				result.append(f.getProjectID() + "/n");
				result.append(f.getFieldID() + "/n");
				result.append(f.getTitle() + "/n");
			}
			return result.toString();
		}
	}
}
