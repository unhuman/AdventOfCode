package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;

import java.util.List;

public class DayOne extends InputParser {
    private static final String regex1 = "(\\d+)";
    /**
     * Creates an InputParser that will process line-by-line
     *
     * @param filename
     */
    public DayOne(String filename) {
        super(filename, regex1, null);
    }

    public static void main(String [] args) {
        DayOne parser = new DayOne(args[0]);
        parser.process();
    }

    @Override
    protected void processInput(List<List<List<String>>> dataItems1, List<List<List<String>>> dataItems2) {
        int maxCarry = 0;
        for (List<List<String>> item: dataItems1) {
            int wallet = 0;
            for (List<String> individual: item) {
                for (String value: individual) {
                    wallet += Integer.parseInt(value);
                }
            }
            if (wallet > maxCarry) {
                maxCarry = wallet;
            }
        }
        System.out.println(maxCarry);
    }
}
