package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Day6 extends InputParser {
    private static final String regex1 = "(\\d+\\s*|\\*\\s*|\\+\\s*)";
    private static final String regex2 = "";

    public Day6() {
        super(2025, 6, regex1, regex2);
    }

    public Day6(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);

        ItemLine operations = group0.getLast();
        Long total = 0L;
        for (int itemNum = 0; itemNum < operations.size(); itemNum++) {
            Long currentTotal = group0.getFirst().getLong(itemNum);
            String operation = operations.get(itemNum).trim();
            for (ItemLine line : group0) {
                if (line == operations) {
                    break;
                } else if (line == group0.getFirst()) {
                    continue;
                }

                Long value = line.getLong(itemNum);
                switch (operation) {
                    case "+":
                        currentTotal += value;
                        break;
                    case "*":
                        currentTotal *= value;
                        break;
                }
            }
            total += currentTotal;
        }

        // Here's code for a 2nd group, if needed
//        GroupItem group1 = configGroup1.get(0);
//        for (ItemLine line : group1) {
//            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
////                char value = line.getChar(itemNum);
////                String value = line.getString(itemNum);
////                Long value = line.getLong(itemNum);
//            }
//        }


        return total;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);

        ItemLine operations = group0.getLast();
        Long total = 0L;
        int index = 0;
        int nextIndex = 0;
        for (int itemNum = 0; itemNum < operations.size(); itemNum++) {
            index = nextIndex;
            Long currentTotal = 0L;
            String operation = operations.get(itemNum);

            // calculate the width of the numbers
            int numberWidth = operation.length();
            nextIndex += numberWidth;
            operation = operation.trim();

            // look through the indexes to build up the numbers
            for (int i = index; i < nextIndex; i++) {
                String number = "";
                for (ItemLine line : group0) {
                    if (line == operations) {
                        break;
                    }
                    char digit = line.getOriginalLine().charAt(i);
                    if (digit >= '0' && digit <= '9') {
                        number += digit;
                    }
                }

                if (number.isEmpty()) {
                    continue;
                }

                long value = Long.parseLong(number);

                if (currentTotal == 0) {
                    currentTotal = value;
                    continue;
                }

                switch (operation) {
                    case "+":
                        currentTotal += value;
                        break;
                    case "*":
                        currentTotal *= value;
                        break;
                }
            }

            total += currentTotal;
        }
        return total;
    }
}
