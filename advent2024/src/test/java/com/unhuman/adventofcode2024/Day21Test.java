package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class Day21Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "\n";

    static InputParser getDay(String data) {
        return new Day21(data);
    }

    @Test
    public void keypadTest1() {
        Day21.KeyPad doorway = new Day21.KeyPad("789\n456\n123\n 0A");
        String answer = doorway.followDirections("<A^A>^^AvvvA");
        Assertions.assertEquals("029A", answer);

        Day21.KeyPad robot = new Day21.KeyPad(" ^A\n<v>");

        String processed = doorway.processCommand(answer);
        Assertions.assertEquals("<A^A>^^AvvvA", processed);
        processed = robot.processCommand(processed);
        Assertions.assertEquals("v<<A>>^A<A>AvA<^AA>A<vAAA>^A".length(), processed.length());
        processed = robot.processCommand(processed);
        Assertions.assertEquals("<vA<AA>>^AvAA<^A>A<v<A>>^AvA^A<vA>^A<v<A>^A>AAvA^A<v<A>A>^AAAvA<^A>A".length(), processed.length());
    }

    @Test
    public void keypadTest2() {
        Day21.KeyPad robot = new Day21.KeyPad(" ^A\n<v>");
        String answer = robot.followDirections("v<<A>>^A<A>AvA<^AA>A<vAAA>^A");
        Assertions.assertEquals("<A^A>^^AvvvA", answer);
    }

    @Test
    public void keypadTest3() {
        Day21.KeyPad doorway = new Day21.KeyPad("789\n456\n123\n 0A");
        String program = doorway.processCommand("379A");
        String answer = doorway.followDirections(program);
        Assertions.assertEquals("379A", answer);

        Day21.KeyPad robot = new Day21.KeyPad(" ^A\n<v>");
        String robotDirections = robot.processCommand(program);
        String robotCommand = robot.followDirections(robotDirections);
        Assertions.assertEquals(program, robotCommand);

        Day21.KeyPad robot2 = new Day21.KeyPad(" ^A\n<v>");
        String robot2Directions = robot.processCommand(robotDirections);
        String robot2Command = robot.followDirections(robot2Directions);
        Assertions.assertEquals(robot2Command, robotDirections);

        // Go back....
        String output1 = robot.followDirections(robot2Command);
        String output2 = doorway.followDirections(output1);
        Assertions.assertEquals("379A", output2);
    }

    @Test
    public void keypadTest4() {
        Day21.KeyPad doorway = new Day21.KeyPad("789\n456\n123\n 0A");
        Day21.KeyPad robot = new Day21.KeyPad(" ^A\n<v>");
        Day21.KeyPad robot2 = new Day21.KeyPad(" ^A\n<v>");
        //final String command = "<v<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>A<v<A>A>^AAAvA<^A>A";
        final String command = "v<<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>Av<<A>A>^AAAvA<^A>A";
        System.out.println("Robot 2 Following: " + command);
        String robot2Results = robot2.followDirections(command);
        System.out.println("Robot 1 Following: " + robot2Results);
        String robotResults = robot.followDirections(robot2Results);
        System.out.println("Doorway Following: " + robotResults);
        String doorwayResults = doorway.followDirections(robotResults);
        System.out.println("Doorway Result: " + doorwayResults);
        Assertions.assertEquals("379A", doorwayResults);

        // Go back....
        System.out.println("Doorway Processing: " + doorwayResults);
        String output1 = doorway.processCommand(doorwayResults);
        Assertions.assertEquals(robotResults, output1);
        System.out.println("Robot 2 Processing: " + output1);
        String output2 = robot2.processCommand(output1);
//        Assertions.assertEquals(robot2Results, output2);
        System.out.println("Robot 1 Processing: " + output2);
        String output = robot.processCommand(output2);
        System.out.println("Robot 1 Output: " + output);
        Assertions.assertEquals(command.length(), output.length());
    }

    @Test
    public void keypadTest5() {
        Day21.KeyPad doorway = new Day21.KeyPad("789\n456\n123\n 0A");
        Day21.KeyPad robot = new Day21.KeyPad(" ^A\n<v>");
        Day21.KeyPad robot2 = new Day21.KeyPad(" ^A\n<v>");
        final String command = "v<<A>>^AvA^A<vA<AA>>^AAvA<^A>AAvA^A<vA>^AA<A>Av<<A>A>^AAAvA<^A>A";
//      final String command = "v<<A^>>AvA^Av<<A^>>AAv<A<A^>>AA<Av>AA^Av<A^>AA<A>Av<A<A^>>AAA<Av>A^A";
        System.out.println("Robot 2 Following: " + command);
        String robot2Results = robot2.followDirections(command);
        System.out.println("Robot 1 Following: " + robot2Results);
        String robotResults = robot.followDirections(robot2Results);
        System.out.println("Doorway Following: " + robotResults);
        String doorwayResults = doorway.followDirections(robotResults);
        System.out.println("Doorway Result: " + doorwayResults);
        Assertions.assertEquals("379A", doorwayResults);

        // Go back....
        System.out.println("Doorway Processing: " + doorwayResults);
        String output1 = doorway.processCommand(doorwayResults);
        Assertions.assertEquals(robotResults, output1);
        System.out.println("Robot 2 Processing: " + output1);
        String output2 = robot2.processCommand(output1);
        Assertions.assertEquals(robot2Results.length(), output2.length());
        System.out.println("Robot 1 Processing: " + output2);
        String output = robot.processCommand(output2);
        System.out.println("Robot 1 Output: " + output);
        Assertions.assertEquals(command.length(), output.length());
    }

    @Test
    public void test1() {
        String data = "029A\n" +
                "980A\n" +
                "179A\n" +
                "456A\n" +
                "379A\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(126384L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1realData() {
        String data = "789A\n" + // 66
                "540A\n" + // 72
                "285A\n" + // 68 (fix me) vs 72
                "140A\n" + // 70
                "189A\n"; // 74
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(134120L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1realDataBustedOne() {
        String data =
                "285A\n"; // 68 (fix me) vs 72
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(19380L, day.processInput1(groups[0], groups[1]));
    }
//    <^A^^AvAvv>A
//    <^A^^AvA>vvA

    @Test
    public void keypadTestThing() {
        List<String> commands = Arrays.asList("789A",
                "540A",
                "285A",
                "140A",
                "189A");
        for (String command : commands) {
            System.out.println("Processing: " + command);
            Day21.KeyPad doorway = new Day21.KeyPad("789\n456\n123\n 0A");
            String program = doorway.processCommand(command);
            String answer = doorway.followDirections(program);
            Assertions.assertEquals(command, answer);

            Day21.KeyPad robot = new Day21.KeyPad(" ^A\n<v>");
            String robotDirections = robot.processCommand(program);
            String robotCommand = robot.followDirections(robotDirections);
            Assertions.assertEquals(program, robotCommand);

            Day21.KeyPad robot2 = new Day21.KeyPad(" ^A\n<v>");
            String robot2Directions = robot.processCommand(robotDirections);
            String robot2Command = robot.followDirections(robot2Directions);
            Assertions.assertEquals(robot2Command, robotDirections);

            // Go back....
            String output1 = robot.followDirections(robot2Command);
            Assertions.assertEquals(robotCommand, output1);
            String output2 = doorway.followDirections(output1);
            Assertions.assertEquals(answer, output2);
        }
    }


    @Test
    public void test1a() {
        String data =
                "379A\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(24256L, day.processInput1(groups[0], groups[1]));
    }


    @Test
    public void test2() {
        String data = DATA;
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(0L, day.processInput2(groups[0], groups[1]));
    }
}
