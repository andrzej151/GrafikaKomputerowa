package Rysowanie;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;

public class Frys extends JFrame {
	private Canva kanwa;
	private Menu menu;
	private Tabele tabele;


	private ArrayList<IFigura> wielokonty = new ArrayList<IFigura>();
	private ArrayList<IFigura> prostokonty = new ArrayList<IFigura>();
	private ArrayList<IFigura> elipsy = new ArrayList<IFigura>();
	

	public Frys() {
		super("Rysowanie - lab 2 GK");
		wielokonty = new ArrayList<IFigura>();
		tabele = new Tabele();
		tabele.setBounds(350, 50, 250, 250);
		tabele.setWielokonty(wielokonty);
		tabele.setProstokonty(prostokonty);
		tabele.setElipsy(elipsy);
		getContentPane().add(tabele);
		

		kanwa = new Canva();
		kanwa.setBounds(50, 150, 250, 250);
		kanwa.setWielokonty(wielokonty);
		kanwa.setProstokonty(prostokonty);
		kanwa.setElipsy(elipsy);
		getContentPane().add(kanwa);

		menu = new Menu();
		kanwa.setMenu(menu);
		getContentPane().add(menu);
		
		
		
		
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(50, 50, 1050, 1050);
		setVisible(true);
	}

}