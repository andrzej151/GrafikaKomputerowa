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
	public void paint(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawLine(points.get(0).x, points.get(0).y, points.get(points.size() - 1).x,
				points.get(points.size() - 1).y);
		for (int i = 0; i < (points.size() - 1); i++) {
			g2d.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);

		}

	}

	public void addPoint(Point p) {
		points.add(p);
	}

}
