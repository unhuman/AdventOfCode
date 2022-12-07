package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.List;

public class Puzzle3 extends InputParser {
    public Puzzle3(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, "(\\d)", null);
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
                    switch(bit) {
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
        return null;
    }
}
