package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class Day18Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "R 6 (#70c710)\n" +
                    "D 5 (#0dc571)\n" +
                    "L 2 (#5713f0)\n" +
                    "D 2 (#d2c081)\n" +
                    "R 2 (#59c680)\n" +
                    "D 2 (#411b91)\n" +
                    "L 5 (#8ceee2)\n" +
                    "U 2 (#caa173)\n" +
                    "L 1 (#1b58a2)\n" +
                    "U 2 (#caa171)\n" +
                    "R 2 (#7807d2)\n" +
                    "U 3 (#a77fa3)\n" +
                    "L 2 (#015232)\n" +
                    "U 2 (#7a21e3)";

    static InputParser getDay(String data) {
        return new Day18(data);
    }

    @Test
    public void test1() {
        InputParser day = getDay(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(62, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        InputParser day = getDay(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(952408144115L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void testCalculate() {
        // from: https://www.youtube.com/watch?v=0KjG8Pg6LGk&t=204s
        List<Pair<Long, Long>> pairs = new ArrayList<>();
        pairs.add(new Pair<Long, Long>(4L, 4L));
        pairs.add(new Pair<Long, Long>(0L, 1L));
        pairs.add(new Pair<Long, Long>(-2L, 5L));
        pairs.add(new Pair<Long, Long>(-6L, -0L));
        pairs.add(new Pair<Long, Long>(-1L, -4L));
        pairs.add(new Pair<Long, Long>(5L, -2L));
        pairs.add(new Pair<Long, Long>(4L, 4L));

        Assertions.assertEquals(55L, Day18.calculateArea(pairs));
    }
}
