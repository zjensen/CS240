import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ImageEditor 
{
	void invert(Image i, File f)
	{
		i.invertImage();
		i.saveImage(f);
		//System.out.println(i.pixelArray[0][0].RedColorValue);
	}
	void grayscale(Image i, File f)
	{
		i.grayscaleImage();
		i.saveImage(f);
	}
	void emboss(Image i, File f)
	{
		i.embossImage();
		i.saveImage(f);
	}
	void motionblur(Image i, File f, int blurStrength)
	{
		i.motionblurImage(blurStrength);
		i.saveImage(f);
	}
	
	Image tokenizer(File inputFile)
	{
		Scanner scan = null;
		try {
			scan = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scan.useDelimiter("(\\s+)(#[^\\n]*\\n)?(\\s*)|(#[^\\n]*\\n)(\\s*)");
		String mn = scan.next();
		int w = scan.nextInt();
		int h = scan.nextInt();
		int mcv = scan.nextInt();
		Pixel[][] pa;
		pa = new Pixel[w][h];
		for(int i = 0; i < h; i++)
		{
			for(int j = 0; j <w; j++)
			{
				int a,b,c;
				a = scan.nextInt();
				b = scan.nextInt();
				c = scan.nextInt();
				//System.out.println(a);
				//System.out.println(b);
				//System.out.println(c);
				Pixel p = new Pixel(a,b,c);	
				pa[j][i] = p;
			}
		}
		scan.close();
		Image inputImage = new Image(mn, w, h, mcv, pa);
		return inputImage;
	}
	public static void main(String[] args)
	{
		ImageEditor IE = new ImageEditor();
		String input = args[0]; //first argument is file to read in
		String output = args[1]; //second argument is file to be created
		String process = args[2]; //third argument is how image will be processed
		int blurStrength = -1;
		File inputfile = new File(input); //turns string from argument into file object
		File outputfile = new File(output);
		Image inputImage = IE.tokenizer(inputfile);
		//System.out.println(inputImage.magicNumber);
		
		if(args.length == 4) //a fourth argument would only be if motionblur was the process
		{
			String blurStrengthString = args[3];
			blurStrength = Integer.parseInt(blurStrengthString);
		}
		
		if(process.equals("invert"))
		{
			IE.invert(inputImage, outputfile);
		}
		else if(process.equals("grayscale"))
		{
			IE.grayscale(inputImage, outputfile);
		}
		else if(process.equals("emboss"))
		{
			IE.emboss(inputImage, outputfile);
		}
		else if(process.equals("motionblur"))
		{
			if(blurStrength >= 0)
			{
				IE.motionblur(inputImage, outputfile, blurStrength);
			}
			else
			{
				System.out.println("Incorrect Arguments");
			}
		}
		else
		{
			System.out.println("Incorrect Arguments");
		}
	}

}
