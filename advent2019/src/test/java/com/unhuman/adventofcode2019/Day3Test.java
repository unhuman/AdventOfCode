package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day3Test {
    // data must be at least 2 lines - add \n for single line data
    static InputParser getDay(String data) {
        return new Day3(data);
    }

    @Test
    public void test1a() {
        String data = "R8,U5,L5,D3\nU7,R6,D4,L4";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(6, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1b() {
        String data = "R75,D30,R83,U83,L12,D49,R71,U7,L72\nU62,R66,U55,R34,D71,R55,D58,R83";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(159, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1c() {
        String data = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\n" +
                "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(135, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2a() {
        String data = "R8,U5,L5,D3\nU7,R6,D4,L4";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(30, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2b() {
        String data = "R75,D30,R83,U83,L12,D49,R71,U7,L72\nU62,R66,U55,R34,D71,R55,D58,R83";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(610, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2c() {
        String data = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\n" +
                "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(410, day.processInput2(groups[0], groups[1]));
    }


}
