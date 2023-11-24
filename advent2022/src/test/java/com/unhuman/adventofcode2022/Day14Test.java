package com.unhuman.adventofcode2022;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day14Test {
    @Test
    public void test1() {
        String filename = "src/test/resources/Day14Case.txt";
        Day14 day = new Day14(filename);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(24, day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(93, day.processInput2(groups[0], groups[1]));
    }
}
