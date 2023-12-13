package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day12Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "???.### 1,1,3\n" +
            ".??..??...?##. 1,1,3\n" +
            "?#?#?#?#?#?#?#? 1,3,1,6\n" +
            "????.#...#... 4,1,1\n" +
            "????.######..#####. 1,6,5\n" +
            "?###???????? 3,2,1\n";

    static InputParser day;

    @Test
    public void test1() {
        day = new Day12(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(21L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1a() {
        day = new Day12("???.### 1,1,3\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1b() {
        day = new Day12(".??..??...?##. 1,1,3\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(4L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1c() {
        day = new Day12("?#?#?#?#?#?#?#? 1,3,1,6\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1d() {
        day = new Day12("????.#...#... 4,1,1\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1e() {
        day = new Day12("????.######..#####. 1,6,5\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(4L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1f() {
        day = new Day12("?###???????? 3,2,1\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(10L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        day = new Day12(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(525152L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2a() {
        day = new Day12("???.### 1,1,3\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2b() {
        day = new Day12(".??..??...?##. 1,1,3\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(16384L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2c() {
        day = new Day12("?#?#?#?#?#?#?#? 1,3,1,6\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2d() {
        day = new Day12("????.#...#... 4,1,1\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(16L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2e() {
        day = new Day12("????.######..#####. 1,6,5\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(2500L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2f() {
        day = new Day12("?###???????? 3,2,1\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(506250L, day.processInput2(groups[0], groups[1]));
    }
}
