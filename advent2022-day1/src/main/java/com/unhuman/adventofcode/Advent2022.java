package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;

/**
 * Hello world!
 *
 */
public class Advent2022
{
    public static void main( String[] args )
    {
        InputParser puzzle;
        String[] argsFixed = new String[2];
        argsFixed[1] = args[0];

//        argsFixed[0] = generateFile(1);
//        puzzle = new Day1(argsFixed);
//        puzzle.process();

        argsFixed[0] = generateFile(2);
        puzzle = new Day2(argsFixed);
        puzzle.process();
//
//        argsFixed[0] = generateFile(3);
//        puzzle = new Day3(argsFixed);
//        puzzle.process();
    }

    static String generateFile(int day) {
        return "https://adventofcode.com/2022/day/" + day + "/input";
    }
}
