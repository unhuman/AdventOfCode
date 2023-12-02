package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day7Test {
    static final String DATA = "16,1,2,0,4,2,7,1,2,14\n";
    @Test
    public void test1() {
        Day7 day = new Day7(DATA);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(37, day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(168L, day.processInput2(groups[0], groups[1]));
    }
}
