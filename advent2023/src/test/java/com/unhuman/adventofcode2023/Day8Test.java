package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day8Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "RL\n" +
            "\n" +
            "AAA = (BBB, CCC)\n" +
            "BBB = (DDD, EEE)\n" +
            "CCC = (ZZZ, GGG)\n" +
            "DDD = (DDD, DDD)\n" +
            "EEE = (EEE, EEE)\n" +
            "GGG = (GGG, GGG)\n" +
            "ZZZ = (ZZZ, ZZZ)";

    private static final String DATA2 =
            "LR\n" +
            "\n" +
            "11A = (11B, XXX)\n" +
            "11B = (XXX, 11Z)\n" +
            "11Z = (11B, XXX)\n" +
            "22A = (22B, XXX)\n" +
            "22B = (22C, 22C)\n" +
            "22C = (22Z, 22Z)\n" +
            "22Z = (22B, 22B)\n" +
            "XXX = (XXX, XXX)";

    static InputParser day;

    @Test
    public void test1() {
        day = new Day8(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(2, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        day = new Day8(DATA2);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(6L, day.processInput2(groups[0], groups[1]));
    }
}
