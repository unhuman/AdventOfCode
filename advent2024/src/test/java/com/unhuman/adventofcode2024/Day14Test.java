package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day14Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "p=0,4 v=3,-3\n" +
                    "p=6,3 v=-1,-3\n" +
                    "p=10,3 v=-1,2\n" +
                    "p=2,0 v=2,-1\n" +
                    "p=0,0 v=1,3\n" +
                    "p=3,0 v=-2,-2\n" +
                    "p=7,6 v=-1,-3\n" +
                    "p=3,0 v=-1,-2\n" +
                    "p=9,3 v=2,3\n" +
                    "p=7,3 v=-1,2\n" +
                    "p=2,4 v=2,-3\n" +
                    "p=9,5 v=-3,-3\n";

    static Day14 getDay(String data) {
        return new Day14(data);
    }

    @Test
    public void test1() {
        String data = DATA;
        Day14 day = getDay(data);
        day.setRoomWidth(11);
        day.setRoomHeight(7);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(12L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(0L, day.processInput2(groups[0], groups[1]));
    }
}
