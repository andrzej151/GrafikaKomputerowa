package Generowanie;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Grafika g = new Grafika(1000, 1000, "plik.bmp");
		g.procedura_szachownica(50, 45, 255, 255, 255, 0, 0, 255);
		g.zmien_nazwe_docelowego("plik1.bmp");
		g.procedura_rings(50);
		g.zmien_nazwe_docelowego("plik2.bmp");
		g.procedura_ringsRepate(10, 200, 200);
		g.zmien_nazwe_docelowego("plik3.bmp");
		g.procedura_rings_max();
		g.zmien_nazwe_docelowego("plik4.bmp");
		g.procedura_promien(15);
		g.zmien_nazwe_docelowego("plik5.bmp");
		g.procedura_pierscienie_rozmyte(10, 10);
		g.zmien_nazwe_docelowego("plik6.bmp");
		g.procedura_krata(5, 50, 100, 20, 20, 255, 255,255, 0,0,0);
		g.procedura_przepisz("lisc.bmp", "liscie.bmp");
		g.procedura_dorysuj_krate("lisc.bmp", "liscieZakrata.bmp",5,50,50,20,40,255,0,0);
		g.procedura_szachownica_polacz(50, 45, "lisc.bmp", "sam.bmp", "polaczszach.bmp");
		g.procedura_promien_polacz(15, "lisc.bmp", "sam.bmp", "polaczprom.bmp");
		g.procedura_pierscienie_rozmyte_polacz(20, 10,"lisc.bmp", "sam.bmp", "polaczrozm.bmp");
		
	
	}

}
