package Rysowanie;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class Tabele extends JComponent {
	private ArrayList<IFigura> wielokonty = new ArrayList<IFigura>();
	private ArrayList<IFigura> prostokonty = new ArrayList<IFigura>();
	private ArrayList<IFigura> elipsy = new ArrayList<IFigura>();
	private JTable twielokonty;
	private JTable tprostokonty;
	private JTable tableProstokat;

	public Tabele() {
		
		tableProstokat = new JTable();
		tableProstokat.setModel(new DefaultTableModel(new Object[][] { { 0, 1, 2, 4, 5 }, },
				new String[] { "id", "x", "y", "width", "height" }) {
			Class[] columnTypes = new Class[] { Integer.class, Integer.class, Integer.class, Integer.class,
					Integer.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});

		tableProstokat.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(tableProstokat);
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

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

	}

}
