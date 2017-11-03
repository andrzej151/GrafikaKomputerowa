package Wycinanie;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;



public class MyWindow extends JFrame{

	 public MyWindow()
	    {   	
	    	// Acquire drawing surface and add own panel to it
	    	// Pozyskanie dostepu do powierzchni rysowania okna	
	    	Container  contents = getContentPane();
	    	contents.add( new Pole() );  	
	    	Object[][] dane = { { "Kowalski", "Jan", new Integer(30), new Boolean(true) },
					{ "Jankowski", "Jan", new Integer(20), new Boolean(false) } };
			Object[] nazwyKolumn = { "Elipsa", "prostok¹t", "wielokat"" };

			JTable tab = new JTable(dane, nazwyKolumn);
			JScrollPane sp = new JScrollPane(tab);
			contents.add(tab);
			
	    }
}
