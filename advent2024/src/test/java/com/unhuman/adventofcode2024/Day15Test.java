package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day15Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
            "##########\n" +
            "#..O..O.O#\n" +
            "#......O.#\n" +
            "#.OO..O.O#\n" +
            "#..O@..O.#\n" +
            "#O#..O...#\n" +
            "#O..O..O.#\n" +
            "#.OO.O.OO#\n" +
            "#....O...#\n" +
            "##########\n" +
            "\n" +
            "<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^\n" +
            "vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v\n" +
            "><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<\n" +
            "<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^\n" +
            "^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><\n" +
            "^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^\n" +
            ">^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^\n" +
            "<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>\n" +
            "^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>\n" +
            "v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^\n";


    static InputParser getDay(String data) {
        return new Day15(data);
    }

    @Test
    public void test1aa() {
        String data = "########\n" +
                      "#...O..#\n" +
                      "#@.....#\n" +
                      "########\n" +
                      "\n" +
                      "<\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(104L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1a() {
        String data = "########\n" +
                "#..O.O.#\n" +
                "##@.O..#\n" +
                "#...O..#\n" +
                "#.#.O..#\n" +
                "#...O..#\n" +
                "#......#\n" +
                "########\n" +
                "\n" +
                "<^^>>>vv<v>>v<<\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(2028L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1b() {
        InputParser day = getDay(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(10092L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2a() {
        String data = "#######\n" +
                "#...#.#\n" +
                "#.....#\n" +
                "#..OO@#\n" +
                "#..O..#\n" +
                "#.....#\n" +
                "#######\n" +
                "\n" +
                "<vv<<^^<<^^\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(2028L, day.processInput2(groups[0], groups[1]));
    }


    @Test
    public void test2() {
        InputParser day = getDay(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(9021L, day.processInput2(groups[0], groups[1]));
    }
}
