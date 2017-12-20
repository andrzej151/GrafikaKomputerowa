package Przeksztalcenia;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Obraz {
	private BufferedImage imagesorginal, imagewyswietl;

	public BufferedImage getObraz() {
		// TODO Auto-generated method stub
		return imagewyswietl;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return imagewyswietl.getWidth();
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return  imagewyswietl.getHeight();
	}
	
	public boolean wczytajObrazek(String nazwa) {
		// TODO Auto-generated method stub
		try {
			imagesorginal = ImageIO.read(new File(nazwa));
			imagewyswietl = ImageIO.read(new File(nazwa));
		
			return true;
		} catch (IOException e) {
			
			System.out.println("blad");
			return false;

		}
	}
}
