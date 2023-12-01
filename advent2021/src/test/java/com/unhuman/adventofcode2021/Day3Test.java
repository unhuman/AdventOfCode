package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day3Test {
    String data = "00100\n" +
            "11110\n" +
            "10110\n" +
            "10111\n" +
            "10101\n" +
            "01111\n" +
            "00111\n" +
            "11100\n" +
            "10000\n" +
            "11001\n" +
            "00010\n" +
            "01010";

    @Test
    public void test1() {
        Day3 day = new Day3(data);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(198L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        Day3 day = new Day3(data);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(230L, day.processInput2(groups[0], groups[1]));
    }
}
