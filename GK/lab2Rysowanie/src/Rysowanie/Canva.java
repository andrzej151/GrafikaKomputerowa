package Rysowanie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Canva extends JPanel implements MouseListener, MouseMotionListener {
	private ArrayList<IFigura> wielokonty = new ArrayList<IFigura>();
	private ArrayList<IFigura> prostokonty = new ArrayList<IFigura>();
	private ArrayList<IFigura> elipsy = new ArrayList<IFigura>();
	private Menu menu;
	private int wielokont, prostokant, elipsa;
	private int startx, starty, with, height;
	private Line2D.Double line;
	private BufferedImage obrazek1, dest;
	private Generator generator;
	private MaskaWektor mwektor;
	private Maskapierscienie mszach;
	private IFigura FZmieniana;
	private String nazwapliku;
	
	
	public Canva() {
		// TODO Auto-generated constructor stub
		wielokont = -1;
		prostokant = -1;
		elipsa = -1;
		addMouseListener(this);
		addMouseMotionListener(this);
		
        
	}

	public void setBounds(int startx, int starty, int with, int height) {
		this.startx = startx;
		this.starty = starty;
		this.with = with;
		this.height = height;

		super.setBounds(startx, starty, with, height);

	}

	public void setWielokonty(ArrayList<IFigura> wielokonty) {

		this.wielokonty = wielokonty;
	}

	public void setProstokonty(ArrayList<IFigura> prostokonty) {
		// TODO Auto-generated method stub
		this.prostokonty = prostokonty;
	}

	public void setElipsy(ArrayList<IFigura> elipsy) {
		// TODO Auto-generated method stub
		this.elipsy = elipsy;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		if (menu.getTryb().equals(Tryb.WCZYTYWANIEKONTURY)) {

			wczytajkontur();
		}

		if (menu.getTryb().equals(Tryb.WCZYTYWANIE)) {
			setBounds(startx, starty, menu.getwidh(), menu.getheight());
			wczytaj();
		}
		if (menu.getTryb().equals(Tryb.ZAPIS)) {

			zapis();
		}

		Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, with - 1, height - 1);
		if (obrazek1 == null) {
			rect = new Rectangle2D.Double(0, 0, with - 1, height - 1);
			g2d.setColor(Color.white);
			g2d.fill(rect);
		} else {
			g2d.drawImage(obrazek1, 0, 0, null);
		}

		// Fill rectangular area to show how XOR drawing renders
		// lines on various background

		g2d.setColor(Color.black);
		g2d.draw(rect);

		g2d.setColor(Color.black);
		g2d.setXORMode(Color.white);

		for (IFigura f : wielokonty) {

			f.paint(g2d, menu);
		}
		for (IFigura f : prostokonty) {

			f.paint(g2d, menu);
		}
		for (IFigura f : elipsy) {

			f.paint(g2d, menu);
		}

		if (menu.getTryb().equals(Tryb.WIELOKONTPAINT)) {
			if (line != null) {
				g2d.draw(line);
			}
		}

	}

	public void setMenu(Menu menu) {
		// TODO Auto-generated method stub
		this.menu = menu;

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent event) {
		// TODO Auto-generated method stub

		if (menu.getTryb().equals(Tryb.WIELOKONTPAINT)) {

			line = new Line2D.Double(((Wielokont) wielokonty.get(wielokont)).getlastPoint(),
					new Point(event.getX(), event.getY()));

		} else if (menu.getTryb().equals(Tryb.PROSTOKONTPAINT)) {
			((Prostokat) prostokonty.get(prostokant)).addend(event.getX(), event.getY());
		} else if (menu.getTryb().equals(Tryb.ELIPSAPAINT)) {
			((Elipsa) elipsy.get(elipsa)).addend(event.getX(), event.getY());
		} else if (menu.getTryb().equals(Tryb.EDYCJAZMIANA)) {
			FZmieniana.zmien(event.getX(), event.getY());
		}

		repaint();

	}

	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub
		if (menu.getTryb().equals(Tryb.PROSTOKONTPAINT)) {
			((Prostokat) prostokonty.get(prostokant)).addend(event.getX(), event.getY());
		} else if (menu.getTryb().equals(Tryb.ELIPSAPAINT)) {
			((Elipsa) elipsy.get(elipsa)).addend(event.getX(), event.getY());

		}
	}

	@Override
	public void mouseExited(MouseEvent event) {
		// TODO Auto-generated method stub
		if (menu.getTryb().equals(Tryb.WIELOKONTPAINT)) {
			((Wielokont) (wielokonty.get(wielokont))).addfirstPoint();
			menu.setTryb(Tryb.OFF);
			line = null;
		} else if (menu.getTryb().equals(Tryb.PROSTOKONTPAINT)) {
			((Prostokat) prostokonty.get(prostokant)).addend(event.getX(), event.getY());

		}
		if (menu.getTryb().equals(Tryb.ELIPSAPAINT)) {
			((Elipsa) elipsy.get(elipsa)).addend(event.getX(), event.getY());

		}

		repaint();

	}

	@Override
	public void mousePressed(MouseEvent event) {
		// TODO Auto-generated method stub

		if (menu.getTryb().equals(Tryb.WIELOKONTPAINT)) {
			if (event.getClickCount() == 2) {

				((Wielokont) (wielokonty.get(wielokont))).addfirstPoint();
				menu.setTryb(Tryb.OFF);
				line = null;

			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent event) {
		// TODO Auto-generated method stub

		if (menu.getTryb().equals(Tryb.WIELOKONT)) {
			menu.setTryb(Tryb.WIELOKONTPAINT);
			wielokont = wielokonty.size();
			Wielokont w = new Wielokont();
			w.addPoint(new Point(event.getX(), event.getY()));
			wielokonty.add(w);

		} else if (menu.getTryb().equals(Tryb.WIELOKONTPAINT)) {
			((Wielokont) wielokonty.get(wielokont)).addPoint(new Point(event.getX(), event.getY()));

		} else if (menu.getTryb().equals(Tryb.PROSTOKONT)) {
			menu.setTryb(Tryb.PROSTOKONTPAINT);
			prostokant = prostokonty.size();
			Prostokat p = new Prostokat();
			p.addstart(event.getX(), event.getY());
			p.addend(event.getX(), event.getY());
			prostokonty.add(p);

		} else if (menu.getTryb().equals(Tryb.PROSTOKONTPAINT)) {
			menu.setTryb(Tryb.OFF);
			((Prostokat) prostokonty.get(prostokant)).addend(event.getX(), event.getY());

		} else if (menu.getTryb().equals(Tryb.ELIPSA)) {
			menu.setTryb(Tryb.ELIPSAPAINT);
			elipsa = elipsy.size();
			Elipsa p = new Elipsa();
			p.addstart(event.getX(), event.getY());
			p.addend(event.getX(), event.getY());
			elipsy.add(p);

		} else if (menu.getTryb().equals(Tryb.ELIPSAPAINT)) {
			menu.setTryb(Tryb.OFF);
			((Elipsa) elipsy.get(elipsa)).addend(event.getX(), event.getY());

		} else if (menu.getTryb().equals(Tryb.EDYCJA)) {

			for (IFigura f : wielokonty) {

				if (f.czyedytowany(event.getX(), event.getY())) {
					menu.setTryb(Tryb.EDYCJAZMIANA);
					FZmieniana = f;
				}
			}
			for (IFigura f : prostokonty) {
				if (f.czyedytowany(event.getX(), event.getY())) {
					menu.setTryb(Tryb.EDYCJAZMIANA);
					FZmieniana = f;
				}
			}
			for (IFigura f : elipsy) {

				if (f.czyedytowany(event.getX(), event.getY())) {
					menu.setTryb(Tryb.EDYCJAZMIANA);
					FZmieniana = f;
				}
			}

		} else if (menu.getTryb().equals(Tryb.EDYCJAZMIANA)) {
			menu.setTryb(Tryb.OFF);
			FZmieniana.zmien(event.getX(), event.getY());
		}

		repaint();
	}

	public void wczytaj() {

		try {
			
			 nazwapliku = menu.getfileName();
			obrazek1 = ImageIO.read(new File(nazwapliku));
			System.out.println(obrazek1.getWidth()+" "+ obrazek1.getHeight());
			setPreferredSize(new Dimension(obrazek1.getWidth(), obrazek1.getHeight()));
			menu.setTryb(Tryb.OFF);
		} catch (IOException e) {
			menu.setTryb(Tryb.ERROR);

		}
	}

	private void wczytajkontur() {
		// TODO Auto-generated method stub
		System.out.println("odczyt kont");

		try {

			String name = menu.getfileName();
			String line;
			String [] w;
			Scanner in = new Scanner(new File(name+".txt"));
			nazwapliku=in.nextLine();
			
			 
			while(in.hasNextLine())
			{
				 line = in.nextLine();
				 w=line.split("x");
				 System.out.println(w[0]+w[1]+w[2]+w[3]+w[4]);
				 if(w[0].equals("p"))
				 {
						 prostokonty.add(new Prostokat(w[1],w[2],w[3],w[4]));
					 
				 }
				 if(w[0].equals("e"))
				 {
						 elipsy.add(new Elipsa(w[1],w[2],w[3],w[4]));
					 
				 }
				 if(w[0].equals("w")) 
				 {
					 Wielokont wi= new Wielokont();
					 for(int i=1;i<w.length;i+=2) {
						 Integer inti = null;
						 Point po=new Point(inti.parseInt(w[i]), inti.parseInt(w[i+1]));
						 wi.addPoint(po);
					 }
					 wielokonty.add(wi);
				 }
			}
			
		
			in.close();
			menu.setfileName(nazwapliku);
			menu.setTryb(Tryb.WCZYTYWANIE);
			
		} catch (IOException e) {
			System.out.println("The image cannot be stored");
		}
	}

	private void zapis() {
		try {

			String name = menu.getfileName();
			PrintWriter out = new PrintWriter(new File(name+".txt"));
			out.println(nazwapliku);
			
			for (IFigura f : prostokonty) {
				out.println(f.zapisz());
			}
			for (IFigura f : elipsy) {
				out.println(f.zapisz());
			}
			for (IFigura f : wielokonty) {
				out.println(f.zapisz());
			}
			out.close();

			// generator = new Generator();
			// generator.setsourse1(obrazek1);
			// mwektor = new MaskaWektor();
			// mwektor.setElipsy(elipsy);
			// mwektor.setProstokonty(prostokonty);
			// mwektor.setWielokonty(wielokonty);
			// mwektor.setWielkoscPolaDorys(with, height);
			// generator.setMaska(mwektor);
			// generator.generuj();
			// generator.polaczSZ();
			//
			// dest = generator.getDest();
			// ImageIO.write(dest, "bmp", new File(name));
			System.out.println(" image created successfully");
			menu.setTryb(Tryb.OFF);
		} catch (IOException e) {
			System.out.println("The image cannot be stored");
		}
	}

}
