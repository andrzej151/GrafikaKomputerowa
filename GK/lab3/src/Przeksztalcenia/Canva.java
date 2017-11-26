package Przeksztalcenia;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class Canva extends JPanel implements MouseListener, MouseMotionListener {

	private int with, height, starty, startx;
	private int Maxwith, Maxheight, rozdzielczosc, srodekx, srodeky;
	private Menu menu;

	public Canva() {
		// TODO Auto-generated constructor stub
		addMouseListener(this);
		addMouseMotionListener(this);

		Maxwith = 2000;
		Maxheight = 2000;
		rozdzielczosc = 50;
		srodekx = Maxwith / 2;
		srodeky = Maxheight / 2;
		setSize(Maxwith, Maxheight);

	}

	public void setBounds(int startx, int starty, int with, int height) {
		this.startx = startx;
		this.starty = starty;
		this.with = with;
		this.height = height;

		super.setBounds(startx, starty, with, height);

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		setSize(Maxwith, Maxheight);

		this.setBackground(Color.white);

		siatka(g2d);
		prosta(g2d);

	}

	private void prosta(Graphics2D g2d) {
		// TODO Auto-generated method stub
		double a = 1;
		double b = 0;
		try {
			a = menu.getA();
			b = menu.getB();
		}catch (Exception e) {
			// TODO: handle exception
		}
		

		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(2));

		int x0, y0, xk, yk;
		y0 = (int) (Maxheight/2-((a * (-(Maxwith / 2))) + b));
		x0 = 0;
		yk = (int) (Maxheight/2-((a * ((Maxwith / 2))) + b));
		xk = Maxwith;
		
		System.out.println(x0+" "+y0+" "+xk+" "+yk);

		g2d.drawLine(x0, y0, xk, yk);

		g2d.setStroke(new BasicStroke(1));

	}

	private void siatka(Graphics2D g2d) {
		// siatka
		setPreferredSize(new Dimension(Maxwith, Maxheight));
		g2d.setColor(Color.GRAY);
		for (int i = 0; i < Maxwith; i += rozdzielczosc) {
			g2d.drawLine(i, 0, i, Maxheight);
		}

		for (int i = 0; i < Maxheight; i += rozdzielczosc) {
			g2d.drawLine(0, i, Maxwith, i);
		}

		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(2));

		g2d.drawLine(Maxwith / 2, 0, Maxheight / 2, Maxheight);
		g2d.drawLine(0, Maxheight / 2, Maxwith, Maxheight / 2);

		g2d.setStroke(new BasicStroke(1));
		menu.setFunkcja(Tryb.OFF);

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void setMenu(Menu menu) {
		// TODO Auto-generated method stub
		this.menu = menu;
	}

}
