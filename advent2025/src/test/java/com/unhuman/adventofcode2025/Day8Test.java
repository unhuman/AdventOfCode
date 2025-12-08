package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day8Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA = "162,817,812\n" +
            "57,618,57\n" +
            "906,360,560\n" +
            "592,479,940\n" +
            "352,342,300\n" +
            "466,668,158\n" +
            "542,29,236\n" +
            "431,825,988\n" +
            "739,650,466\n" +
            "52,470,668\n" +
            "216,146,977\n" +
            "819,987,18\n" +
            "117,168,530\n" +
            "805,96,715\n" +
            "346,949,466\n" +
            "970,615,88\n" +
            "941,993,340\n" +
            "862,61,35\n" +
            "984,92,344\n" +
            "425,690,689\n" +
            "\n";

    static InputParser getDay(String data) {
//        return null;
        return new Day8(data);
    }

    @Test
    public void test1() {
        String data = DATA;
        InputParser day = getDay(data);
        ((Day8) day).setJunctions(10);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(40L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = DATA;
        InputParser day = getDay(data);
        ((Day8) day).setJunctions(10);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(25272L, day.processInput2(groups[0], groups[1]));
    }
}
