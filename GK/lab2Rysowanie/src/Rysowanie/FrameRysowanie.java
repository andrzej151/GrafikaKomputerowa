package Rysowanie;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameRysowanie extends JFrame{
	
	private Canva kanwa;
	
	
	
	private ArrayList<IFigura> wielokonty = new ArrayList<IFigura>();
	
	
	public FrameRysowanie() {
		super("Rysowanie - lab 2 GK");
		wielokonty = new ArrayList<IFigura>();
		Wielokont w = new Wielokont();
		w.addPoint(new Point(100, 100));
		w.addPoint(new Point(150, 100));
		w.addPoint(new Point(250, 250));
		w.addPoint(new Point(300, 250));
		w.addPoint(new Point(300, 300));
		w.addPoint(new Point(250, 300));
		wielokonty.add(w);
		
		kanwa = new Canva(50,50,250,250); 
        kanwa.setWielokonty(wielokonty);
		add(kanwa);
		
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds( 50, 50, 1050, 1050);
        setVisible(true);
	}

}
