package com.unhuman.adventofcode2022;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day25Test {
    @Test
    public void test1() {
        String filename = "src/test/resources/Day25Case.txt";
        Object expectedResult1 = "2=-1=0";
        Object expectedResult2 = 2;

        Day25 day = new Day25(filename);
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

    @Test
    public void test2() {
        Assertions.assertEquals(1747, Day25.convertSnafu("1=-0-2"));
        Assertions.assertEquals(906, Day25.convertSnafu("12111"));
        Assertions.assertEquals(198, Day25.convertSnafu("2=0="));
        Assertions.assertEquals(11, Day25.convertSnafu("21"));
        Assertions.assertEquals(201, Day25.convertSnafu("2=01"));
        Assertions.assertEquals(31, Day25.convertSnafu("111"));
        Assertions.assertEquals(1257, Day25.convertSnafu("20012"));
        Assertions.assertEquals(32, Day25.convertSnafu("112"));
        Assertions.assertEquals(353, Day25.convertSnafu("1=-1="));
        Assertions.assertEquals(107, Day25.convertSnafu("1-12"));
        Assertions.assertEquals(7, Day25.convertSnafu("12"));
        Assertions.assertEquals(3, Day25.convertSnafu("1="));
        Assertions.assertEquals(37, Day25.convertSnafu("122"));

        Assertions.assertEquals(Day25.convertSnafu("2="),8);
    }

    @Test
    public void test3() {
        Assertions.assertEquals("1=-0-2", Day25.getSnafu(1747));
        Assertions.assertEquals("12111", Day25.getSnafu(906));
        Assertions.assertEquals("2=0=", Day25.getSnafu(198));
        Assertions.assertEquals("21", Day25.getSnafu(11));
        Assertions.assertEquals("2=01", Day25.getSnafu(201));
        Assertions.assertEquals("111", Day25.getSnafu(31));
        Assertions.assertEquals("20012", Day25.getSnafu(1257));
        Assertions.assertEquals("112", Day25.getSnafu(32));
        Assertions.assertEquals("1=-1=", Day25.getSnafu(353));
        Assertions.assertEquals("1-12", Day25.getSnafu(107));
        Assertions.assertEquals("12", Day25.getSnafu(7));
        Assertions.assertEquals("1=", Day25.getSnafu(3));
        Assertions.assertEquals("122", Day25.getSnafu(37));

        Assertions.assertEquals("2=", Day25.getSnafu(8));
    }
}
