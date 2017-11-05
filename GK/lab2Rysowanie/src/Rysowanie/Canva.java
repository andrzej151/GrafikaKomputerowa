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
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

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
	private Maskaszachownica mszach;

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
			g2d.drawImage(obrazek1, 0, 0, with, height, null);
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

		}

		repaint();
	}

	public void wczytaj() {

		try {
			String image_name = menu.getfileName();
			obrazek1 = ImageIO.read(new File(image_name));
			menu.setTryb(Tryb.OFF);
		} catch (IOException e) {
			menu.setTryb(Tryb.ERROR);

		}
	}

	private void zapis() {
		try {
			String name = menu.getfileName();
			generator = new Generator();
			generator.setsourse1(obrazek1);
			mwektor = new MaskaWektor();
			mwektor.setElipsy(elipsy);
			mwektor.setProstokonty(prostokonty);
			mwektor.setWielokonty(wielokonty);
			mwektor.setWielkoscPolaDorys(with, height);
			generator.setMaska(mwektor);
			generator.generuj();
			generator.polaczSZ();

			dest = generator.getDest();
			ImageIO.write(dest, "bmp", new File(name));
			System.out.println(" image created successfully");
			menu.setTryb(Tryb.OFF);
		} catch (IOException e) {
			System.out.println("The image cannot be stored");
		}
	}

}
