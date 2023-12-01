package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.List;

public class Day1 extends InputParser {
    private static final String regex1 = "(.*)";
    private static final String regex2 = null;

    public Day1() {
        super(2023, 1, regex1, regex2);
    }

    public Day1(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int total = 0;
        for (GroupItem item: configGroup) {
            for (ItemLine line: item) {
                int value = getLineItemValue1(line);
                total += value;
            }
        }
        return total;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int total = 0;
        for (GroupItem item: configGroup) {
            for (ItemLine line: item) {
                int value = getLineItemValue2(line);
                total += value;
            }
        }
        return total;
    }

    private int getLineItemValue1(List<String> lines) {
        Integer first = null;
        Integer last = null;
        for (String line: lines) {
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);
                if (ch >= '0' && ch <= '9') {
                    last = ch - '0';
                    if (first == null) {
                        first = last;
                    }
                }
            }
        }
        return (first != null) ? first * 10 + last : 0;
    }

    private int getLineItemValue2(List<String> lines) {
        Integer first = null;
        Integer last = null;
        for (String line: lines) {
            for (int i = 0; i < line.length(); i++) {
                Integer lineItem = getLineItemValue(line, i);
                if (lineItem != null) {
                    last = lineItem;
                    if (first == null) {
                        first = last;
                    }
                }
            }
        }
        return (first != null) ? first * 10 + last : 0;
    }



    private Integer getLineItemValue(String line, int index) {
        char ch = line.charAt(index);
        if (ch >= '0' && ch <= '9') {
            return ch - '0';
        }

        try {
            String threeCharChecker = (line.substring(index, index + 3));
            switch (threeCharChecker) {
                case "one":
                    return 1;
                case "two":
                    return 2;
                case "six":
                    return 6;
            }
            String fourCharChecker = (line.substring(index, index + 4));
            switch (fourCharChecker) {
                case "four":
                    return 4;
                case "five":
                    return 5;
                case "nine":
                    return 9;
            }
            String fiveCharChecker = (line.substring(index, index + 5));
            switch (fiveCharChecker) {
                case "three":
                    return 3;
                case "seven":
                    return 7;
                case "eight":
                    return 8;
            }
        } catch (Exception e) {
            // Do nothing - return null
        }
        return null;
    }
}
