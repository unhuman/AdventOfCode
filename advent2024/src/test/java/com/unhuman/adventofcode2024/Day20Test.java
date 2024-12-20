package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day20Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA = "###############\n" +
            "#...#...#.....#\n" +
            "#.#.#.#.#.###.#\n" +
            "#S#...#.#.#...#\n" +
            "#######.#.#.###\n" +
            "#######.#.#...#\n" +
            "#######.#.###.#\n" +
            "###..E#...#...#\n" +
            "###.#######.###\n" +
            "#...###...#...#\n" +
            "#.#####.#.###.#\n" +
            "#.#...#.#.#...#\n" +
            "#.#.#.#.#.#.###\n" +
            "#...#...#...###\n" +
            "###############\n";

    static Day20 getDay(String data) {
        return new Day20(data);
    }

    @Test
    public void test0A() {
        String data =
                "####" + "\n" +
                        "#S #" + "\n" +
                        "## #" + "\n" +
                        "#E #" + "\n" +
                        "####" + "\n";
        Day20 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        day.setSavings(0);
        Assertions.assertEquals(1L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test0B() {
        String data =
                "#####" + "\n" +
                        "#S  #" + "\n" +
                        "### #" + "\n" +
                        "#E  #" + "\n" +
                        "#####" + "\n";
        Day20 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        day.setSavings(0);
        Assertions.assertEquals(2L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test0C() {
        String data =
                "#####" + "\n" +
                "#S  #" + "\n" +
                "### #" + "\n" +
                "#   #" + "\n" +
                "# ###" + "\n" +
                "#  E#" + "\n" +
                "#####" + "\n";
        Day20 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        day.setSavings(0);
        Assertions.assertEquals(4L, day.processInput1(groups[0], groups[1]));
    }


    @Test
    public void test1() {
        String data = DATA;
        Day20 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        day.setSavings(0);
        Assertions.assertEquals(44L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(0L, day.processInput2(groups[0], groups[1]));
    }
}
