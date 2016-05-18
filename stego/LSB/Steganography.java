/*
 *import list
 */
import java.io.File;

import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.DataBufferByte;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import java.util.Random;
import java.util.*;

import java.awt.color.*;
import java.awt.image.*;
import javax.imageio.*;

/*
 *Class Steganography
 */
public class Steganography
{
	
	/*
	 *Steganography Empty Constructor
	 */
	public Steganography()
	{
	}
	
	public boolean encode(String path, String original, String ext1, String stegan, String message)
	{	
		String			file_name 	= image_path(path,original,ext1);
		BufferedImage 	image_orig	= getImage(file_name);
		
		//user space is not necessary for Encrypting
		BufferedImage image = user_space(image_orig);
		image = add_text(image,message);
		
		return(setImage(image,new File(image_path(path,stegan,"bmp")),"bmp"));
	}
	
	
	public String decode(String path, String name)
	{
		byte[] decode;
		try
		{
			//user space is necessary for decrypting
			BufferedImage image  = user_space(getImage(image_path(path,name,"bmp")));
			decode = decode_text(get_byte_data(image));
			return(new String(decode));
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, 
				"There is no hidden message in this image!","Error",
				JOptionPane.ERROR_MESSAGE);
			return "";
		}
	}

	public boolean attackRecript(String path, String original, String ext1)
	{
		String 			message     = "The North Remembers";
		String			file_name 	= image_path(path,original,ext1);
		BufferedImage 	image_orig	= getImage(file_name);
		
		//user space is not necessary for Encrypting
		BufferedImage image = user_space(image_orig);
		image = add_text(image,message);
		
		return(setImage(image,new File(file_name),"bmp"));
	}

	public boolean attackDestroy(String path, String original, String ext1)
	{
		String			file_name 	= image_path(path,original,ext1);
		BufferedImage 	image_orig	= getImage(file_name);
		
		//user space is not necessary for Encrypting
		BufferedImage image = user_space(image_orig);
		image = attack_destroy_text(image);
		
		return(setImage(image,new File(file_name), ext1)); //overwrites the original file
	}
	
	
	private String image_path(String path, String name, String ext)
	{
		return path + "/" + name + "." + ext;
	}
	

	private BufferedImage getImage(String f)
	{
		BufferedImage 	image	= null;
		File 		file 	= new File(f);
		
		try
		{
			image = ImageIO.read(file);
			int rgb = image.getRGB(0, 0);
			int r = (rgb >> 16) & 0xFF;
			int g = (rgb >> 8) & 0xFF;
			int b = (rgb & 0xFF);
			if(r != g && g != b && g != r){

				throw new IllegalStateException();
			}
		}
		catch(IllegalStateException e)
		{
			JOptionPane.showMessageDialog(null, 
				"Image is not grayscale!","Error",JOptionPane.ERROR_MESSAGE);
			throw e;
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null, 
				"Image could not be read!","Error",JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}
	
	
	private boolean setImage(BufferedImage image, File file, String ext)
	{
		try
		{
			file.delete(); //delete resources used by the File
			ImageIO.write(image,ext,file);
			return true;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, 
				"File could not be saved!","Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	
	private BufferedImage add_text(BufferedImage image, String text)
	{
		//convert all items to byte arrays: image, message, message length
		byte img[]  = get_byte_data(image);
		byte msg[] = text.getBytes();
		byte len[]   = bit_conversion(msg.length);
		try
		{
			encode_text(img, len,  0); //0 first positiong
			encode_text(img, msg, 32); //4 bytes of space for length: 4bytes*8bit = 32 bits
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, 
"Target File cannot hold message!", "Error",JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}

	private BufferedImage attack_destroy_text(BufferedImage image)
	{
		//convert all items to byte arrays: image, message, message length
		byte img[]  = get_byte_data(image);
		try
		{
			destroy_text(img, 0); //0 first positiong
			destroy_text(img, 32); //4 bytes of space for length: 4bytes*8bit = 32 bits
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, 
			"Target File cannot hold message!", "Error",JOptionPane.ERROR_MESSAGE);
		}
		return image;
	}
	
	private BufferedImage user_space(BufferedImage image)
	{
		//create new_img with the attributes of image
		BufferedImage new_img  = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D	graphics = new_img.createGraphics();
		graphics.drawRenderedImage(image, null);
		graphics.dispose(); //release all allocated memory for this image
		return new_img;
	}
	

	private byte[] get_byte_data(BufferedImage image)
	{
		WritableRaster raster   = image.getRaster();
		DataBufferByte buffer = (DataBufferByte)raster.getDataBuffer();
		return buffer.getData();
	}
	

	private byte[] bit_conversion(int i)
	{
		//originally integers (ints) cast into bytes	
		//only using 4 bytes
		byte byte3 = (byte)((i & 0xFF000000) >>> 24); //0
		byte byte2 = (byte)((i & 0x00FF0000) >>> 16); //0
		byte byte1 = (byte)((i & 0x0000FF00) >>> 8 ); //0
		byte byte0 = (byte)((i & 0x000000FF)	   );

		return(new byte[]{byte3,byte2,byte1,byte0});
	}
	

	private byte[] encode_text(byte[] image, byte[] addition, int offset)
	{
		//check that the data + offset will fit in the image
		if(addition.length + offset > image.length)
		{
			throw new IllegalArgumentException("File not long enough!");
		}
		//loop through each addition byte
		for(int i=0; i<addition.length; ++i)
		{
			//loop through the 8 bits of each byte
			int add = addition[i];
			for(int bit=7; bit>=0; --bit, ++offset) //ensure the new offset value carries on through both loops
			{
				int b = (add >>> bit) & 1;
				image[offset] = (byte)((image[offset] & 0xFE) | b );
			}
		}
		return image;
	}
	
	
	private byte[] decode_text(byte[] image)
	{
		int length = 0;
		int offset  = 32;
		//loop through 32 bytes of data to determine text length
		for(int i=0; i<32; ++i) //i=24 will also work, as only the 4th byte contains real data
		{
			length = (length << 1) | (image[i] & 1);
		}
		
		byte[] result = new byte[length];
		
		//loop through each byte of text
		for(int b=0; b<result.length; ++b )
		{
			//loop through each bit within a byte of text
			for(int i=0; i<8; ++i, ++offset)
			{
				//assign bit: [(new byte value) << 1] OR [(text byte) AND 1]
				result[b] = (byte)((result[b] << 1) | (image[offset] & 1));
			}
		}
		return result;
	}

	private byte[] destroy_text(byte[] image, int offset)
	{		
		int length =0;
		Random randomGenerator = new Random();

		//loop through 32 bytes of data to determine length
		for(int i=0; i<32; ++i) //i=24 will also work, as only the 4th byte contains real data
		{
			length = (length << 1) | (image[i] & 1);
		}
		
		byte[] result = new byte[length];
		
		//loop through each addition byte
		for(int i=0; i<result.length; ++i)
		{
			//loop through the 8 bits of each byte
			int add = randomGenerator.nextInt(2); //randomly generates a 1 or 0
			for(int bit=7; bit>=0; --bit, ++offset) //ensure the new offset value carries on through both loops
			{
				int b = (add >>> bit) & 1;
				image[offset] = (byte)((image[offset] & 0xFE) | b );
			}
		}
		return image;
	}
}

