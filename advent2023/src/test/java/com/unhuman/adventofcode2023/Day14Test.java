package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day14Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
                    "O....#....\n" +
                    "O.OO#....#\n" +
                    ".....##...\n" +
                    "OO.#O....O\n" +
                    ".O.....O#.\n" +
                    "O.#..O.#.#\n" +
                    "..O..#O..O\n" +
                    ".......O..\n" +
                    "#....###..\n" +
                    "#OO..#....";

    static Day14 day;
    static {
        day = new Day14(DATA);
    }

    @Test
    public void test1() {
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(136L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(64L, day.processInput2(groups[0], groups[1]));
    }
}
