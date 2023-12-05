package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day5Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA = "seeds: 79 14 55 13\n" +
            "\n" +
            "seed-to-soil map:\n" +
            "50 98 2\n" +
            "52 50 48\n" +
            "\n" +
            "soil-to-fertilizer map:\n" +
            "0 15 37\n" +
            "37 52 2\n" +
            "39 0 15\n" +
            "\n" +
            "fertilizer-to-water map:\n" +
            "49 53 8\n" +
            "0 11 42\n" +
            "42 0 7\n" +
            "57 7 4\n" +
            "\n" +
            "water-to-light map:\n" +
            "88 18 7\n" +
            "18 25 70\n" +
            "\n" +
            "light-to-temperature map:\n" +
            "45 77 23\n" +
            "81 45 19\n" +
            "68 64 13\n" +
            "\n" +
            "temperature-to-humidity map:\n" +
            "0 69 1\n" +
            "1 0 69\n" +
            "\n" +
            "humidity-to-location map:\n" +
            "60 56 37\n" +
            "56 93 4";

    static InputParser day;
    static {
        day = new Day5(DATA);
    }

    @Test
    public void test1() {
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(35L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(46L, day.processInput2(groups[0], groups[1]));
    }
}
