package Pracy;

import java.awt.EventQueue;



public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new OknoGlowne();
            }
        });;
	}

}
