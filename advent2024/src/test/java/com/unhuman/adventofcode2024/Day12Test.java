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

    @Test
    public void test2g() {
        String data =
                "AAA\n" +
                        "ABA\n" +
                        "AAA";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(68L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2h() {
        String data =
                "CCCCC\n" +
                "CACAC";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(104L, day.processInput2(groups[0], groups[1]));
    }
    @Test
    public void test2i() {
        String data =
                "CCCCC\n" +
                        "CAAAC\n" +
                        "CABAC\n" +
                        "CAAAC\n" +
                        "CCCCC\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(196L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2j() {
        String data =
                "BAB\n" +
                        "ABA\n" +
                        "BAB";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(36L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2k() {
        String data =
                "ABA\n" +
                        "BBB\n" +
                        "ABA";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(76L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2l() {
        String data =
                "CCCCC\n" +
                        "CAAAC\n" +
                        "CACAC\n" +
                        "CAAAC\n" +
                        "CCCCC\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(196L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2m() {
        String data =
                "CCCCC\n" +
                        "AAAAC\n" +
                        "ACCAC\n" +
                        "ACAAC\n" +
                        "ACCCC\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(280L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2n() {
        String data =
                        "AAABB\n" +
                        "AAABB\n" +
                        "BBBAA\n" +
                        "BBBAA\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(80L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2o() {
        String data =
                "AAABB\n" +
                "AAABB\n" +
                "BBAAA\n" +
                "BBAAA\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(128L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2p() {
        String data =
                "AAAAA\n" +
                "AAABA\n" +
                "ABAAA\n" +
                "AAAAA\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(224L, day.processInput2(groups[0], groups[1]));
    }

}
