package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day6Test {
    @Test
    public void test1() {
        String filename = "src/test/resources/Day6Case.txt";
        Day6 day = new Day6(filename);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(5, day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(23, day.processInput2(groups[0], groups[1]));
    }
}
