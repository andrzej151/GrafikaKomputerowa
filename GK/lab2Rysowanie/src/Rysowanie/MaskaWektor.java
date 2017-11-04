package Rysowanie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MaskaWektor implements IMaska{

	private ArrayList<IFigura> wielokonty = new ArrayList<IFigura>();
	private ArrayList<IFigura> prostokonty = new ArrayList<IFigura>();
	private ArrayList<IFigura> elipsy = new ArrayList<IFigura>();
	private  BufferedImage maska;
	private int x,y;
	
	@Override
	public BufferedImage generuj(int x_res, int y_res) {
		maska = new BufferedImage(x_res, y_res, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d= maska.createGraphics();
		
		g2d.setColor(Color.white);
		g2d.setXORMode(Color.black);
		
		int scala= (x_res<y_res?x_res:y_res)/(x<y?x:y);

		for (IFigura f : wielokonty) {

			f.paint(g2d,scala);
		}
		for (IFigura f : prostokonty) {

			f.paint(g2d,scala);
		}
		for (IFigura f : elipsy) {

			f.paint(g2d,scala);
		}
		
		return maska;
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
	
	public void setWielkoscPolaDorys(int x,int y) {
		this.x=x;
		this.y=y;
	}

}
