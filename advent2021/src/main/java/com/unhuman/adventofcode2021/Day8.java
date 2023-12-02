package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Day8 extends InputParser {
    private static final String regex1 = "^([a-g|]+) ?";
    private static final String regex2 = null;

    public Day8() {
        super(2021, 8, regex1, regex2);
    }

    public Day8(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int counter = 0;
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                boolean foundBar = false;
                for (String element : line) {
                    if (foundBar) {
                        if (element.length() == 2 || element.length() == 3 || element.length() == 4 || element.length() == 7) {
                            ++counter;
                        }
                    }
                    if (element.equals("|")) {
                        foundBar = true;
                    }
                }
            }
        }

        return counter;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        return 2;
    }
}
