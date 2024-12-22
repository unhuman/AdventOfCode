package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day22Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "\n";

    static Day22 getDay(String data) {
        return new Day22(data);
    }

    @Test
    public void test1prep1() {
        String data = "123\n";
        Day22 day = getDay(data);
        day.setEvolutionCount(1);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(15887950L, day.processInput1(groups[0], groups[1]));
    }


    @Test
    public void test1prep2() {
        String data = "123\n";
        Day22 day = getDay(data);
        day.setEvolutionCount(10);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(5908254L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1() {
        String data = "1\n" +
                "10\n" +
                "100\n" +
                "2024\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(37327623L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2pre() {
        String data = "123\n";
        Day22 day = getDay(data);
        day.setEvolutionCount(10);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(6L, day.processInput2(groups[0], groups[1]));
    }


    @Test
    public void test2() {
        String data = "1\n" +
                "2\n" +
                "3\n" +
                "2024\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(23L, day.processInput2(groups[0], groups[1]));
    }
}
