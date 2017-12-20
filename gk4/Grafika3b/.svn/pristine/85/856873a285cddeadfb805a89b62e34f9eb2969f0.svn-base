package grafika3;

import java.awt.Graphics;

import javax.swing.JPanel;

public class ButtonPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	int PANEL_WIDTH = Main.PANEL_WIDTH;
	int PANEL_HEIGHT = PANEL_WIDTH * 2 / 3;
	
	ButtonPanel() {
		setLayout(null);
	}
	
	@Override
	public void paintComponent(Graphics gp) {
		super.paintComponent(gp);
		setBounds(PANEL_WIDTH * 2, 0, 250, PANEL_HEIGHT * 2);
		
		gp.drawString("Kamera", 25, 130);
		gp.drawString("Centrum", 25, 170);
		gp.drawString("Kat widzenia", 25, 210);
		gp.drawString("Swiatlo otoczajace", 130, 210);
	}
}