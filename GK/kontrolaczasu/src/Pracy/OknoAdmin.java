package Pracy;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class OknoAdmin extends JFrame {
	CanvaAdmin c;

	public OknoAdmin() {
		// TODO Auto-generated constructor stub
		super("Ewidencja czasu pracy PWR Admin");

		c =  new CanvaAdmin();
		c.setBounds(0, 0, 500, 500);
		add(c);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setBounds(50, 50, 500, 500);
		setVisible(true);
	}
	

	
}
