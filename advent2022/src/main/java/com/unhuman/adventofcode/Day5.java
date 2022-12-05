package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Day5 extends InputParser {
    private static final String regex1 = "(\\[[\\w]\\]|\\s\\s\\s)\\s?";
    private static final String regex2 = null;

    public Day5(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    @Override
    protected void processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
            }
        }
    }

    @Override
    protected void processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
            }
        }
    }
}
