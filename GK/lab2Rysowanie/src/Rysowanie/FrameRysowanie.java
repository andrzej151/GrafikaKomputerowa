package Rysowanie;

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

	private ArrayList<IFigura> wielokonty = new ArrayList<IFigura>();

	public FrameRysowanie() {
		super("Rysowanie - lab 2 GK");
		wielokonty = new ArrayList<IFigura>();
		

		kanwa = new Canva(50, 150, 250, 250);
		kanwa.setWielokonty(wielokonty);
		add(kanwa);

		menu = new Menu();
		kanwa.setMenu(menu);
		add(menu);
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 1050, 1050);
		setVisible(true);
	}

}
