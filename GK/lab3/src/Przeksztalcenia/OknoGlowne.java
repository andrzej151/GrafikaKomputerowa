package Przeksztalcenia;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class OknoGlowne extends JFrame {

	private Widok widok;
	// private Menu menu;

	private JScrollPane scrollPane;
	private JPanel contentPane;
	private Controler menu;
	private Model model;
	


	public OknoGlowne() {
		super("Przeksztalcenia - lab 3 GK");

		widok = new Widok();
		menu = new Controler();
		model = new Model();
		widok.setBounds(50, 150, 250, 250);

	

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(widok);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(50, 180, 500, 500);
		add(scrollPane);

		
		menu.setBounds(50, 0, 900, 900);

		add(menu);
		
		widok.setModel(model);
		menu.setModel(model);
		menu.setWidok(widok);
		
		widok.setControler(menu);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1050, 1050);
		setVisible(true);
	}
}
