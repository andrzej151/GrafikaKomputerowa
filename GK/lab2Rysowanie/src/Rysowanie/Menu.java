package Rysowanie;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

public class Menu extends JComponent implements ActionListener {

	private JButton bwczytajObrazek;
	private JButton bzapiszObrazek;
	private JButton bprostokat;
	private JButton belipsa;
	private JButton bwielokont;
	private JButton btabela;
	private JTextField textField, textx, texty;
	private Tryb tryb;
	private int width = 80, height = 40;

	public Menu() {
		super();

		// Switch off automatic components positioning
		// Wy³¹czanie automatycznego pozycjonowania komponentów
		setLayout(null);
		

		// Create components and add it to the panel
		// Utworzenie komponentów i dodanie ich do panelu okna
		bwczytajObrazek = new JButton("WCZYTAJ");
		bzapiszObrazek = new JButton("ZAPISZ");
		bprostokat = new JButton("PROSTOKAT");
		belipsa = new JButton("ELIPSA");
		bwielokont = new JButton("WIELOKONT");
		btabela = new JButton("TABELA");
		textField = new JTextField();
		textx = new JTextField("500");
		texty = new JTextField("500");

		bwczytajObrazek.setBounds(50, 50, width, height);
		bzapiszObrazek.setBounds(150, 50, width, height);
		bprostokat.setBounds(50, 100, width, height);
		belipsa.setBounds(150, 100, width, height);
		bwielokont.setBounds(250, 100, width, height);
		btabela.setBounds(250, 50, width, height);
		textField.setBounds(350, 100, width*2, height);
		textx.setBounds(400, 50, width/2, height);
		texty.setBounds(450, 50, width/2, height);

		add(bwczytajObrazek);
		add(bzapiszObrazek);
		add(bprostokat);
		add(belipsa);
		add(bwielokont);
		add(btabela);
		add(textField);
		add(textx);
		add(texty);

		tryb = Tryb.OFF;

		// Add listener to allow reacting to clicking -
		// The same listener is used by all buttons
		// Dodawanie s³uchacza zdarzeñ - tego samego dla wszystkich przycisków
		bwczytajObrazek.addActionListener(this);
		bzapiszObrazek.addActionListener(this);
		bprostokat.addActionListener(this);
		belipsa.addActionListener(this);
		bwielokont.addActionListener(this);
		btabela.addActionListener(this);
		

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawString(tryb.toString(), 350, 50);
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

			} else if (source == btabela) {
				tryb = Tryb.TABELA;

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
		return textField.getText();
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
