package client.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.coobird.thumbnailator.Thumbnails;
import shared.model.Project;
import client.gui.state.BatchState;

@SuppressWarnings("serial")
public class DownloadBatchDialog extends JDialog implements ActionListener
{
	private BatchState bs;
	private JComboBox<String> projectBox;
	private JButton sampleCancelButton;
	private JButton cancelButton;
	private JButton downloadButton;
	private JButton sampleButton;
	private JLabel projectLabel;
	private JPanel p1;
	private JPanel p2;
	private ArrayList<Project> projects;
	private JDialog sampleImageDialog;
	public DownloadBatchDialog(JFrame frame, BatchState bs)
	{
		super(frame, "Download Batch");
		this.setModal(true);
		
	    this.setLayout(new BorderLayout());
		this.bs = bs;
		projects = this.bs.getProjects();
		projectBox = new JComboBox<String>();
		for(int i=0;i<projects.size();i++)
		{
			projectBox.addItem(projects.get(i).getTitle());
		}
		
		projectLabel = new JLabel("Projects: ");
		sampleButton = new JButton("View Sample");
		sampleButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		downloadButton = new JButton("Download");
		downloadButton.addActionListener(this);
		
		sampleCancelButton = new JButton("Cancel");
		sampleCancelButton.addActionListener(this);
		
		p1 = new JPanel();
		p1.setLayout(new FlowLayout(FlowLayout.CENTER));
		p2 = new JPanel();
		p2.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		this.setResizable(false);
		this.setModal(true);
		
		this.setSize(400, 100);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		setup();
	}
	
	public void setup()
	{
		 p1.add(projectLabel);
		 p1.add(projectBox);
		 p1.add(sampleButton);
		 p2.add(cancelButton);
		 p2.add(downloadButton);
		 
		 this.add(p1,BorderLayout.PAGE_START);
		 this.add(p2,BorderLayout.PAGE_END);
	}
	@Override
	public void actionPerformed(ActionEvent a) 
	{
		if(a.getSource() == sampleButton)
		{
			int index = projectBox.getSelectedIndex();
			
			BufferedImage image = bs.getSampleImage(projects.get(index).getProjectID());
			try {
				image = Thumbnails.of(image).size(500, 500).asBufferedImage();
			} catch (IOException e) {
				e.printStackTrace();
			}
			sampleImageDialog = new JDialog(this);
			sampleImageDialog.setLayout(new BorderLayout());
			sampleImageDialog.setModal(true);
			
			sampleImageDialog.setSize(image.getWidth(), image.getHeight()+70);
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			sampleImageDialog.setLocation(dim.width/2-sampleImageDialog.getSize().width/2, dim.height/2-sampleImageDialog.getSize().height/2);
			sampleImageDialog.setResizable(false);
			ImageIcon icon = new ImageIcon(image);
			JPanel j = new JPanel();
			j.add(new JLabel(icon));
			j.revalidate();
			JPanel j2 = new JPanel();
			j2.setLayout(new FlowLayout(FlowLayout.CENTER));
			j2.add(sampleCancelButton);
			sampleImageDialog.add(j,BorderLayout.PAGE_START);
			sampleImageDialog.add(j2,BorderLayout.PAGE_END);
			sampleImageDialog.setVisible(true);
		}
		else if(a.getSource() == cancelButton)
		{
			this.dispose();
		}
		else if(a.getSource() == downloadButton)
		{
			this.dispose();
			bs.downloadBatch(projects.get(projectBox.getSelectedIndex()).getProjectID());
		}
		else if(a.getSource() == sampleCancelButton)
		{
			sampleImageDialog.dispose();
		}
		
	}
}
