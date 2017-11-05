package Rysowanie;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public interface IFigura {
	public void paint(Graphics2D g2d, Menu menu);

	public void paint(Graphics2D g2d, double scala);

}
