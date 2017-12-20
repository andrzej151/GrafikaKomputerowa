package Przeksztalcenia;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Controler extends JComponent implements ActionListener {

	private JButton bwektor, bobrazek, bprosta;
	private JTextField fnazwa, fa, fb, fnazwazl;
	private Tryb tryb, funkcja;
	private JLabel la, lb, lnazwa, lstan;
	private int width = 100, height = 40;
	private JTextField[][] macierzobrut, macierzprzesuniecie, macierzskalowanie;

	private JButton bskalowanie, bprzesuniecie, bobrot, bzlorzony;
	private Model model;
	private Widok widok;

	public Controler() {
		super();

		// Switch off automatic components positioning
		// Wy³¹czanie automatycznego pozycjonowania komponentów
		setLayout(null);

		// Create components and add it to the panel
		// Utworzenie komponentów i dodanie ich do panelu okna
		bwektor = new JButton("WEKTOR");
		bobrazek = new JButton("OBRAZEK");
		bprosta = new JButton("PROSTA");

		bwektor.setBounds(10, 10, width, height);
		bobrazek.setBounds(120, 10, width, height);
		bprosta.setBounds(10, 60, width, height);

		// Add listener to allow reacting to clicking -
		// The same listener is used by all buttons
		// Dodawanie s³uchacza zdarzeñ - tego samego dla wszystkich przycisków
		bobrazek.addActionListener(this);
		bprosta.addActionListener(this);
		bwektor.addActionListener(this);

		add(bwektor);
		add(bobrazek);
		add(bprosta);

		bobrot = new JButton("OBROT");
		bobrot.setBounds(600, 10, width + 50, height);
		bobrot.addActionListener(this);
		add(bobrot);
<<<<<<< HEAD
<<<<<<< HEAD
		macierzobrut = stworzmacierz(macierzobrut, 600, height + 20, 60, 40);
=======
		macierzobrut=stworzmacierz(macierzobrut, 600, height + 20, 60, 40);
>>>>>>> wektor
=======
		macierzobrut = stworzmacierz(macierzobrut, 600, height + 20, 60, 40);
>>>>>>> master

		bprzesuniecie = new JButton("Przesuniecie");
		bprzesuniecie.setBounds(600, 210, width + 50, height);
		bprzesuniecie.addActionListener(this);
		add(bprzesuniecie);
<<<<<<< HEAD
<<<<<<< HEAD
		macierzprzesuniecie = stworzmacierz(macierzprzesuniecie, 600, 260, 60, 40);
=======
		macierzprzesuniecie=stworzmacierz(macierzprzesuniecie, 600, 260, 60, 40);
>>>>>>> wektor
=======
		macierzprzesuniecie = stworzmacierz(macierzprzesuniecie, 600, 260, 60, 40);
>>>>>>> master

		bskalowanie = new JButton("Skalowanie");
		bskalowanie.setBounds(600, 420, width + 50, height);
		bskalowanie.addActionListener(this);
		add(bskalowanie);
<<<<<<< HEAD
<<<<<<< HEAD
		macierzskalowanie = stworzmacierz(macierzskalowanie, 600, 470, 60, 40);
=======
		macierzskalowanie=stworzmacierz(macierzskalowanie, 600, 470, 60, 40);
>>>>>>> wektor
=======
		macierzskalowanie = stworzmacierz(macierzskalowanie, 600, 470, 60, 40);
>>>>>>> master

		bzlorzony = new JButton("Zlorzone");
		bzlorzony.setBounds(600, 630, width, height);
		bzlorzony.addActionListener(this);
		add(bzlorzony);

		fnazwazl = new JTextField();
		fnazwazl.setBounds(710, 630, width, height);
		add(fnazwazl);

		la = new JLabel("y=");
		la.setBounds(120, 60, 20, 40);
		add(la);

		lb = new JLabel("*x+");
		lb.setBounds(190, 60, 20, 40);
		add(lb);

		lnazwa = new JLabel("Nazwa pliku");
		lnazwa.setBounds(240, 10, 100, 40);
		add(lnazwa);

		lstan = new JLabel();
		lstan.setBounds(10, 110, 150, 40);
		add(lstan);

		fa = new JTextField();
		fa.setBounds(140, 60, 50, 40);
		add(fa);

		fb = new JTextField();
		fb.setBounds(210, 60, 50, 40);
		add(fb);

		fnazwa = new JTextField();
		fnazwa.setBounds(320, 10, 100, 40);
		fnazwa.setText("dane.txt");
		add(fnazwa);

		tryb = Tryb.OFF;
		funkcja = Tryb.OFF;
<<<<<<< HEAD
<<<<<<< HEAD
		
=======
		macierzobrut[0][0].setText("1.0");
		macierzobrut[1][1].setText("1.0");
		macierzobrut[2][2].setText("1.0");
		macierzobrut[2][0].setText("2.0");
		macierzobrut[2][1].setText("-4.0");
>>>>>>> wektor
=======
		
>>>>>>> master

	}

	public JTextField[][] stworzmacierz(JTextField[][] macierz, int poczatekx, int poczateky, int w, int h) {

		macierz = new JTextField[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				macierz[j][i] = new JTextField();
				macierz[j][i].setBounds(poczatekx + i * (w + 10), poczateky + j * (h + 10), w, h);
				macierz[j][i].setText("0.0");
				add(macierz[j][i]);
			}
		}
		return macierz;
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
				model.wczytajWektor(fnazwa.getText());
				widok.repaint();

			} else {

				if (model.wczytajWektor(fnazwa.getText())) {
					tryb = Tryb.WEKTOR;
					funkcja = Tryb.OFF;
				} else {
					tryb = Tryb.OFF;
					funkcja = Tryb.OFF;

				}

			}

		} else if (source == bobrazek) {

			if (model.wczytajObrazek(fnazwa.getText())) {
				tryb = Tryb.OBRAZEK;
				funkcja = Tryb.OFF;
				widok.repaint();
			}

		} else if (source == bprosta) {

			funkcja = Tryb.RYSUJPROSTA;
		} else if (source == bobrot) {
			
				model.obrot(pobierz(macierzobrut));
				widok.repaint();		
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

	private double [][] pobierz(JTextField[][] macierz) {
		// TODO Auto-generated method stub
<<<<<<< HEAD
		double [] mac = new double[9];
		String s;
		int l = 0;
=======
		double [][] mac = new double[3][3];
	
>>>>>>> wektor
		for(int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> master
				s=macierz[i][j].getText();
				System.out.println(s);
				
				mac[l] = Double.parseDouble(s);
				l++;	
=======
				mac[j][i] = Double.parseDouble(macierz[j][i].getText());
					
>>>>>>> wektor
			}
		}
		return mac;
	}

	public double getA() {
		// TODO Auto-generated method stub
		return Double.parseDouble(fa.getText());
	}

	public double getB() {
		// TODO Auto-generated method stub
		return Double.parseDouble(fb.getText());
	}

	public Tryb funkcja() {
		return funkcja;
	}

	public Tryb tryb() {
		return tryb;
	}

	public String getfileName() {
		// TODO Auto-generated method stub
		return lstan.getText();
	}

	public void setfileName(String name) {
		// TODO Auto-generated method stub
		lstan.setText(name);
	}

	public void setFunkcja(Tryb tryb) {
		// TODO Auto-generated method stub
		funkcja = tryb;

	}

	public void setModel(Model model) {
		// TODO Auto-generated method stub
		this.model = model;

	}

	public void setWidok(Widok widok) {
		// TODO Auto-generated method stub
		this.widok = widok;

	}

}