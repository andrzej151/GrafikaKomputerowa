package gkb2;

public class Line {
	Point a;
	Point b;
	
	public Line(Point a, Point b) {
		this.a = a;
		this.b = b;
	}
	
	@Override
	public String toString() {
		return a + " " + b + "\n";
	}
}