package com.unhuman.adventofcode2022;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Day20 extends InputParser {
    private static final String regex1 = "(-?\\d+)";
    private static final String regex2 = null;

    public Day20() {
        super(2022, 20, regex1, regex2);
    }

    public Day20(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        LongBox zeroLongBox = null;
        LinkedList<LongBox> values = new LinkedList<>();
        for (int groupItemIdx = 0; groupItemIdx < dataItems1.size(); groupItemIdx++) {
            GroupItem item = dataItems1.get(groupItemIdx);
            for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
                ItemLine line = item.get(lineIdx);
                for (int elementIdx = 0; elementIdx < line.size(); elementIdx++) {
                    String element = line.get(elementIdx);
                    LongBox valueBox = new LongBox(Integer.parseInt(element));
                    values.add(valueBox);
                    if (valueBox.value == 0) {
                        zeroLongBox = valueBox;
                    }
                }
            }
        }

        LinkedList<LongBox> originalValues = new LinkedList(values);

        for (LongBox value: originalValues) {
            int currentLocation = values.indexOf(value);

            values.remove(currentLocation);

            int newLocation = (int) (currentLocation + value.value()) % values.size();
            if (newLocation < 0) {
                newLocation = values.size() + newLocation;
            }

            values.add(newLocation, value);
        }

        int zeroLocation = values.indexOf(zeroLongBox);
        long score = values.get((zeroLocation + 1000) % values.size()).value();
        score += values.get((zeroLocation + 2000) % values.size()).value();
        score += values.get((zeroLocation + 3000) % values.size()).value();

        return score;
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        LongBox zeroLongBox = null;
        LinkedHashMap<LongBox, LongBox> originalValuesMapping = new LinkedHashMap<>();
        LinkedList<LongBox> values = new LinkedList<>();
        for (int groupItemIdx = 0; groupItemIdx < dataItems1.size(); groupItemIdx++) {
            GroupItem item = dataItems1.get(groupItemIdx);
            for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
                ItemLine line = item.get(lineIdx);
                for (int elementIdx = 0; elementIdx < line.size(); elementIdx++) {
                    String element = line.get(elementIdx);
                    LongBox valueBox = new LongBox(Integer.parseInt(element));
                    LongBox adjustedValueBox = new LongBox(valueBox.value() * 811589153);
                    originalValuesMapping.put(valueBox, adjustedValueBox);
                    values.add(adjustedValueBox);
                    if (valueBox.value == 0) {
                        zeroLongBox = adjustedValueBox;
                    }
                }
            }
        }

        for (int i = 0; i < 10; i++) {
            for (LongBox originalValue: originalValuesMapping.keySet()) {
                int currentLocation = values.indexOf(originalValuesMapping.get(originalValue));

                LongBox moveValue = values.remove(currentLocation);

                int newLocation = (int) ((currentLocation + moveValue.value()) % values.size());
                if (newLocation < 0) {
                    newLocation = values.size() + newLocation;
                }

                values.add(newLocation, moveValue);
            }
        }

        int zeroLocation = values.indexOf(zeroLongBox);
        long score = values.get((zeroLocation + 1000) % values.size()).value();
        score += values.get((zeroLocation + 2000) % values.size()).value();
        score += values.get((zeroLocation + 3000) % values.size()).value();

        return score;
    }

    public record LongBox(long value) {
        @Override
        public String toString() {
            return Long.toString(value);
        }

        @Override
        public boolean equals(Object other) {
            // default equals is no good
            return this == other;
        }
    }
}
