package Rysowanie;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;

public interface IMaska {
	BufferedImage generuj(int x_res, int y_res);
}
