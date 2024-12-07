package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day7 extends InputParser {
    private static final String regex1 = "(\\d+)";
    private static final String regex2 = null;

    public Day7() {
        super(2024, 7, regex1, regex2);
    }

    public Day7(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        long grandTotal = 0;
        GroupItem group0 = configGroup.get(0);
        for (ItemLine line : group0) {
            long sum = line.getLong(0);
            List<Long> operands = new ArrayList<>();
            for (int itemNum = 1; itemNum < line.size(); itemNum++) {
                operands.add(line.getLong(itemNum));
            }

            int operationsRequired = operands.size() - 1;
            List<String> permutations = generateMathPermutations(operationsRequired, false);

            for (String permutation: permutations) {
                Long total = operands.get(0);
                for (int i = 0; i < permutation.length(); i++) {
                    char operation = permutation.charAt(i);
                    switch (operation) {
                        case '+':
                            total += operands.get(i + 1);
                            break;
                        case '*':
                            total *= operands.get(i + 1);
                            break;
                    }
                }

                if (total == sum) {
                    grandTotal += sum;
                    break;
                }
            }
        }

        return grandTotal;
    }

    List<String> generateMathPermutations(int length, boolean includeAppend) {
        if (length == 0) {
            return Collections.singletonList("");
        }

        List<String> recursedPermutations = generateMathPermutations(length - 1, includeAppend);

        List<String> permutations = new ArrayList<>();
        for (String recursedPermutation: recursedPermutations) {
            permutations.add("+" + recursedPermutation);
            permutations.add("*" + recursedPermutation);
            if (includeAppend) {
                permutations.add("|" + recursedPermutation);
            }
        }
        return permutations;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        long grandTotal = 0;
        GroupItem group0 = configGroup.get(0);
        for (ItemLine line : group0) {
            long sum = line.getLong(0);
            List<Long> operands = new ArrayList<>();
            for (int itemNum = 1; itemNum < line.size(); itemNum++) {
                operands.add(line.getLong(itemNum));
            }

            int operationsRequired = operands.size() - 1;
            List<String> permutations = generateMathPermutations(operationsRequired, true);

            for (String permutation: permutations) {
                Long total = operands.get(0);
                for (int i = 0; i < permutation.length(); i++) {
                    char operation = permutation.charAt(i);
                    switch (operation) {
                        case '+':
                            total += operands.get(i + 1);
                            break;
                        case '*':
                            total *= operands.get(i + 1);
                            break;
                        case '|':
                            total = Long.parseLong(String.format("%d%d", total, operands.get(i + 1)));
                            break;
                    }
                }

                if (total == sum) {
                    grandTotal += sum;
                    break;
                }
            }
        }

        return grandTotal;
    }
}
