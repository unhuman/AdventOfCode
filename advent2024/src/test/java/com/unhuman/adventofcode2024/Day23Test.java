package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day23Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "kh-tc\n" +
                    "qp-kh\n" +
                    "de-cg\n" +
                    "ka-co\n" +
                    "yn-aq\n" +
                    "qp-ub\n" +
                    "cg-tb\n" +
                    "vc-aq\n" +
                    "tb-ka\n" +
                    "wh-tc\n" +
                    "yn-cg\n" +
                    "kh-ub\n" +
                    "ta-co\n" +
                    "de-co\n" +
                    "tc-td\n" +
                    "tb-wq\n" +
                    "wh-td\n" +
                    "ta-ka\n" +
                    "td-qp\n" +
                    "aq-cg\n" +
                    "wq-ub\n" +
                    "ub-vc\n" +
                    "de-ta\n" +
                    "wq-aq\n" +
                    "wq-vc\n" +
                    "wh-yn\n" +
                    "ka-de\n" +
                    "kh-ta\n" +
                    "co-tc\n" +
                    "wh-qp\n" +
                    "tb-vc\n" +
                    "td-yn\n\n";

    static InputParser getDay(String data) {
        return new Day23(data);
    }

    @Test
    public void test1() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(7, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals("co,de,ka,ta", day.processInput2(groups[0], groups[1]));
    }
}
