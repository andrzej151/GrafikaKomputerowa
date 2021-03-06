
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
		// Ring center coordinates
		int x_c, y_c;

		// Loop variables - indices of the current row and column
		int i, j;

		// Get required image resolution from command line arguments
		x_res = Integer.parseInt(args[0].trim());
		y_res = Integer.parseInt(args[1].trim());

		// szerokosc lini kraty
		int size = Integer.parseInt(args[3].trim());

		// odleglosc
		int x = Integer.parseInt(args[4].trim());
		int y = Integer.parseInt(args[5].trim());

		// kolor lini
		int r_l, g_l, b_l;
		r_l = Integer.parseInt(args[6].trim());
		g_l = Integer.parseInt(args[7].trim());
		b_l = Integer.parseInt(args[8].trim());

		// kolor tla
		int r_t, g_t, b_t;
		r_t = Integer.parseInt(args[9].trim());
		g_t = Integer.parseInt(args[10].trim());
		b_t = Integer.parseInt(args[11].trim());

		int k_l = int2RGB(r_l, g_l, b_l);
		int k_t = int2RGB(r_t, g_t, b_t);

		int x_przes = 20, y_przes = 40;

		// Initialize an empty image, use pixel format
		// with RGB packed in the integer data type
		image = new BufferedImage(x_res, y_res, BufferedImage.TYPE_INT_RGB);



		// Process the image, pixel by pixel
		for (i = 0; i < y_res; i++)
			for (j = 0; j < x_res; j++) {
				image.setRGB(j, i, k_t);
				if ((j+x_przes) % x <= size) {
					image.setRGB(j, i, k_l);
				}
				if ((i+y_przes) % y <= size) {
					image.setRGB(j, i, k_l);
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
}