package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day21Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA0 =
            "...\n" +
            "#S.\n" +
            "..#";

    private static final String DATA1 =
            "...........\n" +
            ".....###.#.\n" +
            ".###.##..#.\n" +
            "..#.#...#..\n" +
            "....#.#....\n" +
            ".##..S####.\n" +
            ".##..#...#.\n" +
            ".......##..\n" +
            ".##.#.####.\n" +
            ".##..##.##.\n" +
            "...........";

    private static final String DATA2 =
            ".................................\n" +
            ".....###.#......###.#......###.#.\n" +
            ".###.##..#..###.##..#..###.##..#.\n" +
            "..#.#...#....#.#...#....#.#...#..\n" +
            "....#.#........#.#........#.#....\n" +
            ".##...####..##...####..##...####.\n" +
            ".##..#...#..##..#...#..##..#...#.\n" +
            ".......##.........##.........##..\n" +
            ".##.#.####..##.#.####..##.#.####.\n" +
            ".##..##.##..##..##.##..##..##.##.\n" +
            ".................................\n" +
            ".................................\n" +
            ".....###.#......###.#......###.#.\n" +
            ".###.##..#..###.##..#..###.##..#.\n" +
            "..#.#...#....#.#...#....#.#...#..\n" +
            "....#.#........#.#........#.#....\n" +
            ".##...####..##..S####..##...####.\n" +
            ".##..#...#..##..#...#..##..#...#.\n" +
            ".......##.........##.........##..\n" +
            ".##.#.####..##.#.####..##.#.####.\n" +
            ".##..##.##..##..##.##..##..##.##.\n" +
            ".................................\n" +
            ".................................\n" +
            ".....###.#......###.#......###.#.\n" +
            ".###.##..#..###.##..#..###.##..#.\n" +
            "..#.#...#....#.#...#....#.#...#..\n" +
            "....#.#........#.#........#.#....\n" +
            ".##...####..##...####..##...####.\n" +
            ".##..#...#..##..#...#..##..#...#.\n" +
            ".......##.........##.........##..\n" +
            ".##.#.####..##.#.####..##.#.####.\n" +
            ".##..##.##..##..##.##..##..##.##.\n" +
            ".................................";

    static Day21 getDay(String data) {
        return new Day21(data);
    }

    @Test
    public void test0a() {
        Day21 day = getDay(DATA0);
        day.setCount(1);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(3L, day.processInput2Original(groups[0], groups[1]));
    }

    @Test
    public void test0b() {
        Day21 day = getDay(DATA0);
        day.setCount(2);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(6L, day.processInput2Original(groups[0], groups[1]));
    }

    @Test
    public void test0c() {
        Day21 day = getDay(DATA0);
        day.setCount(3);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(10L, day.processInput2Original(groups[0], groups[1]));
    }

    @Test
    public void test0d() {
        Day21 day = getDay(DATA0);
        day.setCount(4);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(16L, day.processInput2Original(groups[0], groups[1]));
    }

    @Test
    public void test0e() {
        Day21 day = getDay(DATA0);
        day.setCount(5);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(25L, day.processInput2Original(groups[0], groups[1]));
    }

    @Test
    public void test1a() {
        Day21 day = getDay(DATA1);
        day.setCount(1);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(2L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1b() {
        Day21 day = getDay(DATA1);
        day.setCount(2);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(4L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1c() {
        Day21 day = getDay(DATA1);
        day.setCount(3);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(6L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1d() {
        Day21 day = getDay(DATA1);
        day.setCount(6);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(16L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2a() {
        Day21 day = getDay(DATA2);
        day.setCount(6);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(16L, day.processInput2Original(groups[0], groups[1]));
    }

    @Test
    public void test2b() {
        Day21 day = getDay(DATA2);
        day.setCount(10);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(50L, day.processInput2Original(groups[0], groups[1]));
    }

    @Test
    public void test2c() {
        Day21 day = getDay(DATA2);
        day.setCount(50);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1594L, day.processInput2Original(groups[0], groups[1]));
    }

    @Test
    public void test2d() {
        Day21 day = getDay(DATA2);
        day.setCount(100);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(6536L, day.processInput2Original(groups[0], groups[1]));
    }

    @Test
    public void test2e() {
        Day21 day = getDay(DATA2);
        day.setCount(500);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(167004L, day.processInput2Original(groups[0], groups[1]));
    }

    @Test
    public void test2f() {
        Day21 day = getDay(DATA2);
        day.setCount(1000);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(668697L, day.processInput2Original(groups[0], groups[1]));
    }

//    @Test
//    public void test2g() {
//        Day21 day = getDay(DATA2);
//        day.setCount(5000);
//        ConfigGroup[] groups = day.parseFiles();
//        Assertions.assertEquals(16733044L, day.processInput2Original(groups[0], groups[1]));
//    }

}
