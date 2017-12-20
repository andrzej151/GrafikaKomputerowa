package grafika2b;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Transformation implements KeyListener {

    private BufferedImage _tex;
    private Matrix _matrix = Matrix.identity( 3 );
    private final Vector[] _srcPoints; // punktu org. wspolrzednych
//    private final Point[] _points; // punkty po przeksztalceniach, wyswietlane na ekranie
    private final Edge[] _edges;
    private Vector[] _drawPoints;

    public Transformation( Vector[] points, Edge[] edges, int w, int h ) {
        _srcPoints = points;
        _drawPoints = new Vector[points.length];
        _edges = edges;
        update( w, h );
    }

    @Override
    public void keyReleased( KeyEvent e ) {
        JFrame f = ( JFrame ) e.getSource();
        final float moveDist = 0.1f, angle = 30;

        switch ( e.getKeyCode() ) {
            case KeyEvent.VK_ESCAPE:
                System.exit( 0 );
                break;
            case KeyEvent.VK_E:
                _matrix = _matrix.translate( 0.5f, 0.5f ); // transform to center
                _matrix = _matrix.rotate( angle );
                _matrix = _matrix.translate( -0.5f,-0.5f );  // transform back
                break;
            case KeyEvent.VK_Q:
                _matrix = _matrix.translate( 0.5f, 0.5f );
                _matrix = _matrix.rotate( -angle );
                _matrix = _matrix.translate( -0.5f,-0.5f );
                break;
            case KeyEvent.VK_W:
                _matrix = _matrix.translate( 0, -moveDist );
                break;
            case KeyEvent.VK_S:
                _matrix = _matrix.translate( 0, moveDist );
                break;
            case KeyEvent.VK_A:
                _matrix = _matrix.translate( -moveDist, 0 );
                break;
            case KeyEvent.VK_D:
                _matrix = _matrix.translate( moveDist, 0 );
                break;
            case KeyEvent.VK_R:
                _matrix = _matrix.translate( 0.5f, 0.5f );
                _matrix = _matrix.scale( 0.5f, 0.5f );
                _matrix = _matrix.translate( -0.5f,-0.5f );
                break;
            case KeyEvent.VK_F:
                _matrix = _matrix.translate( 0.5f, 0.5f );
                _matrix = _matrix.scale( 2, 2 );
                _matrix = _matrix.translate( -0.5f, -0.5f );
                break;
        }
        update( f.getWidth(), f.getHeight() );
        f.repaint();
        f.validate();
    }

    public BufferedImage getImage() {
        return _tex;
    }

    private void update( int w, int h ) {
        for ( int i = 0; i < _srcPoints.length; i++ ) {
            _drawPoints[i] = _matrix.mul( new Vector( _srcPoints[i].x(), _srcPoints[i].y(), 1.0f ) );
        }
        System.out.println( "got:\n" + _matrix ); // + "\n"
//                            + new Vector( _srcPoints[0].x(), _srcPoints[0].y(), 1.0f )
//                            + "\nres:" + _drawPoints[0] );

        Graphics2D g2; // TODO
        if ( _tex == null || w != _tex.getWidth() || h != _tex.getHeight() ) {
            _tex = new BufferedImage( w, h, BufferedImage.TYPE_INT_RGB );
            g2 = ( Graphics2D ) _tex.getGraphics();
        } else {
            g2 = ( Graphics2D ) _tex.getGraphics();
            g2.setColor( Color.black );
            g2.fillRect( 0, 0, w, h );
        }

//        Graphics2D g2 = ( Graphics2D ) _tex.getGraphics();
        g2.setColor( Color.red );
        for ( Edge e : _edges ) {
            Vector v1 = _drawPoints[ e.v[0]];
            Vector v2 = _drawPoints[ e.v[1]];
            int x1 = ( int ) ( v1.x() * w ),
                    y1 = ( int ) ( v1.y() * h ),
                    x2 = ( int ) ( v2.x() * w ),
                    y2 = ( int ) ( v2.y() * h );
            g2.drawLine( x1, y1, x2, y2 );
        }
    }

    @Override
    public void keyTyped( KeyEvent e ) {
    }

    @Override
    public void keyPressed( KeyEvent e ) {
    }

    public static class Edge {

        public Edge( int a, int b ) {
            v[0] = a;
            v[1] = b;
        }

        @Override
        public String toString() {
            return String.format( "%2d\t%2d", v[0], v[1] );
        }
        int[] v = new int[2];
    }
}
