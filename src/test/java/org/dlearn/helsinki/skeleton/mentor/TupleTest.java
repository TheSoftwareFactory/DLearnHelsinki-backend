package org.dlearn.helsinki.skeleton.mentor;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TupleTest {
    Tuple<Integer, Double> tuple;

    @Test
    public void tupleTest() {
        Integer x = 1;
        Double y = 2.0;
        tuple = new Tuple(x, y);
        assertEquals(x, tuple.first());
        assertEquals(y, tuple.second(), 0.0);
    }
}