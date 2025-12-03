package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;

public class Day3 extends InputParser {
    private static final String regex1 = "(\\d)";
    private static final String regex2 = null;

    public Day3() {
        super(2025, 3, regex1, regex2);
    }

    public Day3(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);
        long calcScore = 0L;
        for (ItemLine line : group0) {
            Character maxNum1 = null;
            Character maxNum2 = null;
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
                char check = line.getChar(itemNum);
                if (itemNum < line.size() - 1 && (maxNum1 == null || check > maxNum1)) {
                    maxNum1 = check;
                    maxNum2 = null;
                } else if (maxNum2 == null || check > maxNum2) {
                    maxNum2 = check;
                }
            }
            String number = String.format("%c%c", maxNum1, maxNum2);
            calcScore += Long.parseLong(number);
        }

        return calcScore;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        GroupItem group0 = configGroup.get(0);
        long calcScore = 0L;
        int expectedItems = 12;
        for (ItemLine line : group0) {
            ArrayList<Character> data = new ArrayList<>();
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
                data.add(line.getChar(itemNum));
            }

            // todo - we know how many characters are left
            //  so we can find the highest number in the grouping range and remove others preceeding it
            String number = "";
            int startingChar = 0;
            while (number.length() < expectedItems) {
                // Calculate the end char from the starting char
                int remainingItems = expectedItems - number.length();
                int endChar = Math.min(data.size() - 1, data.size() - remainingItems);

                // find the highest character in the grouping, retaining the index where it was.
                char foundChar = '0';
                for (int i = startingChar; i <= endChar; i++) {
                    if (data.get(i) > foundChar) {
                        foundChar = data.get(i);
                        // track the next starting char
                        startingChar = i + 1;
                    }
                }
                number += foundChar;
            }

            calcScore += Long.parseLong(number);
        }
        return calcScore;
    }
}
