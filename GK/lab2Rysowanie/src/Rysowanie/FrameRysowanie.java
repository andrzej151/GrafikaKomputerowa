package Rysowanie;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameRysowanie extends JFrame {

	private Canva kanwa;
	private Menu menu;
	private Listy listy;
	

	private ArrayList<IFigura> wielokonty = new ArrayList<IFigura>();
	private ArrayList<IFigura> prostokonty = new ArrayList<IFigura>();
	private ArrayList<IFigura> elipsy = new ArrayList<IFigura>();

	public FrameRysowanie() {
		super("Rysowanie - lab 2 GK");
		wielokonty = new ArrayList<IFigura>();
		

		kanwa = new Canva();
		kanwa.setBounds(50, 150, 250, 250);
		kanwa.setWielokonty(wielokonty);
		kanwa.setProstokonty(prostokonty);
		kanwa.setElipsy(elipsy);
		add(kanwa);
		
		listy = new Listy();
		listy.setBounds(600, 50, 300, 900);
		listy.setBackground(new Color(255, 0, 0));
		listy.setWielokonty(wielokonty);
		listy.setProstokonty(prostokonty);
		listy.setElipsy(elipsy);
		listy.setMenu(menu);
		add(listy);

		menu = new Menu();
		kanwa.setMenu(menu);
		add(menu);
		
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 1050, 1050);
		setVisible(true);
	}

}
