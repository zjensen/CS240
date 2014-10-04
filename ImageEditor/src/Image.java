import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class Image 
{
	int width, height, maxColorValue;
	String magicNumber;
	Pixel[][] pixelArray;
	public Image(String mn, int w, int h, int mcv, Pixel[][] p)
	{
		width = w;
		height = h;
		maxColorValue = mcv;
		pixelArray = p;
		magicNumber = mn;
	}
	public void invertImage()
	{
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j <width; j++)
			{
				Pixel p = pixelArray[j][i];
				p.RedColorValue = maxColorValue - p.RedColorValue;
				p.GreenColorValue = maxColorValue - p.GreenColorValue;
				p.BlueColorValue = maxColorValue - p.BlueColorValue;
			}
		}
	}
	public void grayscaleImage()
	{
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j <width; j++)
			{
				Pixel p = pixelArray[j][i];
				int a = ((p.RedColorValue + p.GreenColorValue + p.BlueColorValue)/3);
				p.RedColorValue = a;
				p.GreenColorValue = a;
				p.BlueColorValue = a;
			}
		}
	}
	public void embossImage()
	{
		//int h;
		for(int h1 = (height-1);h1>=0;h1--)
		{
			//int w;
			for(int w1 = (width-1);w1>=0;w1--)
			{
				Pixel p = pixelArray[w1][h1];
				int maxDiff = 0;
				int v = 0;
				if(w1>0 && h1>0)
				{
					Pixel p2 = pixelArray[w1-1][h1-1];
					int redDiff = p.RedColorValue - p2.RedColorValue;
					int greenDiff = p.GreenColorValue - p2.GreenColorValue;
					int blueDiff = p.BlueColorValue - p2.BlueColorValue;
					int rd = Math.abs(redDiff);
					int gd = Math.abs(greenDiff);
					int bd = Math.abs(blueDiff);
					if(rd >= gd && rd >= bd)
					{
						maxDiff = redDiff;
					}
					else if(gd >= bd)
					{
						maxDiff = greenDiff;
					}
					else
					{
						maxDiff = blueDiff;
					}
				}
				v = 128 + maxDiff;
				if(v<0)
				{
					v=0;
				}
				if(v>255)
				{
					
					v=255;
				}
				p.RedColorValue = v;
				p.GreenColorValue = v;
				p.BlueColorValue = v;
				//w--;
			}
		}
	}
	public void motionblurImage(int b)
	{
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j <width; j++)
			{
				Pixel p = pixelArray[j][i];
				int avgR = 0;
				int avgG = 0;;
				int avgB = 0;
				int count = 0;
				for(int k=0; k<b; k++)
				{
					if(j+k < width) //so you dont hit edge on right
					{
						Pixel p2 = pixelArray[(j+k)][i];
						avgR += (p2.RedColorValue);
						avgG += (p2.GreenColorValue);
						avgB += (p2.BlueColorValue); 
						count++;
					}
				}
				avgR = (avgR/count);
				avgG = (avgG/count);
				avgB = (avgB/count);
				p.RedColorValue = avgR;
				p.GreenColorValue = avgG;
				p.BlueColorValue = avgB;
			}
		}
	}
	public void saveImage(File outputFile) 
	{
		StringBuilder s = new StringBuilder();

		s.append(magicNumber + "\n");
		s.append(width + "\n");
		s.append(height + "\n");
		s.append(maxColorValue + "\n");
		for(int i = 0; i < height; i++)
		{
			for(int j = 0; j <width; j++)
			{
				Pixel p = pixelArray[j][i];
				s.append(p.RedColorValue + "\n");
				s.append(p.GreenColorValue + "\n");
				s.append(p.BlueColorValue + "\n");
			}
		}
		PrintWriter out;
		try {
			out = new PrintWriter(outputFile);
			out.println(s);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
