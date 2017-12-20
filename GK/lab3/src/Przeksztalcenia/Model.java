package Przeksztalcenia;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JTextField;

public class Model {

	private ArrayList<Punkt> pkt;
	private ArrayList<Shape> polaczenia;
	private int iteratorpolaczenia;
	private Obraz obraz;
	private boolean isimg;
	private AffineTransform transform;
	private int Maxwith, Maxheight;

	public Model(int Maxwith, int Maxheight) {
		pkt = new ArrayList<>();
		polaczenia = new ArrayList<>();
		iteratorpolaczenia = 0;
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> master
		this.Maxwith = Maxwith;
		this.Maxheight = Maxheight;
		
		transform = new AffineTransform(  );  
<<<<<<< HEAD
=======
		obraz = new Obraz();
>>>>>>> wektor
=======
>>>>>>> master
	}

	public boolean pierwszywektor() {
		// TODO Auto-generated method stub
		iteratorpolaczenia = 0;
		return polaczenia.size() > 0;
	}

	public boolean nastepnywektor() {
		// TODO Auto-generated method stub
		return iteratorpolaczenia < polaczenia.size();
	}

	public Shape getWektor() {
		// TODO Auto-generated method stub
<<<<<<< HEAD
<<<<<<< HEAD

		Shape po = polaczenia.get(iteratorpolaczenia);
=======
		Wektor w = new Wektor();
		Polaczenia po = polaczenia.get(iteratorpolaczenia);
		//System.out.println("spr " + po);
		w.xs = po.getA().getX();
		w.ys = po.getA().getY();
		w.xk = po.getB().getX();
		w.yk = po.getB().getY();
>>>>>>> wektor
=======

		Shape po = polaczenia.get(iteratorpolaczenia);
>>>>>>> master
		iteratorpolaczenia++;
		return po;
	}

	public boolean wczytajWektor(String nazwa) {
		int iloscpunktow, iloscpolaczen, x, y, nr;
		String pom = "";
		Punkt p, p2;
		Polaczenia pol;

		try {
			//System.out.println(nazwa);
			Scanner in = new Scanner(new File(nazwa));
			iloscpunktow = in.nextInt();
			iloscpolaczen = in.nextInt();
			pkt = new ArrayList<>();
			polaczenia = new ArrayList<>();
			//System.out.println("ok");
			while (in.hasNext() && (!pom.equals("Punkty"))) {
				pom = in.next();
			}

			for (int i = 0; i < iloscpunktow; i++) {
				x = in.nextInt();
				y = in.nextInt();
				p = new Punkt(x, y);
				pkt.add(p);
			}

			while (in.hasNext() && (!pom.equals("Polaczenia"))) {
				pom = in.next();
			}

			for (int i = 0; i < iloscpolaczen; i++) {
				x = in.nextInt();
				y = in.nextInt();

				polaczenia.add(new Line2D.Double(Maxwith / 2 + pkt.get(x).getX(), Maxheight / 2 - pkt.get(x).getY(),
						Maxwith / 2 + pkt.get(y).getX(), Maxheight / 2 - pkt.get(y).getY()));
			}

			in.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public boolean wczytajObrazek(String nazwa) {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		try {
			images = ImageIO.read(new File(nazwa));
			isimg = true;
			return true;
		} catch (IOException e) {
			isimg = false;
			System.out.println("blad");
			return false;

		}
=======
		isimg=obraz.wczytajObrazek(nazwa);
		return isimg;
	
>>>>>>> wektor
	}

	public BufferedImage getImages() {
		// TODO Auto-generated method stub
		return obraz.getObraz();
	}

	public boolean isObrazek() {
		// TODO Auto-generated method stub
		return isimg;
	}

	public int getImageW() {
		// TODO Auto-generated method stub
		return obraz.getWidth();
	}

	public int getImageH() {
		// TODO Auto-generated method stub
		return obraz.getHeight();
	}

	public void obrot(double[][] ds) {
		// TODO Auto-generated method stub
<<<<<<< HEAD


		
			  transform.translate(  10, 0 );
			//pom.add(przeksztalcenie.createTransformedShape(polaczenia.remove(0)));

		

	}

	public AffineTransform gettransform() {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		return transform;
=======
		System.out.println("ugauga");
		for(int i = 0; i<pkt.size(); i++)
		{
			pkt.get(i).mnorzenie(ds);
		}
>>>>>>> wektor
=======


		
			  transform.translate(  10, 0 );
			//pom.add(przeksztalcenie.createTransformedShape(polaczenia.remove(0)));

		

	}

	public AffineTransform gettransform() {
		// TODO Auto-generated method stub
		return transform;
>>>>>>> master
	}

}
