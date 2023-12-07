package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day6Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA = "Time:      7  15   30\n" +
                                       "Distance:  9  40  200";

    static InputParser day;
    static {
        day = new Day6(DATA);
    }

    @Test
    public void test1() {
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(288L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(71503L, day.processInput2(groups[0], groups[1]));
    }
}
