package com.unhuman.adventofcode2022;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day8Test {
    @Test
    public void test1() {
        String filename = "src/test/resources/Day8Case.txt";
        Day8 day = new Day8(filename);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(21, day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(8, day.processInput2(groups[0], groups[1]));
    }
}
