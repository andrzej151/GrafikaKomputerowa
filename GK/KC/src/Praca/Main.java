package Praca;

import java.awt.EventQueue;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Model m;
				Controler c;
				View v;
				m = new Model();
				v = new View(m);
				c = new Controler(m, v);
			}
		});
		;
	}

}
