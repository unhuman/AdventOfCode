package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Day3 extends InputParser {
    private static final String regex1 = "(do\\(\\))|(don't\\(\\))|mul\\(([\\d]{1,3}),([\\d]{1,3})\\)";
    private static final String regex2 = null;

    public Day3() {
        super(2024, 3, regex1, regex2);
    }

    public Day3(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        long total = 0;
        GroupItem item = configGroup.get(0);
        for (ItemLine line : item) {
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
                String checkItem = line.getString(itemNum);
                if (checkItem == null) {
                    continue;
                }
                if (!checkItem.equals("do()") && !checkItem.equals("don't()")) {
                    Long item1 = line.getLong(itemNum);
                    Long item2 = line.getLong(itemNum + 1);
                    total += item1 * item2;
                    itemNum++;
                }
            }
        }

        return total;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        long total = 0;
        boolean addValue = true;
        GroupItem item = configGroup.get(0);
        for (ItemLine line : item) {
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
                String checkItem = line.getString(itemNum);
                if (checkItem == null) {
                    continue;
                }
                switch (checkItem) {
                    case "do()":
                        addValue = true;
                        break;
                    case "don't()":
                        addValue = false;
                        break;
                    default:
                        Long item1 = line.getLong(itemNum);
                        Long item2 = line.getLong(itemNum + 1);
                        if (addValue) {
                            total += item1 * item2;
                        }
                        itemNum++;
                }

            }
        }

        return total;
    }
}
