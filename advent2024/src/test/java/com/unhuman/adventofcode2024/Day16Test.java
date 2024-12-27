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
        Assertions.assertEquals(1004, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test0b() {
        String data =
                        "#####\n" +
                        "#S..#\n" +
                        "#.#.#\n" +
                        "#...#\n" +
                        "#.###\n" +
                        "#..E#\n" +
                        "#####";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(2006, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test0c() {
        String data =
                "#####\n" +
                "#E.S#\n" +
                "#####";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(2002, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test0d() {
        String data =
                "#####\n" +
                "###E#\n" +
                "##..#\n" +
                "#S.##\n" +
                "#####";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(3004, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test0e() {
        String data =
                "#####\n" +
                "#..E#\n" +
                "#.#.#\n" +
                "#S..#\n" +
                "#####";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1004, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test0f() {
        String data =
                "########\n" +
                "##....E#\n" +
                "##.##.##\n" +
                "#S.....#\n" +
                "########";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(2007, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test0g() {
        String data =
                "########\n" +
                "##....E#\n" +
                "###.####\n" +
                "##....##\n" +
                "##.##.##\n" +
                "#S.....#\n" +
                "########";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(4009, day.processInput1(groups[0], groups[1]));
    }

    public void test0h() {
        String data =
                "########\n" +
                "##....E#\n" +
                "####.###\n" +
                "##....##\n" +
                "##.##.##\n" +
                "#S.....#\n" +
                "########";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(4009, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test0i() {
        String data =
                "#####\n" +
                "#E..#\n" +
                "#...#\n" +
                "#S..#\n" +
                "#####";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1002, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test0j() {
        String data =
                "####\n" +
                "#.E#\n" +
                "#S.#\n" +
                "####";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1002, day.processInput1(groups[0], groups[1]));
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
        Assertions.assertEquals(7036, day.processInput1(groups[0], groups[1]));
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
        Assertions.assertEquals(11048, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void testpre2a() {
        String data =
                "#####\n" +
                "#...#\n" +
                "#S#E#\n" +
                "#...#\n" +
                "#####";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(8, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void testpre2b() {
        String data =
                "######\n" +
                "###.E#\n" +
                "##..##\n" +
                "##..##\n" +
                "#S.###\n" +
                "######";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(8, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void testpre2c() {
        String data =
                "######\n" +
                "#...E#\n" +
                "###.##\n" +
                "#...##\n" +
                "#.#.##\n" +
                "#...##\n" +
                "#.####\n" +
                "#S####\n" +
                "######";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(13, day.processInput2(groups[0], groups[1]));
    }


    @Test
    public void test2a() {
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
                "###############\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(45L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2b() {
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
        Assertions.assertEquals(64L, day.processInput2(groups[0], groups[1]));
    }
}
