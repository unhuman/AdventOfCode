package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day11Test {
    static final String DATA = "";
    static InputParser day;
    static {
        day = new Day11(DATA);
    }

    @Test
    public void test1() {
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(0, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(0, day.processInput2(groups[0], groups[1]));
    }
}
