package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode2023.Day1;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day1Test {
    @Test
    public void test1() {
        String data = "1abc2\n" +
                "pqr3stu8vwx\n" +
                "a1b2c3d4e5f\n" +
                "treb7uchet";
        Day1 day = new Day1(data);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(142, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String filename = "src/test/resources/2023day1-2.txt";
        Day1 day = new Day1(filename);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(281, day.processInput2(groups[0], groups[1]));
    }

}
