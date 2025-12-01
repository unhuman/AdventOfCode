package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;

public class Day1 extends InputParser {
    private static final String regex1 = "(L|R)(\\d+)";
    private static final String regex2 = null;

    public Day1() {
        super(2025, 1, regex1, regex2);
    }

    public Day1(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);
        int position = 50;
        int countZero = 0;
        for (ItemLine line : group0) {
            char rotate = line.getChar(0);
            Long amount = line.getLong(1);

            switch (rotate) {
                case 'L':
                    position -= amount;
                    break;
                case 'R':
                    position += amount;
                    break;
            }
            position = position % 100;
            if (position < 0) position += 100;

            if (position == 0) {
                countZero++;
            }
        }

        return countZero;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);
        int position = 50;
        int priorPosition = position;
        int countZero = 0;
        for (ItemLine line : group0) {
            char rotate = line.getChar(0);
            Long amount = line.getLong(1);

            priorPosition = position;

            switch (rotate) {
                case 'L':
                    position -= amount;
                    break;
                case 'R':
                    position += amount;
                    break;
            }
            if (position >= 100) {
                countZero += Math.abs(position / 100);
            } else if (position <= 0) {
                countZero += Math.abs(position / 100) + ((priorPosition != 0) ? 1 : 0);
            }

            position = position % 100;
            if (position < 0) position += 100;
        }

        return countZero;
    }
}
