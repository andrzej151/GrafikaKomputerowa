package Przeksztalcenia;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Menu extends JComponent implements ActionListener {

	private JButton bwektor, bobrazek, bprosta;
	private JTextField fnazwa, fa, fb;
	private Tryb tryb, funkcja;
	private JLabel la, lb, lnazwa, lstan;
	private int width = 80, height = 40;

	public Menu() {
		super();

		// Switch off automatic components positioning
		// Wy³¹czanie automatycznego pozycjonowania komponentów
		setLayout(null);

		// Create components and add it to the panel
		// Utworzenie komponentów i dodanie ich do panelu okna
		bwektor = new JButton("WEKTOR");
		bobrazek = new JButton("OBRAZEK");
		bprosta = new JButton("PROSTA");

		bwektor.setBounds(10, 10, 100, height);
		bobrazek.setBounds(110, 10, 100, height);
		bprosta.setBounds(10, 50, 100, height);

		// Add listener to allow reacting to clicking -
		// The same listener is used by all buttons
		// Dodawanie s³uchacza zdarzeñ - tego samego dla wszystkich przycisków
		bobrazek.addActionListener(this);
		bprosta.addActionListener(this);
		bwektor.addActionListener(this);

		add(bwektor);
		add(bobrazek);
		add(bprosta);

		la = new JLabel("y=");
		la.setBounds(110, 50, 20, 40);
		add(la);

		lb = new JLabel("*x+");
		lb.setBounds(180, 50, 20, 40);
		add(lb);

		lnazwa = new JLabel("Nazwa pliku");
		lnazwa.setBounds(220, 10, 100, 40);
		add(lnazwa);

		lstan = new JLabel();
		lstan.setBounds(10, 100, 150, 40);
		add(lstan);

		fa = new JTextField();
		fa.setBounds(130, 50, 50, 40);
		add(fa);

		fb = new JTextField();
		fb.setBounds(200, 50, 50, 40);
		add(fb);

		fnazwa = new JTextField();
		fnazwa.setBounds(310, 10, 100, 40);
		add(fnazwa);

		tryb = Tryb.OFF;
		funkcja = Tryb.OFF;

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		lstan.setText("STAN: " + tryb.toString() + " " + funkcja.toString());
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

	
			if (source == bwektor) {
				if (tryb.equals(Tryb.WEKTOR)) {
					tryb = Tryb.OFF;
					funkcja = Tryb.OFF;

				} else {
					tryb = Tryb.WEKTOR;
					funkcja = Tryb.WCZYTYWANIE;
				}

			} else if (source == bobrazek) {
				if (tryb.equals(Tryb.OBRAZEK)) {
					tryb = Tryb.OFF;
					funkcja = Tryb.OFF;

				} else {
					tryb = Tryb.OBRAZEK;
					funkcja = Tryb.WCZYTYWANIE;
				}
			}else if (source == bprosta) {
				funkcja = Tryb.RYSUJPROSTA;
			}

			// } else if (source == bwektor) {
			// tryb = Tryb.WIELOKONT;
			//
			// } else if (source == bzapiszObrazek) {
			// tryb = Tryb.ZAPIS;
			//
			// } else if (source == bwczytajObrazek) {
			// tryb = Tryb.WCZYTYWANIE;
			//
			// } else if (source == bedycja) {
			// tryb = Tryb.EDYCJA;
			//
			// }else if (source == bwczytajkontury) {
			// tryb = Tryb.WCZYTYWANIEKONTURY;
			//
			// }

	

		// Force window redraw to see the result immediately
		// Wymuszenie przerysowania okna aby uzyskaæ efekt operacji natychmiast
		repaint();
	}

	public String getfileName() {
		// TODO Auto-generated method stub
		return lstan.getText();
	}

	public void setfileName(String name) {
		// TODO Auto-generated method stub
		lstan.setText(name);
	}

}