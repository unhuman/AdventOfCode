package com.unhuman.adventofcode2022;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.List;

public class Day1 extends InputParser {
    private static final String regex1 = "(\\d+)";
    private static final String regex2 = null;

    /**
     * Creates an InputParser that will process line-by-line
     */
    public Day1() {
        super(2022, 1, regex1, regex2);
    }

    public Day1(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        int maxCarry = 0;
        for (GroupItem item: dataItems1) {
            int wallet = 0;
            for (ItemLine individual: item) {
                for (String value: individual) {
                    wallet += Integer.parseInt(value);
                }
            }
            if (wallet > maxCarry) {
                maxCarry = wallet;
            }
        }
        return maxCarry;
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        int count = 3;
        int[] maxCarry = new int[count];
        for (int i = 0; i < count; i++) {
            maxCarry[i] = 0;
        }
        for (GroupItem item: dataItems1) {
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
        return total;
    }
}
