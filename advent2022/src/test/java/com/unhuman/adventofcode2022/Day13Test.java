package com.unhuman.adventofcode2022;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day13Test {
    @Test
    public void test1() {
        String filename = "src/test/resources/Day13Case.txt";
        Day13 day = new Day13(filename);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(13, day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(140, day.processInput2(groups[0], groups[1]));
    }
}
