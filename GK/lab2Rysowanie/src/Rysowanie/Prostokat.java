package Rysowanie;

import java.awt.Graphics2D;
import java.io.Serializable;

public class Prostokat implements IFigura, Serializable {

	private int x, y, w, h, zm;
	
	public Prostokat() {
		
	}

	public Prostokat(String string, String string2, String string3, String string4) {
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
		g2d.drawRect(x, y, w, h);
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
		g2d.fillRect(x * s, y * s, w * s, h * s);
	}

	public String toString() {
		return "px" + x + "x" + y + "x" + w + "x" + h;

	}

	@Override
	public boolean czyedytowany(int x, int y) {
		// TODO Auto-generated method stub
		if ((Math.abs(this.x - x) < 4 && Math.abs(this.y - y) < 4)) {
			zm = 1;
			return true;
		} else if ((Math.abs((this.x + this.w) - x) < 4 && Math.abs(this.y - y) < 4)) {
			zm = 2;
			return true;
		} else if ((Math.abs(this.x - x) < 4 && Math.abs((this.y + this.h) - y) < 4)) {
			zm = 3;
			return true;
		} else if ((Math.abs((this.x + this.w) - x) < 4 && Math.abs((this.y + this.h) - y) < 4)) {
			zm = 4;
			return true;
		}

		return false;
	}

	@Override
	public void zmien(int x, int y) {
		// TODO Auto-generated method stub
		switch (zm) {
		case 1:
			this.x = x;
			this.y = y;
			break;

		case 2:
			this.w = x - this.x;
			break;
		case 3:
			this.h = y - this.y;
			break;
		case 4:
			this.w = x - this.x;
			this.h = y - this.y;
			break;

		default:
			break;
		}
	}

}
