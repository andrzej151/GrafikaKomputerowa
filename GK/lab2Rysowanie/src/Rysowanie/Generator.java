package Rysowanie;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Generator {

	protected BufferedImage sourse1, sourse2, mas, dest;
	protected IMaska maska;
	private int x_res, y_res;

	public void setMaska(IMaska m) {
		maska = m;
	}

	public void setsourse1(String image_name) {
		try {
			sourse1 = ImageIO.read(new File(image_name));
		} catch (IOException e) {

		}
	}

	public void setsourse(String image_name) {
		try {
			sourse2 = ImageIO.read(new File(image_name));
		} catch (IOException e) {

		}
	}

	public void setsourse1(BufferedImage image) {
		sourse1 = image;
	}

	public void setsourse2(BufferedImage image) {
		sourse2 = image;
	}

	public BufferedImage getDest() {
		return dest;
	}

	public BufferedImage getmas() {
		return mas;
	}

	public void generuj() {
		if (sourse1 == null) {
			x_res = sourse2.getWidth();
			y_res = sourse2.getHeight();
			sourse1 = new BufferedImage(sourse2.getWidth(), sourse2.getHeight(), BufferedImage.TYPE_INT_RGB);
			int kolor = szarosc(255);
			for (int i = 0; i < y_res; i++)
				for (int j = 0; j < x_res; j++) {
					sourse1.setRGB(j, i, kolor);

				}
		}
		if (sourse2 == null) {
			x_res = sourse1.getWidth();
			y_res = sourse1.getHeight();
			sourse2 = new BufferedImage(sourse1.getWidth(), sourse1.getHeight(), BufferedImage.TYPE_INT_RGB);
			int kolor = szarosc(0);
			for (int i = 0; i < y_res; i++)
				for (int j = 0; j < x_res; j++) {
					sourse2.setRGB(j, i, kolor);

				}
		}
		
		x_res = sourse1.getWidth() > sourse2.getWidth() ? sourse2.getWidth() : sourse1.getWidth();
		y_res = sourse1.getHeight() > sourse2.getHeight() ? sourse2.getHeight() : sourse1.getHeight();
		dest = new BufferedImage(x_res, y_res, BufferedImage.TYPE_INT_RGB);
		mas = maska.generuj(x_res, y_res);

	}

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

	private double obliczA(int mask) {
		// System.out.println(mask/255.0);
		return mask / 255.0;
	}

	private int obliczAl(int mask) {
		// System.out.println(mask/255.0);
		return mask / 255;
	}

	public void polaczSZ() {
		int i, j;
		double r, g, b;
		Color m, o1, o2, d;

		for (i = 0; i < y_res; i++) {
			for (j = 0; j < x_res; j++) {
				m = new Color(mas.getRGB(j, i));
				o1 = new Color(sourse1.getRGB(j, i));
				o2 = new Color(sourse2.getRGB(j, i));
				r = (obliczA(m.getRed())) * o1.getRed() + (1 - obliczA(m.getRed())) * o2.getRed();
				g = (obliczA(m.getGreen())) * o1.getGreen() + (1 - obliczA(m.getGreen())) * o2.getGreen();
				b = (obliczA(m.getBlue())) * o1.getBlue() + (1 - obliczA(m.getBlue())) * o2.getBlue();
				d = new Color((int) r, (int) g, (int) b);
				dest.setRGB(j, i, d.getRGB());
			}
			}

	}

	public void polaczBN() {
		int i, j;
		double r, g, b;
		Color m, o1, o2, d;

		for (i = 0; i < y_res; i++)
			for (j = 0; j < x_res; j++) {
				m = new Color(mas.getRGB(j, i));
				o1 = new Color(sourse1.getRGB(j, i));
				o2 = new Color(sourse2.getRGB(j, i));
				r = (obliczAl(m.getRed())) * o1.getRed() + (1 - obliczAl(m.getRed())) * o2.getRed();
				g = (obliczAl(m.getGreen())) * o1.getGreen() + (1 - obliczAl(m.getGreen())) * o2.getGreen();
				b = (obliczAl(m.getBlue())) * o1.getBlue() + (1 - obliczAl(m.getBlue())) * o2.getBlue();
				d = new Color((int) r, (int) g, (int) b);
				dest.setRGB(j, i, d.getRGB());

			}

	}

}
