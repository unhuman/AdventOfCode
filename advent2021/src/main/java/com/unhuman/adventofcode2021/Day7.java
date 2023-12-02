package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.HashMap;
import java.util.Map;

public class Day7 extends InputParser {
    private static final String regex1 = "(\\d+),?";
    private static final String regex2 = null;

    public Day7() {
        super(2021, 7, regex1, regex2);
    }

    public Day7(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Map<Integer, Integer> crabPositions = new HashMap<>();
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                for (String element : line) {
                    int crabPosition = Integer.parseInt(element);
                    if (!crabPositions.containsKey(crabPosition)) {
                        crabPositions.put(crabPosition, 0);
                    }
                    crabPositions.put(crabPosition, crabPositions.get(crabPosition) + 1);
                }
            }
        }

        int minPosition = crabPositions.keySet().stream().mapToInt(v -> v).min().getAsInt();
        int maxPosition = crabPositions.keySet().stream().mapToInt(v -> v).max().getAsInt();

        Integer bestFuel = null;
        for (int i = minPosition; i <= maxPosition; i++) {
            int fuelUsed = 0;

            for (int j = 0; j < crabPositions.keySet().size(); j++) {
                Integer crabPosition = (Integer) crabPositions.keySet().toArray()[j];
                int crabFuelToPosition = Math.abs(crabPosition - i) * crabPositions.get(crabPosition);
                fuelUsed += crabFuelToPosition;
            }

            // System.out.println("Position: " + i + " Fuel: " + fuelUsed);

            if ((bestFuel == null) || (fuelUsed < bestFuel)) {
                bestFuel = fuelUsed;
            }
        }

        return bestFuel;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Map<Integer, Integer> crabPositions = new HashMap<>();
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                for (String element : line) {
                    int crabPosition = Integer.parseInt(element);
                    if (!crabPositions.containsKey(crabPosition)) {
                        crabPositions.put(crabPosition, 0);
                    }
                    crabPositions.put(crabPosition, crabPositions.get(crabPosition) + 1);
                }
            }
        }

        int minPosition = crabPositions.keySet().stream().mapToInt(v -> v).min().getAsInt();
        int maxPosition = crabPositions.keySet().stream().mapToInt(v -> v).max().getAsInt();

        Long bestFuel = null;
        for (int i = minPosition; i <= maxPosition; i++) {
            long fuelUsed = 0;

            for (int j = 0; j < crabPositions.keySet().size(); j++) {
                Integer crabPosition = (Integer) crabPositions.keySet().toArray()[j];
                int crabFuelToPosition = sumDistance(Math.abs(crabPosition - i)) * crabPositions.get(crabPosition);
                fuelUsed += crabFuelToPosition;
            }

            // System.out.println("Position: " + i + " Fuel: " + fuelUsed);

            if ((bestFuel == null) || (fuelUsed < bestFuel)) {
                bestFuel = fuelUsed;
            }
        }

        return bestFuel;
    }

    private int sumDistance(int distance) {
        int sum = 0;
        for (int i = distance; i >= 0; i--) {
            sum += i;
        }
        return sum;
    }
}
