package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day9Test {
    @Test
    public void test1() {
        String[] test1 = new String[] { "src/test/resources/Day9Case.txt" };
        Day9 day = new Day9(test1);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(13, day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(2, day.processInput2(groups[0], groups[1]));
    }
}
