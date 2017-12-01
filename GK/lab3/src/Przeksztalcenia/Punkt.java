package Przeksztalcenia;

public class Punkt {
	private double x,y,w;
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public double getW() {
		return w;
	}
	public void setW(double w) {
		this.w = w;
	}
	public Punkt()
	{
		x=0;
		y=0;
		w=1;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "("+x+","+y+")";
	}
	
	public Punkt(double x, double y)
	{
		this.x=x;
		this.y=y;
		w=1;
	}
	
	public Punkt(double x, double y,double w)
	{
		this.x=x;
		this.y=y;
		this.w=w;
	}
}
