package Rysowanie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Canva extends JPanel{
	private ArrayList<IFigura> wielokonty = new ArrayList<IFigura>();
	private int startx, starty, with, height;

	public Canva(int startx, int starty, int with, int height) {
		// TODO Auto-generated constructor stub
		this.startx = startx;
		this.starty = starty;
		this.with = with;
		this.height = height;
	    setBounds(startx, starty, with, height);
	}

	public void setWielokonty(ArrayList<IFigura> wielokonty) {
		
		this.wielokonty = wielokonty;
	}
	
	public void paintComponent( Graphics g)
	   {
	       g.setColor( Color.white );
		   super.paintComponent(g);   
		   
	       Graphics2D  g2d = (Graphics2D)g;	   
	     
	       // Fill rectangular area to show how XOR drawing renders 
	       // lines on various background
	       Rectangle2D.Double rect = new Rectangle2D.Double ( 150, 50, 250, 150 );
	       g2d.setColor( Color.red ); 		   
	       g2d.fill( rect );
	       
	       g2d.setColor( Color.black );       
	       g2d.setXORMode( Color.white );  
	       
	       for(IFigura f:wielokonty) {
	    	   f.paint(g2d);
	       }
	          
	   }
	   
	

}
