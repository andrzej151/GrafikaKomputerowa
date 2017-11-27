package Przeksztalcenia;

import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Model {
	
	private ArrayList<Punkt> pkt;
	private ArrayList<Polaczenia> polaczenia;
	

	public boolean pierwszywektor() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean nastepnywektor() {
		// TODO Auto-generated method stub
		return false;
	}

	public Wektor getWektor() {
		// TODO Auto-generated method stub
		
		return new Wektor();
	}
	
	public boolean wczytajWektor(String nazwa)
	{
		int iloscpunktow, iloscpolaczen, x, y, nr;
		String pom = "";
		Punkt p,p2;
		Polaczenia pol;
		
		try {
			Scanner in= new Scanner(new File(nazwa));
			iloscpunktow = in.nextInt();
			iloscpolaczen = in.nextInt();
			pkt = new ArrayList<>();
			polaczenia = new ArrayList<>();
			
			while(in.hasNext()&&(!pom.equals("Punkty")))
			{pom=in.next();}
			
			for(int i=0;i<iloscpunktow;i++) {
				x=in.nextInt();
				y=in.nextInt();
				p = new Punkt(x, y);
				pkt.add(p);
			}
			
			while(in.hasNext()&&(!pom.equals("Polaczenia")))
			{pom=in.next();}
			
			for(int i=0;i<iloscpunktow;i++) {
				x=in.nextInt();
				y=in.nextInt();
				
				pol = new Polaczenia(pkt.get(x), pkt.get(x));
				polaczenia.add(pol);
			}
			
			
			
			in.close();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
	}
	
	
	
}
