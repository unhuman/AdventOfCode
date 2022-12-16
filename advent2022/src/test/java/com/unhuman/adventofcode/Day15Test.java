package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day15Test {
    @Test
    public void test1() {
        String[] test1 = new String[] { "src/test/resources/Day15Case.txt" };
        Day15 day = new Day15(test1);
        day.setPart1CheckRow(10);
        day.setPart2Maximum(20);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(26, day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(56000011, day.processInput2(groups[0], groups[1]));
    }
}
