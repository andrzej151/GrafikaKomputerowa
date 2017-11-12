package Rysowanie;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class Wielokont implements IFigura, Serializable {

	private ArrayList<Point> points = new ArrayList<Point>();
	private int pointzmiany;
	

	public Wielokont() {
		// TODO Auto-generated constructor stub
		points = new ArrayList<Point>();
	}

	@Override
	public void paint(Graphics2D g2d, Menu menu) {
		// TODO Auto-generated method stub

		for (int i = 0; i < (points.size() - 1); i++) {
			g2d.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);

		}

	}

	public void addPoint(Point p) {
		points.add(p);
	}

	public Point getPoint(int index) {

		return points.get(index);
	}

	public Point getlastPoint() {
		return points.get(points.size() - 1);
	}

	public void addfirstPoint() {
		// TODO Auto-generated method stub
		points.add(points.get(0));
	}

	@Override
	public void paint(Graphics2D g2d, double scala) {
		// TODO Auto-generated method stub
		 int[] tbx= new int[ points.size()];
		 int[] tby= new int[ points.size()];
		for (int i = 0; i < points.size() ; i++) {
			tbx[i]=(int)(points.get(i).x*scala);
			tby[i]=(int)(points.get(i).y*scala);
		}
		g2d.fillPolygon(tbx, tby, tbx.length);
	}
	
	public String toString() {
		String s="wielokont: ";
		for(Point p :points)
		{
			s+="("+p.x+","+p.y+") ";
		}
		return s ;
		
	}
	
	
	public String zapisz() {
		String s="wx";
		for(Point p :points)
		{
			s+=""+p.x+"x"+p.y+"x";
		}
		return s ;
	}

	@Override
	public boolean czyedytowany(int x, int y) {
		// TODO Auto-generated method stub
		int i=0;
		for(Point p: points) {
			if(Math.abs(p.x-x)<4 && Math.abs(p.y-y)<4)
			{
				pointzmiany = i;
				return true;
			}
			i++;
		}
		return false;
	}

	@Override
	public void zmien(int x, int y) {
		// TODO Auto-generated method stub
		points.get(pointzmiany).x=x;
		points.get(pointzmiany).y=y;
		
	}

}
