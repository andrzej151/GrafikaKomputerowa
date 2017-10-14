package Generowanie;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Grafika {

	private BufferedImage image;
	private String name;

	// Image resolution
	private int x_res, y_res;

	public Grafika(int xres, int yres, String name) {
		x_res = xres;
		y_res = yres;
		this.name = name;
	}

	public void init() {
		image = new BufferedImage(x_res, y_res, BufferedImage.TYPE_INT_RGB);
	}
	private void rings(int w)
	{
		int i, j, x_c, y_c,  black,white;
		
		// Create packed RGB representation of black and white colors
				black = int2RGB(0, 0, 0);
				white = int2RGB(255, 255, 255);
				
		// Find coordinates of the image center
				x_c = x_res / 2;
				y_c = y_res / 2;

				// Process the image, pixel by pixel
				for (i = 0; i < y_res; i++)
					for (j = 0; j < x_res; j++) {
						double d;
						int r;

						// Calculate distance to the image center
						d = Math.sqrt((i - y_c) * (i - y_c) + (j - x_c) * (j - x_c));

						// Find the ring index
						r = (int) d / w;

						// Make decision on the pixel color
						// based on the ring index
						if (r % 2 == 0)
							// Even ring - set black color
							image.setRGB(j, i, black);
						else
							// Odd ring - set white color
							image.setRGB(j, i, white);
					}
	}
	
	private void rings(int w, int x_s, int y_s,int width, int height)
	{
		int i, j, x_c, y_c,  black,white;
		
		// Create packed RGB representation of black and white colors
				black = int2RGB(0, 0, 0);
				white = int2RGB(255, 255, 255);
				
		// Find coordinates of the image center
				x_c = x_s + width / 2;
				y_c = y_s + height / 2;

				// Process the image, pixel by pixel
				for (i = y_s; i < y_s+height; i++)
					for (j = x_s; j < x_s+width; j++) {
						double d;
						int r;

						// Calculate distance to the image center
						d = Math.sqrt((i - y_c) * (i - y_c) + (j - x_c) * (j - x_c));

						// Find the ring index
						r = (int) d / w;

						// Make decision on the pixel color
						// based on the ring index
						if (r % 2 == 0)
							// Even ring - set black color
							image.setRGB(j, i, black);
						else
							// Odd ring - set white color
							image.setRGB(j, i, white);
					}
	}
	
	private void ringsRepate(int w, int width, int height) {
		int i, j;
		
		for (i = 0; i < y_res/height; i++) {
			for (j = 0; j < x_res/width; j++) {
				System.out.println(i+" "+j+" "+j*width+" "+ i*height);
				rings(w, j*width, i*height, width, height);
			}
		}
		
	}
	

 	private void szachownica(int size, int ank, int r_p1, int g_p1, int b_p1, int r_p2, int g_p2, int b_p2) {

		// Loop variables - indices of the current row and column
		int i, j, x, y;
		int k_l = int2RGB(r_p1, g_p1, b_p1);
		int k_t = int2RGB(r_p2, g_p2, b_p2);

		// Process the image, pixel by pixel
		for (i = 0; i < y_res; i++)
			for (j = 0; j < x_res; j++) {
				x = rotateX(j + size * 100, i, ank);
				y = rotateY(j + size * 100, i, ank);

				if ((x / size) % 2 == 0) {
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

	}

	public void save(String name) {
		// Save the created image in a graphics file
		try {
			ImageIO.write(image, "bmp", new File(name));
			System.out.println(" image created successfully");
		} catch (IOException e) {
			System.out.println("The image cannot be stored");
		}
	}

	// This method assembles RGB color intensities into single
	// packed integer. Arguments must be in <0..255> range
	private int int2RGB(int red, int green, int blue) {
		// Make sure that color intensities are in 0..255 range
		red = red & 0x000000FF;
		green = green & 0x000000FF;
		blue = blue & 0x000000FF;

		// Assemble packed RGB using bit shift operations
		return (red << 16) + (green << 8) + blue;
	}

	private int rotateX(int x, int y, int fi) {
		return (int) (x * Math.cos(fi * Math.PI / 180) - y * Math.sin(fi * Math.PI / 180));
	}

	private int rotateY(int x, int y, int fi) {
		return (int) (x * Math.sin(fi * Math.PI / 180) + y * Math.cos(fi * Math.PI / 180));
	}

	public void procedura_szachownica(int size, int ank, int r_p1, int g_p1, int b_p1, int r_p2, int g_p2, int b_p2) {
		init();
		szachownica(size, ank, r_p1, g_p1, b_p1, r_p2, g_p2, b_p2);
		save(name);

	}
	
	public void procedura_rings(int w) {
		init();
		rings(w);
		save(name);

	}
	
	public void procedura_ringsRepate(int w, int width, int height) {
		init();
		ringsRepate(w, width, height);
		save(name);

	}

	public void zmien_nazwe_docelowego(String name) {
		this.name=name;
		
	}

}
