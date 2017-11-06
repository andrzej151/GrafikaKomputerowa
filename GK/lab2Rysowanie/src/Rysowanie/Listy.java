package Rysowanie;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Listy extends JComponent {

	private ArrayList<IFigura> wielokonty = new ArrayList<IFigura>();
	private ArrayList<IFigura> prostokonty = new ArrayList<IFigura>();
	private ArrayList<IFigura> elipsy = new ArrayList<IFigura>();
	private Menu menu;
	private JList listaprostokontow, listaelips, listawielokontow;
	private JLabel Lprostokaty, Lelipsy, Lwielokonty;
	private DefaultListModel modelProstokonty, modelElipsy, modelWielokonty;

	public Listy() {

		setLayout(new GridLayout(0,1));
		wypelnijListe();

		Lprostokaty = new JLabel("Prostokonty:");
		modelProstokonty = new DefaultListModel();
		listaprostokontow = new JList(modelProstokonty);
		listaprostokontow.setVisibleRowCount(5);
		listaprostokontow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaprostokontow.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (prostokonty.size() > 0 && listaprostokontow.getSelectedIndex()>=0) {
					//menu.setTryb(Tryb.USUWANIE);
					prostokonty.remove(listaprostokontow.getSelectedIndex());
					repaint();
				}
			}
		});
		add(Lprostokaty);
		add(new JScrollPane(listaprostokontow));

		Lelipsy = new JLabel("Elipsy:");
		modelElipsy = new DefaultListModel();
		listaelips = new JList(modelElipsy);
		listaelips.setVisibleRowCount(5);
		listaelips.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaelips.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (elipsy.size() > 0 && listaelips.getSelectedIndex()>=0) {
					//menu.setTryb(Tryb.USUWANIE);
					elipsy.remove(listaelips.getSelectedIndex());
					repaint();
				}
			}
		});
		add(Lelipsy);
		add(new JScrollPane(listaelips));

		Lwielokonty = new JLabel("Wielokonty:");
		modelWielokonty = new DefaultListModel();
		listawielokontow = new JList(modelWielokonty);
		listawielokontow.setVisibleRowCount(5);
		listawielokontow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listawielokontow.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if (wielokonty.size() > 0 && listawielokontow.getSelectedIndex()>=0) {
					//menu.setTryb(Tryb.USUWANIE);
					wielokonty.remove(listawielokontow.getSelectedIndex());
					repaint();
				}
			}
		});
		add(Lwielokonty);
		add(new JScrollPane(listawielokontow));
	}

	public void setBounds(int startx, int starty, int with, int height) {

		super.setBounds(startx, starty, with, height);

	}

	private void wypelnijListe() {
		// TODO Auto-generated method stub
		int i = 0;
		try {
			modelProstokonty.clear();
			modelElipsy.clear();
			modelWielokonty.clear();
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (prostokonty.size() > 0) {
			for (IFigura f : prostokonty) {
				modelProstokonty.add(i, f);

			}
		}

		for (IFigura f : elipsy) {
			modelElipsy.add(i, f);

		}

		for (IFigura f : wielokonty) {
			modelWielokonty.add(i, f);

		}

	}

	public void paintComponent(Graphics g) {
		wypelnijListe();

		super.paintComponent(g);

	}
	public void setMenu(Menu menu) {
		// TODO Auto-generated method stub
		this.menu = menu;

	}

	public void setWielokonty(ArrayList<IFigura> wielokonty) {

		this.wielokonty = wielokonty;
	}

	public void setProstokonty(ArrayList<IFigura> prostokonty) {
		// TODO Auto-generated method stub
		this.prostokonty = prostokonty;
	}

	public void setElipsy(ArrayList<IFigura> elipsy) {
		// TODO Auto-generated method stub
		this.elipsy = elipsy;
	}
}
