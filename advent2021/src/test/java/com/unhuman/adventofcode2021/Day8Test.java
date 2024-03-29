package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day8Test {
    static final String DATA =
            "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe\n" +
                    "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc\n" +
                    "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg\n" +
                    "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb\n" +
                    "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea\n" +
                    "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb\n" +
                    "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe\n" +
                    "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef\n" +
                    "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb\n" +
                    "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce";
    static final String DATA2 = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf\n";
    @Test
    public void test1() {
        InputParser day = new Day8(DATA);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(26, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        InputParser day = new Day8(DATA2);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(5353, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test2a() {
        InputParser day = new Day8("edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec |" +
                " fcgedb cgb dgebacf gc\n");
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(9781, day.processInput2(groups[0], groups[1]));
    }

    @Test
    public void test3() {
        InputParser day = new Day8(DATA);
        ConfigGroup[] groups = day.parseFiles();

        Assertions.assertEquals(61229, day.processInput2(groups[0], groups[1]));
    }
}
