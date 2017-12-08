package org.dlearn.helsinki.skeleton.mentor;

public class Tuple<X, Y> {
    public final X x;
    public final Y y;

    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X first() {
        return this.x;
    }

    public Y second() {
        return this.y;
    }
}