package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day6Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "COM)B\n" +
                    "B)C\n" +
                    "C)D\n" +
                    "D)E\n" +
                    "E)F\n" +
                    "B)G\n" +
                    "G)H\n" +
                    "D)I\n" +
                    "E)J\n" +
                    "J)K\n" +
                    "K)L\n";

    static InputParser getDay(String data) {
        return new Day6(data);
    }

    @Test
    public void test1() {
        InputParser day = getDay(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(42L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        InputParser day = getDay("COM)B\n" +
                "B)C\n" +
                "C)D\n" +
                "D)E\n" +
                "E)F\n" +
                "B)G\n" +
                "G)H\n" +
                "D)I\n" +
                "E)J\n" +
                "J)K\n" +
                "K)L\n" +
                "K)YOU\n" +
                "I)SAN\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(4L, day.processInput2(groups[0], groups[1]));
    }
}
