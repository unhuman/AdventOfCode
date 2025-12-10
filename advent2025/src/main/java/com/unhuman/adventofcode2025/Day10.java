package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Day10 extends InputParser {
    private static final String regex1 = "\\[(.*)] (\\(.*\\)) \\{(.*?,?)\\}";
                                        // we will need other processing to get
                                        // (2,1) (4,5,6) (7)
    private static final String regex1a = "\\(.*?\\)";
    private static final String regex2 = null;

    public Day10() {
        super(2025, 10, regex1, regex2);
    }

    public Day10(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        long score = 0L;
        // easier to assume there's only one group
        GroupItem group0 = configGroup.getFirst();
        for (ItemLine line : group0) {
            String targetButtonConfig = line.get(0);

            List<String> subline = Arrays.stream(line.get(1).split(" ")).toList();
            ArrayList<List<Integer>> buttons = new ArrayList<>();
            subline.forEach(buttonText -> {
                buttonText = buttonText.replace("(", "");
                buttonText = buttonText.replace(")", "");
                List<Integer> button = Arrays.stream(buttonText.split(",")).map(Integer::parseInt).toList();
                buttons.add(button);
            });

            // Unused for part 1
            List<Integer> joltages = Arrays.stream(line.get(2).split(",")).map(Integer::parseInt).toList();

            score += processLine1(targetButtonConfig, buttons);
        }

        // Here's code for a 2nd group, if needed
//        GroupItem group1 = configGroup1.getFirst();
//        for (ItemLine line : group1) {
//            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
////                char value = line.getChar(itemNum);
////                String value = line.getString(itemNum);
////                Long value = line.getLong(itemNum);
//            }
//        }


        return score;
    }

    protected String stringOfCharacter(int length, char charToFill) {
        if (length > 0) {
            char[] array = new char[length];
            Arrays.fill(array, charToFill);
            return new String(array);
        }
        return "";
    }

    String applyButton(String currentState, List<Integer> button) {
        for (int i = 0; i < button.size(); i++) {
            int flag = button.get(i);
            char changedChar = currentState.charAt(flag) == '.' ? '#' : '.';
            currentState = currentState.substring(0, flag) + changedChar + currentState.substring(flag + 1);
        }
        return currentState;
    }

    long processLine1(String desiredState, List<List<Integer>> buttons) {
        HashSet<String> seenStates = new HashSet<>();

        String emptyState = stringOfCharacter(desiredState.length(), '.');

        seenStates.add(emptyState);

        List<String> currentStates = new ArrayList<>();
        currentStates.add(emptyState);

        long count = 0;
        while (true) {
            ++count;

            List<String> newStates = new ArrayList<>();
            for (String currentState: currentStates) {
                for (List<Integer> button: buttons) {
                    // Apply changes to current state based on button
                    String mutated = applyButton(currentState, button);

                    if (mutated.equals(desiredState)) {
                        return count;
                    }

                    if (!seenStates.contains(mutated)) {
                        // track we've seen this so we don't duplicate efforts
                        seenStates.add(mutated);
                        // indicate we'll continue with this one
                        newStates.add(mutated);
                    }
                }
            }
            // setup next round
            currentStates = newStates;
        }
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        return 2L;
    }
}
