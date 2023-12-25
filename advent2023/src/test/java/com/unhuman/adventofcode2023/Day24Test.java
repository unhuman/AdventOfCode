package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day24Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "19, 13, 30 @ -2,  1, -2\n" +
            "18, 19, 22 @ -1, -1, -2\n" +
            "20, 25, 34 @ -2, -2, -4\n" +
            "12, 31, 28 @ -1, -2, -1\n" +
            "20, 19, 15 @  1, -5, -3\n";

    static Day24 getDay(String data) {
        return new Day24(data);
    }

    @Test
    public void test1() {
        Day24 day = getDay(DATA);
        day.setMinMaxPositions(7L, 27L);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(2, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        InputParser day = getDay(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(47L, day.processInput2(groups[0], groups[1]));
    }
}
