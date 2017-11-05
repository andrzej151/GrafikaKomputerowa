package Rysowanie;

import java.awt.image.BufferedImage;

public class Maskapierscienie implements IMaska {
	
	private BufferedImage maska;

	private int int2RGB(int red, int green, int blue) {
		// Make sure that color intensities are in 0..255 range
		red = red & 0x000000FF;
		green = green & 0x000000FF;
		blue = blue & 0x000000FF;

		// Assemble packed RGB using bit shift operations
		return (red << 16) + (green << 8) + blue;
	}

	private int szarosc(int poziom) {
		if (poziom > 255)
			poziom = 255;
		if (poziom < 0)
			poziom = 0;

		return int2RGB(poziom, poziom, poziom);
	}

	@Override
	public BufferedImage generuj(int x_res, int y_res) {
		maska = new BufferedImage(x_res, y_res, BufferedImage.TYPE_INT_RGB);
		rings(20, 0, 0, x_res,y_res);
		return maska;
	}

	// TODO Auto-generated method stub
	private void rings(int w, int x_s, int y_s, int width, int height) {
		int i, j, x_c, y_c, black, white;

		// Create packed RGB representation of black and white colors
		black = int2RGB(0, 0, 0);
		white = int2RGB(255, 255, 255);

		// Find coordinates of the image center
		x_c = x_s + width / 2;
		y_c = y_s + height / 2;

		// Process the image, pixel by pixel
		for (i = y_s; i < y_s + height; i++)
			for (j = x_s; j < x_s + width; j++) {
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
					maska.setRGB(j, i, black);
				else
					// Odd ring - set white color
					maska.setRGB(j, i, white);
			}
	}
}