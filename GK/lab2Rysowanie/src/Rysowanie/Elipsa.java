package Rysowanie;

import java.awt.Graphics2D;
import java.io.Serializable;


public class Elipsa implements IFigura, Serializable {

	private int x, y, w, h;
	
	 public Elipsa() {
		// TODO Auto-generated constructor stub
	}
	
	 public Elipsa(String string, String string2, String string3, String string4) {
		// TODO Auto-generated constructor stub
		Integer i = null;
		x = i.parseInt(string);
		y = i.parseInt(string2);
		w = i.parseInt(string3);
		h = i.parseInt(string4);
		System.out.println(this);

	}

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
	
	public String zapisz() {
		return "ex"+x+"x"+y+"x"+w+"x"+h ;
	}

	@Override
	public boolean czyedytowany(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void zmien(int x, int y) {
		// TODO Auto-generated method stub
		
	}

}