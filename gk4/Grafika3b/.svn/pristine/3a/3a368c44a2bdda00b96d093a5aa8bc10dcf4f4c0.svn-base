package grafika3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Orto extends JPanel implements MouseMotionListener, MouseListener {
	private static final long serialVersionUID = 1L;
	
	int PANEL_WIDTH = Main.PANEL_WIDTH;
	int PANEL_WHALF = PANEL_WIDTH / 2;
	int PANEL_HEIGHT = PANEL_WIDTH * 2 / 3;
	int PANEL_HHALF = PANEL_HEIGHT / 2;
	int hor, ver, dep;
	double w = 0;
	boolean caught, caught2;
	Scene scene;
	BufferedImage cimage;
	
	Orto(Scene _scene, String orientation) {
		setLayout(null);
		scene = _scene;
		
		if (orientation.equals("xy")) {
			hor = 0;
			ver = 1;
			dep = 2;
		}
		else if (orientation.equals("yz")) {
			hor = 2;
			ver = 1;
			dep = 0;
		}
		else {
			hor = 0;
			ver = 2;
			dep = 1;
		}
		
		addMouseMotionListener(this);
		addMouseListener(this);
		caught = caught2 = false;
	}
	
	@Override
	public void paintComponent(Graphics gp) {
		super.paintComponent(gp);
		setBounds(PANEL_WHALF * (2 - hor), PANEL_HEIGHT * (2 - ver), PANEL_WIDTH, PANEL_HEIGHT);
		Graphics2D g2d = (Graphics2D) gp;
		if (scene.initialized) {
			g2d.drawImage(cimage, 0, 0, null);
			g2d.setColor(Color.yellow);
			g2d.fillOval((int) (PANEL_WHALF + scene.light[hor] * w - 5), (int) (PANEL_HHALF - scene.light[ver] * w - 5), 10, 10);
			
			g2d.setColor(Color.black);
			Rectangle2D.Double rect = new Rectangle2D.Double(PANEL_WHALF + scene.camera[hor] * w - 10, PANEL_HHALF - scene.camera[ver] * w - 10, 20,
					20);
			g2d.draw(rect);
			rect = new Rectangle2D.Double(PANEL_WHALF + scene.camera[hor + 3] * w - 5, PANEL_HHALF - scene.camera[ver + 3] * w - 5, 10, 10);
			g2d.draw(rect);
			
			scene.setCone();
			g2d.draw(new Line2D.Double(PANEL_WHALF + scene.lefttop[hor] * w, PANEL_HHALF - scene.lefttop[ver] * w, PANEL_WHALF
					+ scene.leftbottom[hor] * w, PANEL_HHALF - scene.leftbottom[ver] * w));
			g2d.draw(new Line2D.Double(PANEL_WHALF + scene.lefttop[hor] * w, PANEL_HHALF - scene.lefttop[ver] * w, PANEL_WHALF + scene.righttop[hor]
					* w, PANEL_HHALF - scene.righttop[ver] * w));
			g2d.draw(new Line2D.Double(PANEL_WHALF + scene.leftbottom[hor] * w, PANEL_HHALF - scene.leftbottom[ver] * w, PANEL_WHALF
					+ scene.rightbottom[hor] * w, PANEL_HHALF - scene.rightbottom[ver] * w));
			g2d.draw(new Line2D.Double(PANEL_WHALF + scene.righttop[hor] * w, PANEL_HHALF - scene.righttop[ver] * w, PANEL_WHALF
					+ scene.rightbottom[hor] * w, PANEL_HHALF - scene.rightbottom[ver] * w));
			g2d.draw(new Line2D.Double(PANEL_WHALF + scene.lefttop[hor] * w, PANEL_HHALF - scene.lefttop[ver] * w, PANEL_WHALF + scene.camera[hor]
					* w, PANEL_HHALF - scene.camera[ver] * w));
			g2d.draw(new Line2D.Double(PANEL_WHALF + scene.leftbottom[hor] * w, PANEL_HHALF - scene.leftbottom[ver] * w, PANEL_WHALF
					+ scene.camera[hor] * w, PANEL_HHALF - scene.camera[ver] * w));
			g2d.draw(new Line2D.Double(PANEL_WHALF + scene.righttop[hor] * w, PANEL_HHALF - scene.righttop[ver] * w, PANEL_WHALF + scene.camera[hor]
					* w, PANEL_HHALF - scene.camera[ver] * w));
			g2d.draw(new Line2D.Double(PANEL_WHALF + scene.rightbottom[hor] * w, PANEL_HHALF - scene.rightbottom[ver] * w, PANEL_WHALF
					+ scene.camera[hor] * w, PANEL_HHALF - scene.camera[ver] * w));
			
			String panelname = new String();
			if (dep == 2) panelname = new String("Przod");
			else if (dep == 1) panelname = new String("Gora");
			else panelname = new String("Prawo");
			g2d.drawString(panelname, 10, 10);
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (caught) updateCameraPosition(arg0.getX(), arg0.getY());
		else if (caught2) updateCameraPosition2(arg0.getX(), arg0.getY());
	}
	
	@Override
	public void mouseMoved(MouseEvent arg0) {}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		if (cameraCaught(arg0.getX(), arg0.getY())) caught = true;
		else if (cameraCaught2(arg0.getX(), arg0.getY())) caught2 = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (caught) caught = false;
		else if (caught2) caught2 = false;
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	
	@Override
	public void mouseExited(MouseEvent arg0) {
		caught = false;
		caught2 = false;
	}
	
	boolean cameraCaught(double x, double y) {
		if (Math.abs(x - PANEL_WHALF - scene.camera[hor] * w) < 10 && Math.abs(PANEL_HHALF - y - scene.camera[ver] * w) < 10) caught = true;
		else caught = false;
		return caught;
		
	}
	
	boolean cameraCaught2(double x, double y) {
		if (Math.abs(x - PANEL_WHALF - scene.camera[hor + 3] * w) < 5 && Math.abs(PANEL_HHALF - y - scene.camera[ver + 3] * w) < 5) caught2 = true;
		else caught2 = false;
		return caught2;
	}
	
	void updateCameraPosition(double x, double y) {
		if (hor == 0) scene.setObx((int) (1000 * (x - PANEL_WHALF) / w) / 1000.);
		if (hor == 2) scene.setObz((int) (1000 * (x - PANEL_WHALF) / w) / 1000.);
		if (ver == 1) scene.setOby((int) (1000 * (PANEL_HHALF - y) / w) / 1000.);
		if (ver == 2) scene.setObz((int) (1000 * (PANEL_HHALF - y) / w) / 1000.);
		scene.createImage();
	}
	
	void updateCameraPosition2(double x, double y) {
		if (hor == 0) scene.setCtx((int) (1000 * (x - PANEL_WHALF) / w) / 1000.);
		if (hor == 2) scene.setCtz((int) (1000 * (x - PANEL_WHALF) / w) / 1000.);
		if (ver == 1) scene.setCty((int) (1000 * (PANEL_HHALF - y) / w) / 1000.);
		if (ver == 2) scene.setCtz((int) (1000 * (PANEL_HHALF - y) / w) / 1000.);
		scene.createImage();
	}
	
	double setW() {
		double width = 0;
		for (double[] element : scene.vertex) {
			if (Math.abs(element[hor]) > width) width = Math.abs(element[hor]);
			if (Math.abs(element[ver] * 3 / 2) > width) width = Math.abs(element[ver] * 3 / 2);
		}
		
		if (Math.abs(scene.light[hor]) > width) width = Math.abs(scene.light[hor]);
		if (Math.abs(scene.light[ver] * 3 / 2) > width) width = Math.abs(scene.light[hor] * 3 / 2);
		if (Math.abs(scene.camera[hor]) > width) width = Math.abs(scene.camera[hor]);
		if (Math.abs(scene.camera[ver] * 3 / 2) > width) width = Math.abs(scene.camera[ver] * 3 / 2);
		if (Math.abs(scene.lefttop[hor]) > width) width = Math.abs(scene.lefttop[hor]);
		if (Math.abs(scene.lefttop[ver] * 3 / 2) > width) width = Math.abs(scene.lefttop[ver] * 3 / 2);
		if (Math.abs(scene.righttop[hor]) > width) width = Math.abs(scene.righttop[hor]);
		if (Math.abs(scene.righttop[ver] * 3 / 2) > width) width = Math.abs(scene.righttop[ver] * 3 / 2);
		if (Math.abs(scene.leftbottom[hor]) > width) width = Math.abs(scene.leftbottom[hor]);
		if (Math.abs(scene.leftbottom[ver] * 3 / 2) > width) width = Math.abs(scene.leftbottom[ver] * 3 / 2);
		if (Math.abs(scene.rightbottom[hor]) > width) width = Math.abs(scene.rightbottom[hor]);
		if (Math.abs(scene.rightbottom[ver] * 3 / 2) > width) width = Math.abs(scene.rightbottom[ver] * 3 / 2);
		
		width *= 1.25;
		w = PANEL_WHALF / width;
		return w;
		
	}
	
	BufferedImage createImage() {
		
		double temp = w;
		setW();
		if (Math.abs(w - temp) < 0.001) return cimage;
		
		cimage = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		double[][] zbuffer = new double[PANEL_WIDTH][PANEL_HEIGHT];
		
		for (int i = 0; i < PANEL_WIDTH; i++)
			for (int j = 0; j < PANEL_HEIGHT; j++) {
				zbuffer[i][j] = Double.MAX_VALUE;
				cimage.setRGB(i, j, 0);
			}
		
		Polygon[] triangle = new Polygon[scene.triangle.length];
		for (int k = 0; k < triangle.length; k++) {
			triangle[k] = new Polygon();
			triangle[k].addPoint(PANEL_WHALF + (int) (scene.vertex[(int) scene.triangle[k][0]][hor] * w), PANEL_HHALF
					- (int) (scene.vertex[(int) scene.triangle[k][0]][ver] * w));
			triangle[k].addPoint(PANEL_WHALF + (int) (scene.vertex[(int) scene.triangle[k][1]][hor] * w), PANEL_HHALF
					- (int) (scene.vertex[(int) scene.triangle[k][1]][ver] * w));
			triangle[k].addPoint(PANEL_WHALF + (int) (scene.vertex[(int) scene.triangle[k][2]][hor] * w), PANEL_HHALF
					- (int) (scene.vertex[(int) scene.triangle[k][2]][ver] * w));
			
		}
		int minx2 = 0, miny2 = 0, maxx2 = 0, maxy2 = 0;
		double minx = PANEL_WHALF, miny = PANEL_HHALF, maxx = PANEL_WHALF, maxy = PANEL_HHALF;
		for (double[] element : scene.vertex) {
			
			double p;
			if ((p = PANEL_WHALF + element[hor] * w) > maxx) maxx = p;
			else if ((p = PANEL_WHALF + element[hor] * w) < minx) minx = p;
			if ((p = PANEL_HHALF - element[ver] * w) < miny) miny = p;
			else if ((p = PANEL_HHALF - element[ver] * w) > maxy) maxy = p;
			if (maxx > PANEL_WIDTH) maxx = PANEL_WIDTH;
			if (maxy > PANEL_HEIGHT) maxy = PANEL_HEIGHT;
			if (minx < 0) minx = 0;
			if (miny < 0) miny = 0;
			minx2 = (int) Math.floor(minx);
			maxx2 = (int) Math.ceil(maxx);
			miny2 = (int) Math.floor(miny);
			maxy2 = (int) Math.ceil(maxy);
		}
		
		for (int i = minx2; i < maxx2; i++)
			for (int j = miny2; j < maxy2; j++)
				for (int k = 0; k < triangle.length; k++)
					if (triangle[k].contains(i, j)) {
						double zz = getTriangleZ(k, i, j);
						if (zz < zbuffer[i][j]) {
							zbuffer[i][j] = zz;
							cimage.setRGB(i, j, getColorFromParam(k));
						}
					}
		return cimage;
	}
	
	double getTriangleZ(int k, int x, int y) {
		double[][] v = new double[3][3];
		
		v[0][hor] = PANEL_WHALF + scene.vertex[(int) scene.triangle[k][0]][hor] * w;
		v[0][ver] = PANEL_HHALF - scene.vertex[(int) scene.triangle[k][0]][ver] * w;
		v[0][dep] = scene.vertex[(int) scene.triangle[k][0]][dep] * w;
		if (dep == 1) v[0][dep] *= -1;
		
		v[1][hor] = PANEL_WHALF + scene.vertex[(int) scene.triangle[k][1]][hor] * w;
		v[1][ver] = PANEL_HHALF - scene.vertex[(int) scene.triangle[k][1]][ver] * w;
		v[1][dep] = scene.vertex[(int) scene.triangle[k][1]][dep] * w;
		if (dep == 1) v[1][dep] *= -1;
		
		v[2][hor] = PANEL_WHALF + scene.vertex[(int) scene.triangle[k][2]][hor] * w;
		v[2][ver] = PANEL_HHALF - scene.vertex[(int) scene.triangle[k][2]][ver] * w;
		v[2][dep] = scene.vertex[(int) scene.triangle[k][2]][dep] * w;
		if (dep == 1) v[2][dep] *= -1;
		
		double A = v[0][ver] * v[1][dep] - v[0][ver] * v[2][dep] - v[1][ver] * v[0][dep] + v[1][ver] * v[2][dep] + v[2][ver] * v[0][dep] - v[2][ver]
				* v[1][dep];
		double B = v[0][hor] * v[2][dep] - v[0][hor] * v[1][dep] + v[1][hor] * v[0][dep] - v[1][hor] * v[2][dep] - v[2][hor] * v[0][dep] + v[2][hor]
				* v[1][dep];
		double C = v[0][hor] * v[1][ver] - v[0][hor] * v[2][ver] - v[1][hor] * v[0][ver] + v[1][hor] * v[2][ver] - v[2][hor] * v[1][ver] + v[2][hor]
				* v[0][ver];
		double D = -v[0][hor] * v[1][ver] * v[2][dep] + v[0][hor] * v[2][ver] * v[1][dep] + v[1][hor] * v[0][ver] * v[2][dep] - v[1][hor] * v[2][ver]
				* v[0][dep] - v[2][hor] * v[0][ver] * v[1][dep] + v[2][hor] * v[1][ver] * v[0][dep];
		return -(A * x + B * y + D) / C;
	}
	
	int getColorFromParam(int number) {
		double[] param = scene.param[(int) scene.part[number]];
		return 255 << 24 | ((int) param[0] & 255) << 16 | ((int) param[1] & 255) << 8 | (int) param[2] & 255;
	}
}