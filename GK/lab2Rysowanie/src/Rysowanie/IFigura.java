package Rysowanie;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public interface IFigura {
	public void paint(Graphics2D g2d, Menu menu);

	public void paint(Graphics2D g2d, double scala);

	public boolean czyedytowany(int x, int y);
	
	public void zmien(int x, int y);

}
