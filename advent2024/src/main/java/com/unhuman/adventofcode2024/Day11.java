package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.ListHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Long blink2(Map<Long, Long> lineMap, int roundsLeft) {
        if (roundsLeft == 0) {
            Long total = 0L;

            for (Map.Entry<Long, Long> entry : lineMap.entrySet()) {
                total += entry.getValue();
            }

            return total;
        }

        List<Long> blinked = new ArrayList<>();

        Map<Long, Long> nextLineMap = new HashMap<>();

        lineMap.forEach((key, count) -> {
            List<Long> nextResults = blinkRules(key);
            nextResults.forEach(next -> {
                if (!nextLineMap.containsKey(next)) {
                    nextLineMap.put(next, count);
                } else {
                    nextLineMap.put(next, nextLineMap.get(next) + count);
                }
            });
        });

        System.out.println("RoundsLeft = " + roundsLeft + " " + lineMap);
        return (blink2(nextLineMap, roundsLeft - 1));
    }


    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Long> data = new ArrayList<>();

        ItemLine line = configGroup.get(0).get(0);
        for (int itemNum = 0; itemNum < line.size(); itemNum++) {
            data.add(line.getLong(itemNum));
        }

        Map<Long, Long> itemCounts = (Map<Long, Long>) ListHelper.getItemCounts(data);

        return blink2(itemCounts, 75);
    }
}
