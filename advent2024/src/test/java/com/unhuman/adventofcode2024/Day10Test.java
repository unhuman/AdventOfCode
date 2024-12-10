package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day10Test {

    static InputParser getDay(String data) {
        return new Day10(data);
    }

    @Test
    public void test1() {
        String data = "0123\n" +
                "1234\n" +
                "8765\n" +
                "9876\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(1L, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test1a() {
        String data = "89010123\n" +
                "78121874\n" +
                "87430965\n" +
                "96549874\n" +
                "45678903\n" +
                "32019012\n" +
                "01329801\n" +
                "10456732\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(36L, day.processInput1(groups[0], groups[1]));
    }


    @Test
    public void test2a() {
        String data = ".....0.\n" +
                      "..4321.\n" +
                      "..5..2.\n" +
                      "..6543.\n" +
                      "..7..4.\n" +
                      "..8765.\n" +
                      "..9....\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(3L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2b () {
        String data = "..90..9\n" +
                "...1.98\n" +
                "...2..7\n" +
                "6543456\n" +
                "765.987\n" +
                "876....\n" +
                "987....\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(13L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2c () {
        String data = "012345\n" +
                "123456\n" +
                "234567\n" +
                "345678\n" +
                "4.6789\n" +
                "56789.\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(227L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2d () {
        String data = "89010123\n" +
                "78121874\n" +
                "87430965\n" +
                "96549874\n" +
                "45678903\n" +
                "32019012\n" +
                "01329801\n" +
                "10456732\n";
        InputParser day = getDay(data);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(81L, day.processInput2(groups[0], groups[1]));
    }

}
