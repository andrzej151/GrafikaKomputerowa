package Pracy;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class OknoGlowne extends JFrame {
	Canva c;
	

	public OknoGlowne() {
		// TODO Auto-generated constructor stub
		super("Ewidencja czasu pracy PWR");

		c =  new Canva();
		c.setBounds(0, 0, 250, 250);
		add(c);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		  WindowListener sluchacz = new Zamykanie();
	       this.addWindowListener(sluchacz);
		
		setBounds(50, 50, 350, 350);
		setVisible(true);
	}
	
	class Zamykanie extends WindowAdapter
    {
        public void windowClosing(WindowEvent e)
        {
            int answer = JOptionPane.showConfirmDialog(null, "Zamkn¹æ program ?", "", JOptionPane.YES_NO_OPTION);
 
           if (answer == JOptionPane.YES_OPTION)
           {
        	   
                 System.exit(0);
           }
 
        }
    }
	
}
