package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Test;

public class Day19Test {
    @Test
    public void test1() {
        String[] test1 = new String[] { "src/test/resources/Day19Case.txt" };
        Day19 day = new Day19(test1);
        ConfigGroup[] groups = day.parseFiles();

        long time;
        float timeMs;

        System.out.println("Day 19 tests - disabled - take too long");

//        System.out.println("*** Start " + getClass().getSimpleName() + " Task 1 *** ");
//        time = System.nanoTime();
//        Object result = day.processInput1(groups[0], groups[1]);
//        time = System.nanoTime() - time;
//        timeMs = (float) time / 1000000;
//        System.out.println("*** End " + getClass().getSimpleName() + " Task 1 - time: " + time / 1000 + "us, " + timeMs + "ms ***");
//        Assertions.assertEquals(33, result);
//
//        System.out.println("*** Start " + getClass().getSimpleName() + " Task 2 *** ");
//        time = System.nanoTime();
//        Object result2 = day.processInput2(groups[0], groups[1]);
//        time = System.nanoTime() - time;
//        timeMs = (float) time / 1000000;
//        System.out.println("*** End " + getClass().getSimpleName() + " Task 2 - time: " + time / 1000 + "us, " + timeMs + "ms ***");
//        Assertions.assertEquals(62, result2);
    }
}
