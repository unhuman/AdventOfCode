package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 extends InputParser {
    private static final String regex1 = "(\\d+)-(\\d+),?\\r?\\n?";
    private static final String regex2 = null;

    public Day2() {
        super(2025, 2, regex1, regex2);
    }

    public Day2(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);
        long invalidTotal = 0;
        for (ItemLine line : group0) {
            for (int i = 0; i < line.size(); i += 2) {
                Long start = line.getLong(i);
                Long finish = line.getLong(i + 1);
                for (Long item = start; item <= finish; item++) {
                    String itemString = item.toString();
                    if (itemString.length() % 2 == 0) {
                        String firstHalf = itemString.substring(0, itemString.length() / 2);
                        String secondHalf = itemString.substring(itemString.length() / 2);
                        if (firstHalf.equals(secondHalf)) {
                            invalidTotal += item;
                        }
                    }
                }
            }
        }

        return invalidTotal;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        GroupItem group0 = configGroup.get(0);
        long invalidTotal = 0;
        for (ItemLine line : group0) {
            for (int i = 0; i < line.size(); i += 2) {
                Long start = line.getLong(i);
                Long finish = line.getLong(i + 1);
                for (Long item = start; item <= finish; item++) {
                    String itemString = item.toString();
                    for (int splits = 2; splits <= itemString.length(); splits++) {
                        if (itemString.length() % splits == 0) {
                            boolean valid = true;
                            int chunkLength = itemString.length() / splits;
                            String firstBit = itemString.substring(0, chunkLength);
                            for (int check = 2; check <= splits; check++) {
                                String checkBit = itemString.substring((check - 1) * chunkLength, check * chunkLength);
                                if (!firstBit.equals(checkBit)) {
                                    valid = false;
                                    break;
                                }
                            }
                            if (!valid) {
                                continue;
                            }

                            invalidTotal += item;
                            break;
                        }
                    }
                }
            }
        }

        // Alternate way
        long invalidTotal2 = 0;
        Pattern pattern = Pattern.compile("(\\d+)(\\1)+");
        for (ItemLine line : group0) {
            for (int i = 0; i < line.size(); i += 2) {
                Long start = line.getLong(i);
                Long finish = line.getLong(i + 1);
                for (Long item = start; item <= finish; item++) {
                    String itemString = item.toString();
                    Matcher matcher = pattern.matcher(itemString);
                    if (matcher.matches()) {
                        invalidTotal2 += item;
                    }
                }
            }
        }

        return invalidTotal2;

    }
}
