package com.unhuman.adventofcode2022;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day24Test {
    @Test
    public void test1() {
        String filename = "src/test/resources/Day24Case.txt";
        Object expectedResult1 = 18;
        Object expectedResult2 = 54;

        Day24 day = new Day24(filename);
        ConfigGroup[] groups = day.parseFiles();

        long time;
        float timeMs;

        System.out.println("*** Start " + getClass().getSimpleName() + " Task 1 *** ");
        time = System.nanoTime();
        Object result = day.processInput1(groups[0], groups[1]);
        time = System.nanoTime() - time;
        timeMs = (float) time / 1000000;
        System.out.println(result);
        System.out.println("*** End " + getClass().getSimpleName() + " Task 1 - time: " + time / 1000 + "us, " + timeMs + "ms ***");
        Assertions.assertEquals(expectedResult1, result);

        System.out.println("*** Start " + getClass().getSimpleName() + " Task 2 *** ");
        time = System.nanoTime();
        result = day.processInput2(groups[0], groups[1]);
        time = System.nanoTime() - time;
        timeMs = (float) time / 1000000;
        System.out.println(result);
        System.out.println("*** End " + getClass().getSimpleName() + " Task 2 - time: " + time / 1000 + "us, " + timeMs + "ms ***");
        Assertions.assertEquals(expectedResult2, result);
    }
}
