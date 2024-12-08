package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class Day7Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "\n";

    static InputParser getDay(String data) {
        return new Day7(data);
    }

    @Test
    public void test1a() {
        long value = Day7.processSignalPermutation("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0",
                Arrays.asList(new Integer[] {4, 3, 2, 1, 0}));
        Assertions.assertEquals(43210, value);
    }

    @Test
    public void test1b() {
        long value = Day7.processSignalPermutation(
                "3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0",
                Arrays.asList(new Integer[] {0, 1, 2, 3, 4}));
        Assertions.assertEquals(54321, value);
    }

    @Test
    public void test1c() {
        long value = Day7.processSignalPermutation(
                "3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33," +
                        "1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0",
                Arrays.asList(new Integer[] {1, 0, 4, 3, 2}));
        Assertions.assertEquals(65210, value);
    }

    @Test
    public void test2() {
        InputParser day = getDay(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(0, day.processInput2(groups[0], groups[1]));
    }
}
