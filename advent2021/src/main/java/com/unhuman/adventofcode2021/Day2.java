package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Day2 extends InputParser {
    private static final String regex1 = "(\\w+) (\\d+)";
    private static final String regex2 = null;

    public Day2() {
        super(2021, 2, regex1, regex2);
    }

    public Day2(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int depth = 0;
        int horiz = 0;
        for (GroupItem item: configGroup) {
            for (ItemLine line: item) {
                String command = line.get(0);
                Integer value = Integer.parseInt(line.get(1));

                switch (command) {
                    case "forward":
                        horiz += value;
                        break;
                    case "up":
                        depth -= value;
                        break;
                    case "down":
                        depth += value;
                        break;
                }
            }
        }
        return (depth * horiz);
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int aim = 0;
        int depth = 0;
        int horiz = 0;
        for (GroupItem item: configGroup) {
            for (ItemLine line: item) {
                String command = line.get(0);
                Integer value = Integer.parseInt(line.get(1));

                switch (command) {
                    case "forward":
                        horiz += value;
                        depth += value * aim;
                        break;
                    case "up":
                        aim -= value;
                        break;
                    case "down":
                        aim += value;
                        break;
                }
            }
        }
        return (depth * horiz);
    }
}
