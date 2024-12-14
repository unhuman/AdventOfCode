package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day13Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "Button A: X+94, Y+34\n" +
            "Button B: X+22, Y+67\n" +
            "Prize: X=8400, Y=5400\n" +
            "\n" +
            "Button A: X+26, Y+66\n" +
            "Button B: X+67, Y+21\n" +
            "Prize: X=12748, Y=12176\n" +
            "\n" +
            "Button A: X+17, Y+86\n" +
            "Button B: X+84, Y+37\n" +
            "Prize: X=7870, Y=6450\n" +
            "\n" +
            "Button A: X+69, Y+23\n" +
            "Button B: X+27, Y+71\n" +
            "Prize: X=18641, Y=10279\n";

    static InputParser getDay(String data) {
        return new Day13(data);
    }

    @Test
    public void test1pre0() {
        String data = "Button A: X+94, Y+34\n" +
                "Button B: X+22, Y+67\n" +
                "Prize: X=1, Y=1\n";

        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(0L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1pre1() {
        String data = "Button A: X+94, Y+34\n" +
                "Button B: X+22, Y+67\n" +
                "Prize: X=94, Y=34\n";

        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(3L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1pre2() {
        String data = "Button A: X+94, Y+34\n" +
                "Button B: X+22, Y+67\n" +
                "Prize: X=22, Y=67\n";

        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1pre3() {
        String data = "Button A: X+94, Y+34\n" +
                "Button B: X+22, Y+67\n" +
                "Prize: X=116, Y=101\n";

        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(4L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1pre4() {
        String data = "Button A: X+94, Y+34\n" +
                "Button B: X+22, Y+67\n" +
                "Prize: X=117, Y=102\n";

        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(0L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1a() {
        String data = "Button A: X+94, Y+34\n" +
                "Button B: X+22, Y+67\n" +
                "Prize: X=8400, Y=5400\n";

        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(280L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1b() {
        String data =
                "Button A: X+26, Y+66\n" +
                        "Button B: X+67, Y+21\n" +
                        "Prize: X=12748, Y=12176\n";

                        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(0L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1c() {
        String data =
                "Button A: X+17, Y+86\n" +
                        "Button B: X+84, Y+37\n" +
                        "Prize: X=7870, Y=6450\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(200L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1d() {
        String data =
                "Button A: X+69, Y+23\n" +
                        "Button B: X+27, Y+71\n" +
                        "Prize: X=18641, Y=10279\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(0L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(480L, day.processInput1(groups[0], groups[1]));
    }

    // These tests not valid for the englarger numbers
//    @Test
//    public void test2a() {
//        String data = "Button A: X+94, Y+34\n" +
//                "Button B: X+22, Y+67\n" +
//                "Prize: X=8400, Y=5400\n";
//        InputParser day = getDay(data);
//        ConfigGroup[] groups = day.parseFiles();
//        Assertions.assertEquals(280L, day.processInput2(groups[0], groups[1]));
//    }
//
//    @Test
//    public void test2() {
//        String data = DATA;
//        InputParser day = getDay(data);
//        ConfigGroup[] groups = day.parseFiles();
//        Assertions.assertEquals(480L, day.processInput2(groups[0], groups[1]));
//    }
}
