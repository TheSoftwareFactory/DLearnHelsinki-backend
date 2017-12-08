package org.dlearn.helsinki.skeleton.mentor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Sort {

    public static <K extends Comparable, V extends Comparable> Map<K, V> sortMapByValue(
            final Map<K, V> map) {
        Map<K, V> mapSortedByValues = new LinkedHashMap<K, V>();
        List<Map.Entry<K, V>> list = new ArrayList<Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Entry<K, V> entry1, Entry<K, V> entry2) {
                return entry1.getValue().compareTo(entry2.getValue());
            }
        });

        for (Map.Entry<K, V> entry : list) {
            mapSortedByValues.put(entry.getKey(), entry.getValue());
        }

        return mapSortedByValues;
    }

}