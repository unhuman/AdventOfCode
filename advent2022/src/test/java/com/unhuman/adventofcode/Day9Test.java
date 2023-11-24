package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day9Test {
    @Test
    public void test1() {
        String filename = "src/test/resources/Day9Case.txt";
        Day9 day = new Day9(filename);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(13, day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(1, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String filename = "src/test/resources/Day9Case2.txt";
        Day9 day = new Day9(filename);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(88, day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(36, day.processInput2(groups[0], groups[1]));
    }
}
