package Rysowanie;

import java.awt.Graphics2D;

public class Elipsa implements IFigura {

	private int x, y, w, h;

	@Override
	public void paint(Graphics2D g2d, Menu menu) {
		// TODO Auto-generated method stub
		g2d.drawOval(x, y, w, h);
	}

	public void addstart(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void addend(int x, int y) {
		if ((x - this.x) > 0) {
			this.w = x - this.x;
		} else {
			this.w = Math.abs(this.x - x);
			this.x = x;
		}

		if ((y - this.y) > 0) {
			this.h = y - this.y;
		} else {
			this.h = Math.abs(this.y - y);
			this.y = y;
		}
	}

	@Override
	public void paint(Graphics2D g2d, double scala) {
		// TODO Auto-generated method stub
		int s = (int) scala;
		g2d.fillOval(x * s, y * s, w * s, h * s);
	}
	
	public String toString() {
		return "elipsa: "+x+" "+y+" "+w+" "+h ;
		
	}

}