package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

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

            score += processLights(targetButtonConfig, buttons);
        }

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

    String applyButtonLights(String currentState, List<Integer> button) {
        for (int i = 0; i < button.size(); i++) {
            int flag = button.get(i);
            char changedChar = currentState.charAt(flag) == '.' ? '#' : '.';
            currentState = currentState.substring(0, flag) + changedChar + currentState.substring(flag + 1);
        }
        return currentState;
    }

    long processLights(String desiredState, List<List<Integer>> buttons) {
        HashSet<String> seenStates = new HashSet<>();

        String emptyState = stringOfCharacter(desiredState.length(), '.');

        seenStates.add(emptyState);

        List<String> currentStates = new ArrayList<>();
        currentStates.add(emptyState);

        long count = 0;
        while (true) {
            ++count;

            List<String> newStates = new ArrayList<>();
            for (String currentState : currentStates) {
                for (List<Integer> button : buttons) {
                    // Apply changes to current state based on button
                    String mutated = applyButtonLights(currentState, button);

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

    List<Integer> applyButtonJoltage(List<Integer> joltages, List<Integer> button, int presses) {
        joltages = new ArrayList<>(joltages);
        for (int i = 0; i < button.size(); i++) {
            int flag = button.get(i);
            joltages.set(flag, joltages.get(flag) + presses);
        }
        return joltages;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        long score = 0L;
        // easier to assume there's only one group
        GroupItem group0 = configGroup.getFirst();
        for (int i = 0; i < group0.size(); i++) {
            System.out.println("Processing item: " + i + " of " + group0.size());
            ItemLine line = group0.get(i);
            // Unused for part 2
            String targetButtonConfig = line.get(0);

            List<String> subline = Arrays.stream(line.get(1).split(" ")).toList();
            ArrayList<List<Integer>> buttons = new ArrayList<>();
            subline.forEach(buttonText -> {
                buttonText = buttonText.replace("(", "");
                buttonText = buttonText.replace(")", "");
                List<Integer> button = Arrays.stream(buttonText.split(",")).map(Integer::parseInt).toList();
                buttons.add(button);
            });

            // We sort the buttons so we have the buttons with the most changes first
            // They will process data quicker
            buttons.sort(new Comparator<List<Integer>>() {
                @Override
                public int compare(List<Integer> o1, List<Integer> o2) {
                    return o2.size() - o1.size();
                }
            });

            List<Integer> desiredJoltages = Arrays.stream(line.get(2).split(",")).map(Integer::parseInt).toList();

            // build up an empty state to start recursion
            List<Integer> emptyJoltages = new ArrayList<>();
            for (Integer j: desiredJoltages) {
                emptyJoltages.add(0);
            }

            Long currentValue = processJoltage(desiredJoltages, emptyJoltages, buttons);
            if (currentValue != null) {
                score += currentValue;
            }
        }

        return score;
    }

    int findMaxButtonTouch(List<Integer> desiredJoltage, List<Integer> currentState, List<Integer> button) {
        int maxTouch = Integer.MAX_VALUE;
        for (int flag : button) {
            int difference = desiredJoltage.get(flag) - currentState.get(flag);
            if (difference < maxTouch) {
                maxTouch = Math.max(difference, 0);
            }
        }
        return maxTouch;
    }

    void applyButtonPress(List<Integer> currentJoltage, List<Integer> button, int increment) {
        for (int flag: button) {
            currentJoltage.set(flag, currentJoltage.get(flag) + increment);
        }
    }

    int recursiveJoltage(List<Integer> desiredJoltage, AtomicInteger bestValue,
                         List<Integer> currentJoltage, int currentCount, List<List<Integer>> buttons) {
        // if we found what we're looking for, return the current count
        if (desiredJoltage.equals(currentJoltage)) {
            bestValue.set(currentCount);
            return currentCount;
        }

        // if there is buttons left, just bail out
        if (buttons.isEmpty()) {
            return bestValue.get();
        }

        // if we ae greater than the value we know is best, just bail out
        if (currentCount >= bestValue.get()) {
            return bestValue.get();
        }

        List<Integer> button = buttons.getFirst();
        buttons = buttons.subList(1, buttons.size());

        int maxCurrentButtonPress = findMaxButtonTouch(desiredJoltage, currentJoltage, button);
        int lowestValue = Integer.MAX_VALUE;
        for (int i = maxCurrentButtonPress; i >= 0; --i) {
            currentJoltage = new ArrayList<>(currentJoltage);
            applyButtonPress(currentJoltage, button, i);
            int currentScore = recursiveJoltage(desiredJoltage, bestValue, currentJoltage, currentCount, buttons);
            if (currentScore < lowestValue) {
                lowestValue = currentScore;
            }

            ++currentCount;
        }
        return lowestValue;
    }

    Map<String, Long> cache = new HashMap<>();

    Long processJoltage(List<Integer> desiredJoltage, List<Integer> currentJoltage, List<List<Integer>> buttons) {
        String cacheKey = String.format("%d:%d:%d", desiredJoltage.hashCode(), currentJoltage.hashCode(), buttons.hashCode());
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }
        if (currentJoltage.equals(desiredJoltage)) {
            return 0L;
        }
        if (buttons.isEmpty()) {
            return Long.MAX_VALUE;
        }
        AtomicInteger bestValue = new AtomicInteger(Integer.MAX_VALUE);

        int maxCurrentButtonPress = findMaxButtonTouch(desiredJoltage, currentJoltage, buttons.get(0));

        Long bestCase = Long.MAX_VALUE;
        for (int i = maxCurrentButtonPress; i >= 0; --i) {
            List<Integer> tryingJoltages = new ArrayList<>(currentJoltage);
            tryingJoltages = applyButtonJoltage(tryingJoltages, buttons.getFirst(), i);
            List<List<Integer>> nextButtons = buttons.subList(1, buttons.size());
            Long result = processJoltage(desiredJoltage, tryingJoltages, nextButtons);
            if (result != Long.MAX_VALUE) {
                result += i;
            }
            if (result < bestCase) {
                bestCase = result;
            }
        }

        cache.put(cacheKey, (bestCase != null) ? bestCase : Long.MAX_VALUE);

        return bestCase;
    }

    long processJoltageSlow(List<Integer> desiredJoltage, List<List<Integer>> buttons) {
        HashSet<List<Integer>> seenStates = new HashSet<>();

        List<Integer> emptyState = new ArrayList<>();
        for (int i = 0; i < desiredJoltage.size(); i++) {
            emptyState.add(0);
        }

        seenStates.add(emptyState);

        List<List<Integer>> currentStates = new ArrayList<>();
        currentStates.add(emptyState);

        long count = 0;
        while (true) {
            ++count;

            List<List<Integer>> newStates = new ArrayList<>();
            for (List<Integer> currentState: currentStates) {
                for (List<Integer> button: buttons) {
                    // Apply changes to current state based on button
                    List<Integer> mutated = applyButtonJoltage(currentState, button, 1);

                    if (mutated.equals(desiredJoltage)) {
                        return count;
                    }

                    // check if any of the joltage values are over the desired value (we don't want them)
                    boolean isValid = true;
                    for (int i = 0; i < mutated.size(); i++) {
                        if (mutated.get(i) > desiredJoltage.get(i)) {
                            isValid = false;
                        }
                    }

                    if (isValid && !seenStates.contains(mutated)) {
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
}
