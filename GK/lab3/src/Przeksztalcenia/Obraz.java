package Przeksztalcenia;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Obraz {
	private BufferedImage images;

	public BufferedImage getObraz() {
		// TODO Auto-generated method stub
		return images;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return images.getWidth();
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return  images.getHeight();
	}
	
	public boolean wczytajObrazek(String nazwa) {
		// TODO Auto-generated method stub
		try {
			images = ImageIO.read(new File(nazwa));
			
			System.out.println("ok "+images.getHeight());
			
			return true;
		} catch (IOException e) {
			
			System.out.println("blad");
			return false;

		}
	}
}
