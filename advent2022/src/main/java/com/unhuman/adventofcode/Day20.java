package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.LinkedList;

public class Day20 extends InputParser {
    private static final String regex1 = "(-?\\d+)";
    private static final String regex2 = null;

    public Day20(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        LinkedList<Integer> values = new LinkedList<>();
        for (int groupItemIdx = 0; groupItemIdx < dataItems1.size(); groupItemIdx++) {
            GroupItem item = dataItems1.get(groupItemIdx);
            for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
                ItemLine line = item.get(lineIdx);
                for (int elementIdx = 0; elementIdx < line.size(); elementIdx++) {
                    String element = line.get(elementIdx);
                    values.add(Integer.parseInt(element));
                }
            }
        }

        LinkedList<Integer> originalValues = new LinkedList(values);

        for (Integer value: originalValues) {
            int currentLocation = values.indexOf(value);
            int newLocation = (currentLocation + value) % values.size();
            if (newLocation < 0) {
                newLocation = values.size() + newLocation;
            }
            if (newLocation < currentLocation) {
                values.add(newLocation, value);
                values.remove(currentLocation);
            } else {
                values.remove(currentLocation);
                values.add(newLocation, value);
            }
        }

        int zeroLocation = values.indexOf(0);
        int score = values.get((zeroLocation + 1000) % values.size());
        score += values.get((zeroLocation + 2000) % values.size());
        score += values.get((zeroLocation + 3000) % values.size());

        return score;
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        return 2;
    }
}
