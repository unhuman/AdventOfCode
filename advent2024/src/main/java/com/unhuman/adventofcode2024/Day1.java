package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day1 extends InputParser {
    private static final String regex1 = "(\\d+) +(\\d+)";
    private static final String regex2 = null;

    public Day1() {
        super(2024, 1, regex1, regex2);
    }

    public Day1(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        for (ItemLine line : item) {
            left.add(Integer.parseInt(line.get(0)));
            right.add(Integer.parseInt(line.get(1)));
        }

        Collections.sort(left);
        Collections.sort(right);

        int total = 0;
        for (int i = 0; i < left.size(); i++) {
            total += Math.abs(left.get(i) - right.get(i));
        }

        return total;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {

        List<Integer> left = new ArrayList<>();
        Map<Integer, Integer> right = new HashMap<>();
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        for (ItemLine line : item) {
            left.add(Integer.parseInt(line.get(0)));
            Integer rightValue = Integer.parseInt(line.get(1));
            if (!right.containsKey(rightValue)) {
                right.put(rightValue, 1);
            } else {
                right.put(rightValue, right.get(rightValue) + 1);
            }
        }

        Collections.sort(left);

        int total = 0;
        for (int i = 0; i < left.size(); i++) {
            Integer lookValue = left.get(i);
            Integer founds = right.getOrDefault(lookValue, 0);
            total += lookValue * founds;
        }

        return total;
    }
}
