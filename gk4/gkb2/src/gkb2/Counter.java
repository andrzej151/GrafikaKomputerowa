package gkb2;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

class Counter extends RecursiveAction {
	
	private static final long serialVersionUID = 1L;
	
	BufferedImage im, afterIm;
	TransformationMatrix macierz;
	ArrayList<Line> linie;
	
	boolean wektorowa;
	int wierszOd, wierszDo;
	
	Counter(Image source, BufferedImage transfered, TransformationMatrix mac, int wierszOd, int wierszDo) {
		im = (BufferedImage) source;
		afterIm = transfered;
		macierz = mac;
		wektorowa = false;
		this.wierszDo = wierszDo;
		this.wierszOd = wierszOd;
	}
	
	Counter(ArrayList<Line> linie, TransformationMatrix mac) {
		this.linie = linie;
		macierz = mac;
		wektorowa = true;
	}
	
	@Override
	protected void compute() {
		if (((wierszDo - wierszOd) + 1) * afterIm.getWidth() < 0xf4240 || wektorowa) computeDirectly();
		else {
			int srodek = (wierszDo + wierszOd) / 2;
			ForkJoinTask.invokeAll(new Counter(im, afterIm, macierz, wierszOd, srodek), new Counter(im, afterIm, macierz, srodek, wierszDo));
			computeDirectly();
		}
	}
	
	public void computeDirectly() {
		if (wektorowa) {
			for(Line l: linie) {
				macierz.przeksztalcPoint(l.a);
				macierz.przeksztalcPoint(l.b);
			}
		}
		else {
			Point a = new Point(0.0D, 0.0D);
			
			for(int i = wierszOd; i < wierszDo; i++)
				for(int j = 0; j < afterIm.getWidth(); j++) {
					a.x = j;
					a.y = i;
					
					macierz.przeksztalcPoint(a);
					Color color;
					
					if (a.x >= im.getWidth() || a.x < 0.0D || a.y < 0.0D || a.y >= im.getHeight()) color = Color.BLACK;
					else if ((a.x > (int) a.x || a.y > (int) a.y) && im.getHeight() > 1 && im.getWidth() > 1) color = aproksymacjaDwuliniowa(im, a.x, a.y);
					else color = new Color(im.getRGB((int) a.x, (int) a.y));
					
					afterIm.setRGB(j, i, color.getRGB());
				}
		}
	}
	
	private Color aproksymacjaDwuliniowa(BufferedImage im, double x, double y) {
		double alpha = x - (int) x, beta = y - (int) y;
		
		Color A, B, C, D;
		
		if (x >= im.getWidth() - 1) {
			if (y >= im.getHeight() - 1) {
				A = new Color(im.getRGB((int) x, (int) y));
				B = new Color(im.getRGB((int) x - 1, (int) y));
				C = new Color(im.getRGB((int) x, (int) y - 1));
				D = new Color(im.getRGB((int) x - 1, (int) y - 1));
			}
			else {
				A = new Color(im.getRGB((int) x, (int) y));
				B = new Color(im.getRGB((int) x - 1, (int) y));
				C = new Color(im.getRGB((int) x, (int) y + 1));
				D = new Color(im.getRGB((int) x - 1, (int) y + 1));
			}
		}
		else if (y >= im.getHeight() - 1) {
			A = new Color(im.getRGB((int) x, (int) y));
			B = new Color(im.getRGB((int) x + 1, (int) y));
			C = new Color(im.getRGB((int) x, (int) y - 1));
			D = new Color(im.getRGB((int) x + 1, (int) y - 1));
		}
		else {
			A = new Color(im.getRGB((int) x, (int) y));
			B = new Color(im.getRGB((int) x + 1, (int) y));
			C = new Color(im.getRGB((int) x, (int) y + 1));
			D = new Color(im.getRGB((int) x + 1, (int) y + 1));
		}
		int blue = (int) ((1.0D - beta) * ((1.0D - alpha) * A.getBlue() + alpha * B.getBlue()) + beta * ((1.0D - alpha) * C.getBlue() + alpha * D.getBlue()));
		int red = (int) ((1.0D - beta) * ((1.0D - alpha) * A.getRed() + alpha * B.getRed()) + beta * ((1.0D - alpha) * C.getRed() + alpha * D.getRed()));
		int green = (int) ((1.0D - beta) * ((1.0D - alpha) * A.getGreen() + alpha * B.getGreen()) + beta * ((1.0D - alpha) * C.getGreen() + alpha * D.getGreen()));
		
		return new Color(red, green, blue);
	}
}