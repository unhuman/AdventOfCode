package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Day15 extends InputParser {
    private static final String regex1 = null;
    private static final String regex2 = null;

    public Day15() {
        super(2024, 15, regex1, regex2);
    }

    public Day15(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);
        for (ItemLine line : group0) {
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
//                char value = line.getChar(itemNum);
//                String value = line.getString(itemNum);
//                Long value = line.getLong(itemNum);
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


        return 1;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        return 2;
    }
}
