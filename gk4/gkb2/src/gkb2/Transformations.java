package gkb2;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

public class Transformations {
	
	Image obroc(BufferedImage im, double angle, double x, double y) {
		TransformationMatrix macierz = TransformationMatrix.macierzPrzesuniecia(-x, -y);
		macierz.pomnoz(TransformationMatrix.macierzObrotu((angle * 3.1415926535897931D) / 180D));
		macierz.pomnoz(TransformationMatrix.macierzPrzesuniecia(x, y));
		macierz.drukujMacierz();
		Point Minmax[] = new Point[4];
		Minmax[0] = new Point(0.0D, 0.0D);
		Minmax[1] = new Point(im.getWidth() - 1, 0.0D);
		Minmax[2] = new Point(0.0D, im.getHeight() - 1);
		Minmax[3] = new Point(im.getWidth() - 1, im.getHeight() - 1);
		macierz.przeksztalcPoint(Minmax[0]);
		macierz.przeksztalcPoint(Minmax[1]);
		macierz.przeksztalcPoint(Minmax[2]);
		macierz.przeksztalcPoint(Minmax[3]);
		double maxx;
		double minx = maxx = Minmax[0].x;
		double maxy;
		double miny = maxy = Minmax[0].y;
		for(int i = 0; i < 4; i++) {
			if (Minmax[i].x > maxx) maxx = Minmax[i].x;
			if (Minmax[i].x < minx) minx = Minmax[i].x;
			if (Minmax[i].y > maxy) maxy = Minmax[i].y;
			if (Minmax[i].y < miny) miny = Minmax[i].y;
		}
		
		macierz.pomnoz(TransformationMatrix.macierzPrzesuniecia(-minx, -miny));
		Counter licz = new Counter(im, new BufferedImage((int) (maxx - minx), (int) (maxy - miny), 1), TransformationMatrix.macierzOdwrotna(macierz), 0, (int) (maxy - miny));
		ForkJoinPool pl = new ForkJoinPool();
		pl.invoke(licz);
		return licz.afterIm;
	}
	
	Image przeskaluj(BufferedImage im, double a, double d, int w, int h) {
		TransformationMatrix macierz = TransformationMatrix.macierzSkalowania(a, d);
		macierz.drukujMacierz();
		
		Counter licz = new Counter(im, new BufferedImage(w, h, 1), TransformationMatrix.macierzOdwrotna(macierz), 0, h);
		ForkJoinPool pl = new ForkJoinPool();
		pl.invoke(licz);
		return licz.afterIm;
	}
	
	Image przesun(BufferedImage im, double x, double y) {
		TransformationMatrix macierz = TransformationMatrix.macierzPrzesuniecia(x, y);
		macierz.drukujMacierz();
		System.out.println("e: " + x + ", f: " + y);
		Counter licz = new Counter(im, new BufferedImage(im.getWidth(), im.getHeight(), 1), TransformationMatrix.macierzOdwrotna(macierz), 0, im.getHeight());
		ForkJoinPool pl = new ForkJoinPool();
		pl.invoke(licz);
		return licz.afterIm;
	}
	
	Image dowolna(BufferedImage im, double mac[][]) {
		TransformationMatrix macierz = new TransformationMatrix(mac);
		macierz.drukujMacierz();
		Point Minmax[] = new Point[4];
		Minmax[0] = new Point(0.0D, 0.0D);
		Minmax[1] = new Point(im.getWidth() - 1, 0.0D);
		Minmax[2] = new Point(0.0D, im.getHeight() - 1);
		Minmax[3] = new Point(im.getWidth() - 1, im.getHeight() - 1);
		macierz.przeksztalcPoint(Minmax[0]);
		macierz.przeksztalcPoint(Minmax[1]);
		macierz.przeksztalcPoint(Minmax[2]);
		macierz.przeksztalcPoint(Minmax[3]);
		double maxx = Minmax[0].x;
		double maxy = Minmax[0].y;
		for(int i = 1; i < 4; i++) {
			if (Minmax[i].x > maxx) maxx = Minmax[i].x;
			if (Minmax[i].y > maxy) maxy = Minmax[i].y;
		}
		
		Counter licz;
		try {
			licz = new Counter(im, new BufferedImage((int) maxx, (int) maxy, 1), TransformationMatrix.macierzOdwrotna(macierz), 0, (int) maxy);
		}
		catch (IllegalArgumentException ex) {
			return null;
		}
		ForkJoinPool pl = new ForkJoinPool();
		pl.invoke(licz);
		return licz.afterIm;
	}
	
	ArrayList<Line> obroc(ArrayList<Line> linie, double angle, double x, double y) {
		TransformationMatrix macierz = TransformationMatrix.macierzPrzesuniecia(-x - 1.0D, -y);
		macierz.pomnoz(TransformationMatrix.macierzObrotu((angle / 180D) * 3.1415926535897931D));
		macierz.pomnoz(TransformationMatrix.macierzPrzesuniecia(x, y));
		macierz.drukujMacierz();
		ArrayList<Line> trans = new ArrayList<Line>();
		
		trans.addAll(linie);
		
		Counter licz = new Counter(trans, macierz);
		licz.computeDirectly();
		return licz.linie;
	}
	
	ArrayList<Line> przeskaluj(ArrayList<Line> linie, double a, double d, double x, double y) {
		TransformationMatrix macierz = TransformationMatrix.macierzPrzesuniecia(-x, -y);
		macierz.pomnoz(TransformationMatrix.macierzSkalowania(a, d));
		macierz.pomnoz(TransformationMatrix.macierzPrzesuniecia(x, y));
		macierz.drukujMacierz();
		ArrayList<Line> trans = new ArrayList<Line>();
		
		trans.addAll(linie);
		
		Counter licz = new Counter(trans, macierz);
		licz.computeDirectly();
		return licz.linie;
	}
	
	ArrayList<Line> przesun(ArrayList<Line> linie, double x, double y) {
		TransformationMatrix macierz = TransformationMatrix.macierzPrzesuniecia(x, y);
		macierz.drukujMacierz();
		ArrayList<Line> trans = new ArrayList<Line>();
		
		trans.addAll(linie);
		
		Counter licz = new Counter(trans, macierz);
		licz.computeDirectly();
		return licz.linie;
	}
	
	ArrayList<Line> odbijProsta(ArrayList<Line> linie, double a, double b) {
		double kat = Math.atan(a);
		TransformationMatrix macierz = TransformationMatrix.macierzPrzesuniecia(b, 0.0D);
		macierz.pomnoz(TransformationMatrix.macierzObrotu(kat));
		macierz.pomnoz(TransformationMatrix.macierzSkalowania(1.0D, -1D));
		macierz.pomnoz(TransformationMatrix.macierzObrotu(-kat));
		macierz.pomnoz(TransformationMatrix.macierzPrzesuniecia(-b, 0.0D));
		macierz.drukujMacierz();
		ArrayList<Line> trans = new ArrayList<Line>();
		
		trans.addAll(linie);
		
		Counter licz = new Counter(trans, macierz);
		licz.computeDirectly();
		return licz.linie;
	}
	
	ArrayList<Line> dowolna(ArrayList<Line> linie, double mac[][]) {
		TransformationMatrix macierz = new TransformationMatrix(mac);
		macierz.drukujMacierz();
		ArrayList<Line> trans = new ArrayList<Line>();
		
		trans.addAll(linie);
		
		Counter licz = new Counter(trans, macierz);
		licz.computeDirectly();
		return licz.linie;
	}
}