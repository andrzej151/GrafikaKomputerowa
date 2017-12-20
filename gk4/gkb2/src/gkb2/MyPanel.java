package gkb2;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

class MyPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	public ArrayList<Line> linie;
	public Image im;
	private boolean czyWektorowa;
	private BasicStroke pioro;
	private JFrame parent;
	
	MyPanel(JFrame parent) {
		this.parent = parent;
		pioro = new BasicStroke(5F);
	}
	
	void ustawRastrowa(Image buf) {
		im = buf;
		czyWektorowa = false;
		repaint();
	}
	
	void ustawWektorowa(ArrayList<Line> linie) {
		this.linie = linie;
		czyWektorowa = true;
		repaint();
	}
	
	void wyczysc() {
		im = null;
		linie = null;
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		if (linie == null && im == null) {
			Rectangle2D napis = g.getFontMetrics().getStringBounds("Brak Obrazka", g);
			g.drawString("Brak Obrazka", (int) (getWidth() / 2 - napis.getWidth() / 2D), (int) (getHeight() / 2 - napis.getHeight() / 2D));
		}
		else if (czyWektorowa) {
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(pioro);
			
			for(Line line: linie)
				g2.drawLine((int) line.a.x, (int) line.a.y, (int) line.b.x, (int) line.b.y);
			
		}
		else g.drawImage(im, 0, 0, null);
		//		else if (im.getHeight(parent) > getHeight() && im.getWidth(parent) > getWidth()) g.drawImage(im, 0, 0, null);
		//		else g.drawImage(im, (int) (getWidth() - dajWielkoscX()) / 2, (int) (getHeight() - dajWielkoscY()) / 2, null);
	}
	
	public void rysujNa(Graphics g) {
		g.drawImage(im, 0, 0, null);
	}
	
	boolean czyNamalowane() {
		return im == null || linie == null;
	}
	
	double dajWielkoscX() {
		if (czyWektorowa) {
			double x = linie.get(1).a.x;
			
			for(Line l: linie)
				if (l.b.x > x) x = l.b.x;
				else if (l.a.x > x) x = l.a.x;
			
			return x;
		}
		return im.getWidth(this);
	}
	
	double dajWielkoscY() {
		if (czyWektorowa) {
			double y = linie.get(1).a.y;
			
			for(Line l: linie)
				if (l.b.y > y) y = l.b.y;
				else if (l.a.y > y) y = l.a.y;
			
			return y;
		}
		return im.getHeight(this);
	}
}