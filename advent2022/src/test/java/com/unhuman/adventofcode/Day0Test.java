package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day0Test {
    @Test
    public void test1() {
        String[] test1 = new String[] { "src/test/resources/Day0Case.txt" };
        Day0 day = new Day0(test1);
        ConfigGroup[] groups = day.parseFiles();

        long time;
        float timeMs;

        System.out.println("*** Start " + getClass().getSimpleName() + " Task 1 *** ");
        time = System.nanoTime();
        timeMs = (float) time / 1000000;
        Object result = day.processInput1(groups[0], groups[1]);
        System.out.println("*** End " + getClass().getSimpleName() + " Task 1 - time: " + time / 1000 + "us, " + timeMs + "ms ***");
        Assertions.assertEquals(1, result);

        System.out.println("*** Start " + getClass().getSimpleName() + " Task 2 *** ");
        time = System.nanoTime();
        timeMs = (float) time / 1000000;
        result = day.processInput2(groups[0], groups[1]);
        System.out.println("*** End " + getClass().getSimpleName() + " Task 2 - time: " + time / 1000 + "us, " + timeMs + "ms ***");
        Assertions.assertEquals(2, result);
    }
}
