package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day10Test {
    @Test
    public void test1() {
        String filename = "src/test/resources/Day10Case.txt";
        Day10 day = new Day10(filename);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(13140, day.processInput1(groups[0], groups[1]));

        String expected =
                "XX..XX..XX..XX..XX..XX..XX..XX..XX..XX..\n" +
                "XXX...XXX...XXX...XXX...XXX...XXX...XXX.\n" +
                "XXXX....XXXX....XXXX....XXXX....XXXX....\n" +
                "XXXXX.....XXXXX.....XXXXX.....XXXXX.....\n" +
                "XXXXXX......XXXXXX......XXXXXX......XXXX\n" +
                "XXXXXXX.......XXXXXXX.......XXXXXXX.....\n";
        Assertions.assertEquals(expected, day.processInput2(groups[0], groups[1]));
    }
}
