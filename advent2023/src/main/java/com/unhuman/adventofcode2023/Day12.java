package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day12 extends InputParser {
    private static final String regex1 = "((?:[#\\.\\?]+)|(?:\\d+))(?:\\s+|,)?";
    private static final String regex2 = null;
    int cacheHits = 0;
    Map<String, Long> cache = new HashMap<>(1024);

    public Day12() {
        super(2023, 12, regex1, regex2);
    }

    public Day12(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        cache.clear();
        long permutations = 0;
        cacheHits = 0;
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                List<Integer> requirements = new ArrayList<>();
                String record = line.get(0);
//                int poundsRequired = 0;
                for (int i = 1; i < line.size(); i++) {
                    int groupSize = Integer.parseInt(line.get(i));
                    requirements.add(groupSize);
//                    poundsRequired += groupSize;
                }

                permutations += findPermutations(record, requirements);

//                permutations += findPermutations(record.toCharArray(), 0, requirements, requirements.size(), poundsRequired);
            }
        }

        return permutations;
    }

    long findPermutations(String record, List<Integer> requirements) {
        String cacheKey = record + ":" + requirements.toString();
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        // do operation
        long operationValue = findPermutationsInternal(record, requirements);

        // put in cache
        cache.put(cacheKey, operationValue);
        return operationValue;
    }

    long findPermutationsInternal(String record, List<Integer> requirements) {
        if (requirements.size() == 0) {
            return (record.contains("#")) ? 0 : 1;
        }
        if (record.length() == 0) {
            return 0;
        }

        Integer nextGroup = requirements.get(0);
        List<Integer> next = requirements.subList(1, requirements.size());

        char nextChar = record.charAt(0);

        switch (nextChar) {
            case '.':
                long result = findPermutations(record.substring(1), requirements);
                return result;
            case '#':
                long result2 = handlePound(record, requirements);
                return result2;
            case '?':
                long result3 = handlePound(record, requirements);
                long result4 = findPermutations(record.substring(1), requirements);
                return result3 + result4;
        }
        // Should never get here
        return 0;
    }

    Long handlePound(String record, List<Integer> groups) {
        int groupSize = groups.get(0);

        if (record.length() < groupSize) {
            return 0L;
        }

        String currentGroup = record.substring(0, groupSize)
                .replace('?', '#');
        String checkForNext = createBlock(groupSize);
        if (!currentGroup.equals(checkForNext)) {
            return 0L;
        }
        if (record.length() == groupSize) {
            return (groups.size() == 1) ? 1L : 0L;
        }

        if (record.charAt(groupSize) == '?' || record.charAt(groupSize) == '.') {
            List<Integer> nextGroups = groups.subList(1, groups.size());
            return findPermutations(record.substring(groupSize + 1), nextGroups);
        }

        return 0L;
    }

    String createBlock(int blockSize) {
        StringBuilder sb = new StringBuilder(blockSize);
        for (int i = 0; i < blockSize; i++) {
            sb.append('#');
        }
        return sb.toString();
    }

    /*
    long findPermutations(char[] record, int starting, List<Integer> requirements, int reqCount, int poundsRequired) {
        List<Integer> next = requirements.subList(1, requirements.size());
        int canStopPoint = record.length;
        String matchFun = new String(record);
        Map<String, Integer> knownStopPoints = new HashMap<>();
        for (int i = next.size() - 1; i >= 0; --i) {
            Integer item = next.get(i);
            String regex = "";
            regex += "^(.*)(?:[\\.\\?]+?)(?:.*?)(?:[#\\?]{" + item + "})(?:[\\.\\?]*)$";
            String cacheKey =  "group:i:" + i;
            if (knownStopPoints.containsKey(cacheKey)) {
                canStopPoint = knownStopPoints.get(cacheKey);
                ++cacheHits;
            } else {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(matchFun);
                if (matcher.matches()) {
                    matchFun = matcher.group(1);
                    canStopPoint = matchFun.length();
                    knownStopPoints.put(cacheKey, canStopPoint);
                } else {
                    System.out.println("No match - using entire string");
                    knownStopPoints.put(cacheKey, record.length);
                }
            }
        }
        return findPermutationsInternal(record, starting, knownStopPoints, requirements, reqCount, poundsRequired);
    }

    long findPermutationsInternal(char[] record, int starting, Map<String, Integer> knownStopPoints,
                                  List<Integer> requirements, int reqCount, int poundsRequired) {
        long permutations = 0;
        Integer top = requirements.get(0);
        List<Integer> next = requirements.subList(1, requirements.size());

        String cacheKey = "group:i:" + (reqCount - requirements.size());
        int canStopPoint = knownStopPoints.containsKey(cacheKey) ? knownStopPoints.get(cacheKey) : record.length;

        // Skip any leading periods
        while (starting < record.length && record[starting] == '.') {
            starting++;
        }

        // find the ending window of the next period
        // stop for any contiguous '#'s that are > our spec
        int poundsFound = 0;
        for (int i = starting; i < record.length - top + 1 && i < canStopPoint; i++) {
            if (record[i] == '#') {
                if (++poundsFound > top) {
                    canStopPoint = i - poundsFound;
                    break;
                }
            } else {
                poundsFound = 0;
            }
        }

        for (int i = starting; i < record.length - top + 1 && i < canStopPoint; i++) {
            // We can't place on top of a dot
            if (record[i] == '.') {
                continue;
            }

            // we can't place in the middle if it's on top of something
            // later if we place something here, we need to ensure that the previous char set to .
            if (i > 0 && record[i - 1] == '#') {
                break;
            }

            boolean canPlace = true;
            for (int j = 0; j < top; j++) {
                if (record[i + j] == '.') {
                    canPlace = false;
                    break;
                }
            }

            if (canPlace) {
                char[] clone = record.clone();

                // If we can place a character before us, do it.
                if (i > 0) {
                    clone[i - 1] = '.';
                }

                // Determine if we are valid after our work - and place a spacer
                if (i + top < record.length) {
                    char afterCheck = record[i + top];
                    switch (afterCheck) {
                        case '.':
                            break;
                        case '?':
                            clone[i + top] = '.';
                            break;
                        case '#':
                            continue;
                    }
                }

                for (int j = 0; j < top; j++) {
                    clone[i + j] = '#';
                }

                if (next.size() == 0) {
                    int poundTotal = 0;
                    for (int counter = 0; counter < clone.length; counter++) {
                        poundTotal += (clone[counter] == '#') ? 1 : 0;
                    }
                    if (poundTotal == poundsRequired) {
                        ++permutations;
                    }
                } else {
                    permutations += findPermutationsInternal(clone, i + top, knownStopPoints, next, reqCount, poundsRequired);
                }
            }
        }
        return permutations;
    }
    */

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        cache.clear();
        long permutations = 0;
        cacheHits = 0;
        for (GroupItem item : configGroup) {
            for (int lineCounter = 0; lineCounter < item.size(); lineCounter++) {
                ItemLine line = item.get(lineCounter);

                System.out.println("Processing " + lineCounter + ": " + line);

                List<Integer> requirements = new ArrayList<>();
                String record = line.get(0);
                int groupReqTotal = 0;
                for (int i = 1; i < line.size(); i++) {
                    int groupSize = Integer.parseInt(line.get(i));
                    requirements.add(groupSize);
                    groupReqTotal += groupSize;
                }

                int pointsRequired = groupReqTotal;
                String useRecord = record;
                List<Integer> useRequirements = new ArrayList<>(requirements);
                for (int i = 0; i < 4; i++) {
                    useRecord += '?' + record;
                    useRequirements.addAll(requirements);
                    pointsRequired += groupReqTotal;
                }

                permutations += findPermutations(useRecord, useRequirements);
            }
        }

        return permutations;
    }
}
