package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day2Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "1,9,10,3,2,3,11,0,99,30,40,50";

    static InputParser getDay(String data) {
        return new Day2(data);
    }

    @Test
    public void test1() {
        InputParser day = getDay(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(0, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        InputParser day = getDay(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(0, day.processInput2(groups[0], groups[1]));
    }
}
