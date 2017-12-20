package Pracy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class CanvaAdmin extends JComponent implements ActionListener {
	private JButton blista, breset;
	private JTextField fid;// llista;
	private JLabel lid, lkomunikat;
	private Pracownicy pr;
	private JList czasy;
	private DefaultListModel lm;

	public CanvaAdmin() {
		// TODO Auto-generated constructor stub

		lm = new DefaultListModel();

		czasy = new JList(lm);
		czasy.setVisibleRowCount(10);
		czasy.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		czasy.setBounds(50, 150, 300, 300);
		add(czasy);

		breset = new JButton("Reset");

		breset.setBounds(180, 10, 70, 40);

		breset.addActionListener(this);
		add(breset);

		blista = new JButton("Lista");

		blista.setBounds(260, 10, 70, 40);

		blista.addActionListener(this);
		add(blista);

		fid = new JTextField();
		fid.setBounds(150, 60, 100, 50);
		add(fid);

		// llista = new JTextField();
		// llista.setBounds(50,100, 400, 400);
		// add(llista);

		lid = new JLabel("Identyfikator");
		lid.setBounds(50, 50, 100, 50);
		add(lid);

		lkomunikat = new JLabel("ok");
		lkomunikat.setBounds(100, 10, 100, 40);
		add(lkomunikat);
		pr = new Pracownicy();
		if (pr.wczytaj()) {
			lkomunikat.setText("Wczytano");
		} else {
			lkomunikat.setText("Blad wczytywania");
		}

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		Object source = event.getSource();
		Pracownik p;

		if (source == blista) {
			lm.clear();
			p = pr.pracownik(fid.getText());
			lm.addElement(p.czaspracysuma());
			for (int i = 0; i < p.size(); i++) {
				lm.addElement(p.czas(i));
			}
			repaint();
		} else if (source == breset) {
			pr.reset();
			this.setVisible(false);
		}
	}

	@Override
	protected void paintComponent(Graphics arg0) {
		// TODO Auto-generated method stub
		super.paintComponent(arg0);
	}
	
	

}
