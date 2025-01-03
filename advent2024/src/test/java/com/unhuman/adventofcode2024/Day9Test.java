package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day9Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "2333133121414131402\n";

    static InputParser getDay(String data) {
        return new Day9(data);
    }

    @Test
    public void test1() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1928L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(2858L, day.processInput2(groups[0], groups[1]));
    }
}
