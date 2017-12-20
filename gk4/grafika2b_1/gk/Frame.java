package grafika2b;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

@ SuppressWarnings ( "serial" )
public class Frame extends JFrame {

    private Transformation _transformation;

    public Frame( Transformation transformation, int w, int h ) {
        setTitle( "View" );
        setSize( w, h );
        setResizable( true );
        setMinimumSize( new Dimension( 400, 300 ) );

        this.addKeyListener( transformation );
        _transformation = transformation;

        add( new ImagePanel( this ) );
    }

    public class ImagePanel extends JPanel {

        private final Frame _parent;

        public ImagePanel( Frame frame ) {
            _parent = frame;
        }

        @Override
        protected void paintComponent( Graphics g ) {
            System.out.println( "draw" );

            Graphics2D g2 = ( Graphics2D ) g;
            g2.setColor( this.getBackground() );

            final BufferedImage draw = _transformation.getImage();
            g2.drawImage( draw, 0, 0, _parent.getWidth(), _parent.getHeight(), null );
        }
    }
}
