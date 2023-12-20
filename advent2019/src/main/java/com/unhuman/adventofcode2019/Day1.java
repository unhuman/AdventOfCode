package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Day1 extends InputParser {
    private static final String regex1 = "(\\d+)";
    private static final String regex2 = null;

    public Day1() {
        super(2019, 1, regex1, regex2);
    }

    public Day1(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Long total = 0L;
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        for (ItemLine line : item) {
            Long value = Long.parseLong(line.get(0));
            total += value / 3 - 2;
        }

        return total;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Long total = 0L;
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        for (ItemLine line : item) {
            Long mass = Long.parseLong(line.get(0));

            while (mass > 0L) {
                long fuel = Math.max(0L, mass / 3 - 2);
                total += fuel;
                mass = fuel;
            }
        }

        return total;
    }
}
