package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day3Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String data = "467..114..\n" +
            "...*......\n" +
            "..35..633.\n" +
            "......#...\n" +
            "617*......\n" +
            ".....+.58.\n" +
            "..592.....\n" +
            "......755.\n" +
            "...$.*....\n" +
            ".664.598..";

    static InputParser day;
    static {
        day = new Day3(data);
    }

    @Test
    public void test1() {
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(4361, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(467835, day.processInput2(groups[0], groups[1]));
    }
}
