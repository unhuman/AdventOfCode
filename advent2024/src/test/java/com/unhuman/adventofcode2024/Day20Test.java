package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day20Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "###############\n" +
            "#...#...#.....#\n" +
            "#.#.#.#.#.###.#\n" +
            "#S#...#.#.#...#\n" +
            "#######.#.#.###\n" +
            "#######.#.#...#\n" +
            "#######.#.###.#\n" +
            "###..E#...#...#\n" +
            "###.#######.###\n" +
            "#...###...#...#\n" +
            "#.#####.#.###.#\n" +
            "#.#...#.#.#...#\n" +
            "#.#.#.#.#.#.###\n" +
            "#...#...#...###\n" +
            "###############\n";

    static Day20 getDay(String data) {
        return new Day20(data);
    }

    @Test
    public void test0A() {
        String data =
                "####" + "\n" +
                        "#S #" + "\n" +
                        "## #" + "\n" +
                        "#E #" + "\n" +
                        "####" + "\n";
        Day20 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        day.setRequiredSavings(1);
        Assertions.assertEquals(1, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test0B() {
        String data =
                        "#####" + "\n" +
                        "#S  #" + "\n" +
                        "### #" + "\n" +
                        "#E  #" + "\n" +
                        "#####" + "\n";
        Day20 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        day.setRequiredSavings(1);
        Assertions.assertEquals(2, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test0C() {
        String data =
                "#####" + "\n" +
                "#S  #" + "\n" +
                "### #" + "\n" +
                "#   #" + "\n" +
                "# ###" + "\n" +
                "#  E#" + "\n" +
                "#####" + "\n";
        Day20 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        day.setRequiredSavings(1);
        Assertions.assertEquals(4, day.processInput1(groups[0], groups[1]));
    }


    @Test
    public void test1() {
        String data = DATA;
        Day20 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        day.setRequiredSavings(1);
        Assertions.assertEquals(44, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2pre0() {
        String data =
                        "####" + "\n" +
                        "#SE#" + "\n" +
                        "#####" + "\n";
        Day20 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        day.setRequiredSavings(1);
        Assertions.assertEquals(0, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2pre1() {
        String data =
                        "####" + "\n" +
                        "#S #" + "\n" +
                        "## #" + "\n" +
                        "#E #" + "\n" +
                        "####" + "\n";
        Day20 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        day.setRequiredSavings(1);
        Assertions.assertEquals(1, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2pre2() {
        String data =
                        "#####" + "\n" +
                        "#S  #" + "\n" +
                        "### #" + "\n" +
                        "#E  #" + "\n" +
                        "#####" + "\n";
        Day20 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        day.setRequiredSavings(1);
        Assertions.assertEquals(4, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2pre3() {
        String data =
                        "#####" + "\n" +
                        "#S  #" + "\n" +
                        "### #" + "\n" +
                        "### #" + "\n" +
                        "#E  #" + "\n" +
                        "#####" + "\n";
        Day20 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        day.setRequiredSavings(1);
        Assertions.assertEquals(2, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = DATA;
        Day20 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        day.setRequiredSavings(50);
        Assertions.assertEquals(285, day.processInput2(groups[0], groups[1]));

        // {64=19, 66=12, 68=14, 70=12, 72=22, 74=4, 76=3, 50=32, 52=31, 54=29, 56=39, 58=25, 60=22, 62=20}
        // should have 60=23, not 22.
    }
}
