package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day10Test {
    // data must be at least 2 lines - add \n for single line data

    @Test
    public void test1a() {
        final String DATA =
                ".....\n" +
                ".S-7.\n" +
                ".|.|.\n" +
                ".L-J.\n" +
                ".....\n";
        InputParser day = new Day10(DATA);

        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(4, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1b() {
        final String DATA =
                "..F7.\n" +
                ".FJ|.\n" +
                "SJ.L7\n" +
                "|F--J\n" +
                "LJ...";
        InputParser day = new Day10(DATA);

        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(8, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2a() {
        final String DATA =
                "...........\n" +
                ".S-------7.\n" +
                ".|F-----7|.\n" +
                ".||.....||.\n" +
                ".||.....||.\n" +
                ".|L-7.F-J|.\n" +
                ".|..|.|..|.\n" +
                ".L--J.L--J.\n" +
                "...........";
        InputParser day = new Day10(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(4, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2b() {
        final String DATA =
                        "..........\n" +
                        ".S------7.\n" +
                        ".|F----7|.\n" +
                        ".||....||.\n" +
                        ".||....||.\n" +
                        ".|L-7F-J|.\n" +
                        ".|..||..|.\n" +
                        ".L--JL--J.\n" +
                        "..........";
        InputParser day = new Day10(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(4, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2c() {
        final String DATA =
                ".F----7F7F7F7F-7....\n" +
                ".|F--7||||||||FJ....\n" +
                ".||.FJ||||||||L7....\n" +
                "FJL7L7LJLJ||LJ.L-7..\n" +
                "L--J.L7...LJS7F-7L7.\n" +
                "....F-J..F7FJ|L7L7L7\n" +
                "....L7.F7||L7|.L7L7|\n" +
                ".....|FJLJ|FJ|F7|.LJ\n" +
                "....FJL-7.||.||||...\n" +
                "....L---J.LJ.LJLJ...";
        InputParser day = new Day10(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(8, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2d() {
        final String DATA =
                "FF7FSF7F7F7F7F7F---7\n" +
                "L|LJ||||||||||||F--J\n" +
                "FL-7LJLJ||||||LJL-77\n" +
                "F--JF--7||LJLJ7F7FJ-\n" +
                "L---JF-JLJ.||-FJLJJ7\n" +
                "|F|F-JF---7F7-L7L|7|\n" +
                "|FFJF7L7F-JF7|JL---7\n" +
                "7-L-JL7||F7|L7F-7F7|\n" +
                "L.L7LFJ|||||FJL7||LJ\n" +
                "L7JLJL-JLJLJL--JLJ.L";
        InputParser day = new Day10(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(10, day.processInput2(groups[0], groups[1]));
    }
}
