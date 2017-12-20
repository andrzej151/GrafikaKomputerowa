package Pracy;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Canva extends JComponent implements ActionListener {
	private JButton bstart, bstop, bnowyprzcownk, badmin;
	private JTextField fid;
	private JLabel lid, lkomunikat;
	private Pracownicy p;
	private OknoAdmin oa;

	public Canva() {
		// TODO Auto-generated constructor stub
		p = new Pracownicy();
		bstart = new JButton("Start");

		bstart.setBounds(50, 130, 100, 50);
		bstart.setBackground(new Color(0, 255, 0));
		bstart.addActionListener(this);
		add(bstart);

		bnowyprzcownk = new JButton("Nowy pracownik");

		bnowyprzcownk.setBounds(50, 200, 100, 40);

		bnowyprzcownk.addActionListener(this);
		add(bnowyprzcownk);
		
		badmin = new JButton("Admin");

		badmin.setBounds(170, 200, 100, 40);

		badmin.addActionListener(this);
		add(badmin);

		fid = new JTextField();
		fid.setBounds(150, 50, 100, 50);
		add(fid);

		bstop = new JButton("Stop");
		// stop.setBackground(new Color(255, 0, 0));
		bstop.setBounds(170, 130, 100, 50);
		bstop.setBackground(new Color(255, 0, 0));
		bstop.addActionListener(this);
		add(bstop);

		lid = new JLabel("Identyfikator");
		lid.setBounds(50, 50, 100, 50);
		add(lid);

		lkomunikat = new JLabel("ok");
		lkomunikat.setBounds(100, 10, 200, 40);
		add(lkomunikat);

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		Object source = event.getSource();
		String s;
		if (source == bstart) {
			if (p.wczytaj()) {
				s = p.rozpoczeciepracy(fid.getText());
				if (p.zapisz()) {
					lkomunikat.setText(s);
				} else {
					lkomunikat.setText("blad zapisu");
				}

			} else {
				lkomunikat.setText("blad odczytu");
			}

		} else if (source == bstop) {
			if (p.wczytaj()) {
				s = p.zakonczeniepracy(fid.getText());
				if (p.zapisz()) {
					lkomunikat.setText(s);
				} else {
					lkomunikat.setText("blad zapisu");
				}
			} else {
				lkomunikat.setText("blad odczytu");
			}

		}else if (source == bnowyprzcownk) {
			
			if (p.wczytaj()) {
				s = p.nowypracownik(fid.getText());
				if (p.zapisz()) {
					lkomunikat.setText(s);
				} else {
					lkomunikat.setText("blad zapisu");
				}

			} else {
				lkomunikat.setText("blad odczytu");
			}
			
		}else if (source == badmin) {
			
			oa = new OknoAdmin();
			
		}
		

	}

	@Override
	protected void paintComponent(Graphics arg0) {
		// TODO Auto-generated method stub
		super.paintComponent(arg0);
	}

	public void zakoncz() {
		// TODO Auto-generated method stub
		if (p.wczytaj()) {
			String s = p.zakonczeniepracy(fid.getText());
			if (p.zapisz()) {
				lkomunikat.setText(s);
			} else {
				lkomunikat.setText("blad zapisu");
			}
		} else {
			lkomunikat.setText("blad odczytu");
		}
	}

}
