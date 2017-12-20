package gkb2;

public class TransformationMatrix {
	
	private double tab[][];
	
	TransformationMatrix() {
		tab = new double[3][3];
	}
	
	TransformationMatrix(double mac[][]) {
		if (mac.length == mac[0].length && mac.length == 3) tab = mac;
		else throw new IllegalArgumentException("Dopuszczalne wymiary to 3x3!");
	}
	
	void pomnoz(TransformationMatrix macierz) {
		if (macierz.tab.length != tab[1].length || tab.length != 3) throw new IllegalArgumentException("Dopuszczalne wymiary to 3x3!");
		
		double mac[][] = new double[tab.length][macierz.tab[1].length];
		
		for(int i = 0; i < mac.length; i++)
			for(int j = 0; j < mac[0].length; j++)
				for(int x = 0; x < tab[1].length; x++)
					mac[i][j] += tab[i][x] * macierz.tab[x][j];
		
		tab = mac;
	}
	
	static TransformationMatrix macierzDiagonalna() {
		double mac[][] = new double[3][3];
		
		mac[0][0] = 1.0D;
		mac[1][1] = 1.0D;
		mac[2][2] = 1.0D;
		
		return new TransformationMatrix(mac);
	}
	
	static TransformationMatrix pomnozSkalarnie(TransformationMatrix mac, double x) {
		TransformationMatrix a = new TransformationMatrix();
		a.tab = mac.tab.clone();
		
		for(int i = 0; i < mac.tab[0].length; i++)
			for(int j = 0; j < mac.tab.length; j++)
				a.tab[i][j] *= x;
		
		return a;
	}
	
	static TransformationMatrix macierzObrotu(double angle) {
		double mac[][] = new double[3][3];
		
		double sin = Math.abs(Math.sin(angle)) > 6.1232339957367662E-014D ? Math.sin(angle) : 0.0D;
		double cos = Math.abs(Math.cos(angle)) > 6.1232339957367662E-014D ? Math.cos(angle) : 0.0D;
		
		mac[0][0] = cos;
		mac[0][1] = sin;
		mac[1][0] = -sin;
		mac[1][1] = cos;
		mac[2][0] = 0.D;
		mac[2][1] = 0.D;
		mac[2][2] = 1.0D;
		
		return new TransformationMatrix(mac);
	}
	
	static TransformationMatrix macierzPrzesuniecia(double x, double y) {
		double mac[][] = new double[3][3];
		
		mac[0][0] = 1.0D;
		mac[1][1] = 1.0D;
		mac[2][0] = x;
		mac[2][1] = y;
		mac[2][2] = 1.0D;
		
		return new TransformationMatrix(mac);
	}
	
	static TransformationMatrix macierzSkalowania(double a, double d) {
		double mac[][] = new double[3][3];
		
		mac[0][0] = a;
		mac[1][1] = d;
		mac[2][2] = 1.0D;
		
		return new TransformationMatrix(mac);
	}
	
	static TransformationMatrix macierzDopelnienia(TransformationMatrix a) {
		if (a.tab.length != a.tab[1].length || a.tab.length != 3) throw new RuntimeException();
		double mac[][] = new double[3][3];
		
		mac[0][0] = a.tab[1][1] * a.tab[2][2] - a.tab[1][2] * a.tab[2][1];
		mac[0][1] = -(a.tab[1][0] * a.tab[2][2] - a.tab[1][2] * a.tab[2][0]);
		mac[0][2] = a.tab[1][0] * a.tab[2][1] - a.tab[1][1] * a.tab[2][0];
		mac[1][0] = -(a.tab[0][1] * a.tab[2][2] - a.tab[0][2] * a.tab[2][1]);
		mac[1][1] = a.tab[0][0] * a.tab[2][2] - a.tab[0][2] * a.tab[2][0];
		mac[1][2] = -(a.tab[0][0] * a.tab[2][1] - a.tab[0][1] * a.tab[2][0]);
		mac[2][0] = a.tab[0][1] * a.tab[1][2] - a.tab[0][2] * a.tab[1][1];
		mac[2][1] = -(a.tab[0][0] * a.tab[1][2] - a.tab[0][2] * a.tab[1][0]);
		mac[2][2] = a.tab[0][0] * a.tab[1][1] - a.tab[0][1] * a.tab[1][0];
		
		return new TransformationMatrix(mac);
	}
	
	static TransformationMatrix macierzTransponowana(TransformationMatrix a) {
		double mac[][] = new double[a.tab[0].length][a.tab.length];
		
		for(int i = 0; i < mac[0].length; i++)
			for(int j = 0; j < mac.length; j++)
				mac[i][j] = a.tab[j][i];
		
		return new TransformationMatrix(mac);
	}
	
	static TransformationMatrix macierzOdwrotna(TransformationMatrix a) {
		return TransformationMatrix.pomnozSkalarnie(TransformationMatrix.macierzTransponowana(TransformationMatrix.macierzDopelnienia(a)), 1.0D / TransformationMatrix.wyznacznik(a));
	}
	
	static double wyznacznik(TransformationMatrix a) {
		return (a.tab[0][0] * a.tab[1][1] * a.tab[2][2] + a.tab[0][1] * a.tab[1][2] * a.tab[2][0] + a.tab[0][2] * a.tab[1][0] * a.tab[2][1]) - a.tab[0][2] * a.tab[1][1] * a.tab[2][0] - a.tab[0][0]
				* a.tab[1][2] * a.tab[2][1] - a.tab[0][1] * a.tab[1][0] * a.tab[2][2];
	}
	
	void przeksztalcPoint(Point a) {
		double x = a.x * tab[0][0] + a.y * tab[1][0] + tab[2][0];
		double y = a.x * tab[0][1] + a.y * tab[1][1] + tab[2][1];
		double w = a.x * tab[0][2] + a.y * tab[1][2] + tab[2][2];
		
		if (w != 1.0D) {
			x /= w;
			y /= w;
		}
		
		a.x = x;
		a.y = y;
	}
	
	void drukujMacierz() {
		System.out.println("Obecna macierz przekszta³ceñ:\n---------------");
		
		for(double[] element: tab) {
			for(int j = 0; j < tab[1].length; j++)
				System.out.printf("%3.2f ", element[j]);
			System.out.println();
		}
		
		System.out.println("---------------");
	}
	
	double[][] dajMacierz() {
		return tab.clone();
	}
}