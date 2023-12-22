package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day22Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "1,0,1~1,2,1\n" +
            "0,0,2~2,0,2\n" +
            "0,2,3~2,2,3\n" +
            "0,0,4~0,2,4\n" +
            "2,0,5~2,2,5\n" +
            "0,1,6~2,1,6\n" +
            "1,1,8~1,1,9";

    static InputParser getDay(String data) {
        return new Day22(data);
    }

    @Test
    public void test1() {
        InputParser day = getDay(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(5, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        InputParser day = getDay(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(7, day.processInput2(groups[0], groups[1]));
    }
}
