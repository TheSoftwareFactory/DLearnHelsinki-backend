package org.dlearn.helsinki.skeleteon.mentor;

import java.util.HashMap;
import java.util.Map;
import org.dlearn.helsinki.skeleton.model.Answer;
import static org.dlearn.helsinki.skeleton.mentor.Sort.sortMapByValue;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SortTest {
    public Map<Integer, Double> expected;

    @Before
    public void setUp() {
        expected = new HashMap();
        expected.put(2, 0.01);
        expected.put(3, 0.9);
        expected.put(1, 1.0);
    }

    @Test
    public void orderDoesNotChangeIfAlreadySorted() {
        assertEquals(expected, sortMapByValue(expected));
    }

    @Test
    public void testMapSortByValues() {
        Map<Integer, Double> map = new HashMap();
        map.put(1, 1.0);
        map.put(2, 0.01);
        map.put(3, 0.9);
        assertEquals(expected, sortMapByValue(map));
    }

}