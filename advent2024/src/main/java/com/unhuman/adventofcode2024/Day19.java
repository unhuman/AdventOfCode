package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 extends InputParser {
    private static final String regex1 = "([^,]*),?\\s*";
    private static final String regex2 = "(.*)";

    public Day19() {
        super(2024, 19, regex1, regex2);
    }

    public Day19(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        List<String> patterns = new ArrayList<>();
        GroupItem group0 = configGroup.get(0);
        for (ItemLine line : group0) {
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
                patterns.add(line.getString(itemNum));
            }
        }

        List<String> designs = new ArrayList<>();
        // Here's code for a 2nd group, if needed
        GroupItem group1 = configGroup1.get(0);
        for (ItemLine line : group1) {
            designs.add(line.getString(0));
        }

        long count = 0L;

        for (String design : designs) {
            if (canMakeDesign(patterns, design)) {
                count++;
            }
        }
        return count;
    }

    boolean canMakeDesign(List<String> patterns, String design) {
        for (String pattern : patterns) {
            if (design.equals(pattern)) {
                return true;
            }

            if (design.startsWith(pattern)) {
                String remainder = design.substring(pattern.length());
                if (canMakeDesign(patterns, remainder)) {
                    return true;
                }
            }
        }

        return false;
    }

    Map<String, Long> memoizedResults = new HashMap<>();
    long canMakeDesign2(List<String> patterns, String design) {
        if (memoizedResults.containsKey(design)) {
            return memoizedResults.get(design);
        }

        long count = 0;
        for (String pattern : patterns) {
            if (design.equals(pattern)) {
                ++count;
            } else if (design.startsWith(pattern)) {
                String remainder = design.substring(pattern.length());
                long result = canMakeDesign2(patterns, remainder);
                memoizedResults.put(remainder, result);
                count += result;
            }
        }

        return count;
    }



    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        List<String> patterns = new ArrayList<>();
        GroupItem group0 = configGroup.get(0);
        for (ItemLine line : group0) {
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
                patterns.add(line.getString(itemNum));
            }
        }

        List<String> designs = new ArrayList<>();
        // Here's code for a 2nd group, if needed
        GroupItem group1 = configGroup1.get(0);
        for (ItemLine line : group1) {
            designs.add(line.getString(0));
        }

        long count = 0L;

        for (String design : designs) {
            count += canMakeDesign2(patterns, design);
        }
        return count;
    }
}
