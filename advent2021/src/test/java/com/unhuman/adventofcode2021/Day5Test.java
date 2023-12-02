package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day5Test {
    static final String DATA =
            "0,9 -> 5,9\n" +
                    "8,0 -> 0,8\n" +
                    "9,4 -> 3,4\n" +
                    "2,2 -> 2,1\n" +
                    "7,0 -> 7,4\n" +
                    "6,4 -> 2,0\n" +
                    "0,9 -> 2,9\n" +
                    "3,4 -> 1,4\n" +
                    "0,0 -> 8,8\n" +
                    "5,5 -> 8,2";
    @Test
    public void test1() {
        Day5 day = new Day5(DATA);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(5L, day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(12L, day.processInput2(groups[0], groups[1]));
    }
}
