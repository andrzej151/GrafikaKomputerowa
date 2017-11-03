package Wycinanie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Pole extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

	JButton button;
	ArrayList<Line2D.Double> lines;

	// Interaction state variables:
	// =======================================
	// Index of line being dragged or -1 if none
	int line_caught;
	// Index of line end point caught: 0 or 1 or 2 for center
	int point_caught;

	public Pole() {
		super();

		setLayout(null);

		setBackground(Color.white);

		button = new JButton("Reset");
		button.setBounds(10, 10, 70, 30);
		add(button);

		lines = new ArrayList<>();

		lines.add(new Line2D.Double());
		lines.add(new Line2D.Double());
		initLines();

		button.addActionListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);

		
	}

	private void initLines() {
		Line2D.Double l = lines.get(0);
		Line2D.Double li = lines.get(1);
		l.x1 = 10;
		l.y1 = 100;
		l.x2 = 100;
		l.y2 = 100;
		li.x1 = 10;
		li.y1 = 130;
		li.x2 = 100;
		li.y2 = 130;
		line_caught = -1;
	}

	private boolean CatchClosePoint(double x, double y) {
		int i = 0;
		for (Line2D.Double l : lines) {

			if ((Math.abs(l.x1 - x) < 10) && (Math.abs(l.y1 - y) < 10)) {
				line_caught = i;
				point_caught = 0;
				return true;
			}
			if ((Math.abs(l.x2 - x) < 10) && (Math.abs(l.y2 - y) < 10)) {
				line_caught = i;
				point_caught = 1;
				return true;
			}

			i++;
		}
		return false;
	}

	private void initDisplay() {
		Graphics g = getGraphics();
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(1));
		g2d.setXORMode(Color.white);

		// Delete at previous position
		for (Line2D.Double l : lines) {
			g2d.draw(l);
		}

	}

	private void UpdateLinePosition(int x, int y) {
		if (line_caught >= 0) {
			Graphics g = getGraphics();
			Graphics2D g2d = (Graphics2D) g;

			g2d.setXORMode(getBackground());

			// Delete at previous position
			g2d.draw(lines.get(line_caught));

			// Update line end position
			if (point_caught == 0) {
				lines.get(line_caught).x1 = x;
				lines.get(line_caught).y1 = y;
			} else if (point_caught == 1) {
				lines.get(line_caught).x2 = x;
				lines.get(line_caught).y2 = y;
			}

			// Draw line at new position
			g2d.draw(lines.get(line_caught));
			g2d.setPaintMode();
		}
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.white);
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		// Fill rectangular area to show how XOR drawing renders
		// lines on various background
		Rectangle2D.Double rect = new Rectangle2D.Double(150, 50, 250, 150);
		g2d.setColor(Color.red);
		g2d.fill(rect);

		g2d.setColor(Color.black);
		g2d.setXORMode(Color.white);

		for (Line2D.Double l : lines) {
			g2d.draw(l);
		}
		g2d.setPaintMode();

	}

	public void actionPerformed(ActionEvent event) {
		initLines();
		repaint();
	}

	// Mouse event handlers
	// Funkcje obs³ugi zdarzeñ myszy
	public void mouseClicked(MouseEvent arg0) {

	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
		// Release line if currently being dragged
		line_caught = -1;
	}

	public void mousePressed(MouseEvent arg0) {
		// No line can may be caught at this moment.
		// Find close end of line to catch
		if (CatchClosePoint(arg0.getX(), arg0.getY())) {
			// Clicked near one of line ends catch it and update position
			UpdateLinePosition(arg0.getX(), arg0.getY());
		} else {
			System.out.println("ok");
			Line2D.Double l = new Line2D.Double();
			l.x1 = arg0.getX();
			l.y1 = arg0.getY();
			l.x2 = arg0.getX();
			l.y2 = arg0.getY();
			lines.add(l);
			point_caught = 0;
			line_caught = lines.size() - 1;

			UpdateLinePosition(arg0.getX(), arg0.getY());
		}
	}

	public void mouseReleased(MouseEvent arg0) {
		line_caught = -1;
	}

	public void mouseDragged(MouseEvent arg0) {
		if (line_caught != -1)
			UpdateLinePosition(arg0.getX(), arg0.getY());
	}

	public void mouseMoved(MouseEvent arg0) {
	}

}
