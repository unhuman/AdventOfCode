package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day11Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA = "aaa: you hhh\n" +
            "you: bbb ccc\n" +
            "bbb: ddd eee\n" +
            "ccc: ddd eee fff\n" +
            "ddd: ggg\n" +
            "eee: out\n" +
            "fff: out\n" +
            "ggg: out\n" +
            "hhh: ccc fff iii\n" +
            "iii: out\n" +
            "\n";

    static InputParser getDay(String data) {
//        return null;
        return new Day11(data);
    }

    @Test
    public void test1() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(5L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = "svr: aaa bbb\n" +
                "aaa: fft\n" +
                "fft: ccc\n" +
                "bbb: tty\n" +
                "tty: ccc\n" +
                "ccc: ddd eee\n" +
                "ddd: hub\n" +
                "hub: fff\n" +
                "eee: dac\n" +
                "dac: fff\n" +
                "fff: ggg hhh\n" +
                "ggg: out\n" +
                "hhh: out\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(2L, day.processInput2(groups[0], groups[1]));
    }
}
