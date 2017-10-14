package lab1;
/*
* Computer graphics courses at Wroclaw University of Technology
* (C) Wroclaw University of Technology, 2010
*
* Description:
* This demo shows basic raster operations on raster image
* represented by BufferedImage object. Image is created
* on pixel-by-pixel basis and then stored in a file.
*
*/

import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

public class Demo0 {
	public static void main(String[] args) {
		System.out.println("Ring pattern synthesis");

		BufferedImage image;

		// Image resolution
		int x_res, y_res;

		// Loop variables - indices of the current row and column
		int i, j;

		// Get required image resolution from command line arguments
		x_res = Integer.parseInt(args[0].trim());
		y_res = Integer.parseInt(args[1].trim());

		// szerokosc lini kraty
		int size = Integer.parseInt(args[3].trim());

		// kolor pola 1
		int r_l, g_l, b_l;
		r_l = Integer.parseInt(args[4].trim());
		g_l = Integer.parseInt(args[5].trim());
		b_l = Integer.parseInt(args[6].trim());

		// kolor tla 2
		int r_t, g_t, b_t;
		r_t = Integer.parseInt(args[7].trim());
		g_t = Integer.parseInt(args[8].trim());
		b_t = Integer.parseInt(args[9].trim());
		

		int k_l = int2RGB(r_l, g_l, b_l);
		int k_t = int2RGB(r_t, g_t, b_t);

		int x,y;

		// Initialize an empty image, use pixel format
		// with RGB packed in the integer data type
		image = new BufferedImage(x_res, y_res, BufferedImage.TYPE_INT_RGB);

		// Process the image, pixel by pixel
		for (i = 0; i < y_res; i++)
			for (j = 0; j < x_res; j++) {
				x=rotateX(j+size*100, i, 0);
				y=rotateY(j+size*100, i, 0);

				if (( x/ size) % 2 == 0) {
					if ((y / size) % 2 == 1) {
						image.setRGB(j, i, k_l);
					} else {
						image.setRGB(j, i, k_t);
					}
					
				} else {
					if ((y / size) % 2 == 1) {
						image.setRGB(j, i, k_t);
					} else {
						
						image.setRGB(j, i, k_l);
					}
				}

			}

		// Save the created image in a graphics file
		try {
			ImageIO.write(image, "bmp", new File(args[2]));
			System.out.println("Ring image created successfully");
		} catch (IOException e) {
			System.out.println("The image cannot be stored");
		}
	}

	// This method assembles RGB color intensities into single
	// packed integer. Arguments must be in <0..255> range
	static int int2RGB(int red, int green, int blue) {
		// Make sure that color intensities are in 0..255 range
		red = red & 0x000000FF;
		green = green & 0x000000FF;
		blue = blue & 0x000000FF;

		// Assemble packed RGB using bit shift operations
		return (red << 16) + (green << 8) + blue;
	}
	
	static int rotateX(int x, int y , int fi)
	{
		return (int) (x*Math.cos(fi*Math.PI/180) - y*Math.sin(fi*Math.PI/180));
	}
	static int rotateY(int x, int y , int fi)
	{
		return (int) (x*Math.sin(fi*Math.PI/180) + y*Math.cos(fi*Math.PI/180));
	}
	
}