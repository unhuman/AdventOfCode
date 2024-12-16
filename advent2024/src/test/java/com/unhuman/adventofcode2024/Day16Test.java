package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day16Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "\n";

    static InputParser getDay(String data) {
        return new Day16(data);
    }

    @Test
    public void test0a() {
        String data =
                "#####\n" +
                "###E#\n" +
                "###.#\n" +
                "#S..#\n" +
                "#####";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1004L, day.processInput1(groups[0], groups[1]));
    }



    @Test
    public void test1a() {
        String data = "###############\n" +
                "#.......#....E#\n" +
                "#.#.###.#.###.#\n" +
                "#.....#.#...#.#\n" +
                "#.###.#####.#.#\n" +
                "#.#.#.......#.#\n" +
                "#.#.#####.###.#\n" +
                "#...........#.#\n" +
                "###.#.#####.#.#\n" +
                "#...#.....#.#.#\n" +
                "#.#.#.###.#.#.#\n" +
                "#.....#...#.#.#\n" +
                "#.###.#.#.#.#.#\n" +
                "#S..#.....#...#\n" +
                "###############";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(7036L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1b() {
        String data = "#################\n" +
                "#...#...#...#..E#\n" +
                "#.#.#.#.#.#.#.#.#\n" +
                "#.#.#.#...#...#.#\n" +
                "#.#.#.#.###.#.#.#\n" +
                "#...#.#.#.....#.#\n" +
                "#.#.#.#.#.#####.#\n" +
                "#.#...#.#.#.....#\n" +
                "#.#.#####.#.###.#\n" +
                "#.#.#.......#...#\n" +
                "#.#.###.#####.###\n" +
                "#.#.#...#.....#.#\n" +
                "#.#.#.#####.###.#\n" +
                "#.#.#.........#.#\n" +
                "#.#.#.#########.#\n" +
                "#S#.............#\n" +
                "#################\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(11048L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(0L, day.processInput2(groups[0], groups[1]));
    }
}
