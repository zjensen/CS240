package shared.communication;
import java.util.ArrayList;

import shared.model.Project;

public class GetProjects_Result
{
	private ArrayList<Project> projects;
	/**
	 * If user input was valid, will take in the list of projects in the server
	 * @param projects
	 */
	public GetProjects_Result(ArrayList<Project> projects) 
	{
		this.projects = projects;
	}
	/**
	 * @return the projects
	 */
	public ArrayList<Project> getProjects() {
		return projects;
	}
	/**
	 * @param projects the projects to set
	 */
	public void setProjects(ArrayList<Project> projects) {
		this.projects = projects;
	}
	/**
	 * @returns FAILED if there was an error. Else project ID's and titles for all projects in server
	 */
	@Override
	public String toString()
	{
		if(projects.isEmpty())
		{
			return "FAILED\n";
		}
		else
		{
			StringBuilder sb = new StringBuilder();
			for(Project p : projects)
			{
				sb.append(p.getProjectID() + "\n" + p.getTitle() + "\n");
			}
			return sb.toString();
		}
		
	}
}
