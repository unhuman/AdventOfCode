package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.awt.*;
import java.util.HashMap;

public class Day5 extends InputParser {
    private static final String regex1 = "(\\d+),(\\d+) -> (\\d+),(\\d+)";
    private static final String regex2 = null;

    public Day5() {
        super(2021, 5, regex1, regex2);
    }

    public Day5(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        HashMap<Point, Integer> ocean = new HashMap<>();
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                int x1 = Integer.parseInt(line.get(0));
                int y1 = Integer.parseInt(line.get(1));
                int x2 = Integer.parseInt(line.get(2));
                int y2 = Integer.parseInt(line.get(3));

                // horizontal / vertical lines only
                if (x1 != x2 && y1 != y2) {
                    continue;
                }

                if (x1 > x2) {
                    int swap = x1;
                    x1 = x2;
                    x2 = swap;
                }
                if (y1 > y2) {
                    int swap = y1;
                    y1 = y2;
                    y2 = swap;
                }

                for (int x = x1; x <= x2; x++) {
                    for (int y = y1; y <= y2; y++) {
                        Point p = new Point(x, y);
                        if (!ocean.containsKey(p)) {
                            ocean.put(p, 0);
                        }
                        ocean.put(p, ocean.get(p) + 1);
                    }
                }
            }
        }

        long count = ocean.values().stream().filter(value -> value >= 2).count();

        return count;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        HashMap<Point, Integer> ocean = new HashMap<>();
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                int x1 = Integer.parseInt(line.get(0));
                int y1 = Integer.parseInt(line.get(1));
                int x2 = Integer.parseInt(line.get(2));
                int y2 = Integer.parseInt(line.get(3));

                int xInc = 1;
                int xDest = x2 + 1;
                if (x1 > x2) {
                    xInc = -1;
                    xDest = x2 - 1;
                }
                int yInc = 1;
                int yDest = y2 + 1;
                if (y1 > y2) {
                    yInc = -1;
                    yDest = y2 - 1;
                }

                boolean diagonal = (Math.abs(x2 - x1) == Math.abs(y2 - y1));

                // horizontal / vertical lines only
                if (x1 != x2 && y1 != y2 && !diagonal) {
                    continue;
                }

                for (int x = x1; x != xDest; x += (diagonal) ? 0 : xInc) {
                    for (int y = y1; y != yDest; y += yInc, x += (diagonal) ? xInc : 0) {
                        Point p = new Point(x, y);
                        if (!ocean.containsKey(p)) {
                            ocean.put(p, 0);
                        }
                        ocean.put(p, ocean.get(p) + 1);
                    }
                }
            }
        }

        long count = ocean.values().stream().filter(value -> value >= 2).count();

        return count;    }
}
