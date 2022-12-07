package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day3 extends InputParser {
    private static final String regex1 = "(\\w)";
    private static final String regex2 = null;

    /**
     * Creates an InputParser that will process line-by-line
     * @param filenameAndCookieInfo
     */
    public Day3(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Character> dupes = new ArrayList<>();
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                int itemsInEachPocket = line.size() / 2;
                HashSet<Character> items = new HashSet<>();
                for (int i = 0; i < itemsInEachPocket; i++) {
                    items.add(line.get(i).charAt(0));
                }
                for (int i = itemsInEachPocket; i < line.size(); i++) {
                    char check = line.get(i).charAt(0);
                    if (items.contains(check)) {
                        dupes.add(check);
                        break;
                    }
                }
            }
        }
        int total = 0;
        for (int i = 0; i < dupes.size(); i++) {
            char character = dupes.get(i);
            if (character >= 'a' && character <= 'z') {
                total += Character.getNumericValue(character) - Character.getNumericValue('a') + 1;
            } else {
                total += Character.getNumericValue(character) - Character.getNumericValue('A') + 27;
            }
        }
        return total;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Character> dupes = new ArrayList<>();
        for (GroupItem item : configGroup) {
            for (int li = 0; li < item.size(); li++) {
                if ((li + 1) % 3 == 0) {
                    ItemLine line = item.get(li);
                    HashSet<Character> items1 = new HashSet<>();
                    HashSet<Character> items2 = new HashSet<>();
                    for (int i = 0; i < item.get(li - 1).size(); i++) {
                        items1.add(item.get(li - 1).get(i).charAt(0));
                    }
                    for (int i = 0; i < item.get(li - 2).size(); i++) {
                        items2.add(item.get(li - 2).get(i).charAt(0));
                    }
                    for (int i = 0; i < line.size(); i++) {
                        char check = line.get(i).charAt(0);
                        if (items1.contains(check) && items2.contains(check)) {
                            dupes.add(check);
                            break;
                        }
                    }
                }
            }
        }
        int total = 0;
        for (int i = 0; i < dupes.size(); i++) {
            char character = dupes.get(i);
            if (character >= 'a' && character <= 'z') {
                total += Character.getNumericValue(character) - Character.getNumericValue('a') + 1;
            } else {
                total += Character.getNumericValue(character) - Character.getNumericValue('A') + 27;
            }
        }
        return total;
    }
}
