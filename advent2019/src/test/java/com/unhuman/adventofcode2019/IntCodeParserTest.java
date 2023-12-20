package com.unhuman.adventofcode2019;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntCodeParserTest {
    @Test
    public void testDay2pt1_1() {
        String data = "1,9,10,3,2,3,11,0,99,30,40,50";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        List<Integer> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals(3500, parser.peek(0));
    }

    @Test
    public void testDay2pt1_2() {
        String data = "1,0,0,0,99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        List<Integer> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals(2, parser.peek(0));
    }

    @Test
    public void testDay2pt1_3() {
        String data = "2,3,0,3,99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        List<Integer> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals(6, parser.peek(3));
    }

    @Test
    public void testDay2pt1_4() {
        String data = "2,4,4,5,99,0";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        List<Integer> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals(9801, parser.peek(5));
    }

    @Test
    public void testDay2pt1_5() {
        String data = "1,1,1,4,99,5,6,0,99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        List<Integer> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals(30, parser.peek(0));
    }
}
