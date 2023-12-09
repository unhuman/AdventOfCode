package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.List;

public class Day9 extends InputParser {
    private static final String regex1 = "(-?\\d+)\\s?";
    private static final String regex2 = null;

    public Day9() {
        super(2023, 9, regex1, regex2);
    }

    public Day9(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        long total = 0;
        List<List<Long>> problems = new ArrayList<>();
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                List<Long> problem = new ArrayList<>();
                for (String element : line) {
                    problem.add(Long.parseLong(element));
                }
                problems.add(problem);
            }
        }

        for (List<Long> problem: problems) {
            List<List<Long>> solution = new ArrayList<>();
            solution.add(problem);
            List<Long> current = solution.get(0);
            while(true) {
                List<Long> diffs = new ArrayList<>();
                boolean allZeros = true;
                for (int i = 0; i < current.size() - 1; i++) {
                    long diff = current.get(i + 1) - current.get(i);
                    diffs.add(diff);
                    if (diff != 0) {
                        allZeros = false;
                    }
                }
                solution.add(diffs);
                current = diffs;
                if (allZeros) {
                    break;
                }
            }

            // work the answer back up
            long lastValue = 0;
            System.out.println(problem);
            for (int i = solution.size() - 1; i >= 1; --i) {
                List<Long> diffs = solution.get(i);
                long diff = diffs.get(diffs.size() - 1);

                List<Long> adjust = solution.get(i - 1);
                lastValue = adjust.get(adjust.size() - 1) + diff;
                adjust.add(lastValue);
            }
            total += lastValue;
        }

        return total;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        long total = 0;
        List<List<Long>> problems = new ArrayList<>();
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                List<Long> problem = new ArrayList<>();
                for (int i = line.size() - 1; i >= 0; --i) {
                    String element = line.get(i);
                    problem.add(Long.parseLong(element));
                }
                problems.add(problem);
            }
        }

        for (List<Long> problem: problems) {
            List<List<Long>> solution = new ArrayList<>();
            solution.add(problem);
            List<Long> current = solution.get(0);
            while(true) {
                List<Long> diffs = new ArrayList<>();
                boolean allZeros = true;
                for (int i = 0; i < current.size() - 1; i++) {
                    long diff = current.get(i + 1) - current.get(i);
                    diffs.add(diff);
                    if (diff != 0) {
                        allZeros = false;
                    }
                }
                solution.add(diffs);
                current = diffs;
                if (allZeros) {
                    break;
                }
            }

            // work the answer back up
            long lastValue = 0;
            System.out.println(problem);
            for (int i = solution.size() - 1; i >= 1; --i) {
                List<Long> diffs = solution.get(i);
                long diff = diffs.get(diffs.size() - 1);

                List<Long> adjust = solution.get(i - 1);
                lastValue = adjust.get(adjust.size() - 1) + diff;
                adjust.add(lastValue);
            }
            total += lastValue;
        }

        return total;
    }
}
