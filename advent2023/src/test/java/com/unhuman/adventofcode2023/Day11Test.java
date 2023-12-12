package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day11Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "...#......\n" +
            ".......#..\n" +
            "#.........\n" +
            "..........\n" +
            "......#...\n" +
            ".#........\n" +
            ".........#\n" +
            "..........\n" +
            ".......#..\n" +
            "#...#.....\n";

    static Day11 day;
    static {
        day = new Day11(DATA);
    }

    @Test
    public void test1() {
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(374L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2a() {
        ConfigGroup[] groups = day.parseFiles();
        day.setGapMultiplier(10);
        Assertions.assertEquals(1030L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2b() {
        ConfigGroup[] groups = day.parseFiles();
        day.setGapMultiplier(100);
        Assertions.assertEquals(8410L, day.processInput2(groups[0], groups[1]));
    }
}
