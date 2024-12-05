package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day5 extends InputParser {
    private static final String regex1 = "(\\d+)\\|(\\d+)";
    private static final String regex2 = "(\\d+)";

    public Day5() {
        super(2024, 5, regex1, regex2);
    }

    public Day5(String filename) {
        super(filename, regex1, regex2);
    }

    // map of page number to pages that must come before it (if both exist).
    private class ConditionalPageRules extends HashMap<Long, Set<Long>> { }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // For a given page, what pages must come before it
        ConditionalPageRules pageRules = new ConditionalPageRules();

        long total = 0;

        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        for (ItemLine line : item) {
            Long pageNum = line.getLong(0);
            Long priorPage = line.getLong(1);
            if (!pageRules.containsKey(priorPage)) {
                pageRules.put(priorPage, new HashSet<>(List.of(pageNum)));
            } else {
                pageRules.get(priorPage).add(pageNum);
            }
        }

        item = configGroup1.get(0);
        for (ItemLine line : item) {
            List<Long> pagesToPrint = new ArrayList<>();
            for (int i = 0; i< line.size(); i++) {
                pagesToPrint.add(line.getLong(i));
            }
            if (isValidOrdering(pagesToPrint, pageRules)) {
                total += pagesToPrint.get((pagesToPrint.size() - 1) / 2);
            }
        }

        return total;
    }

    boolean isValidOrdering(List<Long> pagesToPrint, ConditionalPageRules pageRules) {
        HashSet<Long> printingPages = new HashSet<>(pagesToPrint);
        HashSet<Long> printedPages = new HashSet<>();
        for (int i = 0; i < pagesToPrint.size(); i++) {
            long pageNum = pagesToPrint.get(i);

            Set<Long> checkPageNums = pageRules.get(pageNum);

            if (checkPageNums != null) {
                for (Long checkPage : checkPageNums) {
                    if (printingPages.contains(checkPage)) {
                        if (!printedPages.contains(checkPage)) {
                            return false;
                        }
                    }
                }
            }

            // track
            printedPages.add(pageNum);
        }

        return true;
    }

    /** This will return true if it can mutate the pagesToPrint to be in a valid order */
    boolean fixValidOrdering(List<Long> pagesToPrint, ConditionalPageRules pageRules) {
        HashSet<Long> printingPages = new HashSet<>(pagesToPrint);

        while (true) {
            boolean modified = false;
            HashSet<Long> printedPages = new HashSet<>();
            for (int i = 0; i < pagesToPrint.size(); i++) {
                long pageNum = pagesToPrint.get(i);

                Set<Long> checkPageNums = pageRules.get(pageNum);

                if (checkPageNums != null) {
                    for (Long checkPage : checkPageNums) {
                        if (printingPages.contains(checkPage)) {
                            if (!printedPages.contains(checkPage)) {
                                // remove the checkPage from the list
                                pagesToPrint.remove(checkPage);
                                // add it back right before where we are
                                pagesToPrint.add(i, checkPage);

                                if (isValidOrdering(pagesToPrint, pageRules)) {
                                    return true;
                                }
                                modified = true;
                                break;
                            }
                        }
                    }
                    if (modified) {
                        break;
                    }
                }
                // track
                printedPages.add(pageNum);
            }
        }
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // For a given page, what pages must come before it
        ConditionalPageRules pageRules = new ConditionalPageRules();

        long total = 0;

        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        for (ItemLine line : item) {
            Long pageNum = line.getLong(0);
            Long priorPage = line.getLong(1);
            if (!pageRules.containsKey(priorPage)) {
                pageRules.put(priorPage, new HashSet<>(List.of(pageNum)));
            } else {
                pageRules.get(priorPage).add(pageNum);
            }
        }

        item = configGroup1.get(0);
        for (ItemLine line : item) {
            List<Long> pagesToPrint = new ArrayList<>();
            for (int i = 0; i< line.size(); i++) {
                pagesToPrint.add(line.getLong(i));
            }
            // compared to part 1 - we want the lines that are invalidly ordered
            // and we want to fix the ordering
            if (!isValidOrdering(pagesToPrint, pageRules)) {
                if (fixValidOrdering(pagesToPrint, pageRules)) {
                    total += pagesToPrint.get((pagesToPrint.size() - 1) / 2);
                }
            }
        }

        return total;
    }
}
