package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day13Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "#.##..##.\n" +
            "..#.##.#.\n" +
            "##......#\n" +
            "##......#\n" +
            "..#.##.#.\n" +
            "..##..##.\n" +
            "#.#.##.#.\n" +
            "\n" +
            "#...##..#\n" +
            "#....#..#\n" +
            "..##..###\n" +
            "#####.##.\n" +
            "#####.##.\n" +
            "..##..###\n" +
            "#....#..#";

    static InputParser day;

    @Test
    public void test1() {
        day = new Day13(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(405L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        day = new Day13(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(400L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void testCustomFinalData() {
        String data =
                "###...#...#\n" +
                "###..#..#.#\n" +
                ".#.##.#..##\n" +
                ".#.#..##.##\n" +
                "#...##.#..#\n" +
                ".##.....#..\n" +
                "..#..######\n" +
                ".....#.##.#\n" +
                ".#..#.###.#\n" +
                "#.#.#.#.#..\n" +
                "#.#.###.#..\n" +
                "#.#.###.#..\n" +
                "#.#.#.#.#..";
        day = new Day13(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1200L, day.processInput2(groups[0], groups[1]));
    }
}
