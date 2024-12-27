package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;

public class Day9 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    public Day9() {
        super(2019, 9, regex1, regex2);
    }

    public Day9(String filename) {
        super(filename, regex1, regex2);
    }

    // 2030 is too low

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        IntCodeParser parser = new IntCodeParser((item.get(0).toString()));
        parser.setInput("1");
        parser.process();
        return parser.getOutput();
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        IntCodeParser parser = new IntCodeParser((item.get(0).toString()));
        parser.setInput("2");
        parser.process();
        return parser.getOutput();
    }
}
