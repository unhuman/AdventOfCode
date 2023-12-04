package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Day10 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    private static Map<Character, Character> openCloses = new HashMap<>();
    {
        openCloses.put('(', ')');
        openCloses.put('[', ']');
        openCloses.put('{', '}');
        openCloses.put('<', '>');
    }
    public Day10() {
        super(2021, 10, regex1, regex2);
    }

    public Day10(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Map<Character, Integer> corruptions = new HashMap<>();
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                Stack<Character> closingStack = new Stack<>();
                for (String symbol : line) {
                    Character input = symbol.charAt(0);
                    // openings - put on stack
                    if (openCloses.containsKey(input)) {
                        Character closing = openCloses.get(input);
                        closingStack.push(closing);
                    } else {
                        if (closingStack.isEmpty()) {
                            continue;
                        }
                        // expect a closing
                        if (closingStack.peek().equals(input)) {
                            closingStack.pop();
                        } else {
                            if (!corruptions.containsKey(input)) {
                                corruptions.put(input, 0);
                            }
                            corruptions.put(input, corruptions.get(input) + 1);
                            break;
                        }
                    }
                }
            }
        }

        long total = 0;
        for (Character key: corruptions.keySet()) {
            long instances = corruptions.get(key);
            switch(key) {
                case ')':
                    total += instances * 3;
                    break;
                case ']':
                    total += instances * 57;
                    break;
                case '}':
                    total += instances * 1197;
                    break;
                case '>':
                    total += instances * 25137;
            }
        }

        return total;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Long> totals = new ArrayList<>();
        Map<Character, Integer> corruptions = new HashMap<>();
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                Stack<Character> closingStack = new Stack<>();
                boolean corrupted = false;
                for (int i = 0; i < line.size(); i++) {
                    Character input = line.get(i).charAt(0);
                    // openings - put on stack
                    if (openCloses.containsKey(input)) {
                        Character closing = openCloses.get(input);
                        closingStack.push(closing);
                    } else {
                        // expect a closing
                        if (closingStack.peek().equals(input)) {
                            closingStack.pop();
                        } else {
                            corrupted = true;
                            break;
                        }
                    }
                }

                if (!corrupted) {
                    Long total = 0L;
                    while (!closingStack.isEmpty()) {
                        Character character = closingStack.pop();
                        switch (character) {
                            case ')':
                                total = 5 * total + 1;
                                break;
                            case ']':
                                total = 5 * total + 2;
                                break;
                            case '}':
                                total = 5 * total + 3;
                                break;
                            case '>':
                                total = 5 * total + 4;
                        }
                    }
                    totals.add(total);
                }
            }
        }
        totals.sort(Comparator.naturalOrder());
        return totals.get(totals.size() / 2);
    }
}
