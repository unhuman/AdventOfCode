package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day11 extends InputParser {
    private static final String regex1 = "(\\d+)\\s*";
    private static final String regex2 = null;

    public Day11() {
        super(2024, 11, regex1, regex2);
    }

    public Day11(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Long> lineMap = new ArrayList<>();

        ItemLine line = configGroup.get(0).get(0);
        for (int itemNum = 0; itemNum < line.size(); itemNum++) {
            lineMap.add(line.getLong(itemNum));
        }

        for (int i = 0; i < 25; i++) {
            lineMap = blink(lineMap);
        }

        return (long) lineMap.size();
    }

    public List<Long> blinkRules(Long value) {
        List<Long> expansion;
        if (value == 0) {
            expansion = Collections.singletonList(1L);
        } else if (value.toString().length() % 2 == 0) {
            int split = value.toString().length() / 2;
            long firstHalf = Long.parseLong(value.toString().substring(0, split));
            long secondHalf = Long.parseLong(value.toString().substring(split));
            expansion = Arrays.asList(firstHalf, secondHalf);
        } else {
            expansion = Collections.singletonList(value * 2024);
        }
        return expansion;
    }


    public List<Long> blink(List<Long> lineMap) {
        List<Long> blinked = new ArrayList<>();
        for (int i = 0; i < lineMap.size(); i++) {
            blinked.addAll(blinkRules(lineMap.get(i)));
        }
        return blinked;
    }

    public List<Long> blink2(Long value, int roundsLeft) {
        if (roundsLeft == 0) {
            return Collections.singletonList(value);
        }

        List<Long> result = new ArrayList<>();

        List<Long> nextResults = blinkRules(value);
        for (Long next: nextResults) {
            result.addAll(blink2(next, roundsLeft - 1));
        }

        return result;
    }

    public List<Long> blink2(List<Long> lineMap, int roundsLeft) {
        List<Long> blinked = new ArrayList<>();
        for (int i = 0; i < lineMap.size(); i++) {
            blinked.addAll(blink2(lineMap.get(i), roundsLeft));
        }
        return blinked;
    }


    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Long> lineMap = new ArrayList<>();

        ItemLine line = configGroup.get(0).get(0);
        for (int itemNum = 0; itemNum < line.size(); itemNum++) {
            lineMap.add(line.getLong(itemNum));
        }

        lineMap = blink2(lineMap, 25);

        return (long) lineMap.size();
    }
}
