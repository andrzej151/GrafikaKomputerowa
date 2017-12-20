package grafika3;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Main {
	static int PANEL_WIDTH = 400;

	public static void main(String[] args) {
		new MyFrame2("Grafika 3");
	}
}

class MyFrame2 extends JFrame implements ActionListener, ChangeListener {
	private static final long serialVersionUID = 1L;
	int PANEL_WIDTH = Main.PANEL_WIDTH;
	int PANEL_WHALF = PANEL_WIDTH / 2;
	int PANEL_HEIGHT = PANEL_WIDTH * 2 / 3;
	int PANEL_HHALF = PANEL_HEIGHT / 2;
	int sliderAnglestate, sliderAmbientstate;
	Perspective pvp;
	Orto dvp;
	Orto lvp;
	Orto fvp;
	ButtonPanel bp;
	JButton buttonLoad, buttonSave;
	JSlider sliderAngle, sliderAmbient;
	JTextField obx, oby, obz, ctx, cty, ctz;
	Scene scene;
	final JFileChooser fc;

	MyFrame2(String title) {
		super(title);
		try {
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		scene = new Scene(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 2 * PANEL_WIDTH + 250, 2 * PANEL_HEIGHT + 35);
		setLayout(new GridLayout());
		bp = new ButtonPanel();
		fvp = new Orto(scene, "xy");
		lvp = new Orto(scene, "yz");
		dvp = new Orto(scene, "xz");
		pvp = new Perspective(scene);
		fvp.setBorder(BorderFactory.createLineBorder(Color.black));
		lvp.setBorder(BorderFactory.createLineBorder(Color.black));
		dvp.setBorder(BorderFactory.createLineBorder(Color.black));
		pvp.setBorder(BorderFactory.createLineBorder(Color.black));
		getContentPane().add(bp);
		getContentPane().add(fvp);
		getContentPane().add(lvp);
		getContentPane().add(dvp);
		getContentPane().add(pvp);
		buttonLoad = new JButton("Wczytaj");
		buttonSave = new JButton("Zapisz");
		buttonLoad.setBounds(20, 20, 210, 40);
		buttonSave.setBounds(20, 70, 210, 40);
		obx = new JTextField();
		oby = new JTextField();
		obz = new JTextField();
		ctx = new JTextField();
		cty = new JTextField();
		ctz = new JTextField();
		obx.setBounds(25, 135, 55, 20);
		oby.setBounds(95, 135, 55, 20);
		obz.setBounds(165, 135, 55, 20);
		ctx.setBounds(25, 175, 55, 20);
		cty.setBounds(95, 175, 55, 20);
		ctz.setBounds(165, 175, 55, 20);
		sliderAngle = new JSlider(SwingConstants.VERTICAL, 1, 89, 1);
		sliderAmbient = new JSlider(SwingConstants.VERTICAL, 0, 100, 0);
		sliderAngle.setBounds(40, 215, 40, 300);
		sliderAngle.setMajorTickSpacing(10);
		sliderAngle.setMinorTickSpacing(1);
		sliderAngle.setSnapToTicks(true);
		sliderAngle.setLabelTable(sliderAngle.createStandardLabels(10, 10));
		sliderAngle.setPaintLabels(true);
		sliderAmbient.setBounds(160, 215, 40, 300);
		sliderAmbient.setMajorTickSpacing(10);
		sliderAmbient.setMinorTickSpacing(1);
		sliderAmbient.setSnapToTicks(true);
		sliderAmbient.setLabelTable(sliderAmbient.createStandardLabels(10, 0));
		sliderAmbient.setPaintLabels(true);
		bp.add(buttonLoad);
		bp.add(buttonSave);
		bp.add(obx);
		bp.add(oby);
		bp.add(obz);
		bp.add(ctx);
		bp.add(cty);
		bp.add(ctz);
		bp.add(sliderAngle);
		bp.add(sliderAmbient);
		buttonLoad.addActionListener(this);
		buttonSave.addActionListener(this);
		sliderAngle.addChangeListener(this);
		sliderAmbient.addChangeListener(this);
		obx.addActionListener(this);
		oby.addActionListener(this);
		obz.addActionListener(this);
		ctx.addActionListener(this);
		cty.addActionListener(this);
		ctz.addActionListener(this);
		fc = new JFileChooser(".");
		fc.setFileFilter(new FileNameExtensionFilter("Opis sceny (pliki brp)", "brp"));
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();

		if (source == buttonLoad) {
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				scene.load(file.getAbsolutePath());
			}
		}
		if (source == buttonSave)
			scene.save();

		else if (scene.initialized) {
			if (source == obx)
				scene.setObx(Double.parseDouble(obx.getText()));
			if (source == oby)
				scene.setOby(Double.parseDouble(oby.getText()));
			if (source == obz)
				scene.setObz(Double.parseDouble(obz.getText()));
			if (source == ctx)
				scene.setCtx(Double.parseDouble(ctx.getText()));
			if (source == cty)
				scene.setCty(Double.parseDouble(cty.getText()));
			if (source == ctz)
				scene.setCtz(Double.parseDouble(ctz.getText()));
			scene.createImage();
			setOrto();
		}
	}

	public void updateTextBoxes(double[] newvalues, double a) {
		obx.setText("" + newvalues[0]);
		oby.setText("" + newvalues[1]);
		obz.setText("" + newvalues[2]);
		ctx.setText("" + newvalues[3]);
		cty.setText("" + newvalues[4]);
		ctz.setText("" + newvalues[5]);
		sliderAngle.setValue((int) newvalues[6]);
		sliderAmbient.setValue((int) (a * 100));
		if (scene.initialized)
			super.repaint();
	}

	@Override
	public void stateChanged(ChangeEvent event) {
		Object source = event.getSource();
		if (scene.initialized && source == sliderAngle) {
			if (sliderAnglestate != sliderAngle.getValue()) {
				scene.setAngle(sliderAngle.getValue());

				scene.createImage();
				setOrto();
				sliderAnglestate = sliderAngle.getValue();
			}
		}
		if (scene.initialized && source == sliderAmbient) {
			scene.setAmbient(sliderAmbient.getValue());
			scene.createImage();
			setOrto();
		}
	}

	public void setOrto() {
		fvp.createImage();
		lvp.createImage();
		dvp.createImage();
		super.repaint();
	}
}