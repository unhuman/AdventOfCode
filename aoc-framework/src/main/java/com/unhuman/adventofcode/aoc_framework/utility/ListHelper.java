package com.unhuman.adventofcode.aoc_framework.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListHelper {
    public static Map<?, Long> getItemCounts(List<?> list) {
        Map<Object, Long> countedItems = new HashMap<>();
        for (Object item : list) {
            if (countedItems.containsKey(item)) {
                countedItems.put(item, countedItems.get(item) + 1);
            } else {
                countedItems.put(item, 1L);
            }
        }
        return countedItems;
    }
}
