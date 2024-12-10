package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.List;

public class Day9 extends InputParser {
    private static final String regex1 = "(\\d)";
    private static final String regex2 = null;

    public Day9() {
        super(2024, 9, regex1, regex2);
    }

    public Day9(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        ItemLine line = configGroup.get(0).get(0);

        // lay out original content on disk
        long idNum = 0;
        int pointer = 0;
        boolean isContent = true;
        List<Long> storage = new ArrayList<>(line.size());
        List<Integer> nullPositions = new ArrayList<>(line.size());
        for (int itemNum = 0; itemNum < line.size(); itemNum++) {
            long value = line.getLong(itemNum);
            for (int i = 0; i < value; i++) {
                // keep track of null positions
                // will be faster to insert
                if (!isContent) {
                    nullPositions.add(pointer);
                }
                storage.add(pointer++, (isContent) ? idNum : null);
            }
            if (isContent) {
                ++idNum;
            }
            isContent = !isContent;
        }

        // compress the disk
        while (nullPositions.size() > 0) {
            int location = nullPositions.remove(0);
            while (true) {
                Long value = storage.remove(storage.size() - 1);
                if (value != null) {
                    if (location < storage.size()) {
                        storage.set(location, value);
                    } else {
                        // just put this back on the end
                        storage.add(value);
                    }
                    break;
                }
            }
        }

        // calculate checksum
        long checksum = 0;
        for (int i = 0; i < storage.size(); i++) {
            checksum += i * storage.get(i);
        }
        return checksum;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        ItemLine line = configGroup.get(0).get(0);

        // lay out original content on disk
        long idNum = 0;
        int pointer = 0;
        boolean isContent = true;
        List<Long> storage = new ArrayList<>(line.size());
        List<Integer> nullPositions = new ArrayList<>(line.size());
        for (int itemNum = 0; itemNum < line.size(); itemNum++) {
            long value = line.getLong(itemNum);
            for (int i = 0; i < value; i++) {
                // keep track of null positions
                // will be faster to insert
                if (!isContent) {
                    nullPositions.add(pointer);
                }
                storage.add(pointer++, (isContent) ? idNum : null);
            }
            if (isContent) {
                ++idNum;
            }
            isContent = !isContent;
        }

        // compress the disk
        int compressor = storage.size() - 1;
        while (compressor > 0) {
            // backtrack over any nulls
            while (storage.get(compressor) == null) {
                compressor--;
            }

            // find an item
            int indexRemove = compressor;
            int neededLength = 1;
            Long item = storage.get(compressor--);

            if (item == null) {
                continue;
            }

            while (compressor > 0 && item.equals(storage.get(compressor))) {
                neededLength++;
                compressor--;
            }
            // calculate starting point of item
            indexRemove -= neededLength - 1;

            // find a location for this one
            Integer start = null;
            for (int i = 0; i < nullPositions.size(); i++) {
                if (start == null) {
                    start = nullPositions.get(i);
                } else {
                    if (!nullPositions.get(i).equals(nullPositions.get(i - 1) + 1)) {
                        start = nullPositions.get(i);
                    }
                }
                if (nullPositions.get(i) - start + 1 == neededLength
                    && indexRemove > start) {
                    for (int j = 0; j < neededLength; j++) {
                        storage.set(indexRemove + j, null);
                        storage.set(start + j, item);
                        nullPositions.remove(i - j);
                    }
                    break;
                }
            }
        }

        // calculate checksum
        long checksum = 0;
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i) != null) {
                checksum += i * storage.get(i);
            }
        }
        return checksum;
    }
}
