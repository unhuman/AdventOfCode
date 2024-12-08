package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day8Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "............\n" +
                    "........0...\n" +
                    ".....0......\n" +
                    ".......0....\n" +
                    "....0.......\n" +
                    "......A.....\n" +
                    "............\n" +
                    "............\n" +
                    "........A...\n" +
                    ".........A..\n" +
                    "............\n" +
                    "............\n";

    static InputParser getDay(String data) {
        return new Day8(data);
    }

    @Test
    public void test1() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(14, day.processInput1(groups[0], groups[1]));
    }


    @Test
    public void test2a() {
        String data = "T.........\n" +
                "...T......\n" +
                ".T........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........\n" +
                "..........";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(9, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(34, day.processInput2(groups[0], groups[1]));
    }

}
