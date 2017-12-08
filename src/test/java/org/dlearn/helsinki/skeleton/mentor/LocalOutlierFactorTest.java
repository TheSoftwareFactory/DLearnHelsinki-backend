package org.dlearn.helsinki.skeleton.mentor;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertArrayEquals;

public class LocalOutlierFactorTest {
    double[] u = { 1, 2, 3 };
    double[] v = { 2, 2, 3 };
    double[] w = { 3, 4, 5 };
    double[][] data = { u, v, w };
    LocalOutlierFactor lof = new LocalOutlierFactor();

    @Test
    public void sliceTest() {
        double expected[][] = { v };
        assertArrayEquals(expected, LocalOutlierFactor.slice(data, 1, 2));
    }
}