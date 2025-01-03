package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day7Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "190: 10 19\n" +
                    "3267: 81 40 27\n" +
                    "83: 17 5\n" +
                    "156: 15 6\n" +
                    "7290: 6 8 6 15\n" +
                    "161011: 16 10 13\n" +
                    "192: 17 8 14\n" +
                    "21037: 9 7 18 13\n" +
                    "292: 11 6 16 20\n";

    static InputParser getDay(String data) {
        return new Day7(data);
    }

    @Test
    public void test1() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(3749L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(11387L, day.processInput2(groups[0], groups[1]));
    }
}
