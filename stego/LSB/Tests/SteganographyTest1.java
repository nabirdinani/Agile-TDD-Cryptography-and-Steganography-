import static org.junit.Assert.*;

import org.junit.Test;
import java.util.Arrays;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class SteganographyTest1 {

	@Test
	public void testBit_conversion() {
		//fail("Not yet implemented");
		
		Steganography s = new Steganography();
		
		String expectedOutput = "[0, 0, 0, 0]";
		
		byte[] b = s.bit_conversion(0);
		String actualOutput = Arrays.toString(b);
		
		assertEquals(expectedOutput, actualOutput);
	}

	@Test
	public void testEncode_text() {
        Steganography s = new Steganography();

        String uncoded_img = "input1.bmp";
        BufferedImage image	= null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(uncoded_img));
        }
        catch(Exception e)
        {
            System.out.println("File could not be read!");
        }

        String coded_img = "coded_input1.bmp";
        BufferedImage image2 = null;

        try {
            image2 = ImageIO.read(getClass().getResourceAsStream(coded_img));
        }
        catch(Exception e) {

            System.out.println("File could not be read!");
        }

        String text = "hello";
        byte img[]  = s.get_byte_data(image);
        byte msg[] = text.getBytes();

        byte e_img[] = s.get_byte_data(image2);
        String expectedOutput = Arrays.toString(e_img);

        byte len[]   = s.bit_conversion(msg.length);

        s.encode_text(img, len, 0);
        s.encode_text(img, msg, 32);

        byte data[] = s.get_byte_data(image);

        String actualOutput = Arrays.toString(data);

        assertEquals(actualOutput, expectedOutput);
	}
	

	@Test
	public void testDecode_text() {
		Steganography s = new Steganography();

        String coded_img = "coded_input1.bmp";
        BufferedImage image2 = null;

        try {
            image2 = ImageIO.read(getClass().getResourceAsStream(coded_img));
        }
        catch(Exception e) {

            System.out.println("File could not be read!");
        }
       
        byte img[]  = s.get_byte_data(image2);
        byte[] msg = s.decode_text(img);
        
        String expectedOutput = Arrays.toString(msg);
        
        String actualOutput = "[104, 101, 108, 108, 111]";
        
        assertEquals(actualOutput, expectedOutput);
	}
	
	@Test
	public void testDestroy_text() {
		Steganography s = new Steganography();

        String uncoded_img = "input1.bmp";
        BufferedImage image	= null;

        try {
            image = ImageIO.read(getClass().getResourceAsStream(uncoded_img));
        }
        catch(Exception e)
        {
            System.out.println("File could not be read!");
        }
        
        byte img1[]  = s.get_byte_data(image);
        String expectedOutput = Arrays.toString(img1);
        
        String coded_img = "coded_input1.bmp";
        BufferedImage image2 = null;

        try {
            image2 = ImageIO.read(getClass().getResourceAsStream(coded_img));
        }
        catch(Exception e) {

            System.out.println("File could not be read!");
        }
       
        byte img2[]  = s.get_byte_data(image2);
        byte[] msg = s.destroy_text(img2, 32);
        
        String actualOutput = Arrays.toString(msg);
	}
}
