package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import org.apache.commons.collections4.iterators.PermutationIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day7 extends InputParser {
    private static final String regex1 = "(.+)";
    private static final String regex2 = null;

    public Day7() {
        super(2019, 7, regex1, regex2);
    }

    public Day7(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        String program = item.get(0).toString();

        List<Integer> phaseValues = Arrays.asList(0, 1, 2, 3, 4);

        PermutationIterator<Integer> permutationIterator = new PermutationIterator<>(phaseValues);

        int maxSignal = 0;

        while (permutationIterator.hasNext()) {
            List<Integer> permutation = permutationIterator.next();

            int nextInputSignal = processSignalPermutation(program, permutation);

            if (nextInputSignal > maxSignal) {
                maxSignal = nextInputSignal;
            }
        }

        return maxSignal;
    }

    protected static int processSignalPermutation(String program, List<Integer> permutation) {
        Integer nextInputSignal = 0;
        for (int i = 0; i <= 4; i++) {
            IntCodeParser parser = new IntCodeParser(program);
            parser.addInput(permutation.get(i).toString());
            parser.addInput(nextInputSignal.toString());
            parser.process();
            nextInputSignal = Integer.parseInt(parser.getOutput());
        }
        return nextInputSignal;
    }

    protected static int processSignalPermutation2(String program, List<Integer> permutation) {
        List<IntCodeParser> parsers = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            IntCodeParser parser = new IntCodeParser(program);
            parser.setReturnOnOutput(true);
            parser.addInput(permutation.get(i).toString());
            parsers.add(parser);
        }

        int nextInputSignal = 0;
        boolean isRunning = true;
        while (isRunning) {
            for (int i = 0; i <= 4; i++) {
                IntCodeParser parser = parsers.get(i);
                parser.addInput(Integer.toString(nextInputSignal));
                parser.process();

                // stop when parser has halted
                isRunning = !parser.hasHalted();
                if (!isRunning) {
                    break;
                }

                nextInputSignal = Integer.parseInt(parser.getOutput());
            }
        }
        return nextInputSignal;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {

        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        String program = item.get(0).toString();

        List<Integer> phaseValues = Arrays.asList(5, 6, 7, 8, 9);

        PermutationIterator<Integer> permutationIterator = new PermutationIterator<>(phaseValues);

        int maxSignal = 0;

        while (permutationIterator.hasNext()) {
            List<Integer> permutation = permutationIterator.next();

            int nextInputSignal = processSignalPermutation2(program, permutation);

            if (nextInputSignal > maxSignal) {
                maxSignal = nextInputSignal;
            }
        }

        return maxSignal;
    }
}
