package Wycinanie;

import java.util.Scanner;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) 
    {
		Scanner in = new Scanner(System.in);

        MyWindow wnd = new MyWindow();
        wnd.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        wnd.setVisible(true);
        wnd.setBounds( 70, 70, 450, 300);
        wnd.setTitle( "" );
	}

}


