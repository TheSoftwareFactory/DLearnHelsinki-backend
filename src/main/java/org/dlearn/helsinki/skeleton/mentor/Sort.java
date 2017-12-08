package org.dlearn.helsinki.skeleton.mentor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Sort {

    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(
            Map<K, V> map) {
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
    }

}