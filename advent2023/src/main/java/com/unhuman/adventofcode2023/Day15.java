package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Day15 extends InputParser {
    private static final String regex1 = "([^,]+),?";
    private static final String regex2 = null;

    public Day15() {
        super(2023, 15, regex1, regex2);
    }

    public Day15(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        long total = 0L;
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                for (String element : line) {
                    long currentValue = hash(element);

                    total += currentValue;
                }
            }
        }

        return total;
    }

    private static int hash(String element) {
        int currentValue = 0;
        for (int i = 0; i < element.length(); i++) {
            char thing = element.charAt(i);
            currentValue += thing;
            currentValue *= 17;
            currentValue %= 256;
        }
        return currentValue;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<LinkedHashMap<String, AtomicInteger>> boxes = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            boxes.add(new LinkedHashMap<>());
        }
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                for (String element : line) {
                    int equalsLoc = element.indexOf('=');
                    int minusLoc = element.indexOf('-');
                    String prefix = element.substring(0, equalsLoc + minusLoc + 1);
                    char operation = (equalsLoc >= 0) ? '=' : '-';
                    Integer lens = (operation == '=') ? Integer.parseInt(element.substring(equalsLoc + minusLoc + 2)) : null;

                    int boxNum = hash(prefix);

                    LinkedHashMap<String, AtomicInteger> box = boxes.get(boxNum);

                    switch (operation) {
                        case '-':
                            box.remove(prefix);
                            break;
                        case '=':
                            if (box.containsKey(prefix)) {
                                box.get(prefix).set(lens);
                            } else {
                                box.put(prefix, new AtomicInteger(lens));
                            }
                    }
                }
            }
        }

        long total = 0;
        for (int i = 0; i < 256; i++) {
            int slot = 1;
            for (AtomicInteger lensValue : boxes.get(i).values()) {
                int value = (1 + i) * slot++ * lensValue.intValue();
                total += value;
            }
        }
        return total;
    }
}
