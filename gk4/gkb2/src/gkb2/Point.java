package gkb2;

public class Point {
	
	double x;
	double y;
	
	Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return (int) x + " " + (int) y;
	}
}