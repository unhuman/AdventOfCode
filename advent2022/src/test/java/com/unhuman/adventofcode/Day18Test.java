package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day18Test {
    @Test
    public void test1() {
        String filename = "src/test/resources/Day18Case.txt";
        Day18 day = new Day18(filename);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(64, day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(58, day.processInput2(groups[0], groups[1]));
    }
}
