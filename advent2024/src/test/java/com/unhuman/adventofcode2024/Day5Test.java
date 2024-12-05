package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day5Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "47|53\n" +
                    "97|13\n" +
                    "97|61\n" +
                    "97|47\n" +
                    "75|29\n" +
                    "61|13\n" +
                    "75|53\n" +
                    "29|13\n" +
                    "97|29\n" +
                    "53|29\n" +
                    "61|53\n" +
                    "97|53\n" +
                    "61|29\n" +
                    "47|13\n" +
                    "75|47\n" +
                    "97|75\n" +
                    "47|61\n" +
                    "75|61\n" +
                    "47|29\n" +
                    "75|13\n" +
                    "53|13\n" +
                    "\n" +
                    "75,47,61,53,29\n" +
                    "97,61,53,29,13\n" +
                    "75,29,13\n" +
                    "75,97,47,61,53\n" +
                    "61,13,29\n" +
                    "97,13,75,29,47";

    static InputParser getDay(String data) {
        return new Day5(data);
    }

    @Test
    public void test1() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(143L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(123L, day.processInput2(groups[0], groups[1]));
    }
}
