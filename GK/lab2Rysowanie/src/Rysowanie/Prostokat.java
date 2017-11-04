package Rysowanie;

import java.awt.Graphics2D;

public class Prostokat implements IFigura{

	private int x,y, w, h;
	
	
	@Override
	public void paint(Graphics2D g2d, Menu menu) {
		// TODO Auto-generated method stub	
		g2d.drawRect(x, y, w, h);
	}
	
	public void addstart(int x, int y) {
		this.x=x;
		this.y=y;
	}
	public void addend(int x, int y) {
		this.w=x-this.x;
		this.h=y-this.y;
	}



	@Override
	public void paint(Graphics2D g2d, double scala) {
		// TODO Auto-generated method stub
		int s=(int)scala;
		g2d.fillRect(x*s, y*s, w*s, h*s);
	}

}
