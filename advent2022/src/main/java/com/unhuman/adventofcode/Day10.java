package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Day10 extends InputParser {
    private static final String regex1 = "(\\w+)(\\s+\\-?\\d+)?";
    private static final String regex2 = null;

    public Day10(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        int cycle = 1;
        int x = 1;
        int signalStrength = 0;
        for (GroupItem item : dataItems1) {
            for (ItemLine line : item) {
                String instruction = line.get(0);
                int processCycles = 0;
                int addAfterCycles = 0;
                switch (instruction) {
                    case "noop":
                        processCycles = 1;
                        break;
                    case "addx":
                        processCycles = 2;
                        addAfterCycles = Integer.parseInt(line.get(1).trim());
                        break;
                }

                for (int i = 0; i < processCycles; i++) {
                    if (cycle == 20 || (cycle - 20) % 40 == 0) {
                        signalStrength += cycle * x;
                    }
                    cycle++;
                }

                x += addAfterCycles;
            }
        }

        // catch any leftover
        if (cycle == 20 || (cycle - 20) % 40 == 0) {
            signalStrength += cycle * x;
        }

        return signalStrength;
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        int cycle = 1;
        int x = 1;

        char[][] screen = new char[6][40];

        for (GroupItem item : dataItems1) {
            for (ItemLine line : item) {
                String instruction = line.get(0);
                int processCycles = 0;
                int addAfterCycles = 0;
                switch (instruction) {
                    case "noop":
                        processCycles = 1;
                        break;
                    case "addx":
                        processCycles = 2;
                        addAfterCycles = Integer.parseInt(line.get(1).trim());
                        break;
                }

                for (int i = 0; i < processCycles; i++) {
                    int col = (cycle - 1) % 40;
                    int row = (cycle - 1) / 40;

                    boolean overlap = (col == x - 1 || col == x || col == x + 1);
                    char pixel = (overlap) ? 'X' : '.';
                    screen[row][col] = pixel;

                    cycle++;
                }

                x += addAfterCycles;
            }
        }

        // catch any leftover
        String results = "";
        for (int i = 0; i < 6; i++) {
            results += new String(screen[i]) + "\n";
        }
        System.out.println(results);

        return results;
    }
}
