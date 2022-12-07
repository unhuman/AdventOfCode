package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Day6 extends InputParser {
    private static final String regex1 = "(\\w)";
    private static final String regex2 = null;

    public Day6(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        return process(dataItems1, 4);
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        return process(dataItems1, 14);
    }

    protected Object process(ConfigGroup dataItems1, int groupCount) {
        for (GroupItem item : dataItems1) {
            for (ItemLine line : item) {
                for (int i = 0; i < line.size(); i++) {
                    boolean foundDupe = false;
                    if (i >= groupCount) {
                        for (int j = i - groupCount + 1; j <= i -1; j++) {
                            for (int k = j + 1; k <= i; k++) {
                                if (line.get(j).charAt(0) == line.get(k).charAt(0)) {
                                    foundDupe = true;
                                    break;
                                }
                            }
                            if (foundDupe) {
                                break;
                            }
                        }

                        if (!foundDupe) {
                            return i + 1;
                        }
                    }
                }
            }
        }
        throw new RuntimeException("bad data");
    }
}
