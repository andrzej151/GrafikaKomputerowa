package Przeksztalcenia;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Widok extends JFrame {

	private Canva kanwa;
	// private Menu menu;

	private JScrollPane scrollPane;
	private JPanel contentPane;
	private JButton b;
	private Menu menu;


	public Widok() {
		super("Przeksztalcenia - lab 3 GK");

		kanwa = new Canva();
		kanwa.setBounds(50, 150, 250, 250);

	

		scrollPane = new JScrollPane();
		scrollPane.setViewportView(kanwa);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(50, 180, 500, 500);
		add(scrollPane);

		menu = new Menu();
		menu.setBounds(50, 0, 900, 900);

		add(menu);
		
		kanwa.setMenu(menu);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1050, 1050);
		setVisible(true);
	}
}
