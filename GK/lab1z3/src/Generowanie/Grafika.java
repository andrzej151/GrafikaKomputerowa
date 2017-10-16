package Generowanie;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Grafika {

	private BufferedImage maska;
	private BufferedImage wczytany, obraz1, obraz2, dest;

	private String name;

	// Image resolution
	private int x_res, y_res;

	public Grafika(int xres, int yres, String name) {
		x_res = xres;
		y_res = yres;
		this.name = name;
	}

	public void init() {
		maska = new BufferedImage(x_res, y_res, BufferedImage.TYPE_INT_RGB);
	}

	public void wczytaj(String name) {

		File imageFile = new File(name);
		try {
			wczytany = ImageIO.read(imageFile);
			x_res = wczytany.getWidth();
			y_res = wczytany.getHeight();
			maska = new BufferedImage(x_res, y_res, BufferedImage.TYPE_INT_RGB);
		} catch (IOException e) {
			System.err.println("Blad odczytu obrazka");
			e.printStackTrace();
		}
	}

	public void wczytaj(String obraz1, String obraz2) {

		File fo1 = new File(obraz1);
		File fo2 = new File(obraz2);

		try {
			this.obraz1 = ImageIO.read(fo1);
			this.obraz2 = ImageIO.read(fo2);
			x_res = (this.obraz1.getWidth() < this.obraz2.getWidth()) ? this.obraz1.getWidth() : this.obraz2.getWidth();
			y_res = (this.obraz1.getHeight() < this.obraz2.getHeight()) ? this.obraz1.getHeight()
					: this.obraz2.getHeight();
			maska = new BufferedImage(x_res, y_res, BufferedImage.TYPE_INT_RGB);
			dest = new BufferedImage(x_res, y_res, BufferedImage.TYPE_INT_RGB);
		} catch (IOException e) {
			System.err.println("Blad odczytu obrazka");
			e.printStackTrace();
		}
	}

	private void copy() {
		int i, j;
		for (i = 0; i < y_res; i++)
			for (j = 0; j < x_res; j++) {
				maska.setRGB(j, i, wczytany.getRGB(j, i));
			}

	}

	public void krata(int size, int x, int y, int x_przes, int y_przes, int r_l, int g_l, int b_l, int r_t, int g_t,
			int b_t) {

		int i, j;
		int k_l = int2RGB(r_l, g_l, b_l);
		int k_t = int2RGB(r_t, g_t, b_t);

		// Initialize an empty image, use pixel format
		// with RGB packed in the integer data type
		maska = new BufferedImage(x_res, y_res, BufferedImage.TYPE_INT_RGB);

		// Process the image, pixel by pixel
		for (i = 0; i < y_res; i++)
			for (j = 0; j < x_res; j++) {
				maska.setRGB(j, i, k_t);
				if ((j + x_przes) % x <= size) {
					maska.setRGB(j, i, k_l);
				}
				if ((i + y_przes) % y <= size) {
					maska.setRGB(j, i, k_l);
				}

			}

	}
	
	private double obliczA(int mask) {
		//System.out.println(mask/255.0);
		return mask/255.0;
	}

	private void polacz() {
		int i, j;
		double r, g, b;
		Color m, o1, o2, d;

		for (i = 0; i < y_res; i++)
			for (j = 0; j < x_res; j++) {
				m = new Color(maska.getRGB(j, i));
				o1 = new Color(obraz1.getRGB(j, i));
				o2 = new Color(obraz2.getRGB(j, i));
				r = (obliczA(m.getRed()) ) * o1.getRed() + (1-obliczA(m.getRed()) ) * o2.getRed();
				g = (obliczA(m.getGreen()) ) * o1.getGreen() + (1-obliczA(m.getGreen()) ) * o2.getGreen();
				b = (obliczA(m.getBlue() )) * o1.getBlue() + (1-obliczA(m.getBlue()) ) * o2.getBlue();
				d = new Color((int) r, (int) g, (int)b);
				dest.setRGB(j, i, d.getRGB());

			}

	}

	public void krata_na_obraz(int size, int x, int y, int x_przes, int y_przes, int r_l, int g_l, int b_l) {

		int i, j;
		int k_l = int2RGB(r_l, g_l, b_l);

		// Process the image, pixel by pixel
		for (i = 0; i < y_res; i++)
			for (j = 0; j < x_res; j++) {
				maska.setRGB(j, i, wczytany.getRGB(j, i));
				if ((j + x_przes) % x <= size) {
					maska.setRGB(j, i, k_l);
				}
				if ((i + y_przes) % y <= size) {
					maska.setRGB(j, i, k_l);
				}

			}

	}

	private void ringsMax(int w, int maxr, int minr) {
		int i, j, x_c, y_c, black, white;
		System.out.println("s " + w);
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

				if (minr <= d && maxr >= d) {

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

	private void rings(int w) {
		int i, j, x_c, y_c, black, white;

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
					maska.setRGB(j, i, black);
				else
					// Odd ring - set white color
					maska.setRGB(j, i, white);
			}
	}

	private void promien(int ilosc) {
		int i, j, x_c, y_c, black, white;

		// Create packed RGB representation of black and white colors
		black = int2RGB(0, 0, 0);
		white = int2RGB(255, 255, 255);

		// Find coordinates of the image center
		x_c = x_res / 2;
		y_c = y_res / 2;
		int x, y;
		int a = 0;

		// Process the image, pixel by pixel
		for (i = 0; i < y_res; i++)
			for (j = 0; j < x_res; j++) {
				double d;

				// Calculate distance to the image center
				d = Math.sqrt((i - y_c) * (i - y_c) + (j - x_c) * (j - x_c));

				// Find the ring index
				a = (int) (Math.asin(Math.abs(j - x_c) / d) * 180 / Math.PI - 1);

				// Make decision on the pixel color
				// based on the ring index
				if ((a / 15) % 2 == 0) {
					// Even ring - set black color
					maska.setRGB(j, i, black);
				} else {
					// Odd ring - set white color
					maska.setRGB(j, i, white);
				}
			}
	}

	private void pierscienierozmyte(int szybkosc, int wielkosc) {
		int x_c, y_c, i, j, kolor;
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
				r = (int) d / wielkosc;

				if ((r * szybkosc / 255) % 2 == 0) {
					kolor = 255 - (r * szybkosc) % 255;
				} else {
					kolor = (r * szybkosc) % 255;

				}

				maska.setRGB(j, i, szarosc(kolor));

				// Make decision on the pixel color
				// // based on the ring index
				// if (rozjasnij)
				// // rozjasnij
				// image.setRGB(j, i, szarosc(poziom+=szybkosc));
				// else
				// // sciemnij
				// image.setRGB(j, i, szarosc(poziom-=szybkosc));
			}
	}

	private void ringMax() {

		int w = 15;
		int d = (int) Math.sqrt(x_res * x_res + y_res * y_res) / 2;
		int b = d / w;

		for (int i = 0; i < w; i++) {
			ringsMax((i + 1) * 2, (i + 1) * b, i * b);
			b = d / w;
		}

	}

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
	
	private void kolo(int r, int x_s, int y_s, int width, int height) {
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
				

				// Calculate distance to the image center
				d = Math.sqrt((i - y_c) * (i - y_c) + (j - x_c) * (j - x_c));

			

				// Make decision on the pixel color
				// based on the ring index
				if (d < r)
					// Even ring - set black color
					maska.setRGB(j, i, black);
				else
					// Odd ring - set white color
					maska.setRGB(j, i, white);
			}
	}

	private void ringsRepate(int w, int width, int height) {
		int i, j;

		for (i = 0; i < y_res / height; i++) {
			for (j = 0; j < x_res / width; j++) {
				//System.out.println(i + " " + j + " " + j * width + " " + i * height);
				rings(w, j * width, i * height, width, height);
			}
		}

	}
	
	private void kolaRepate(int r, int width, int height) {
		int i, j;

		for (i = 0; i < y_res / height; i++) {
			for (j = 0; j < x_res / width; j++) {
				//System.out.println(i + " " + j + " " + j * width + " " + i * height);
				kolo(r, j * width, i * height, width, height);
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
						maska.setRGB(j, i, k_l);
					} else {
						maska.setRGB(j, i, k_t);
					}

				} else {
					if ((y / size) % 2 == 1) {
						maska.setRGB(j, i, k_t);
					} else {

						maska.setRGB(j, i, k_l);
					}
				}

			}

	}

	public void save(String name) {
		// Save the created image in a graphics file
		try {
			ImageIO.write(maska, "bmp", new File(name));
			System.out.println(" image created successfully");
		} catch (IOException e) {
			System.out.println("The image cannot be stored");
		}
	}

	public void saveDest(String name) {
		// Save the created image in a graphics file
		try {
			ImageIO.write(dest, "bmp", new File(name));
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

	private int szarosc(int poziom) {
		if (poziom > 255)
			poziom = 255;
		if (poziom < 0)
			poziom = 0;

		return int2RGB(poziom, poziom, poziom);
	}

	private int rotateX(int x, int y, int fi) {
		return (int) (x * Math.cos(fi * Math.PI / 180) - y * Math.sin(fi * Math.PI / 180));
	}

	private int rotateY(int x, int y, int fi) {
		return (int) (x * Math.sin(fi * Math.PI / 180) + y * Math.cos(fi * Math.PI / 180));
	}

	private int fi(int x, int y) {
		return (int) (((Math.acos(y / x) - 1) * (2 / Math.PI) / (180 / Math.PI)));
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

	public void procedura_pierscienie_rozmyte(int szybkosc, int wielkosc) {
		init();
		pierscienierozmyte(szybkosc, wielkosc);
		save(name);

	}

	public void procedura_rings_max() {
		init();
		ringMax();
		save(name);

	}

	public void procedura_przepisz(String sourse, String destination) {
		wczytaj(sourse);
		copy();
		save(destination);

	}

	public void procedura_ringsRepate(int w, int width, int height) {
		init();
		ringsRepate(w, width, height);
		save(name);

	}
	public void procedura_kolo_Repate(int r, int width, int height) {
		init();
		kolaRepate(r, width, height);
		save(name);

	}

	public void zmien_nazwe_docelowego(String name) {
		this.name = name;

	}

	public void procedura_promien(int ilosc) {
		init();
		promien(ilosc);
		save(name);

	}

	public void procedura_krata(int size, int x, int y, int x_przes, int y_przes, int r_l, int g_l, int b_l, int r_t,
			int g_t, int b_t) {
		init();
		krata(size, x, y, x_przes, y_przes, r_l, g_l, b_l, r_t, g_t, b_t);
		save(name);

	}

	public void procedura_dorysuj_krate(String sourse, String destination, int size, int x, int y, int x_przes,
			int y_przes, int r_l, int g_l, int b_l) {
		wczytaj(sourse);
		krata_na_obraz(size, x, y, x_przes, y_przes, r_l, g_l, b_l);
		save(destination);

	}

	public void procedura_szachownica_polacz(int size, int ank, String o1, String o2, String destanacion) {
		wczytaj(o1, o2);
		szachownica(size, ank, 255, 255, 255, 0, 0, 0);
		polacz();
		saveDest(destanacion);
	}

	public void procedura_promien_polacz(int ilosc, String o1, String o2, String destanacion) {
		wczytaj(o1, o2);
		promien(ilosc);
		polacz();
		saveDest(destanacion);

	}

	public void procedura_pierscienie_rozmyte_polacz(int szybkosc, int wielkosc, String o1, String o2,
			String destanacion) {
		wczytaj(o1, o2);
		pierscienierozmyte(szybkosc, wielkosc);
		polacz();
		saveDest(destanacion);

	}
	
	public void procedura_kolo_repate_polacz(int r, int width, int height, String o1, String o2,
			String destanacion) {
		wczytaj(o1, o2);
		kolaRepate(r, width, height);
		polacz();
		saveDest(destanacion);

	}

}
