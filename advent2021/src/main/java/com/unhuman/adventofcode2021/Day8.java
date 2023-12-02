package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.*;

public class Day8 extends InputParser {
    private static final String regex1 = "^([a-g|]+) ?";
    private static final String regex2 = null;

    public Day8() {
        super(2021, 8, regex1, regex2);
    }

    public Day8(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int counter = 0;
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                boolean foundBar = false;
                for (String element : line) {
                    if (foundBar) {
                        if (element.length() == 2 || element.length() == 3 || element.length() == 4 || element.length() == 7) {
                            ++counter;
                        }
                    }
                    if (element.equals("|")) {
                        foundBar = true;
                    }
                }
            }
        }

        return counter;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int grandTotal = 0;
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                int lineValue = 0;
                boolean foundBar = false;
                Map<String, Integer> key = new HashMap<>();
                Map<Integer, List<String>> candidates = new HashMap<>();

                for (String element : line) {
                    element = sortString(element);
                    if (!foundBar) {
                        switch (element.length()) {
                            case 1:
                                // we know this is the bar
                                foundBar = true;

                                // Figure out all the numbers so we can look them up.

                                // Figure out the top character (7-1)
                                char topChar = findDiffSeven(candidates.get(1).get(0), candidates.get(7).get(0));

                                // figure out 6 - which will have only one of the 2 chars from 1
                                String six = extractSix(candidates.get(6), candidates.get(1).get(0));
                                key.put(six, 6);

                                // figure out the missing piece of one so we can figure out 5
                                char topRightChar = findCharacterAbsent(candidates.get(1).get(0), six);

                                String five = extractNumNotHavingChar(candidates.get(5), topRightChar);
                                key.put(five, 5);

                                // Find 3
                                char bottomLeftChar = findCharacterAbsent(six, five);
                                String three = extractNumNotHavingChar(candidates.get(5), bottomLeftChar);
                                key.put(three, 3);

                                // what's left is two
                                String two = candidates.get(5).get(0);
                                key.put(two, 2);

                                // figure out the central character
                                List<Character> centralChars = findCommonChars(Arrays.asList(five, two,
                                        candidates.get(4).get(0)));
                                char centralChar = centralChars.get(0);

                                // Find 0
                                String zero = extractNumNotHavingChar(candidates.get(6), centralChar);
                                key.put(zero, 0);

                                // Nine is left
                                String nine = candidates.get(6).get(0);
                                key.put(nine, 9);

                                break;
                            case 2:
                                key.put(element, 1);
                                candidates.put(1, Collections.singletonList(element));
                                break;
                            case 3:
                                key.put(element, 7);
                                candidates.put(7, Collections.singletonList(element));
                                break;
                            case 4:
                                key.put(element, 4);
                                candidates.put(4, Collections.singletonList(element));
                                break;
                            case 7:
                                key.put(element, 8);
                                candidates.put(8, Collections.singletonList(element));
                                break;
                            default:
                                if (!candidates.containsKey(element.length())) {
                                    candidates.put(element.length(), new ArrayList<>());
                                }
                                candidates.get(element.length()).add(element);
                        }
                    } else {
                        lineValue = lineValue * 10 + key.get(element);
                    }
                }
//                System.out.println(lineValue);
                grandTotal += lineValue;
            }
        }
        return grandTotal;
    }

    List<Character> findCommonChars(List<String> data) {

        List<Character> characters = new ArrayList<>();
        char[] baseChars = data.get(0).toCharArray();
        for (int i = 0; i < baseChars.length; i++) {
            characters.add(baseChars[i]);
        }
        for (String check: data) {
            for (int i = characters.size() - 1; i >= 0; i--) {
                if (!check.contains(String.valueOf(characters.get(i)))) {
                    characters.remove(i);
                }
            }
        }

        return characters;
    }

    char findCharacterAbsent(String checkForChars, String inItem) {
        for (int i = 0; i < checkForChars.length(); i++) {
            char check = checkForChars.charAt(i);
            if (!inItem.contains(String.valueOf(check))) {
                return check;
            }
        }
        throw new RuntimeException("absent char not found");
    }

    private char findDiffSeven(String str1, String str2) {
        if (str2.length() > str1.length()) {
            String temp = str1;
            str1 = str2;
            str2 = temp;
        }

        for (int i = 0; i < str1.length(); i++) {
            char check = str1.charAt(i);
            if (!str2.contains(String.valueOf(check))) {
                return check;
            }
        }

        throw new RuntimeException("diff char not found");
    }

    private String extractNumNotHavingChar(List<String> candidates, char missing) {
        for (int j = 0; j < candidates.size(); j++) {
            if (!candidates.get(j).contains(String.valueOf(missing))) {
                return candidates.remove(j);
            }
        }
        throw new RuntimeException("extract num not having candidate not found");
    }

    private String extractSix(List<String> sixCandidates, String oneChars) {
        // Find six - won't have both characters from one
        for (int i = 0; i < oneChars.length(); i++) {
            char check = oneChars.charAt(i);
            for (int j = 0; j < sixCandidates.size(); j++) {
                if (!sixCandidates.get(j).contains(String.valueOf(check))) {
                    return sixCandidates.remove(j);
                }
            }
        }
        throw new RuntimeException("six candidate not found");
    }

    private String sortString(String input) {
        char tempArray[] = input.toCharArray();

        // Sorting temp array using
        Arrays.sort(tempArray);

        // Returning new sorted string
        return new String(tempArray);
    }
}
