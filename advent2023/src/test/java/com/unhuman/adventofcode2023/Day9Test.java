package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day9Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "0 3 6 9 12 15\n" +
            "1 3 6 10 15 21\n" +
            "10 13 16 21 30 45\n";

    static InputParser day;
    static {
        day = new Day9(DATA);
    }

    @Test
    public void test1() {
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(114L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(2L, day.processInput2(groups[0], groups[1]));
    }
}
