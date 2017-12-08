package org.dlearn.helsinki.skeleteon.mentor;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import org.dlearn.helsinki.skeleton.model.Answer;
import static org.dlearn.helsinki.skeleton.mentor.Sort.sortMapByValue;
import static org.dlearn.helsinki.skeleton.mentor.Sort.sortMapByValueReverse;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class SortTest {
    public Map<Integer, Double> expected, map, reverse;

    @Before
    public void setUp() {
        map = new HashMap();
        map.put(1, 1.0);
        map.put(2, 0.01);
        map.put(3, 0.9);
        expected = new LinkedHashMap();
        expected.put(2, 0.01);
        expected.put(3, 0.9);
        expected.put(1, 1.0);
        reverse = new LinkedHashMap();
        reverse.put(1, 1.0);
        reverse.put(3, 0.9);
        reverse.put(2, 0.01);
    }

    @Test
    public void testMapSortByValue() {
        Map<Integer, Double> sorted = sortMapByValue(map);
        assertEquals(new ArrayList(expected.values()),
                new ArrayList(sorted.values()));
    }

    @Test
    public void testMapSortByValueReverse() {
        Map<Integer, Double> reversed = sortMapByValueReverse(map);
        assertEquals(new ArrayList(reverse.values()),
                new ArrayList(reversed.values()));
    }

}