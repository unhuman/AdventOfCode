package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.List;

public class Day11 extends InputParser {
    private static final String regex1 = "(\\d+)\\s*";
    private static final String regex2 = null;

    public Day11() {
        super(2024, 11, regex1, regex2);
    }

    public Day11(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Long> lineMap = new ArrayList<>();

        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);
        for (ItemLine line : group0) {
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
                lineMap.add(line.getLong(itemNum));
            }
        }

        for (int i = 0; i < 25; i++) {
            blink(lineMap);
        }

        // Here's code for a 2nd group, if needed
//        GroupItem group1 = configGroup1.get(0);
//        for (ItemLine line : group1) {
//            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
////                char value = line.getChar(itemNum);
////                String value = line.getString(itemNum);
////                Long value = line.getLong(itemNum);
//            }
//        }


        return (long) lineMap.size();
    }

    public void blink(List<Long> lineMap) {
        for (int i = 0; i < lineMap.size(); i++) {
            Long value = lineMap.get(i);
            if (value == 0) {
                lineMap.set(i, 1L);
            } else if (value.toString().length() % 2 == 0) {
                int split = value.toString().length() / 2;
                long firstHalf = Long.parseLong(value.toString().substring(0, split));
                long secondHalf = Long.parseLong(value.toString().substring(split));
                lineMap.set(i, firstHalf);
                lineMap.add(++i, secondHalf);
            } else {
                lineMap.set(i, value * 2024);
            }
        }
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Long> lineMap = new ArrayList<>();

        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);
        for (ItemLine line : group0) {
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
                lineMap.add(line.getLong(itemNum));
            }
        }

        long count = 0;

        for (int item = 0 ; item < lineMap.size(); item++) {
            count += lineMap.get(item);
            List<Long> line = new ArrayList<>();
            line.add(lineMap.get(item));
            for (int i = 0; i < 75; i++) {
                blink(line);
            }
            count += line.size();
        }
        return count;
    }
}
