package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.List;

public class Day2 extends InputParser {
    enum RateType {
        UNKNOWN,
        INCREASING,
        DECREASING
    }
    private static final String regex1 = "(\\d+)\\s*";
    private static final String regex2 = null;

    public Day2() {
        super(2024, 2, regex1, regex2);
    }

    public Day2(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);

        int count = 0;
        for (ItemLine line : item) {
            List<Integer> report = new ArrayList<>();
            for (String element : line) {
                report.add(Integer.parseInt(element));
            }
            if (isSafe(report)) {
                count += 1;
            }
        }

        return count;
    }

    private boolean isSafe(List<Integer> report) {
        int current = report.get(0);
        RateType rateType = RateType.UNKNOWN;
        for (int i = 1; i < report.size(); i++) {
            int next = report.get(i);
            int diff = Math.abs(next - current);
            if (diff <= 0 || diff > 3) {
                return false;
            }
            RateType nextRateType = (next > current) ? RateType.INCREASING : RateType.DECREASING;
            if (rateType != RateType.UNKNOWN && nextRateType != rateType) {
                return false;
            }
            rateType = nextRateType;
            current = next;
        }
        return true;
    }

    private boolean isSafe2(List<Integer> report) {
        if (isSafe(report)) {
            return true;
        }
        for (int i = 0; i < report.size(); i++) {
            List<Integer> reportClone = new ArrayList<>(report);
            reportClone.remove(i);
            if (isSafe(reportClone)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);

        int count = 0;
        for (ItemLine line : item) {
            List<Integer> report = new ArrayList<>();
            for (String element : line) {
                report.add(Integer.parseInt(element));
            }
            if (isSafe2(report)) {
                count += 1;
            }
        }

        return count;
    }
}
