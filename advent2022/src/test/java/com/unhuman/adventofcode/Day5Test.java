package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day5Test {
    @Test
    public void test1() {
        String[] test1 = new String[] { "src/test/resources/Day5Case.txt" };
        Day5 day = new Day5(test1);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals("CMZ", day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals("MCD", day.processInput2(groups[0], groups[1]));
    }
}
