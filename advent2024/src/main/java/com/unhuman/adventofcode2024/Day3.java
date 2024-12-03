package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Day3 extends InputParser {
    private static final String regex1 = null;
    private static final String regex2 = null;

    public Day3() {
        super(2024, 3, regex1, regex2);
    }

    public Day3(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        for (ItemLine line : item) {
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
//                char item = line.getChar(itemNum);
//                String item = line.getString(itemNum);
//                Long item = line.getLong(itemNum);
            }
        }

        return 1;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        return 2;
    }
}
