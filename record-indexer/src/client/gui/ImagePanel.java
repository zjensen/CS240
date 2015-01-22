package client.gui;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;

import javax.imageio.*;
import java.util.*;
import java.io.*;
import java.net.URL;

import javax.swing.JComponent;

import client.gui.state.BatchState;
import client.gui.state.BatchStateListener;
import client.gui.state.Cell;

@SuppressWarnings("serial")
public class ImagePanel extends JComponent 
{
	private BatchState bs;
	private static Image NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
	
	private DrawingImage drawingImage;
	
	// HIGHLIGHTING
	private DrawingRect drawingRect;
	private DrawingRect[][] cells;
	private int highX;
	private int highY;
	private int highHeight;
	private int highWidth;
	
	private AffineTransform transform;
	
	private int w_originX;
	private int w_originY;
	private double scale;
	
	// TRANSLATING
	private boolean dragging;
	private int w_dragStartX;
	private int w_dragStartY;
	private int w_dragStartOriginX;
	private int w_dragStartOriginY;

	private ArrayList<DrawingShape> shapes;
	
	
	private BufferedImage image;

	public ImagePanel(BatchState bs) 
	{
		this.bs = bs;
		bs.addListener(bsListener);
		this.drawingImage=null;
		this.drawingRect=null;
		this.image=null;
		w_originX = bs.getImageX();
		w_originY = bs.getImageY();
		scale = bs.getZoomLevel();
		
		initDrag();

		shapes = new ArrayList<DrawingShape>();
		
		this.requestFocusInWindow();
		this.setBackground(new Color(225, 225, 225));
		this.setPreferredSize(new Dimension(1000, 300));
		this.setMinimumSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(1000, 1000));
		
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addMouseWheelListener(mouseAdapter);
		Image image = loadImage("");
		if(bs.getBatch()!= null)
		{
			image = loadImage(bs.getBatch().getFile());
			this.image=(BufferedImage)image;
			if(bs.getImageX()==0)
			{
				w_originX = image.getWidth(null)/2;
				w_originY = image.getHeight(null)/2;
			}
			drawCells();
		}
		drawingImage = new DrawingImage(image, new Rectangle2D.Double(0, 0, image.getWidth(null), image.getHeight(null)));
		shapes.add(drawingImage);
		drawHighlights();
		if(bs.isInverted() && bs.getBatch()!= null)
		{
			invertImage();
		}
	}
	
	/**
	 * draws highlight based on current selected cell
	 */
	public void drawHighlights()
	{
		if(bs.getBatch()==null)
		{
			return;
		}
		if(shapes.size()==2)
		{
			shapes.remove(1);
		}
		if(this.bs.isHighlightsVisible())
		{
			highWidth = bs.getFields().get(bs.getCurrentCell().getColumn()).getWidth();
			highHeight = bs.getProject().getRecordHeight();
			highX = bs.getFields().get(bs.getCurrentCell().getColumn()).getXCoord();
			highY = bs.getProject().getFirstYCoord() + (bs.getCurrentCell().getRecord()*highHeight);
			this.drawingRect = new DrawingRect(new Rectangle2D.Double(highX, highY, highWidth, highHeight), new Color(23,107,236,100));
			drawingRect.changeColor(new Color(23,107,236,100));
		}
		else
		{
			highWidth = bs.getFields().get(bs.getCurrentCell().getColumn()).getWidth();
			highHeight = bs.getProject().getRecordHeight();
			highX = bs.getFields().get(bs.getCurrentCell().getColumn()).getXCoord();
			highY = bs.getProject().getFirstYCoord() + (bs.getCurrentCell().getRecord()*highHeight);
			this.drawingRect = new DrawingRect(new Rectangle2D.Double(highX, highY, highWidth, highHeight), new Color(23,107,236,0));
			drawingRect.changeColor(new Color(23,107,236,0));
		}
		shapes.add(drawingRect);
		this.repaint();
	}
	
	/**
	 * creates rectangles for every cell in image
	 */
	public void drawCells()
	{
		cells = new DrawingRect[this.bs.getProject().getRecordsPerImage()][this.bs.getFields().size()];
		int firstY = bs.getProject().getFirstYCoord();
		highHeight = bs.getProject().getRecordHeight();
		for(int i=0;i<bs.getProject().getRecordsPerImage();i++) //rows
		{
			highY = firstY + (i*highHeight);
			for(int j=0;j<bs.getFields().size();j++) //columns
			{
				highWidth = bs.getFields().get(j).getWidth();
				highX = bs.getFields().get(j).getXCoord();
				cells[i][j] = new DrawingRect(new Rectangle2D.Double(highX, highY, highWidth, highHeight), new Color(23,107,236,100));
			}
		}
	}
	
	/**
	 * changes scale level then repaints with new scale
	 */
	public void zoomImage()
	{
		if(this.scale!=this.bs.getZoomLevel())
		{
			this.scale=this.bs.getZoomLevel();
			this.repaint();
		}
	}
	
	/**
	 * inverts then repaints image panel
	 */
	public void invertImage()
	{
		RescaleOp op = new RescaleOp(-1.0f, 255f, null);
		BufferedImage negative = op.filter(this.image, null);
		this.image = negative;
		shapes.clear();
		drawingImage = new DrawingImage(image, new Rectangle2D.Double(0, 0, image.getWidth(null), image.getHeight(null)));
		shapes.add(drawingImage);
		shapes.add(drawingRect);
		this.repaint();
		this.revalidate();
	}
	
	/**
	 * prepares to drag image
	 */
	private void initDrag() {
		dragging = false;
		w_dragStartX = 0;
		w_dragStartY = 0;
		w_dragStartOriginX = 0;
		w_dragStartOriginY = 0;
	}
	
	/**
	 * loads and returns image from url
	 * @param imageFile
	 * @return
	 */
	private Image loadImage(String imageFile) {
		try {
			InputStream input = new URL(imageFile).openStream();
			BufferedImage image = ImageIO.read(input);
			this.image=image;
			return image ;
		}
		catch (Exception e) {
			return NULL_IMAGE;
		}
	}
	
	/**
	 * updates scale then repaints using new scale
	 * @param newScale
	 */
	public void setScale(double newScale) {
		bs.setZoomLevel(newScale);
		this.repaint();
	}
	
	/**
	 * updates origin
	 * @param w_newOriginX
	 * @param w_newOriginY
	 */
	public void setOrigin(int w_newOriginX, int w_newOriginY) {
		bs.setImageX(w_newOriginX);
		bs.setImageY(w_newOriginY);
		this.repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);
		g2.translate(ImagePanel.this.getWidth()/2, ImagePanel.this.getHeight()/2);
		g2.scale(bs.getZoomLevel(), bs.getZoomLevel());
		g2.translate(-w_originX, -w_originY);
		drawShapes(g2);
	}
	
	private void drawBackground(Graphics2D g2) {
		g2.setColor(getBackground());
		g2.fillRect(0,  0, getWidth(), getHeight());
	}

	private void drawShapes(Graphics2D g2) {
		for (DrawingShape shape : shapes) {
			shape.draw(g2);
		}
	}
	
	private MouseAdapter mouseAdapter = new MouseAdapter() {
		
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			int d_X = e.getX();
			int d_Y = e.getY();
			transform = new AffineTransform();
			
			transform.translate(getWidth()/2, getHeight()/2);
			transform.scale(bs.getZoomLevel(), bs.getZoomLevel());
			transform.translate(-w_originX, -w_originY);
			
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex) {
				return;
			}
			int w_X = (int)w_Pt.getX();
			int w_Y = (int)w_Pt.getY();
			
			boolean hitCell = false;
			
			Graphics2D g2 = (Graphics2D)getGraphics();
			
			Cell cell = null;
			for(int i=0;i<bs.getProject().getRecordsPerImage();i++) //rows
			{
				for(int j=0;j<bs.getFields().size();j++) //columns
				{
					if(cells[i][j].contains(g2, w_X, w_Y))
					{
						hitCell = true;
						cell = new Cell(i,j);
					}
				}
			}
			if(hitCell) //a cell has been clicked
			{
				bs.cellChanged(cell);
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			int d_X = e.getX();
			int d_Y = e.getY();
			transform = new AffineTransform();
			
			transform.translate(getWidth()/2, getHeight()/2);
			transform.scale(bs.getZoomLevel(), bs.getZoomLevel());
			transform.translate(-w_originX, -w_originY);
			
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex) {
				return;
			}
			int w_X = (int)w_Pt.getX();
			int w_Y = (int)w_Pt.getY();
			
			boolean hitImage = false;
			
			Graphics2D g2 = (Graphics2D)getGraphics();
			for (DrawingShape shape : shapes) {
				if (shape.contains(g2, w_X, w_Y)) {
					hitImage = true;
					break;
				}
			}
			
			if (hitImage) {
				dragging = true;		
				w_dragStartX = w_X;
				w_dragStartY = w_Y;		
				w_dragStartOriginX = w_originX;
				w_dragStartOriginY = w_originY;
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {		
			if (dragging) {
				int d_X = e.getX();
				int d_Y = e.getY();
				
				Point2D d_Pt = new Point2D.Double(d_X, d_Y);
				Point2D w_Pt = new Point2D.Double();
				try
				{
					transform.inverseTransform(d_Pt, w_Pt);
				}
				catch (NoninvertibleTransformException ex) {
					return;
				}
				int w_X = (int)w_Pt.getX();
				int w_Y = (int)w_Pt.getY();
				
				int w_deltaX = w_X - w_dragStartX; // distance traveled x
				int w_deltaY = w_Y - w_dragStartY; // distance traveled y
				
				w_originX = w_dragStartOriginX - w_deltaX;
				w_originY = w_dragStartOriginY - w_deltaY;
				
				if(w_originX < 0)
				{
					w_originX = 10;
				}
				else if(w_originX > image.getWidth(null))
				{
					w_originX = image.getWidth(null) - 10;
				}
				if(w_originY < 0)
				{
					w_originY = 10;
				}
				else if(w_originY > image.getHeight(null))
				{
					w_originY = image.getHeight(null) - 10;
				}

				bs.setImageX(w_originX);
				bs.setImageY(w_originY);
				
				ImagePanel.this.repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			initDrag();
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			double scroll = (double)e.getWheelRotation()/5;
			if((bs.getZoomLevel()<0.25 && scroll > 0 ) || (bs.getZoomLevel()>2.5 && scroll < 0))
			{
				scroll = 0;
			}
			bs.setZoomLevel(bs.getZoomLevel()-scroll);
			zoomImage();
		}	
	};

	
	/////////////////
	// Drawing Shape
	/////////////////
	
	interface DrawingShape {
		boolean contains(Graphics2D g2, double x, double y);
		void draw(Graphics2D g2);
		Rectangle2D getBounds(Graphics2D g2);
	}


	class DrawingRect implements DrawingShape { 

		private Rectangle2D rect;
		private Color color;
		
		public DrawingRect(Rectangle2D rect, Color color) {
			this.rect = rect;
			this.color = color;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.setColor(color);
			g2.fill(rect);
		}
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}
		
		public void changeColor(Color color)
		{
			this.color=color;
		}
	}


	class DrawingImage implements DrawingShape {

		private Image image;
		private Rectangle2D rect;
		
		public DrawingImage(Image image, Rectangle2D rect) {
			this.image = image;
			this.rect = rect;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {
			g2.drawImage(image, (int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getMaxX(), (int)rect.getMaxY(),
							0, 0, image.getWidth(null), image.getHeight(null), null);
		}	
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}
	}
	
	private BatchStateListener bsListener = new BatchStateListener()
	{

		@Override
		public void valueChanged(Cell cell, String newValue) 
		{
			
		}

		@Override
		public void currentCellChanged(Cell cell) 
		{
			drawHighlights();
		}
		
	};
}
