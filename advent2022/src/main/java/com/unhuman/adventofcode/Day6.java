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
        Queue<Character> last4 = new ConcurrentLinkedQueue<>();
        int counter;

        for (GroupItem item : dataItems1) {
            for (ItemLine line : item) {
                for (int i = 0; i < line.size(); i++) {
                    Character character = line.get(i).charAt(0);
                    boolean foundDupe = false;
                    if (last4.size() == 4) {
                        Object[] last4Array = last4.toArray();
                        for (int j = 0; j < last4.size() - 1; j++) {
                            for (int k = j + 1; k < last4.size(); k++) {
                                if (last4Array[j].equals(last4Array[k])) {
                                    foundDupe = true;
                                }
                            }
                        }

                        if (!foundDupe) {
                            System.out.println(i);
                            return;
                        }

                        last4.remove();
                    }
                    last4.add(character);
                }
            }
        }
    }

    @Override
    protected void processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        Queue<Character> last14 = new ConcurrentLinkedQueue<>();
        int counter;
git 
        for (GroupItem item : dataItems1) {
            for (ItemLine line : item) {
                for (int i = 0; i < line.size(); i++) {
                    Character character = line.get(i).charAt(0);
                    boolean foundDupe = false;
                    if (last14.size() == 14) {
                        Object[] last4Array = last14.toArray();
                        for (int j = 0; j < last14.size() - 1; j++) {
                            for (int k = j + 1; k < last14.size(); k++) {
                                if (last4Array[j].equals(last4Array[k])) {
                                    foundDupe = true;
                                }
                            }
                        }

                        if (!foundDupe) {
                            System.out.println(i);
                            return;
                        }

                        last14.remove();
                    }
                    last14.add(character);
                }
            }
        }
    }
}
