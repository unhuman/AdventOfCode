package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day11Test {
    @Test
    public void test1() {
        String[] test1 = new String[] { "src/test/resources/Day11Case.txt" };
        Day11 day = new Day11(test1);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(10605L, day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(2713310158L, day.processInput2(groups[0], groups[1]));
    }
}
