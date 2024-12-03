package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day3Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))\n";

    static InputParser getDay(String data) {
        return new Day3(data);
    }

    @Test
    public void test1() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(161L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(48L, day.processInput2(groups[0], groups[1]));
    }
}
