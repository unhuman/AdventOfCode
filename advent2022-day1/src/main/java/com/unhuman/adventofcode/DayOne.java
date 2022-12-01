package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;

import java.util.List;

public class DayOne extends InputParser {
    private static final String regex1 = "(\\d+)";
    /**
     * Creates an InputParser that will process line-by-line
     *
     * @param args
     */
    public DayOne(String[] args) {
        super(args, regex1, null);
    }

    public static void main(String [] args) {
        DayOne parser = new DayOne(args);
        parser.process();
    }

    @Override
    protected void processInput1(List<List<List<String>>> dataItems1, List<List<List<String>>> dataItems2) {
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

    @Override
    protected void processInput2(List<List<List<String>>> dataItems1, List<List<List<String>>> dataItems2) {
        int count = 3;
        int[] maxCarry = new int[count];
        for (int i = 0; i < count; i++) {
            maxCarry[i] = 0;
        }
        for (List<List<String>> item: dataItems1) {
            int wallet = 0;
            for (List<String> individual: item) {
                for (String value: individual) {
                    wallet += Integer.parseInt(value);
                }
            }
            for (int i = 0; i < count; i++) {
                if (wallet > maxCarry[i]) {
                    // copy the other wallets down
                    for (int j = count - 1; j > i; j--) {
                        maxCarry[j] = maxCarry[j-1];
                    }

                    // now swap in our wallet
                    maxCarry[i] = wallet;
                    break;
                }
            }
        }

        int total = 0;
        for (int i = 0; i < count; i++) {
            total += maxCarry[i];
        }
        System.out.println(total);
    }
}
