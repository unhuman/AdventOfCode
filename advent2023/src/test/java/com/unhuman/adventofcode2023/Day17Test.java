package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day17Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
                    "2413432311323\n" +
                    "3215453535623\n" +
                    "3255245654254\n" +
                    "3446585845452\n" +
                    "4546657867536\n" +
                    "1438598798454\n" +
                    "4457876987766\n" +
                    "3637877979653\n" +
                    "4654967986887\n" +
                    "4564679986453\n" +
                    "1224686865563\n" +
                    "2546548887735\n" +
                    "4322674655533\n";

    @Test
    public void test1() {
        Day17 testDay = new Day17(DATA);
        ConfigGroup[] groups = testDay.parseFiles();
        Assertions.assertEquals(102L, testDay.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1a1() {
        String data =
                "259\n";
        Day17 testDay = new Day17(data);
        ConfigGroup[] groups = testDay.parseFiles();
        Assertions.assertEquals(14L, testDay.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1a2() {
        String data =
                "259\n" +
                "329\n" +
                "371";
        Day17 testDay = new Day17(data);
        ConfigGroup[] groups = testDay.parseFiles();
        Assertions.assertEquals(13L, testDay.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1a3() {
        String data =
                "269\n" +
                "311\n" +
                "921\n";
        Day17 testDay = new Day17(data);
        ConfigGroup[] groups = testDay.parseFiles();
        Assertions.assertEquals(6L, testDay.processInput1(groups[0], groups[1]));
    }


    @Test
    public void test1a4() {
        String data =
                "269\n" +
                "311\n" +
                "529\n" +
                "171";
        Day17 testDay = new Day17(data);
        ConfigGroup[] groups = testDay.parseFiles();
        Assertions.assertEquals(14L, testDay.processInput1(groups[0], groups[1]));
    }


    @Test
    public void test1b1() {
        String data =
                "12\n";
        Day17 testDay = new Day17(data);
        ConfigGroup[] groups = testDay.parseFiles();
        Assertions.assertEquals(2L, testDay.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1b2() {
        String data =
                "124\n";
        Day17 testDay = new Day17(data);
        ConfigGroup[] groups = testDay.parseFiles();
        Assertions.assertEquals(6L, testDay.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1b3() {
        String data =
                "1248\n";
        Day17 testDay = new Day17(data);
        ConfigGroup[] groups = testDay.parseFiles();
        Assertions.assertEquals(14L, testDay.processInput1(groups[0], groups[1]));
    }

//    @Test
//    public void test1b4() {
//        String data =
//                "12489\n";
//        Day17 testDay = new Day17(data);
//        ConfigGroup[] groups = testDay.parseFiles();
//        Assertions.assertEquals(Integer.MAX_VALUE, testDay.processInput1(groups[0], groups[1]));
//    }

    @Test
    public void test1c1() {
        String data =
                "1\n" +
                "2\n";

        Day17 testDay = new Day17(data);
        ConfigGroup[] groups = testDay.parseFiles();
        Assertions.assertEquals(2L, testDay.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1c2() {
        String data =
                "1\n" +
                "2\n" +
                "4\n";

        Day17 testDay = new Day17(data);
        ConfigGroup[] groups = testDay.parseFiles();
        Assertions.assertEquals(6L, testDay.processInput1(groups[0], groups[1]));
    }

//    @Test
//    public void test1c3() {
//        String data =
//                "1\n" +
//                "2\n" +
//                "4\n" +
//                "8\n";
//
//        Day17 testDay = new Day17(data);
//        ConfigGroup[] groups = testDay.parseFiles();
//        Assertions.assertEquals(14L, testDay.processInput1(groups[0], groups[1]));
//    }

    @Test
    public void test1d() {
        String data =
                "2451\n" +
                "9952\n" +
                "9952\n";
        Day17 testDay = new Day17(data);
        ConfigGroup[] groups = testDay.parseFiles();
        Assertions.assertEquals(14L, testDay.processInput1(groups[0], groups[1]));
    }


    @Test
    public void test2() {
        Day17 testDay = new Day17(DATA);
        ConfigGroup[] groups = testDay.parseFiles();
        Assertions.assertEquals(94L, testDay.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2a() {
        String data = "111111111111\n" +
                "999999999991\n" +
                "999999999991\n" +
                "999999999991\n" +
                "999999999991\n";
        Day17 testDay = new Day17(data);
        ConfigGroup[] groups = testDay.parseFiles();
        Assertions.assertEquals(94L, testDay.processInput2(groups[0], groups[1]));
    }
}
