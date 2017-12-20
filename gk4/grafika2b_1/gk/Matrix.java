package grafika2b;

import java.util.Locale;

public class Matrix {
    // Matrix_impl

    /**
     [0][3][6]
     [1][4][7]
     [2][5][8]
     <p/>
     [ 0][ 4][ 8][12]
     [ 1][ 5][ 9][13]
     [ 2][ 6][10][14]
     [ 3][ 7][11][15]
     */
    float[] _data;
    int _size; // { 2, 3, 4} etc.

    Matrix( int size ) { // identity( size) is better
        _data = new float[size * size];
        _size = size;
    }

    public static Matrix identity( int size ) {
        Matrix m = new Matrix( size );
        for ( int i = 0; i < size; i++ ) {
            m.set( size * i + i, 1 );
        }
        return m;
    }

    public void set( int el, float val ) {
        assert ( el < _data.length );
        _data[el] = val;
    }

    public float get( int el ) {
        assert ( el < _data.length );
        return _data[el];
    }

    public int getSize() {
        return _size;
    }

    /**
     @return (this) * (m)
     */
    public Matrix mul( final Matrix m ) {
//        System.out.println( "got:\n" + this + "  *\n" + m );

        assert ( m._size == _size );
        Matrix nm = new Matrix( getSize() );
        int n = getSize();
//        nm.set( 0, 0 );
        for ( int i = 0; i < n; ++i ) { // kolejne kolumny
            for ( int j = 0; j < n; ++j ) { // kolejne rzedy
//                System.out.println( "el: " + i + " x " + j );
                for ( int k = 0; k < n; ++k ) {
//                    nm._data[i * n + j] += _data[k * n + j] * m._data[i * n + k];
//                    System.out.println( "a[" + ( k * n + j ) + "] * b[" + ( i * n + k ) + "]" );
                    nm._data[i * n + j] += _data[i * n + k] * m._data[k * n + j];
                }
            }
        }
//        System.out.println( "res:\n" + nm );

        return nm;
    }

    public Vector mul( final Vector v ) {
        assert ( v.getSize() == _size );
        int n = getSize();
        Vector nv = new Vector( n );
        for ( int i = 0; i < n; ++i ) {
            float r = 0;
            for ( int j = 0; j < n; j++ ) {
//                r += v.get( j )r += v.get( j ) * _data[j * n + i]; * _data[j * n + i];
                r += v.get( j ) * _data[i * n + j];
            }
            nv.set( i, r );
        }
        return nv;
    }

    /*
     _size == 3 ( 2D transformation) operations
     */
    public Matrix translate( float x, float y ) {
        assert ( _size == 3 );
        Matrix r = identity( 3 );
        r._data[2] = x;
        r._data[5] = y;
        return this.mul( r );
    }

    public Matrix rotate( float angle_dgr ) {
        assert ( _size == 3 );
        Matrix r = identity( 3 );
        double angle_rad = angle_dgr / 180 * Math.PI;
        double sin_fi = Math.sin( angle_rad );
        double cos_fi = Math.cos( angle_rad );
        r._data[0] = ( float ) cos_fi;
        r._data[3] = ( float ) sin_fi;
        r._data[1] = ( float ) -sin_fi;
        r._data[4] = ( float ) cos_fi;
        return this.mul( r );
    }

    public Matrix scale( float sx, float sy ) {
        assert ( _size == 3 );
        Matrix r = identity( 3 );
        r._data[0] = sx;
        r._data[4] = sy;
        return this.mul( r );
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(); // TODO init. capacity ?
        int n = getSize();
        for ( int i = 0; i < n; i++ ) {
            for ( int j = 0; j < n; j++ ) {
                buf.append( String.format( Locale.UK, "%4.2f\t", _data[j * n + i] ) );
            }
            buf.append( "\n" );
        }
        return buf.toString();
    }
}
