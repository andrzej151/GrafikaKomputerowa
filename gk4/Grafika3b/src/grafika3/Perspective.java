package grafika3;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Perspective extends JPanel {
	private static final long serialVersionUID = 1L;
	int PANEL_WIDTH = Main.PANEL_WIDTH;
	int PANEL_WHALF = PANEL_WIDTH / 2;
	int PANEL_HEIGHT = PANEL_WIDTH * 2 / 3;
	int PANEL_HHALF = PANEL_HEIGHT / 2;
	BufferedImage cimage;
	Scene scene;
	
	Perspective(Scene scene) {
		setLayout(null);
		this.scene = scene;
	}
	
	@Override
	public void paintComponent(Graphics gp) {
		super.paintComponent(gp);
		
		Graphics2D g2d = (Graphics2D) gp;
		setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		if (scene.initialized) {
			double[][] vertex = new double[scene.vertex.length][3];
			for (int i = 0; i < vertex.length; i++)
				vertex[i] = scene.matrix.transformPoint(scene.vertex[i]);
			double[] camera = scene.matrix.transformPoint(scene.camera);
			double d = Math.abs(camera[2]);
			for (int i = 0; i < vertex.length; i++) {
				double scale = d / (d + vertex[i][2]);
				vertex[i][0] = vertex[i][0] * scale;
				vertex[i][1] = vertex[i][1] * scale;
			}
			double coneWidth = d * Math.tan(Math.toRadians(scene.camera[6]));
			double scale = PANEL_WHALF / coneWidth;
			for (int i = 0; i < vertex.length; i++) {
				vertex[i][0] *= scale;
				vertex[i][1] *= scale;
			}
			
			createImage();
			g2d.drawImage(cimage, 0, 0, null);
		}
		
	}
	
	BufferedImage createImage() {
		cimage = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		double[][] vertex = new double[scene.vertex.length][3];
		for (int i = 0; i < vertex.length; i++)
			vertex[i] = scene.matrix.transformPoint(scene.vertex[i]);
		double[] camera = scene.matrix.transformPoint(scene.camera);
		double d = Math.abs(camera[2]);
		for (int i = 0; i < vertex.length; i++) {
			double scale = d / (d + vertex[i][2]);
			vertex[i][0] = vertex[i][0] * scale;
			vertex[i][1] = vertex[i][1] * scale;
		}
		double coneWidth = d * Math.tan(Math.toRadians(scene.camera[6]));
		double scale = PANEL_WHALF / coneWidth;
		for (int i = 0; i < vertex.length; i++) {
			vertex[i][0] *= scale;
			vertex[i][1] *= scale;
		}
		
		double[][] zbuffer = new double[PANEL_WIDTH][PANEL_HEIGHT];
		for (int i = 0; i < PANEL_WIDTH; i++)
			for (int j = 0; j < PANEL_HEIGHT; j++) {
				zbuffer[i][j] = Double.MAX_VALUE;
				cimage.setRGB(i, j, 0);
			}
		Polygon[] triangle = new Polygon[scene.triangle.length];
		for (int k = 0; k < triangle.length; k++) {
			triangle[k] = new Polygon();
			triangle[k]
					.addPoint(PANEL_WHALF + (int) vertex[(int) scene.triangle[k][0]][0], PANEL_HHALF - (int) vertex[(int) scene.triangle[k][0]][1]);
			triangle[k]
					.addPoint(PANEL_WHALF + (int) vertex[(int) scene.triangle[k][1]][0], PANEL_HHALF - (int) vertex[(int) scene.triangle[k][1]][1]);
			triangle[k]
					.addPoint(PANEL_WHALF + (int) vertex[(int) scene.triangle[k][2]][0], PANEL_HHALF - (int) vertex[(int) scene.triangle[k][2]][1]);
			
		}
		int minx2 = 0, miny2 = 0, maxx2 = 0, maxy2 = 0;
		double minx = PANEL_WHALF, miny = PANEL_HHALF, maxx = PANEL_WHALF, maxy = PANEL_HHALF;
		for (double[] element : vertex) {
			
			double p;
			if ((p = PANEL_WHALF + element[0]) > maxx) maxx = p;
			else if ((p = PANEL_WHALF + element[0]) < minx) minx = p;
			if ((p = PANEL_HHALF - element[1]) < miny) miny = p;
			else if ((p = PANEL_HHALF - element[1]) > maxy) maxy = p;
			if (maxx > PANEL_WIDTH) maxx = PANEL_WIDTH;
			if (maxy > PANEL_HEIGHT) maxy = PANEL_HEIGHT;
			if (minx < 0) minx = 0;
			if (miny < 0) miny = 0;
			minx2 = (int) Math.floor(minx);
			maxx2 = (int) Math.ceil(maxx);
			miny2 = (int) Math.floor(miny);
			maxy2 = (int) Math.ceil(maxy);
			
		}
		
		int[][] t = new int[scene.triangle.length][3];
		
		for (int k = 0; k < scene.triangle.length; k++) {
			scene.matrix.transformPoint(scene.light);
			
			double kd = scene.param[(int) scene.part[k]][3];
			double ks = scene.param[(int) scene.part[k]][4];
			double g = scene.param[(int) scene.part[k]][5];
			
			int surfcolor = getColorFromParam(k);
			
			t[k][0] = phong((int) scene.triangle[k][0], ks, kd, g, surfcolor, scene.normalvsurf[k]);
			t[k][1] = phong((int) scene.triangle[k][1], ks, kd, g, surfcolor, scene.normalvsurf[k]);
			t[k][2] = phong((int) scene.triangle[k][2], ks, kd, g, surfcolor, scene.normalvsurf[k]);
		}
		
		for (int i = minx2; i < maxx2; i++)
			for (int j = miny2; j < maxy2; j++)
				for (int k = 0; k < triangle.length; k++) {
					
					if (triangle[k].contains(i, j)) {
						double zz = getTriangleZ(vertex[(int) scene.triangle[k][0]], vertex[(int) scene.triangle[k][1]],
								vertex[(int) scene.triangle[k][2]], i, j);
						if (zz < zbuffer[i][j]) {
							zbuffer[i][j] = zz;
							
							double[] v0 = vertex[(int) scene.triangle[k][0]].clone();
							double[] v1 = vertex[(int) scene.triangle[k][1]].clone();
							double[] v2 = vertex[(int) scene.triangle[k][2]].clone();
							
							v0[2] = t[k][0] >> 16 & 255;
							v1[2] = t[k][1] >> 16 & 255;
							v2[2] = t[k][2] >> 16 & 255;
							int red = (int) getTriangleZ(v0, v1, v2, i, j);
							
							v0[2] = t[k][0] >> 8 & 255;
							v1[2] = t[k][1] >> 8 & 255;
							v2[2] = t[k][2] >> 8 & 255;
							int green = (int) getTriangleZ(v0, v1, v2, i, j);
							
							v0[2] = t[k][0] & 255;
							v1[2] = t[k][1] & 255;
							v2[2] = t[k][2] & 255;
							int blue = (int) getTriangleZ(v0, v1, v2, i, j);
							
							cimage.setRGB(i, j, 255 << 24 | (red & 255) << 16 | (green & 255) << 8 | blue & 255);
						}
					}
				}
		return cimage;
	}
	
	double getTriangleZ(double[] v0, double[] v1, double[] v2, int x, int y) {
		double[][] v = new double[3][3];
		
		v[0][0] = PANEL_WHALF + v0[0];
		v[0][1] = PANEL_HHALF - v0[1];
		v[0][2] = v0[2];
		
		v[1][0] = PANEL_WHALF + v1[0];
		v[1][1] = PANEL_HHALF - v1[1];
		v[1][2] = v1[2];
		
		v[2][0] = PANEL_WHALF + v2[0];
		v[2][1] = PANEL_HHALF - v2[1];
		v[2][2] = v2[2];
		
		double A = v[0][1] * v[1][2] - v[0][1] * v[2][2] - v[1][1] * v[0][2] + v[1][1] * v[2][2] + v[2][1] * v[0][2] - v[2][1] * v[1][2];
		double B = v[0][0] * v[2][2] - v[0][0] * v[1][2] + v[1][0] * v[0][2] - v[1][0] * v[2][2] - v[2][0] * v[0][2] + v[2][0] * v[1][2];
		double C = v[0][0] * v[1][1] - v[0][0] * v[2][1] - v[1][0] * v[0][1] + v[1][0] * v[2][1] - v[2][0] * v[1][1] + v[2][0] * v[0][1];
		double D = -v[0][0] * v[1][1] * v[2][2] + v[0][0] * v[2][1] * v[1][2] + v[1][0] * v[0][1] * v[2][2] - v[1][0] * v[2][1] * v[0][2] - v[2][0]
				* v[0][1] * v[1][2] + v[2][0] * v[1][1] * v[0][2];
		return -(A * x + B * y + D) / C;
	}
	
	int getColorFromParam(int number) {
		double[] param = scene.param[(int) scene.part[number]];
		return 255 << 24 | ((int) param[0] & 255) << 16 | ((int) param[1] & 255) << 8 | (int) param[2] & 255;
	}
	
	double dotProduct(double[] v1, double[] v2) {
		return v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2];
	}
	
	int phong(int k, double ks, double kd, double g, int materialcolor, double[] normal) {
		double[] light = scene.lightvvert[k].clone();
		light = normalize(light);
		vectorLength(scene.lightvvert[k]);
		double[] cam = scene.camera.clone();
		cam[0] -= scene.vertex[k][0];
		cam[1] -= scene.vertex[k][1];
		cam[2] -= scene.vertex[k][2];
		vectorLength(cam);
		cam = normalize(cam);
		double[] h = cam.clone();
		h[0] += light[0];
		h[1] += light[1];
		h[2] += light[2];
		h = normalize(h);
		double[] normalv = scene.normalvvert[k].clone();
		double ndotl = dotProduct(normalv, light);
		
		double hdotn = dotProduct(normalv, h);
		
		ndotl = ndotl < 0.001 ? 0 : ndotl * kd;
		hdotn = hdotn < 0.001 ? 0 : Math.pow(hdotn, g) * ks;
		
		int rm = materialcolor >> 16 & 255;
		int gm = materialcolor >> 8 & 255;
		int bm = materialcolor & 255;
		
		int red = (int) ((ndotl * rm + hdotn * scene.light[3]) * scene.light[3] / 255 + scene.ambient * rm);
		int green = (int) ((ndotl * gm + hdotn * scene.light[4]) * scene.light[4] / 255 + scene.ambient * gm);
		int blue = (int) ((ndotl * bm + hdotn * scene.light[5]) * scene.light[5] / 255 + scene.ambient * bm);
		
		if (red > 255) red = 255;
		if (green > 255) green = 255;
		if (blue > 255) blue = 255;
		
		return 255 << 24 | (red & 255) << 16 | (green & 255) << 8 | blue & 255;
	}
	
	double vectorLength(double[] vector) {
		return Math.sqrt(vector[0] * vector[0] + vector[1] * vector[1] + vector[2] * vector[2]);
	}
	
	double[] normalize(double[] vector) {
		double length = vectorLength(vector);
		vector[0] /= length;
		vector[1] /= length;
		vector[2] /= length;
		return vector;
	}
}