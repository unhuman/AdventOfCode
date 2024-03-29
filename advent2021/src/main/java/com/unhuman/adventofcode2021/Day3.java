package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.List;

public class Day3 extends InputParser {
    private static final String regex1 = "(\\d)";
    private static final String regex2 = null;

    private enum CommonType { MOST, LEAST }

    public Day3() {
        super(2021, 3, regex1, regex2);
    }

    public Day3(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Integer> zeroBits = new ArrayList<>();
        List<Integer> oneBits = new ArrayList<>();

        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                for (int i = 0; i < line.size(); i++) {
                    int bit = Integer.parseInt(line.get(i));
                    if (i >= zeroBits.size()) {
                        zeroBits.add(0);
                        oneBits.add(0);
                    }
                    switch (bit) {
                        case 0:
                            zeroBits.set(i, zeroBits.get(i) + 1);
                            break;
                        case 1:
                            oneBits.set(i, oneBits.get(i) + 1);
                            break;
                    }
                }
            }

            List<Integer> mostBitsGamma = new ArrayList<>();
            List<Integer> leastBitsEpsilon = new ArrayList<>();
            for (int i = 0; i < zeroBits.size(); i++) {
                if (mostBitsGamma.size() <= i) {
                    mostBitsGamma.add(0);
                    leastBitsEpsilon.add(0);
                }
                mostBitsGamma.set(i, (zeroBits.get(i) > oneBits.get(i)) ? 0 : 1);
                leastBitsEpsilon.set(i, (zeroBits.get(i) > oneBits.get(i)) ? 1 : 0);
            }

            long gamma = convertBitsToLong(mostBitsGamma);
            long epsilon = convertBitsToLong(leastBitsEpsilon);

            return (gamma * epsilon);
        }

        throw new RuntimeException("bad data");
    }

    private long convertBitsToLong(List<Integer> bits) {
        long value = 0;
        int multiplier = 1;
        for (int i = bits.size() - 1; i >= 0; i--) {
            value += bits.get(i) * multiplier;
            multiplier *= 2;
        }
        return value;
    }



    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        GroupItem lines = configGroup.get(0);

        GroupItem oxygen = processLines(lines, 0, CommonType.MOST);
        GroupItem co2 = processLines(lines, 0, CommonType.LEAST);

        return Long.parseLong(oxygen.get(0).toString(), 2) * Long.parseLong(co2.get(0).toString(), 2);
    }

    GroupItem processLines(GroupItem lines, int index, CommonType commonType) {
        if (lines.size() == 1) {
            return lines;
        }

        GroupItem zeroLines = new GroupItem();
        GroupItem oneLines = new GroupItem();
        for (ItemLine line : lines) {
            int bit = Integer.parseInt(line.get(index));
            switch (bit) {
                case 0:
                    zeroLines.add(line);
                    break;
                case 1:
                    oneLines.add(line);
                    break;
            }
        }

        GroupItem recurseData;
        switch (commonType) {
            case MOST:
                recurseData = oneLines.size() >= zeroLines.size() ? oneLines : zeroLines;
                break;
            default: // LEAST
                recurseData = zeroLines.size() <= oneLines.size() ? zeroLines : oneLines;
        }

        if (recurseData.size() == 1) {
            return recurseData;
        }

        return processLines(recurseData, ++index, commonType);
    }
}
