package grafika3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Scene {
	boolean initialized;
	boolean voutside;
	
	double[][] vertex;
	double[][] triangle;
	double[][] param;
	double[][] normalvsurf;
	double[][] normalvvert;
	double[][] lightvvert;
	
	double[] part;
	double[] light;
	double[] camera;
	double[] lefttop;
	double[] righttop;
	double[] leftbottom;
	double[] rightbottom;
	
	double ambient;
	
	String camfile;
	MyFrame2 frame;
	Matrix matrix;
	Matrix inverted;
	
	Scene(MyFrame2 _frame) {
		frame = _frame;
		initialized = false;
	}
	
	void load(String filename) {
		try {
			voutside = false;
			StreamTokenizer input = new StreamTokenizer(new BufferedReader(new FileReader(filename)));
			input.nextToken();
			int n = (int) input.nval;
			vertex = new double[n][3];
			input.nextToken();
			for (int i = 0; i < n; i++) {
				
				vertex[i][0] = input.nval;
				input.nextToken();
				vertex[i][1] = input.nval;
				input.nextToken();
				vertex[i][2] = input.nval;
				input.nextToken();
				
			}
			n = (int) input.nval;
			input.nextToken();
			triangle = new double[n][3];
			part = new double[n];
			for (int i = 0; i < n; i++) {
				triangle[i][2] = input.nval;
				input.nextToken();
				triangle[i][1] = input.nval;
				input.nextToken();
				triangle[i][0] = input.nval;
				input.nextToken();
				
				if (voutside) {
					double temp = triangle[i][0];
					triangle[i][0] = triangle[i][2];
					triangle[i][2] = temp;
					
				}
			}
			for (int i = 0; i < n; i++) {
				part[i] = input.nval;
				input.nextToken();
			}
			n = (int) input.nval;
			param = new double[n][6];
			input.nextToken();
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < 6; j++) {
					param[i][j] = input.nval;
					input.nextToken();
				}
			}
			light = new double[6];
			for (int i = 0; i < 6; i++) {
				light[i] = input.nval;
				input.nextToken();
			}
			camfile = new String();
			camfile = filename.replaceAll(".brp", ".cam");
			input = new StreamTokenizer(new BufferedReader(new FileReader(camfile)));
			input.nextToken();
			camera = new double[7];
			for (int i = 0; i < 7; i++) {
				camera[i] = input.nval;
				input.nextToken();
			}
			ambient = 0.5;
			frame.sliderAnglestate = (int) camera[6];
			frame.sliderAmbientstate = (int) (100 * ambient);
			
			frame.updateTextBoxes(camera, ambient);
			
			normalvsurf = new double[triangle.length][3];
			for (int k = 0; k < normalvsurf.length; k++) {
				double[] v1 = vertex[(int) triangle[k][0]].clone();
				double[] v2 = vertex[(int) triangle[k][1]].clone();
				double[] v3 = vertex[(int) triangle[k][2]].clone();
				normalvsurf[k] = normalsvector(v1, v2, v3);
			}
			normalvvert = new double[vertex.length][3];
			for (int i = 0; i < normalvvert.length; i++) {
				double vlength = 0;
				for (int j = 0; j < triangle.length; j++)
					if (triangle[j][0] == i || triangle[j][1] == i || triangle[j][2] == i) {
						normalvvert[i][0] += normalvsurf[j][0];
						normalvvert[i][1] += normalvsurf[j][1];
						normalvvert[i][2] += normalvsurf[j][2];
						vlength += vectorLength(normalvsurf[j]);
						
					}
				normalvvert[i][0] /= vlength;
				normalvvert[i][1] /= vlength;
				normalvvert[i][2] /= vlength;
				normalvvert[i] = normalize(normalvvert[i]);
				
			}
			lightvvert = new double[vertex.length][3];
			for (int i = 0; i < lightvvert.length; i++) {
				lightvvert[i][0] = light[0] - vertex[i][0];
				lightvvert[i][1] = light[1] - vertex[i][1];
				lightvvert[i][2] = light[2] - vertex[i][2];
			}
			initialized = true;
		}
		catch (FileNotFoundException ex) {
			System.out.print("Error - Can't find file");
		}
		catch (IOException ex) {
			System.out.print("Error - Can't read from input");
		}
	}
	
	void save() {
		try (PrintWriter output = new PrintWriter(new BufferedWriter(new FileWriter(camfile)))) {
			output.print("" + camera[0] + " " + camera[1] + " " + camera[2] + "\n");
			output.print("" + camera[3] + " " + camera[4] + " " + camera[5] + "\n");
			output.print(camera[6]);
		}
		catch (IOException ex) {
			System.out.print("Can't write to output");
		}
	}
	
	void setObx(double param) {
		camera[0] = param;
		frame.updateTextBoxes(camera, ambient);
	}
	
	void setOby(double param) {
		camera[1] = param;
		frame.updateTextBoxes(camera, ambient);
	}
	
	void setObz(double param) {
		camera[2] = param;
		frame.updateTextBoxes(camera, ambient);
	}
	
	void setCtx(double param) {
		camera[3] = param;
		frame.updateTextBoxes(camera, ambient);
	}
	
	void setCty(double param) {
		camera[4] = param;
		frame.updateTextBoxes(camera, ambient);
	}
	
	void setCtz(double param) {
		camera[5] = param;
		frame.updateTextBoxes(camera, ambient);
	}
	
	void setAngle(int angle) {
		camera[6] = angle;
	}
	
	void setAmbient(int amb) {
		ambient = amb / 100.;
		frame.updateTextBoxes(camera, ambient);
	}
	
	void createImage() {
		double[] cam = new double[3];
		cam[0] = camera[0];
		cam[1] = camera[1];
		cam[2] = camera[2];
		
		double[] cam2 = new double[3];
		cam2[0] = cam[0];
		cam2[1] = camera[1];
		cam2[2] = camera[2];
		
		matrix = new Matrix();
		matrix = matrix.multiple(Matrix.setTranslation(-camera[3], -camera[4], -camera[5]));
		cam = matrix.transformPoint(cam);
		matrix = matrix.multiple(Matrix.setOYRotation(Math.PI - Math.atan2(cam[0], cam[2])));
		cam2 = matrix.transformPoint(cam2);
		matrix = matrix.multiple(Matrix.setOXRotation(-Math.atan2(cam2[2], cam2[1]) - Math.PI / 2));
		
		inverted = matrix.multiple(new Matrix());
		inverted.invert();
		setCone();
	}
	
	double[] normalize(double[] vector) {
		double length = vectorLength(vector);
		vector[0] /= length;
		vector[1] /= length;
		vector[2] /= length;
		return vector;
	}
	
	double vectorLength(double[] vector) {
		return Math.sqrt(vector[0] * vector[0] + vector[1] * vector[1] + vector[2] * vector[2]);
	}
	
	double[] normalsvector(double[] v1, double[] v2, double[] v3) {
		v1[0] -= v2[0];
		v1[1] -= v2[1];
		v1[2] -= v2[2];
		v3[0] -= v2[0];
		v3[1] -= v2[1];
		v3[2] -= v2[2];
		return CrossProduct(v1, v3);
	}
	
	double[] CrossProduct(double[] a, double[] b) {
		double[] vector = new double[3];
		vector[0] = a[1] * b[2] - a[2] * b[1];
		vector[1] = a[2] * b[0] - a[0] * b[2];
		vector[2] = a[0] * b[1] - a[1] * b[0];
		return vector;
	}
	
	void setCone() {
		double coneWidth = Math.sqrt(Math.pow(camera[0] - camera[3], 2) + Math.pow(camera[1] - camera[4], 2) + Math.pow(camera[2] - camera[5], 2))
				* Math.tan(Math.toRadians(camera[6]));
		double[] point = new double[3];
		point[0] = -coneWidth;
		point[1] = -coneWidth * 2 / 3;
		point[2] = 0;
		lefttop = inverted.transformPoint(point);
		point[1] *= -1;
		leftbottom = inverted.transformPoint(point);
		point[0] *= -1;
		rightbottom = inverted.transformPoint(point);
		point[1] *= -1;
		righttop = inverted.transformPoint(point);
	}
}