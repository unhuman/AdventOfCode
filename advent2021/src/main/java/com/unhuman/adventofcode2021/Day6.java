package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day6 extends InputParser {
    private static final String regex1 = "(\\d+),?";
    private static final String regex2 = null;

    public Day6() {
        super(2021, 6, regex1, regex2);
    }

    public Day6(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Integer> fish = new ArrayList<>();
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                for (String element : line) {
                    fish.add(Integer.parseInt(element));
                }
            }
        }

        for (int i = 0; i < 80; i++) {
            List<Integer> newFish = new ArrayList<>();
            for (int j = 0; j < fish.size(); j++) {
                int newCounter = fish.get(j) - 1;
                if (newCounter == -1) {
                    newCounter = 6;
                    newFish.add(8);
                }
                fish.set(j, newCounter);
            }
            fish.addAll(newFish);
            // System.out.println((i + 1) + ": " + fish.size());
        }

        return fish.size();
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Map<Integer, Long> fish = new HashMap();
        for (int i = 0; i <= 8; i++) {
            fish.put(i, 0L);
        }
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                for (String element : line) {
                    int daysOld = Integer.parseInt(element);
                    fish.put(daysOld, fish.get(daysOld) + 1);
                }
            }
        }

        for (int i = 0; i < 256; i++) {
            Map<Integer, Long> newFish = new HashMap();
            for (int j = 8; j >= 0; j--) {
                long fishAtAge = fish.get(j);
                int newAge = j - 1;
                if (newAge == -1) {
                    newFish.put(6, newFish.get(6) + fishAtAge);
                    newFish.put(8, fishAtAge);
                } else {
                    newFish.put(newAge, fishAtAge);
                }
            }
            fish = newFish;

            // System.out.println((i + 1) + ": " + newFish.toString());
        }

        long total = fish.values().stream().mapToLong(Long::longValue).sum();
        return total;
    }
}
