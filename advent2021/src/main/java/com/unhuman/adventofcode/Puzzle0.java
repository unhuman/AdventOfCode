package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Puzzle0 extends InputParser {
    public Puzzle0(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, "(\\d)", null);
    }

    @Override
    protected void processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                for (String element : line) {

                }
            }
        }
    }

    @Override
    protected void processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
    }
}
