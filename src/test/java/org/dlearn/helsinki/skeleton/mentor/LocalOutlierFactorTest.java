package org.dlearn.helsinki.skeleton.mentor;

import java.util.ArrayList;
import java.util.List;
import org.dlearn.helsinki.skeleton.model.Answer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class LocalOutlierFactorTest {
    LocalOutlierFactor lof = new LocalOutlierFactor();
    List<Answer> data = new ArrayList();

    @Before
    public void setUp() {
        data.add(new Answer());
    }

    @Test
    public void prepareDataEmptyRawData() {
        List<Answer> rawData = new ArrayList();
        List<List<Answer>> expected = new ArrayList();
        assertEquals(expected, lof.prepareData(0, rawData));
    }

}