package stego;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

/** Least-significant-bit steganography: hides a text or a whole secret image inside the pixels of a cover
 *  image and extracts it back. Object-Oriented Programming / Information Security exercise, PUCPR (2018). */
public class Steganography {

  // embed secret information/TEXT into a "cover image"
    public static BufferedImage embedText(BufferedImage image, String text) {
        int bitMask = 0x00000001;    // define the mask bit used to get the digit
        int bit;                // define a integer number to represent the ASCII number of a character
        int x = 0;                // define the starting pixel x
        int y = 0;                // define the starting pixel y
        for(int i = 0; i < text.length(); i++) {            
            bit = (int) text.charAt(i);        // get the ASCII number of a character
            for(int j = 0; j < 8; j++) {
                int flag = bit & bitMask;    // get 1 digit from the character
                if(flag == 1) {    
                    if(x < image.getWidth()) {
                        image.setRGB(x, y, image.getRGB(x, y) | 0x00000001);     // store the bit which is 1 into a pixel's last digit
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        image.setRGB(x, y, image.getRGB(x, y) | 0x00000001);     // store the bit which is 1 into a pixel's last digit
                    }
                } 
                else {    
                    if(x < image.getWidth()) {
                        image.setRGB(x, y, image.getRGB(x, y) & 0xFFFFFFFE);    // store the bit which is 0 into a pixel's last digit
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        image.setRGB(x, y, image.getRGB(x, y) & 0xFFFFFFFE);    // store the bit which is 0 into a pixel's last digit
                    }
                }
                bit = bit >> 1;                // get the next digit from the character
            }            
        }
        
        // save the image which contains the secret information to another image file
        try {
            File outputfile = new File(COVER_WITH_TEXT);    
            ImageIO.write(image, "png", outputfile);    
        } catch (IOException e) {
            
        }        
        return image;
    }
    
