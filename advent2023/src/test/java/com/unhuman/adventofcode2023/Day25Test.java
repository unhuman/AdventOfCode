package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day25Test {

    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "rsh: frs pzl lsr\n" +
            "xhk: hfx\n" +
            "cmg: qnr nvd lhk bvb\n" +
            "rhn: xhk bvb hfx\n" +
            "jqt: rhn xhk nvd\n" +
            "bvb: xhk hfx\n" +
            "pzl: lsr hfx nvd\n" +
            "qnr: nvd\n" +
            "ntq: jqt hfx bvb xhk\n" +
            "nvd: lhk\n" +
            "lsr: lhk\n" +
            "rzs: qnr cmg lsr rsh\n" +
            "frs: qnr lhk lsr\n";

    static InputParser getDay(String data) {
        return new Day25(data);
    }

    @Test
    public void test1() {
        InputParser day = getDay(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(54, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        InputParser day = getDay(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(0, day.processInput2(groups[0], groups[1]));
    }
}
