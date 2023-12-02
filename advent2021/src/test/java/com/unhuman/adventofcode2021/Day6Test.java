package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day6Test {
    static final String DATA = "3,4,3,1,2\n";
    @Test
    public void test1() {
        Day6 day = new Day6(DATA);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(5934, day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(26984457539L, day.processInput2(groups[0], groups[1]));
    }
}
