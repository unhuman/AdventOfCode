package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Puzzle2 extends InputParser {
    public Puzzle2(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, "(\\w+) (\\d+)", null);
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
