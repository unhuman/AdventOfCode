package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Day6 extends InputParser {
    private static final String regex1 = "(\\w)";
    private static final String regex2 = null;

    public Day6(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    @Override
    protected void processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        process(dataItems1, 4);
    }

    @Override
    protected void processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        process(dataItems1, 14);
    }

    protected void process(ConfigGroup dataItems1, int groupCount) {
        Queue<Character> lookback = new ConcurrentLinkedQueue<>();
        for (GroupItem item : dataItems1) {
            for (ItemLine line : item) {
                for (int i = 0; i < line.size(); i++) {
                    Character character = line.get(i).charAt(0);
                    boolean foundDupe = false;
                    if (lookback.size() == groupCount) {
                        Object[] last4Array = lookback.toArray();
                        for (int j = 0; j < lookback.size() - 1; j++) {
                            for (int k = j + 1; k < lookback.size(); k++) {
                                if (last4Array[j].equals(last4Array[k])) {
                                    foundDupe = true;
                                }
                            }
                        }

                        if (!foundDupe) {
                            System.out.println(i);
                            return;
                        }

                        lookback.remove();
                    }
                    lookback.add(character);
                }
            }
        }
    }

}
