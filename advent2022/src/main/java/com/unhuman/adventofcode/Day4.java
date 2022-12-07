package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Day4 extends InputParser {
    private static final String regex1 = "(\\d+)\\-(\\d+),(\\d+)\\-(\\d+)";
    private static final String regex2 = null;

    public Day4(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int count = 0;
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                int start1 = Integer.parseInt(line.get(0));
                int end1 = Integer.parseInt(line.get(1));
                int start2 = Integer.parseInt(line.get(2));
                int end2 = Integer.parseInt(line.get(3));

                if ((start1 >= start2 && end1 <= end2) || (start2 >= start1 && end2 <= end1)) {
                    ++count;
                }
            }
        }
        return count;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int count = 0;
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                int start1 = Integer.parseInt(line.get(0));
                int end1 = Integer.parseInt(line.get(1));
                int start2 = Integer.parseInt(line.get(2));
                int end2 = Integer.parseInt(line.get(3));

                for (int i = start2; i <= end2; i++) {
                    if (i >= start1 && i <= end1) {
                        ++count;
                        break;
                    }
                }
            }
        }
        return count;
    }
}
