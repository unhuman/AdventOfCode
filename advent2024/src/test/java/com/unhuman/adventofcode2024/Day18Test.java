package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day18Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "\n";

    static Day18 getDay(String data) {
        return new Day18(data);
    }

    @Test
    public void test1() {
        String data = "5,4\n" +
                "4,2\n" +
                "4,5\n" +
                "3,0\n" +
                "2,1\n" +
                "6,3\n" +
                "2,4\n" +
                "1,5\n" +
                "0,6\n" +
                "3,3\n" +
                "2,6\n" +
                "5,1\n" +
                "1,2\n" +
                "5,5\n" +
                "2,5\n" +
                "6,5\n" +
                "1,4\n" +
                "0,4\n" +
                "6,4\n" +
                "1,1\n" +
                "6,1\n" +
                "1,0\n" +
                "0,5\n" +
                "1,6\n" +
                "2,0\n";
        Day18 day = getDay(data);
        day.resetMatrix(7, 7);
        day.resetLimit(12);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(22L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = "5,4\n" +
                "4,2\n" +
                "4,5\n" +
                "3,0\n" +
                "2,1\n" +
                "6,3\n" +
                "2,4\n" +
                "1,5\n" +
                "0,6\n" +
                "3,3\n" +
                "2,6\n" +
                "5,1\n" +
                "1,2\n" +
                "5,5\n" +
                "2,5\n" +
                "6,5\n" +
                "1,4\n" +
                "0,4\n" +
                "6,4\n" +
                "1,1\n" +
                "6,1\n" +
                "1,0\n" +
                "0,5\n" +
                "1,6\n" +
                "2,0\n";
        Day18 day = getDay(data);
        day.resetMatrix(7, 7);
        day.resetLimit(12);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals("6,1", day.processInput2(groups[0], groups[1]));
    }
}
