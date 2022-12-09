package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.awt.Point;
import java.util.HashSet;

public class Day9 extends InputParser {
    private static final String regex1 = "(\\w) (\\d+)";
    private static final String regex2 = null;

    public Day9(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        HashSet<Point> trail = new HashSet<>();
        int headX = 0;
        int headY = 0;
        int tailX = headX;
        int tailY = headY;
        for (GroupItem item : dataItems1) {
            for (ItemLine line : item) {
                String command = line.get(0);
                int distance = Integer.parseInt(line.get(1));
                for (int i = 0; i < distance; i++) {
                    int priorX = headX;
                    int priorY = headY;

                    switch (command) {
                        case "U":
                            headY--;
                            break;
                        case "D":
                            headY++;
                            break;
                        case "L":
                            headX--;
                            break;
                        case "R":
                            headX++;
                            break;
                    }

                    int xDelta = headX - tailX;
                    int yDelta = headY - tailY;
                    if (Math.abs(xDelta) >= 2 || Math.abs(yDelta) >= 2) {
                        tailX = priorX;
                        tailY = priorY;
                    }

                    trail.add(new Point(tailX, tailY));
                }
            }
        }

        return trail.size();
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        return 2;
    }
}
