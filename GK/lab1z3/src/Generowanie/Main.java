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
		
	
	}

}
