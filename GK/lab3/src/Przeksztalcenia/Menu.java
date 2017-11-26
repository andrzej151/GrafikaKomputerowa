package Przeksztalcenia;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;



public class Menu extends JComponent implements ActionListener {

	private JButton bwczytajObrazek, bwczytajkontury;
	private JButton bzapiszObrazek;
	private JButton bprostokat;
	private JButton belipsa;
	private JButton bwielokont;
	private JButton bedycja;
	private JTextField wczytaj, textx, texty;
	private JLabel ltryb;
	private Tryb tryb;
	private int width = 80, height = 40;

	public Menu() {
		super();

		// Switch off automatic components positioning
		// Wy³¹czanie automatycznego pozycjonowania komponentów
		setLayout(null);
		

		// Create components and add it to the panel
		// Utworzenie komponentów i dodanie ich do panelu okna
		bwczytajObrazek = new JButton("WCZYT");
		bzapiszObrazek = new JButton("ZAPISZ");
		bprostokat = new JButton("PROST");
		belipsa = new JButton("ELIPSA");
		bwielokont = new JButton("WIELO");
		bedycja = new JButton("Edycja");
		wczytaj = new JTextField();
		textx = new JTextField("500");
		texty = new JTextField("500");
		ltryb = new JLabel("OFF");
		bwczytajkontury = new  JButton("kontury");

		bwczytajObrazek.setBounds(50, 50, width, height);
		bzapiszObrazek.setBounds(150, 50, width, height);
		bprostokat.setBounds(50, 100, width, height);
		belipsa.setBounds(150, 100, width, height);
		bwielokont.setBounds(250, 100, width, height);
		bedycja.setBounds(250, 50, width, height);
		wczytaj.setBounds(350, 100, width*2, height);
		textx.setBounds(400, 50, width/2, height);
		texty.setBounds(450, 50, width/2, height);
		ltryb.setBounds(350, 10, width+30, height);
		bwczytajkontury.setBounds(50, 0, width, height);

		add(bwczytajObrazek);
		add(bzapiszObrazek);
		add(bprostokat);
		add(belipsa);
		add(bwielokont);
		add(bedycja);
		add(wczytaj);
		add(textx);
		add(texty);
		add(ltryb);
		add(bwczytajkontury);

		tryb = Tryb.OFF;

		// Add listener to allow reacting to clicking -
		// The same listener is used by all buttons
		// Dodawanie s³uchacza zdarzeñ - tego samego dla wszystkich przycisków
		bwczytajObrazek.addActionListener(this);
		bzapiszObrazek.addActionListener(this);
		bprostokat.addActionListener(this);
		belipsa.addActionListener(this);
		bwielokont.addActionListener(this);
		bedycja.addActionListener(this);
		bwczytajkontury.addActionListener(this);
		

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ltryb.setText(tryb.toString());
	}

	public Tryb getTryb() {
		return tryb;
	}

	public void setTryb(Tryb t) {
		tryb = t;
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		Object source = event.getSource();

		if (tryb == Tryb.OFF) {
			if (source == belipsa) {
				tryb = Tryb.ELIPSA;

			} else if (source == bprostokat) {
				tryb = Tryb.PROSTOKONT;

			} else if (source == bwielokont) {
				tryb = Tryb.WIELOKONT;

			} else if (source == bzapiszObrazek) {
				tryb = Tryb.ZAPIS;

			} else if (source == bwczytajObrazek) {
				tryb = Tryb.WCZYTYWANIE;

			} else if (source == bedycja) {
				tryb = Tryb.EDYCJA;

			}else if (source == bwczytajkontury) {
				tryb = Tryb.WCZYTYWANIEKONTURY;

			}

		} else {
			tryb = Tryb.OFF;

		}

		// Force window redraw to see the result immediately
		// Wymuszenie przerysowania okna aby uzyskaæ efekt operacji natychmiast
		repaint();
	}

	public String getfileName() {
		// TODO Auto-generated method stub
		return wczytaj.getText();
	}
	
	public void setfileName(String name) {
		// TODO Auto-generated method stub
		 wczytaj.setText(name);
	}

	public int getwidh() {
		// TODO Auto-generated method stub
		return Integer.parseInt(textx.getText());
	}

	public int getheight() {
		// TODO Auto-generated method stub
		return Integer.parseInt(texty.getText());
	}

}