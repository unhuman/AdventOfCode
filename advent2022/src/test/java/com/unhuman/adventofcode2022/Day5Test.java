package com.unhuman.adventofcode2022;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day5Test {
    @Test
    public void test0() {
        String data = "    [D]    \n" +
                "[N] [C]    \n" +
                "[Z] [M] [P]\n" +
                " 1   2   3 \n" +
                "\n" +
                "move 1 from 2 to 1\n" +
                "move 3 from 1 to 3\n" +
                "move 2 from 2 to 1\n" +
                "move 1 from 1 to 2";
        Day5 day = new Day5(data);

        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals("CMZ", day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals("MCD", day.processInput2(groups[0], groups[1]));
    }


    @Test
    public void test1() {
        Day5 day = new Day5("src/test/resources/Day5Case.txt");
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals("CMZ", day.processInput1(groups[0], groups[1]));
        Assertions.assertEquals("MCD", day.processInput2(groups[0], groups[1]));
    }
}
