package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day17Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "\n";

    static Day17 getDay(String data) {
        return new Day17(data);
    }

    //    If register C contains 9, the program 2,6 would set register B to 1.
    @Test
    public void test1a() {
        String data = "Register A: 0\n" +
                "Register B: 0\n" +
                "Register C: 9\n" +
                "\n" +
                "Program: 2,6";
        Day17 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals("", day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(1, day.getRegisterValue('B'));
    }

    //    If register A contains 10, the program 5,0,5,1,5,4 would output 0,1,2.
    @Test
    public void test1b() {
        String data = "Register A: 10\n" +
                "Register B: 0\n" +
                "Register C: 0\n" +
                "\n" +
                "Program: 5,0,5,1,5,4";
        Day17 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals("0,1,2", day.processInput1(groups[0], groups[1]));
    }

    //    If register A contains 2024, the program 0,1,5,4,3,0 would output 4,2,5,6,7,7,7,7,3,1,0 and leave 0 in register A.
    @Test
    public void test1c() {
        String data = "Register A: 2024\n" +
                "Register B: 0\n" +
                "Register C: 0\n" +
                "\n" +
                "Program: 0,1,5,4,3,0";
        Day17 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals("4,2,5,6,7,7,7,7,3,1,0", day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(0, day.getRegisterValue('A'));

    }

    //    If register B contains 29, the program 1,7 would set register B to 26.
    @Test
    public void test1d() {
        String data = "Register A: 0\n" +
                "Register B: 29\n" +
                "Register C: 0\n" +
                "\n" +
                "Program: 1,7";
        Day17 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals("", day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(26, day.getRegisterValue('B'));
    }

    //    If register B contains 2024 and register C contains 43690, the program 4,0 would set register B to 44354.
    @Test
    public void test1e() {
        String data = "Register A: 0\n" +
                "Register B: 2024\n" +
                "Register C: 43690\n" +
                "\n" +
                "Program: 4,0";
        Day17 day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals("", day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals(44354, day.getRegisterValue('B'));
    }

    @Test
    public void test1() {
        String data = "Register A: 729\n" +
                "Register B: 0\n" +
                "Register C: 0\n" +
                "\n" +
                "Program: 0,1,5,4,3,0";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals("4,6,3,5,6,3,5,2,1,0", day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2vet() {
        String data = "Register A: 117440\n" +
                "Register B: 0\n" +
                "Register C: 0\n" +
                "\n" +
                "Program: 0,3,5,4,3,0";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals("0,3,5,4,3,0", day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = "Register A: 0\n" +
                "Register B: 0\n" +
                "Register C: 0\n" +
                "\n" +
                "Program: 0,3,5,4,3,0";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(117440L, day.processInput2(groups[0], groups[1]));
    }

}
