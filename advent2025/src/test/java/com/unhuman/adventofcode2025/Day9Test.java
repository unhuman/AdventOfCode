package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day9Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA = "7,1\n" +
            "11,1\n" +
            "11,7\n" +
            "9,7\n" +
            "9,5\n" +
            "2,5\n" +
            "2,3\n" +
            "7,3\n" +
            "\n";

    static InputParser getDay(String data) {
//        return null;
        return new Day9(data);
    }

    @Test
    public void test1() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(50L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(24L, day.processInput2(groups[0], groups[1]));
    }
}
