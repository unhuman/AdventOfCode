package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.Point;

public class Day10Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "";

    static Day10 getDay(String data) {
        return new Day10(data);
    }

    @Test
    public void test1a() {
        InputParser day = getDay(".#..#\n" +
                ".....\n" +
                "#####\n" +
                "....#\n" +
                "...##\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(8L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1b() {
        InputParser day = getDay("......#.#.\n" +
                "#..#.#....\n" +
                "..#######.\n" +
                ".#.#.###..\n" +
                ".#..#.....\n" +
                "..#....#.#\n" +
                "#..#....#.\n" +
                ".##.#..###\n" +
                "##...#..#.\n" +
                ".#....####\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(33L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1c() {
        InputParser day = getDay("#.#...#.#.\n" +
                ".###....#.\n" +
                ".#....#...\n" +
                "##.#.#.#.#\n" +
                "....#.#.#.\n" +
                ".##..###.#\n" +
                "..#...##..\n" +
                "..##....##\n" +
                "......#...\n" +
                ".####.###.\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(35L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1d() {
        InputParser day = getDay(".#..#..###\n" +
                "####.###.#\n" +
                "....###.#.\n" +
                "..###.##.#\n" +
                "##.##.#.#.\n" +
                "....###..#\n" +
                "..#.#..#.#\n" +
                "#..#.#.###\n" +
                ".##...##.#\n" +
                ".....#.#..\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(41L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1e() {
        InputParser day = getDay(".#..##.###...#######\n" +
                "##.############..##.\n" +
                ".#.######.########.#\n" +
                ".###.#######.####.#.\n" +
                "#####.##.#.##.###.##\n" +
                "..#####..#.#########\n" +
                "####################\n" +
                "#.####....###.#.#.##\n" +
                "##.#################\n" +
                "#####.##.###..####..\n" +
                "..######..##.#######\n" +
                "####.##.####...##..#\n" +
                ".#####..#.######.###\n" +
                "##...#.##########...\n" +
                "#.##########.#######\n" +
                ".####.#.###.###.#.##\n" +
                "....##.##.###..#####\n" +
                ".#.#.###########.###\n" +
                "#.#.#.#####.####.###\n" +
                "###.##.####.##.#..##\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(210L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2a() {
        Day10 day = getDay("..#\n" +
                ".#.\n" +
                "...");
        day.setShots(1);
        ConfigGroup[] groups = day.parseFiles();
        day.forceBase(new Point(1, 1));
        Assertions.assertEquals(200, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2b() {
        Day10 day = getDay("...\n" +
                ".##\n" +
                "...");
        day.setShots(1);
        day.forceBase(new Point(1, 1));
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(201, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2c() {
        Day10 day = getDay("...\n" +
                ".#.\n" +
                "..#");
        day.setShots(1);
        day.forceBase(new Point(1, 1));
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(202, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2d() {
        Day10 day = getDay("...\n" +
                ".#.\n" +
                ".#.");
        day.setShots(1);
        day.forceBase(new Point(1, 1));
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(102, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2e() {
        Day10 day = getDay("...\n" +
                ".#.\n" +
                "#..");
        day.setShots(1);
        day.forceBase(new Point(1, 1));
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(2, day.processInput2(groups[0], groups[1]));
    }


    @Test
    public void test2f() {
        Day10 day = getDay("...\n" +
                "##.\n" +
                "...");
        day.setShots(1);
        day.forceBase(new Point(1, 1));
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2g() {
        Day10 day = getDay("#..\n" +
                ".#.\n" +
                "...");
        day.setShots(1);
        day.forceBase(new Point(1, 1));
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(0, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2h() {
        Day10 day = getDay(".#.\n" +
                ".#.\n" +
                "...");
        day.setShots(1);
        ConfigGroup[] groups = day.parseFiles();
        day.forceBase(new Point(1, 1));
        Assertions.assertEquals(100, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2i() {
        Day10 day = getDay(".#.\n" +
                "###\n" +
                ".#.");
        day.setShots(4);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2j() {
        Day10 day = getDay("#.#\n" +
                ".#.\n" +
                "#.#");
        day.setShots(3);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(2, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2k() {
        Day10 day = getDay(".#....#####...#..\n" +
                "##...##.#####..##\n" +
                "##...#...#.#####.\n" +
                "..#.....#...###..\n" +
                "..#.#.....#....##\n");
        day.setShots(10);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1202, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2l() {
        InputParser day = getDay(".#..##.###...#######\n" +
                "##.############..##.\n" +
                ".#.######.########.#\n" +
                ".###.#######.####.#.\n" +
                "#####.##.#.##.###.##\n" +
                "..#####..#.#########\n" +
                "####################\n" +
                "#.####....###.#.#.##\n" +
                "##.#################\n" +
                "#####.##.###..####..\n" +
                "..######..##.#######\n" +
                "####.##.####...##..#\n" +
                ".#####..#.######.###\n" +
                "##...#.##########...\n" +
                "#.##########.#######\n" +
                ".####.#.###.###.#.##\n" +
                "....##.##.###..#####\n" +
                ".#.#.###########.###\n" +
                "#.#.#.#####.####.###\n" +
                "###.##.####.##.#..##\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(802, day.processInput2(groups[0], groups[1]));
    }

}
