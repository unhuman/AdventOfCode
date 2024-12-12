package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day12Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "\n";

    static InputParser getDay(String data) {
        return new Day12(data);
    }

    @Test
    public void test1a() {
        String data = "AAAA\n" +
                "BBCD\n" +
                "BBCC\n" +
                "EEEC\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(140L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1b() {
        String data = "OOOOO\n" +
                "OXOXO\n" +
                "OOOOO\n" +
                "OXOXO\n" +
                "OOOOO\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(772L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1c() {
        String data = "RRRRIICCFF\n" +
                "RRRRIICCCF\n" +
                "VVRRRCCFFF\n" +
                "VVRCCCJFFF\n" +
                "VVVVCJJCFE\n" +
                "VVIVCCJJEE\n" +
                "VVIIICJJEE\n" +
                "MIIIIIJJEE\n" +
                "MIIISIJEEE\n" +
                "MMMISSJEEE\n\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1930L, day.processInput1(groups[0], groups[1]));
    }


    @Test
    public void test2a() {
        String data = "AAAA\n" +
                "BBCD\n" +
                "BBCC\n" +
                "EEEC";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(80L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2b() {
        String data = "OOOOO\n" +
                "OXOXO\n" +
                "OOOOO\n" +
                "OXOXO\n" +
                "OOOOO\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(436L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2c() {
        String data = "EEEEE\n" +
                "EXXXX\n" +
                "EEEEE\n" +
                "EXXXX\n" +
                "EEEEE\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(236L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2d() {
        String data = "AAAAAA\n" +
                "AAABBA\n" +
                "AAABBA\n" +
                "ABBAAA\n" +
                "ABBAAA\n" +
                "AAAAAA\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(368L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2e() {
        String data = "RRRRIICCFF\n" +
                "RRRRIICCCF\n" +
                "VVRRRCCFFF\n" +
                "VVRCCCJFFF\n" +
                "VVVVCJJCFE\n" +
                "VVIVCCJJEE\n" +
                "VVIIICJJEE\n" +
                "MIIIIIJJEE\n" +
                "MIIISIJEEE\n" +
                "MMMISSJEEE\n\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1206L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2f() {
        String data =
                "MAAA\n" +
                "MAAA\n" +
                "MMMA";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(72L, day.processInput2(groups[0], groups[1]));
    }
}
