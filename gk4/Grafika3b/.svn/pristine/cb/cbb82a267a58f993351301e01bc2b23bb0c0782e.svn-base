package grafika3;

public class Matrix {
	private double[][] mac;
	
	Matrix() {
		mac = new double[4][4];
		mac[0][0] = 1;
		mac[1][1] = 1;
		mac[2][2] = 1;
		mac[3][3] = 1;
	}
	
	Matrix(double[] m) {
		mac = new double[4][4];
		for (int i = 0; i < 16; i++)
			mac[i / 4][i % 4] = m[i];
	}
	
	Matrix invert() {
		int n = 4;
		Matrix mac2 = new Matrix();
		double[] w = new double[n];
		
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				if (Math.abs(mac[i][j]) < 0.000001) mac[i][j] = 0;
		
		for (int k = 0; k < n; k++) {
			if (mac[k][k] == 0) {
				
				double[] wiersz, wiersz2;
				wiersz = mac[k];
				wiersz2 = mac2.mac[k];
				boolean zmiana = true;
				
				for (int i = k + 1; i < n && zmiana; i++)
					if (mac[i][k] != 0) {
						mac[k] = mac[i];
						mac[i] = wiersz;
						mac2.mac[k] = mac2.mac[i];
						mac2.mac[i] = wiersz2;
						zmiana = false;
					}
			}
			for (int i = 0; i < n; i++)
				w[i] = mac[i][k] / mac[k][k];
			
			w[k] = 0;
			
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++) {
					mac[i][j] -= mac[k][j] * w[i];
					mac2.mac[i][j] -= mac2.mac[k][j] * w[i];
				}
			w[k] = mac[k][k];
			
			for (int j = 0; j < n; j++) {
				mac[k][j] /= w[k];
				mac2.mac[k][j] /= w[k];
			}
			
		}
		mac = mac2.mac;
		return this;
	}
	
	Matrix multiple(Matrix mac2) {
		Matrix c = new Matrix();
		c.mac[0][0] = c.mac[1][1] = c.mac[2][2] = c.mac[3][3] = 0;
		
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				for (int k = 0; k < 4; k++)
					c.mac[i][j] += mac[i][k] * mac2.mac[k][j];
		return c;
		
	}
	
	static Matrix setIdentity() {
		return new Matrix();
	}
	
	static Matrix setTranslation(double x, double y, double z) {
		Matrix m = new Matrix();
		m.mac[3][0] = x;
		m.mac[3][1] = y;
		m.mac[3][2] = z;
		return m;
	}
	
	static Matrix setScale(double sx, double sy, double sz) {
		Matrix m = new Matrix();
		m.mac[0][0] = sx;
		m.mac[1][1] = sy;
		m.mac[2][2] = sz;
		return m;
	}
	
	static Matrix setOYRotation(double angle) {
		Matrix m = new Matrix();
		m.mac[0][0] = Math.cos(angle);
		m.mac[2][0] = Math.sin(angle);
		m.mac[0][2] = -Math.sin(angle);
		m.mac[2][2] = Math.cos(angle);
		return m;
	}
	
	static Matrix setOXRotation(double angle) {
		Matrix m = new Matrix();
		m.mac[1][1] = Math.cos(angle);
		m.mac[1][2] = Math.sin(angle);
		m.mac[2][1] = -Math.sin(angle);
		m.mac[2][2] = Math.cos(angle);
		return m;
	}
	
	static Matrix setOZRotation(double angle) {
		Matrix m = new Matrix();
		m.mac[0][0] = Math.cos(angle);
		m.mac[0][1] = Math.sin(angle);
		m.mac[1][0] = -Math.sin(angle);
		m.mac[1][1] = Math.cos(angle);
		return m;
	}
	
	double[] transformPoint(double[] _point) {
		double[] point = new double[3];
		point[0] = mac[0][0] * _point[0] + mac[1][0] * _point[1] + mac[2][0] * _point[2] + mac[3][0];
		point[1] = mac[0][1] * _point[0] + mac[1][1] * _point[1] + mac[2][1] * _point[2] + mac[3][1];
		point[2] = mac[0][2] * _point[0] + mac[1][2] * _point[1] + mac[2][2] * _point[2] + mac[3][2];
		return point;
	}
}