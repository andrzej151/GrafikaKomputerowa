package grafika2b;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Main {

    public static void main( String[] args ) {
        final int w = 600, h = 550;

        // przygotuj strzalke
        int[] x = { 2, 2, 0, 4, 8, 6, 6 }; // punkty strzalki w grid 8x8
        int[] y = { 8, 4, 4, 0, 4, 4, 8 };
        float max = 8.0f;

        Vector[] points = new Vector[7];
        for ( int i = 0; i < 7; ++i ) {
            points[i] = new Vector( x[i] / max, y[i] / max );
        }
        // points to teraz punkty o wspolrzednych z przedzialu [0,1]

        Transformation.Edge[] edges = {
            new Transformation.Edge( 0, 1 ),
            new Transformation.Edge( 1, 2 ),
            new Transformation.Edge( 2, 3 ),
            new Transformation.Edge( 3, 4 ),
            new Transformation.Edge( 4, 5 ),
            new Transformation.Edge( 5, 6 ),
            new Transformation.Edge( 6, 0 ) };
        // strzalka gotowa
        
        final Transformation m = new Transformation( points, edges, w, h );

        EventQueue.invokeLater( new Runnable() {

            @ Override
            public void run() {
                Frame f = new Frame( m, w, h );
                f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                f.setVisible( true );
            }
        } );

    }
}
