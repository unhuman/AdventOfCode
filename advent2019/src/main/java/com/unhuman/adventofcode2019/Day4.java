package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.HashSet;
import java.util.Set;

public class Day4 extends InputParser {
    private static final String regex1 = "(\\d+)-?";
    private static final String regex2 = null;

    public Day4() {
        super(2019, 4, regex1, regex2);
    }

    public Day4(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        int counter = 0;
        for (ItemLine line : item) {
            int start = Integer.parseInt(line.get(0));
            int end = Integer.parseInt(line.get(1));
            for (Integer i = start; i <= end; i++) {
                String check = i.toString();
                Character lastChar = null;
                boolean increasing = true;
                boolean hasPairs = false;
                for (int c = 0; c < check.length(); c++) {
                    char inspectChar = check.charAt(c);
                    if (lastChar != null) {
                        if (inspectChar < lastChar) {
                            increasing = false;
                            break;
                        }
                        if (inspectChar == lastChar) {
                            hasPairs = true;
                        }
                    }
                    lastChar = inspectChar;
                }
                if (increasing && hasPairs) {
                    counter++;
                }
            }
        }

        return counter;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        int counter = 0;
        for (ItemLine line : item) {
            int start = Integer.parseInt(line.get(0));
            int end = Integer.parseInt(line.get(1));
            Set<Character> checkPairs = new HashSet<>();
            for (Integer i = start; i <= end; i++) {
                String check = i.toString();
                Character lastChar = null;
                boolean increasing = true;
                for (int c = 0; c < check.length(); c++) {
                    char inspectChar = check.charAt(c);
                    if (lastChar != null) {
                        if (inspectChar < lastChar) {
                            increasing = false;
                            break;
                        }
                        if (inspectChar == lastChar) {
                            checkPairs.add(inspectChar);
                        }
                    }
                    lastChar = inspectChar;
                }

                if (increasing && checkPairs.size() > 0) {
                    boolean hasValidPair = false;
                    for (Character lookup: checkPairs) {
                        if (check.chars().filter(ch -> ch == lookup).count() == 2) {
                            hasValidPair = true;
                        }
                    }
                    if (hasValidPair) {
                        counter++;
                    }
                }
            }
        }
        return counter;
    }
}
