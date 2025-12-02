package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day1Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA = "L68\n" +
            "L30\n" +
            "R48\n" +
            "L5\n" +
            "R60\n" +
            "L55\n" +
            "L1\n" +
            "L99\n" +
            "R14\n" +
            "L82" +
            "\n";

    static InputParser getDay(String data) {
//        return null;
        return new Day1(data);
    }

    @Test
    public void test1() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(3, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(6, day.processInput2(groups[0], groups[1]));
    }
}
