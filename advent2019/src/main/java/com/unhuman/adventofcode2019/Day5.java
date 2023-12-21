package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;

public class Day5 extends InputParser {
    private static final String regex1 = "(-?\\d+),?";
    private static final String regex2 = null;

    public Day5() {
        super(2019, 5, regex1, regex2);
    }

    public Day5(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        IntCodeParser parser = new IntCodeParser(item.get(0));
        parser.setInput("1");
        parser.process();
        String output = parser.getOutput();
        return output;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        IntCodeParser parser = new IntCodeParser(item.get(0));
        parser.setInput("5");
        parser.process();
        String output = parser.getOutput();
        return output;
    }
}
