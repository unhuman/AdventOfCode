package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day1Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "";

    static InputParser getDay(String data) {
         return new Day1(data);
    }

    @Test
    public void test2() {
        String data = "14\n1969\n100756";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(51314L, day.processInput2(groups[0], groups[1]));
    }
}
