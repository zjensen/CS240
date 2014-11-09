package client.searchGUI;

import java.util.ArrayList;

import javax.swing.*;

import shared.model.*;

@SuppressWarnings("serial")
public class SearchParamPanel extends JPanel
{
	private ArrayList<JRadioButton> projectButtons;
	private SearchGUIHandler handler;
	public ArrayList<JRadioButton> fieldButtons = new ArrayList<JRadioButton>();
	public SearchParamPanel() 
	{
//		super();
//		Border middleEdge = BorderFactory.createLoweredBevelBorder();
//		this.setBorder(middleEdge);
//		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//		this.add(Box.createRigidArea(new Dimension(0, 150)));
//		this.setMaximumSize(getPreferredSize());
	}
	public JPanel addProjects(ArrayList<Project> projects,ArrayList<Field> fields)
	{
		JPanel result = new JPanel();
		result.setLayout(new BoxLayout(result, BoxLayout.X_AXIS));

		for(int i=0;i<projects.size();i++)
		{
			JPanel p = new JPanel();
			p.add(new JLabel("<html><b>"+projects.get(i).getTitle()+"</b></html>"));
			p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
			p.setSize(800, 100);
			for(int j=0;j<fields.size();j++)
			{
				if(fields.get(j).getProjectID() == projects.get(i).getProjectID())
				{
					JRadioButton b = new JRadioButton(fields.get(j).getTitle());
					p.add(b);
					fieldButtons.add(b);
				}
			}
			result.add(p);
		}
		return result;
	}

	public SearchGUIHandler getHandler() {
		return handler;
	}

	public void setHandler(SearchGUIHandler handler) {
		this.handler = handler;
	}
	public ArrayList<JRadioButton> getProjectButtons() {
		return projectButtons;
	}
	public void setProjectButtons(ArrayList<JRadioButton> projectButtons) {
		this.projectButtons = projectButtons;
	}

}
