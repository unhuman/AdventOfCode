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
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals(3500, parser.peek(0));
    }

    @Test
    public void testDay2pt1_2() {
        String data = "1,0,0,0,99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals(2L, parser.peek(0));
    }

    @Test
    public void testDay2pt1_3() {
        String data = "2,3,0,3,99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals(6L, parser.peek(3));
    }

    @Test
    public void testDay2pt1_4() {
        String data = "2,4,4,5,99,0";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals(9801, parser.peek(5));
    }

    @Test
    public void testDay2pt1_5() {
        String data = "1,1,1,4,99,5,6,0,99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals(30, parser.peek(0));
    }

    @Test
    public void testDay5pt1_1() {
        String data = "1002,4,3,4,33";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals(99, parser.peek(4));
    }

    @Test
    public void testDay5pt1_2() {
        String data = "1101,100,-1,4,0";
        IntCodeParser parser = new IntCodeParser(data);
        parser.addInput("7");
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals(99, parser.getReadOnlyMemory().get(4));
    }

    @Test
    public void testDay5pt2_1a() { // equals 8 (position)
        String data = "3,9,8,9,10,9,4,9,99,-1,8";
        IntCodeParser parser = new IntCodeParser(data);
        parser.addInput("8");
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals("1", parser.getOutput());
    }

    @Test
    public void testDay5pt2_1b() { // equals 8 (position)
        String data = "3,9,8,9,10,9,4,9,99,-1,8";
        IntCodeParser parser = new IntCodeParser(data);
        parser.addInput("7");
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals("0", parser.getOutput());
    }

    @Test
    public void testDay5pt2_2a() { // less than 8 (position)
        String data = "3,9,7,9,10,9,4,9,99,-1,8";
        IntCodeParser parser = new IntCodeParser(data);
        parser.addInput("7");
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals("1", parser.getOutput());
    }

    @Test
    public void testDay5pt2_2b() { // less than 8 (position)
        String data = "3,9,7,9,10,9,4,9,99,-1,8";
        IntCodeParser parser = new IntCodeParser(data);
        parser.addInput("8");
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals("0", parser.getOutput());
    }

    @Test
    public void testDay5pt2_3a() { // equal 8 (immediate)
        String data = "3,3,1108,-1,8,3,4,3,99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.addInput("7");
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals("0", parser.getOutput());
    }

    @Test
    public void testDay5pt2_3b() { // equal 8 (immediate)
        String data = "3,3,1108,-1,8,3,4,3,99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.addInput("8");
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals("1", parser.getOutput());
    }

    @Test
    public void testDay5pt2_4a() { // less than 8 (immediate)
        String data = "3,3,1107,-1,8,3,4,3,99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.addInput("7");
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals("1", parser.getOutput());
    }

    @Test
    public void testDay5pt2_4b() { // less than 8 (immediate)
        String data = "3,3,1107,-1,8,3,4,3,99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.addInput("8");
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals("0", parser.getOutput());
    }

    @Test
    public void testDay5pt2_5a() { // jump tests = 0 (position)
        String data = "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9";
        IntCodeParser parser = new IntCodeParser(data);
        parser.addInput("0");
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals("0", parser.getOutput());
    }

    @Test
    public void testDay5pt2_5b() { // jump tests = 0 (position)
        String data = "3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9";
        IntCodeParser parser = new IntCodeParser(data);
        parser.addInput("3");
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals("1", parser.getOutput());
    }

    @Test
    public void testDay5pt2_6a() { // jump tests = 0 (immediate)
        String data = "3,3,1105,-1,9,1101,0,0,12,4,12,99,1";
        IntCodeParser parser = new IntCodeParser(data);
        parser.addInput("0");
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals("0", parser.getOutput());
    }

    @Test
    public void testDay5pt2_6b() { // jump tests = 0 (immediate)
        String data = "3,3,1105,-1,9,1101,0,0,12,4,12,99,1";
        IntCodeParser parser = new IntCodeParser(data);
        parser.addInput("3");
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals("1", parser.getOutput());
    }

    @Test
    public void testDay5pt2_7a() { // larger test
        String data = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
                "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.addInput("7");
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals("999", parser.getOutput());
    }

    @Test
    public void testDay5pt2_7b() { // larger test
        String data = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
                "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.addInput("8");
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals("1000", parser.getOutput());
    }

    @Test
    public void testDay5pt2_7c() { // larger test
        String data = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
                "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.addInput("9");
        parser.process();
        List<Long> readonlyMemory = parser.getReadOnlyMemory();
        assertEquals("1001", parser.getOutput());
    }

    @Test
    public void testDay9pt1_1() { // larger test
        String data = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        assertEquals(data, parser.getOutput());
    }


    @Test
    public void testDay9pt1_2() { // larger test
        String data = "1102,34915192,34915192,7,4,7,99,0";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        assertEquals(16, parser.getOutput().toString().length());
    }

    @Test
    public void testDay9pt1_3() { // larger test
        String data = "104,1125899906842624,99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        assertEquals("1125899906842624", parser.getOutput());
    }


    @Test
    public void testDay9bonus1() { // larger test
        String data = "109, -1, 4, 1, 99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        assertEquals("-1", parser.getOutput());
    }

    @Test
    public void testDay9bonus2() { // larger test
        String data = "109, -1, 104, 1, 99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        assertEquals("1", parser.getOutput());
    }
    @Test
    public void testDay9bonus3() { // larger test
        String data = "109, -1, 204, 1, 99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        assertEquals("109", parser.getOutput());
    }
    @Test
    public void testDay9bonus4() { // larger test
        String data = "109, 1, 9, 2, 204, -6, 99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        assertEquals("204", parser.getOutput());
    }
    @Test
    public void testDay9bonus5() { // larger test
        String data = "109, 1, 109, 9, 204, -6, 99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        assertEquals("204", parser.getOutput());
    }
    @Test
    public void testDay9bonus6() { // larger test
        String data = "109, 1, 209, -1, 204, -106, 99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.process();
        assertEquals("204", parser.getOutput());
    }
    @Test
    public void testDay9bonus7() { // larger test
        String data = "109, 1, 3, 3, 204, 2, 99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.setInput("4");
        parser.process();
        assertEquals("4", parser.getOutput());
    }
    @Test
    public void testDay9bonus8() { // larger test
        String data = "109, 1, 203, 2, 204, 2, 99";
        IntCodeParser parser = new IntCodeParser(data);
        parser.setInput("4");
        parser.process();
        assertEquals("4", parser.getOutput());
    }
}
