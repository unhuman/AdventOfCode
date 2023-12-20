package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day20Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "";

    static InputParser getDay(String data) {
         return new Day20(data);
    }

    @Test
    public void test1() {
        String data = "broadcaster -> a, b, c\n" +
                "%a -> b\n" +
                "%b -> c\n" +
                "%c -> inv\n" +
                "&inv -> a\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(32000000L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = "broadcaster -> a\n" +
                "%a -> inv, con\n" +
                "&inv -> b\n" +
                "%b -> con\n" +
                "&con -> output\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(11687500L, day.processInput1(groups[0], groups[1]));
    }
}
