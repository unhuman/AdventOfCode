package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day17Test {
    @Test
    public void test1() {
        String[] test1 = new String[] { "src/test/resources/Day17Case.txt" };
        Day17 day = new Day17(test1);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(3068L, day.processInput1(groups[0], groups[1]));
//        Assertions.assertEquals(1514285714288L, day.processInput2(groups[0], groups[1]));
    }
}
