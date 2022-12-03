package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.List;

public class Puzzle1 extends InputParser {
    public Puzzle1(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, "(\\d+)", null);
    }

    @Override
    protected void processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int increases = 0;
        Integer prior = null;
        for (GroupItem item: configGroup) {
            for (ItemLine line: item) {
                for (String element: line) {
                    Integer value = Integer.parseInt(element);
                    if (prior != null) {
                        if (value > prior) {
                            ++increases;
                        }
                    }
                    prior = value;
                }
            }
        }
        System.out.println(increases);
    }

    @Override
    protected void processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int increases = 0;

        List<Integer> lists = new ArrayList<>();
        Integer[] trailing = new Integer[] { 0, 0 };
        int index = 0;
        for (GroupItem item: configGroup) {
            for (ItemLine line: item) {
                for (String element: line) {
                    Integer number = Integer.parseInt(element);
                    Integer value = number + trailing[0] + trailing[1];
                    lists.add(value);

                    trailing[index % 2] = number;

                    if ((index >= 3) && (lists.get(index) > lists.get(index-1))) {
                        ++increases;
                    }

                    ++index;
                }
            }
        }
        System.out.println(increases);
    }
}
