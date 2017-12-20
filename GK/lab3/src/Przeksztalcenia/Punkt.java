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
	public void mnorzenie(double[][] ds) {
		System.out.println("("+this.x+" "+this.y+" "+this.w+")->przekszta³c");
		// TODO Auto-generated method stub
		System.out.println("ok "+this.x+"*"+ds[0][0]+"+"+this.y+"*"+ds[1][0]+"+"+this.w+"*"+ds[2][0]);
		this.x=this.x*ds[0][0]+this.y*ds[1][0]+this.w*ds[2][0];
		
		
		this.y=this.x*ds[0][1]+this.y*ds[1][1]+this.w*ds[2][1];
		this.w=this.x*ds[0][2]+this.y*ds[1][2]+this.w*ds[2][2];
		
		for(int i=0;i<3;i++) {
			for(int j = 0;j<3;j++)
			{
				System.out.print(ds[j][i]+" ");
			}
			System.out.println();
		}
		System.out.println("->("+this.x+" "+this.y+" "+this.w+")");
		
	}
}
