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
import java.util.ArrayList;

import javax.swing.JPanel;

public class Canva extends JPanel implements MouseListener, MouseMotionListener {
	private ArrayList<IFigura> wielokonty = new ArrayList<IFigura>();
	private Menu menu;
	private int wielokont;
	private int startx, starty, with, height;
	private Line2D.Double line;

	public Canva(int startx, int starty, int with, int height) {
		// TODO Auto-generated constructor stub
		this.startx = startx;
		this.starty = starty;
		this.with = with;
		this.height = height;
		setBounds(startx, starty, with, height);
		wielokont = -1;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void setWielokonty(ArrayList<IFigura> wielokonty) {

		this.wielokonty = wielokonty;
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		// Fill rectangular area to show how XOR drawing renders
		// lines on various background
		Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, with - 1, height - 1);
		g2d.setColor(Color.white);
		g2d.fill(rect);
		g2d.setColor(Color.black);
		g2d.draw(rect);

		g2d.setColor(Color.black);
		g2d.setXORMode(Color.white);

		for (IFigura f : wielokonty) {

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
		System.out.println("OK");
		if (menu.getTryb().equals(Tryb.WIELOKONTPAINT)) {

			line = new Line2D.Double(((Wielokont) wielokonty.get(wielokont)).getlastPoint(),
					new Point(event.getX(), event.getY()));
			repaint();
		}

	}

	@Override
	public void mouseClicked(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if (menu.getTryb().equals(Tryb.WIELOKONTPAINT)) {
			((Wielokont) (wielokonty.get(wielokont))).addfirstPoint();
			menu.setTryb(Tryb.OFF);
			line = null;
		}

		repaint();

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
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

		}

		repaint();
	}

}
