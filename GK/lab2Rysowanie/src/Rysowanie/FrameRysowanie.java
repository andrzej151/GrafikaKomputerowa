package Rysowanie;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameRysowanie extends JFrame{
	
	public FrameRysowanie() {
		super("Rysowanie - lab 2 GK");
		 
        add(new MouseTestPanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds( 70, 70, 450, 300);
        setVisible(true);
	}

}
