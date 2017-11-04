package Rysowanie;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class Wielokont implements IFigura {

	private ArrayList<Point> points = new ArrayList<Point>();
	

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

}
