package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day15Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7\n";

    static Day15 day;

    @Test
    public void test1a() {
        day = new Day15("HASH\n");
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(52L, day.processInput1(groups[0], groups[1]));
    }


    @Test
    public void test1b() {
        day = new Day15(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1320L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        day = new Day15(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(145L, day.processInput2(groups[0], groups[1]));
    }
}
