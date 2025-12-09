package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;

import java.util.ArrayList;

public class Day9 extends InputParser {
    private static final String regex1 = "(\\d+),(\\d+)";
    private static final String regex2 = null;

    public Day9() {
        super(2025, 9, regex1, regex2);
    }

    public Day9(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.getFirst();
        ArrayList<Pair<Long, Long>> points = new ArrayList<>();
        for (ItemLine line : group0) {
            points.add(new Pair(line.getLong(0), line.getLong(1)));
        }

        long maxArea = 0L;
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {
                long area = (Math.abs(points.get(j).getLeft() - points.get(i).getLeft()) + 1)
                        * (Math.abs(points.get(j).getRight() - points.get(i).getRight()) + 1);
                if (area > maxArea) {
                    maxArea = area;
                }
            }
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

        return maxArea;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        return 2;
    }
}
