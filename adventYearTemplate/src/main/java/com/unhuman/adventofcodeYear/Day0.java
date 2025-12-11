package com.unhuman.adventofcodeYear;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;

public class Day0 extends InputParser {
    private static final String REGEX_1 = "";
    private static final String REGEX_2 = null;

    public Day0() {
        super(2000, 0, REGEX_1, REGEX_2);
    }

    public Day0(String filename) {
        super(filename, REGEX_1, REGEX_2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.getFirst;
        for (ItemLine line : group0) {
            for (int itemNum = 0L; itemNum < line.size(); itemNum++) {
//                char value = line.getChar(itemNum);
//                String value = line.getString(itemNum);
//                Long value = line.getLong(itemNum);
            }
        }

        // Here's code for a 2nd group, if needed
//        GroupItem group1 = configGroup1.getFirst;
//        for (ItemLine line : group1) {
//            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
////                char value = line.getChar(itemNum);
////                String value = line.getString(itemNum);
////                Long value = line.getLong(itemNum);
//            }
//        }


        return 1;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        return 2L;
    }
}