    // extract secret information/Text from a "cover image"
    public static void extractText(BufferedImage image, int length) {
        System.out.print("Extracting: ");
        int bitMask = 0x00000001;    // define the mask bit used to get the digit
        int x = 0;                    // define the starting pixel x
        int y = 0;                    // define the starting pixel y
        int flag;
        char[] c = new char[length] ;    // define a character array to store the secret information
        for(int i = 0; i < length; i++) {    
            int bit = 0;
            
            // 8 digits form a character
            for(int j = 0; j < 8; j++) {                
                if(x < image.getWidth()) {
                    flag = image.getRGB(x, y) & bitMask;    // get the last digit of the pixel
                    x++;
                }
                else {
                    x = 0;
                    y++;
                    flag = image.getRGB(x, y) & bitMask;    // get the last digit of the pixel
                }
                
                // store the extracted digits into an integer as a ASCII number
                if(flag == 1) {                    
                    bit = bit >> 1;    
                    bit = bit | 0x80;
                } 
                else {                    
                    bit = bit >> 1;
                }                
            }
            c[i] = (char) bit;    // represent the ASCII number by characters
            
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(EXTRACTED_TEXT)));
            writer.write(c);
            writer.close();    
        } catch (IOException e) {
            
        }
    }
        
    // embed secret image into a "cover image"
    public static BufferedImage embedImage(BufferedImage imageC, BufferedImage imageS) {
        int bitMask = 0x00000001;    // define the mask bit used to get the digit
        int x = 0;                    // define the starting pixel x
        int y = 0;                    // define the starting pixel y
        int l = imageS.getWidth() * imageS.getHeight();    // calculate the total pixel in the secret image
        int[] array = new int[l];    // define an array to store the color numbers from the secret image
        
        // store the RGB from the secret to an array
        for(int i = 0; i < array.length; i++) {
            if(x < imageS.getWidth()) {
                array[i] = imageS.getRGB(x, y);
                x++;
            }
            else {
                x = 0;
                y++;
                array[i] = imageS.getRGB(x, y);
            }
        }
        x = 0;  // reset the x coordinate of the cover image
        y = 0;    // reset the y coordinate of the cover image
        for(int i = 0; i < l; i++) {            
            for(int j = 0; j < 32; j++) {
                int flag = array[i] & bitMask;    // get 1 digit from the character
                if(flag == 1) {    
                    if(x < imageC.getWidth()) {
                        imageC.setRGB(x, y, imageC.getRGB(x, y) | 0x00000001);     // store the bit which is 1 into a pixel's last digit
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        imageC.setRGB(x, y, imageC.getRGB(x, y) | 0x00000001);     // store the bit which is 1 into a pixel's last digit
                    }
                } 
                else {    
                    if(x < imageC.getWidth()) {
                        imageC.setRGB(x, y, imageC.getRGB(x, y) & 0xFFFFFFFE);    // store the bit which is 0 into a pixel's last digit
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        imageC.setRGB(x, y, imageC.getRGB(x, y) & 0xFFFFFFFE);    // store the bit which is 0 into a pixel's last digit
                    }
                }
                array[i] = array[i] >> 1;                // get the next digit from the character
            }            
        }
        
        // save the image which contains the secret information to another image file
        try {
            File outputfile = new File(COVER_WITH_IMAGE);    
            ImageIO.write(imageC, "png", outputfile);    
        } catch (IOException e) {
            
        }        
        return imageC;
    }    
    
    // extract secret image from a "cover image"
    public static void extractImage(BufferedImage image, int width, int height) {
        int bitMask = 0x00000001;    // define the mask bit used to get the digit
        int x = 0;                    // define the starting pixel x
        int y = 0;                    // define the starting pixel y
        int flag;
        BufferedImage imageStore = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int pixelNumber = width * height;
        int[] array = new int[pixelNumber] ;
        for(int i = 0; i < pixelNumber; i++) {    
            int bit = 0x00000000;
            
            // 32 digits form a pixel
            for(int j = 0; j < 32; j++) {                
                if(x < image.getWidth()) {
                    flag = image.getRGB(x, y) & bitMask;    // get the last digit of the pixel
                    x++;
                }
                else {
                    x = 0;
                    y++;
                    flag = image.getRGB(x, y) & bitMask;    // get the last digit of the pixel
                }
                
                // store the extracted digits into an integer
                if(flag == 1) {                    
                    bit = bit >> 1;    
                    bit = bit | 0x80000000;
                } 
                else {                    
                    bit = bit >> 1;
                    bit = bit & 0x7FFFFFFF;
                }                
            }
            array[i] = bit;    // store the integer color in to an array
            //System.out.println(Integer.toBinaryString(array[i]));
        }
        
        
        x = 0;  // reset the x coordinate of the extracted image
        y = 0;    // reset the y coordinate of the extracted image
        
        // draw the extracted image
        for(int i = 0; i < array.length; i++){
            if(x < width) {
                imageStore.setRGB(x, y, array[i]);
                x++;
            }
            else {
                x = 0;
                y++;
                imageStore.setRGB(x, y, array[i]);
            }
        }
        
        // store the extracted image
        try {
            File outputfile = new File(EXTRACTED_IMAGE);    
            ImageIO.write(imageStore, "png", outputfile);    
        } catch (IOException e) {
            
        }        
                
    }
        
    private static final String IMAGES = "images/";
    private static final String COVER = IMAGES + "cover.jpg";
    private static final String SECRET_IMAGE = IMAGES + "secret.jpg";
    private static final String SECRET_TEXT = "Lets play minecraft";
    private static final String COVER_WITH_TEXT = IMAGES + "cover-with-text.png";
    private static final String COVER_WITH_IMAGE = IMAGES + "cover-with-image.png";
    private static final String EXTRACTED_IMAGE = IMAGES + "extracted-image.png";
    private static final String EXTRACTED_TEXT = IMAGES + "extracted-text.txt";

    public static void main(String[] args) {
        try {
            BufferedImage coverForText = ImageIO.read(new File(COVER));
            BufferedImage coverForImage = ImageIO.read(new File(COVER));
            BufferedImage secretImage = ImageIO.read(new File(SECRET_IMAGE));

            embedText(coverForText, SECRET_TEXT);                                   // hide the text
            extractText(ImageIO.read(new File(COVER_WITH_TEXT)), SECRET_TEXT.length());   // read it back
            System.out.println("text extracted to " + EXTRACTED_TEXT);

            embedImage(coverForImage, secretImage);                                 // hide the image
            extractImage(ImageIO.read(new File(COVER_WITH_IMAGE)), secretImage.getWidth(), secretImage.getHeight());
            System.out.println("image extracted to " + EXTRACTED_IMAGE);

            if (!java.awt.GraphicsEnvironment.isHeadless()) {
                JFrame frame = new JFrame("Steganography");
                JPanel panel = new JPanel();
                panel.add(new JLabel(new ImageIcon(ImageIO.read(new File(COVER)))));
                panel.add(new JLabel(new ImageIcon(coverForText)));
                panel.add(new JLabel(new ImageIcon(coverForImage)));
                frame.add(panel);
                frame.pack();
                frame.setVisible(true);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
