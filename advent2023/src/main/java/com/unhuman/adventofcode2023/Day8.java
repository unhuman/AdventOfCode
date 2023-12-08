package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day8 extends InputParser {
    private static final String regex1 = "([RL])";
    private static final String regex2 =  "(\\w{3}) = \\((\\w{3}), (\\w{3})\\)";

    public Day8() {
        super(2023, 8, regex1, regex2);
    }

    public Day8(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Character> path = new ArrayList<>();
        Map<String, Pair<String, String>> map = new HashMap<>();
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                for (String element : line) {
                    path.add(element.charAt(0));
                }
            }
        }
        for (GroupItem item : configGroup1) {
            for (ItemLine line : item) {
                map.put(line.get(0), new Pair<>(line.get(1), line.get(2)));
            }
        }

        String current = "AAA";
        String end = "ZZZ";

        int steps = 0;
        while (!current.equals(end)) {
            Pair<String, String> choices = map.get(current);
            int nextStepIndex = steps % path.size();
            Character nextStep = path.get(nextStepIndex);
            current = (nextStep == 'L') ? choices.getLeft(): choices.getRight();
            ++steps;
        }
        return steps;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Character> path = new ArrayList<>();
        Map<String, Pair<String, String>> map = new HashMap<>();
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                for (String element : line) {
                    path.add(element.charAt(0));
                }
            }
        }
        for (GroupItem item : configGroup1) {
            for (ItemLine line : item) {
                map.put(line.get(0), new Pair<>(line.get(1), line.get(2)));
            }
        }

        List<String> currents = new ArrayList<>(map.keySet().stream().filter(current -> current.endsWith("A")).toList());

        List<Long> times = currents.stream().map(current -> findPaths(map, path, current)).toList();

        long lcm = times.get(0);
        for (int i = 1; i < times.size(); i++) {
            long higher = Math.max(lcm, times.get(i));
            long lower = Math.min(lcm, times.get(i));
            lcm = higher;
            while (lcm % lower != 0) {
                lcm += higher;
            }
        }
        return lcm;
    }

    Long findPaths(Map<String, Pair<String, String>> map,
                         List<Character> path, String current) {
        List<Long> finishCounts = new ArrayList<>();
        long steps = -1L;
        while (true) {
            ++steps;
            int nextStepIndex = (int) (steps % path.size());
            Character nextStep = path.get(nextStepIndex);
            Pair<String, String> choices = map.get(current);
            current = (nextStep == 'L') ? choices.getLeft() : choices.getRight();
            if (current.endsWith("Z")) {
                return steps + 1;
            }
        }
    }
}
