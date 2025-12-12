package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day12Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "99:\n" +
            "#..\n" +
            "...\n" +
            "...\n" +
            "\n" +
            "0:\n" +
            "###\n" +
            "##.\n" +
            "##.\n" +
            "\n" +
            "1:\n" +
            "###\n" +
            "##.\n" +
            ".##\n" +
            "\n" +
            "2:\n" +
            ".##\n" +
            "###\n" +
            "##.\n" +
            "\n" +
            "3:\n" +
            "##.\n" +
            "###\n" +
            "##.\n" +
            "\n" +
            "4:\n" +
            "###\n" +
            "#..\n" +
            "###\n" +
            "\n" +
            "5:\n" +
            "###\n" +
            ".#.\n" +
            "###\n" +
            "\n" +
            "4x4: 0 0 0 0 2 0\n" +
            "12x5: 1 0 1 0 2 2\n" +
            "12x5: 1 0 1 0 3 2\n" +
            "\n";

    static InputParser getDay(String data) {
//        return null;
        return new Day12(data);
    }

    @Test
    public void test1() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(2L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(0L, day.processInput2(groups[0], groups[1]));
    }
}
