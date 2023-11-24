package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Day8 extends InputParser {
    private static final String regex1 = "(\\d)";
    private static final String regex2 = null;

    public Day8() {
        super(2022, 8, regex1, regex2);
    }

    public Day8(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        GroupItem item = dataItems1.get(0);
        int vert = item.size();
        int horiz = item.get(0).size();
        int[][] trees = new int[horiz][vert];
        int[][] visibles = new int[horiz][vert];

        // assume edges are visible (don't double-count corners)
        int visibleCount = horiz * 2 + vert * 2 - 4;

        for (int y = 0; y < item.size(); y++) {
            ItemLine line = item.get(y);
            for (int x = 0; x < line.size(); x++) {
                int element = Integer.parseInt(line.get(x));
                trees[y][x] = element;
                visibles[y][x] = 0;
            }
        }

        // approach from every path - keeping track of highest

        // left to right
        for (int y = 1; y < vert - 1; y++) {
            int highest = trees[y][0];
            for (int x = 1; x < horiz - 1; x++) {
                if (trees[y][x] > highest) {
                    visibles[y][x] = 1;
                    highest = trees[y][x];
                }
            }
        }

        // right to left
        for (int y = 1; y < vert - 1; y++) {
            int highest = trees[y][horiz - 1];
            for (int x = horiz - 2; x > 0; x--) {
                if (trees[y][x] > highest) {
                    visibles[y][x] = 1;
                    highest = trees[y][x];
                }
            }
        }

        // top to bottom
        for (int x = 1; x < horiz - 1; x++) {
            int highest = trees[0][x];
            for (int y = 1; y < vert - 1; y++) {
                if (trees[y][x] > highest) {
                    visibles[y][x] = 1;
                    highest = trees[y][x];
                }
            }
        }

        // bottom to top
        for (int x = 1; x < horiz - 1; x++) {
            int highest = trees[vert - 1][x];
            for (int y = vert - 2; y > 0; y--) {
                if (trees[y][x] > highest) {
                    visibles[y][x] = 1;
                    highest = trees[y][x];
                }
            }
        }

        // count up all the visibles
        for (int x = 1; x < horiz - 1; x++) {
            for (int y = 1; y < vert - 1; y++) {
                visibleCount += visibles[y][x];
            }
        }

        return visibleCount;
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        GroupItem item = dataItems1.get(0);
        int vert = item.size();
        int horiz = item.get(0).size();
        int[][] trees = new int[horiz][vert];

        // assume edges are visible (don't double-count corners)
        int visibleCount = horiz * 2 + vert * 2 - 4;

        for (int y = 0; y < item.size(); y++) {
            ItemLine line = item.get(y);
            for (int x = 0; x < line.size(); x++) {
                int element = Integer.parseInt(line.get(x));
                trees[y][x] = element;
            }
        }

        int maxView = 0;
        for (int x = 1; x < horiz - 1; x++) {
            for (int y = 1; y < vert -1; y++) {
                int seeUp = look(trees, horiz, vert, x, y, 0, -1);
                int seeDown = look(trees, horiz, vert, x, y, 0, 1);
                int seeLeft = look(trees, horiz, vert, x, y, -1, 0);
                int seeRight = look(trees, horiz, vert, x, y, 1, 0);

                int calcView = seeUp * seeDown * seeLeft * seeRight;
                if (calcView > maxView) {
                    maxView = calcView;
                }
            }
        }
        return maxView;
    }

    public int look(int[][] trees, int horiz, int vert, int x, int y, int lookHoriz, int lookVert) {
        int distance = 0;
        int maxHeight = trees[y][x];
        for (x += lookHoriz, y += lookVert; x >= 0 && x < horiz && y >= 0 && y < vert; x += lookHoriz, y += lookVert) {
            distance++;

            // We're done if we find something our height or more
            if (trees[y][x] >= maxHeight) {
                break;
            }
        }
        return distance;
    }
}
