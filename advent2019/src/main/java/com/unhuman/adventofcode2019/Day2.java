package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;

public class Day2 extends InputParser {
    private static final String regex1 = "(\\d+),?";
    private static final String regex2 = null;

    public Day2() {
        super(2019, 2, regex1, regex2);
    }

    public Day2(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        IntCodeParser parser = new IntCodeParser(item.get(0));
        parser.poke(1, 12);
        parser.poke(2, 2);
        parser.process();
        return parser.peek(0);
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);

        for (int noun = 0; noun <= 99; noun++) {
            for (int verb = 0; verb <= 99; verb++) {
                IntCodeParser parser = new IntCodeParser(item.get(0));
                parser.poke(1, noun);
                parser.poke(2, verb);
                parser.process();
                if (parser.peek(0) == 19690720) {
                    return 100 * noun + verb;
                }
            }
        }
        throw new RuntimeException("Couldn't find the thing");
    }
}
