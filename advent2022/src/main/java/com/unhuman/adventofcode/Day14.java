package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Day14 extends InputParser {
    private static final String regex1 = "(\\d+),(\\d+)( -> )?";
    private static final String regex2 = null;

    private static final int EMPTY = 0;
    private static final int WALL = 1;
    private static final int SAND = 2;

    public Day14(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        int[][] cave = new int[1000][1000];
        int caveDepth = prepareCaveFindDepth(dataItems1, cave);

        return doGravity(caveDepth, cave);
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        int[][] cave = new int[1000][1000];
        int caveDepth = prepareCaveFindDepth(dataItems1, cave);

        // add a platform
        caveDepth += 2;
        for (int x = 0; x < 1000; x++) {
            cave[caveDepth ][x] = WALL;
        }

        return doGravity(caveDepth, cave);
    }

    private int prepareCaveFindDepth(ConfigGroup dataItems1, int[][] cave) {
        int caveDepth = Integer.MIN_VALUE;
        for (GroupItem item : dataItems1) {
            for (ItemLine line : item) {
                Integer lastX = null;
                Integer lastY = null;
                for (int i = 0; i < line.size(); i += 3) {
                    int newX = Integer.parseInt(line.get(i));
                    int newY = Integer.parseInt(line.get(i + 1));

                    if (lastX == null) {
                        lastX = newX;
                        lastY = newY;
                    }

                    int minX = Math.min(newX, lastX);
                    int maxX = Math.max(newX, lastX);
                    int minY = Math.min(newY, lastY);
                    int maxY = Math.max(newY, lastY);

                    if (maxY > caveDepth) {
                        caveDepth = maxY;
                    }

                    for (int x = minX; x <= maxX; x++) {
                        for (int y = minY; y <= maxY; y++) {
                            cave[y][x] = WALL;
                        }
                    }

                    lastX = newX;
                    lastY = newY;
                }
            }
        }
        return caveDepth;
    }

    public int doGravity(int caveDepth, int[][] cave) {
        int restedItems = 0;
        boolean dataIsStoring = true;
        while (dataIsStoring) {
            int sandX = 500;
            int sandY = 0;

            // See if we can even start the sand falling
            if (cave[sandY][sandX] != EMPTY) {
                // can't even start
                break;
            }

            boolean sandFell = true;
            while (sandFell) {
                int checkY = sandY + 1;

                if (cave[checkY][sandX] == EMPTY) {
                    // gravity down
                    sandY = checkY;
                } else if (cave[checkY][sandX - 1] == EMPTY) {
                    // gravity down-left
                    sandY = checkY;
                    sandX--;
                } else if (cave[checkY][sandX + 1] == EMPTY) {
                    // gravity down-right
                    sandY = checkY;
                    sandX++;
                } else {
                    sandFell = false;
                    restedItems++;
                    cave[sandY][sandX] = SAND;
                }

                if (sandY > caveDepth) {
                    sandFell = false;
                    dataIsStoring = false;
                }
            }
        }

        return restedItems;
    }
}
