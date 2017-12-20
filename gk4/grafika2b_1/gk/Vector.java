package grafika2b;

import java.util.Locale;

public class Vector {
       // Vector_impl

    private float[] _data;
    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;
    private static final int W = 3;

    public Vector( int size ) {
        _data = new float[size];
    }

    public Vector( float x, float y ) {
        _data = new float[2];
        _data[0] = x;
        _data[1] = y;
    }

    public Vector( float x, float y, float z ) {
        _data = new float[3];
        _data[0] = x;
        _data[1] = y;
        _data[2] = z;
    }

    public Vector( float x, float y, float z, float w ) {
        _data = new float[4];
        _data[0] = x;
        _data[1] = y;
        _data[2] = z;
        _data[3] = w;
    }

    public Vector( final Vector v ) {
        _data = new float[v._data.length];
        for ( int i = 0; i < _data.length; i++ ) {
            _data[i] = v._data[i];
        }
    }

    public Vector( final Vector v, int size ) {
        _data = new float[size];
        int m = Math.min( size, v.getSize() );
        for ( int i = 0; i < m; i++ ) {
            _data[i] = v._data[i];
        }
    }

    public final int getSize() {
        return _data.length;
    }

    public final float length() {
        float a = 0;
        for ( int i = 0; i < getSize(); i++ ) {
            a += _data[i] * _data[i];
        }
        return ( float ) Math.sqrt( a );
    }

    // operations altering 'this' object
    public Vector add( Vector a ) {
        int m = Math.min( a._data.length, _data.length );
        for ( int i = 0; i < m; i++ ) {
            _data[i] += a._data[ i];
        }
        return this;
    }

    public Vector add( float a ) {
        for ( int i = 0; i < getSize(); i++ ) {
            _data[i] += a;
        }
        return this;
    }

    public Vector sub( final Vector a ) {
        int m = Math.min( a._data.length, _data.length );
        for ( int i = 0; i < m; i++ ) {
            _data[i] -= a._data[ i];
        }
        return this;
    }

    public Vector mul( float a ) {
        for ( int i = 0; i < _data.length; i++ ) {
            _data[i] *= a;
        }
        return this;
    }

    public Vector mul( final Vector v ) {
        int m = Math.min( v._data.length, _data.length );
        for ( int i = 0; i < m; i++ ) {
            _data[i] *= v._data[i];
        }
        return this;
    }

    public Vector div( float a ) {
        return mul( 1 / a );
    }

    // operations not altering this vector
    public Vector addi( Vector a, int size ) {
        Vector r = new Vector( size );
        int m = Math.min( a._data.length, Math.min( _data.length, size ) );
        for ( int i = 0; i < m; i++ ) {
            r._data[i] = _data[i] + a._data[ i];
        }
        return r;
    }

    public Vector addi( float a, int size ) {
        Vector r = new Vector( size );
        for ( int i = 0; i < getSize(); i++ ) {
            _data[i] += a;
        }
        return r;
    }

    public Vector subi( Vector a, int size ) {
        Vector r = new Vector( size );
        int m = Math.min( a._data.length, Math.min( _data.length, size ) );
        for ( int i = 0; i < m; i++ ) {
            r._data[i] = _data[i] - a._data[ i];
        }
        return r;
    }

    public Vector muli( float a ) {
        Vector r = new Vector( this );
        for ( int i = 0; i < r.getSize(); i++ ) {
            r._data[i] *= a;
        }
        return r;
    }

    public Vector muli( final Vector v, int size ) {
        Vector r = new Vector( size );
        int m = Math.min( v._data.length, Math.min( _data.length, size ) );
        for ( int i = 0; i < m; i++ ) {
            r._data[i] = _data[i] * v._data[i];
        }
        return r;
    }

    public Vector divi( float a ) {
        return muli( 1 / a );
    }

    public final float dot( Vector v ) {
        assert ( v._data.length == _data.length );
        float r = 0;
        for ( int i = 0; i < _data.length; i++ ) {
            r += _data[i] * v._data[i];
        }
        return r;
    }

    /**
     @return (this) * (v)
     */
    public final Vector cross( Vector v ) {
        assert ( v.getSize() == 3 && getSize() == 3 );
        Vector a = this.normalize(); // TODO : needed ?
        Vector b = v.normalize();
        Vector r = new Vector( 3 );
        r._data[X] = a._data[Y] * b._data[Z] - a._data[Z] * b._data[Y];
        r._data[Y] = a._data[Z] * b._data[X] - a._data[X] * b._data[Z];
        r._data[Z] = a._data[X] * b._data[Y] - a._data[Y] * b._data[X];
        return r;
    }

    public Vector normalize() {
        Vector r = new Vector( this );
        r.div( r.length() );
        return r;
    }

    // others functions
    public boolean equals( final Vector o ) {
        boolean r = getSize() == o.getSize() ? true : false;
        for ( int i = 0; r && i < getSize(); i++ ) {
            r = _data[i] == o._data[i];
        }
        return r;
    }

    @Override
    public String toString() {
        String r = "Vector" + _data.length + " [ ";
        for ( int i = 0; i < _data.length; i++ ) {
            r += String.format( Locale.UK, "% 4.2f  ", _data[i] );
        }
        return r + "]";
    }

    // get, set
    public float get( int i ) {
        return _data.length > i ? _data[i] : Float.MIN_VALUE;
    }

    public float x() {
        return _data.length > 0 ? _data[0] : Float.MIN_VALUE;
    }

    public float y() {
        return _data.length > 1 ? _data[1] : Float.MIN_VALUE;
    }

    public float z() {
        return _data.length > 2 ? _data[2] : Float.MIN_VALUE;
    }

    public float w() {
        return _data.length > 3 ? _data[3] : Float.MIN_VALUE;
    }

    public void set( int i, float f ) {
        _data[i] = f;
    }

    public void x( float f ) {
        _data[0] = f;
    }

    public void y( float f ) {
        _data[1] = f;
    }

    public void z( float f ) {
        _data[2] = f;
    }

    public void w( float f ) {
        _data[3] = f;
    }
}
