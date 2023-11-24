package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day7Test {
    @Test
    public void test1() {
        String filename = "src/test/resources/Day7Case.txt";
        Day7 day = new Day7(filename);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(95437, day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(24933642, day.processInput2(groups[0], groups[1]));
    }
}
