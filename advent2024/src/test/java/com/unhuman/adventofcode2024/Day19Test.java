package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day19Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "\n";

    static InputParser getDay(String data) {
        return new Day19(data);
    }

    @Test
    public void test1() {
        String data = "r, wr, b, g, bwu, rb, gb, br\n" +
                "\n" +
                "brwrr\n" +
                "bggr\n" +
                "gbbr\n" +
                "rrbgbr\n" +
                "ubwu\n" +
                "bwurrg\n" +
                "brgr\n" +
                "bbrgwb\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(6L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = "r, wr, b, g, bwu, rb, gb, br\n" +
                "\n" +
                "brwrr\n" +
                "bggr\n" +
                "gbbr\n" +
                "rrbgbr\n" +
                "ubwu\n" +
                "bwurrg\n" +
                "brgr\n" +
                "bbrgwb\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(16L, day.processInput2(groups[0], groups[1]));
    }
}
