package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Day0 extends InputParser {
    private static final String regex1 = null;
    private static final String regex2 = null;

    public Day0(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    @Override
    protected void processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        for (GroupItem item : dataItems1) {
            for (ItemLine line : item) {
                for (String element : line) {

                }
            }
        }
    }

    @Override
    protected void processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
    }
}
