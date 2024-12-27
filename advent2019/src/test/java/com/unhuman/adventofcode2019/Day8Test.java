package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day8Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "123456789012\n";

    static Day8 getDay(String data) {
        return new Day8(data);
    }

    @Test
    public void test1() {
        Day8 day = getDay(DATA);
        day.overrideDimensions(3, 2);

        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        Day8 day = getDay("0222112222120000/\n");
        day.overrideDimensions(2, 2);

        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(".#\n#.", day.processInput2(groups[0], groups[1]));
    }
}
