package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Day9 extends InputParser {
    private static final String regex1 = null;
    private static final String regex2 = null;

    public Day9() {
        super(2021, 9, regex1, regex2);
    }

    public Day9(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                for (String element : line) {

                }
            }
        }

        return 1;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        return 2;
    }
}
