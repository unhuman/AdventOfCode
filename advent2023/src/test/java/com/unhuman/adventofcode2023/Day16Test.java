package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day16Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
                    ".|...\\....\n" +
                    "|.-.\\.....\n" +
                    ".....|-...\n" +
                    "........|.\n" +
                    "..........\n" +
                    ".........\\\n" +
                    "..../.\\\\..\n" +
                    ".-.-/..|..\n" +
                    ".|....-|.\\\n" +
                    "..//.|....";

    static InputParser day;
    static {
        day = new Day16(DATA);
    }

    @Test
    public void test1() {
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(46, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(51, day.processInput2(groups[0], groups[1]));
    }
}
