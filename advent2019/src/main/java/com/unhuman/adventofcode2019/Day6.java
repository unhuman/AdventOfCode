package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day6 extends InputParser {
    private static final String regex1 = "(.+)\\)(.+)";
    private static final String regex2 = null;

    public Day6() {
        super(2019, 6, regex1, regex2);
    }

    public Day6(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        Map<String, String> directOrbits = new HashMap<>();
        GroupItem item = configGroup.get(0);

        long orbitCount = 0;
        for (ItemLine line : item) {
            directOrbits.put(line.getString(1), line.getString(0));
        }

        Map<String, Long> knownOrbits = new HashMap<>();
        for (String planet: directOrbits.keySet()) {
            orbitCount += findOrbitLength(directOrbits, planet, knownOrbits);
        };

        return orbitCount;
    }

    long findOrbitLength(Map<String, String> directOrbits, String planet, Map<String, Long> knownOrbits) {
        if (knownOrbits.containsKey(planet)) {
            return knownOrbits.get(planet);
        }
        if (!directOrbits.containsKey(planet)) {
            return 0;
        }
        long orbitLength = 1 + findOrbitLength(directOrbits, directOrbits.get(planet), knownOrbits);
        knownOrbits.put(planet, orbitLength);
        return orbitLength;
    }

    List<String> findOrbits(Map<String, String> directOrbits, String planet) {
        List<String> orbits = new ArrayList<>();
        do {
            orbits.add(0, planet);
            planet = directOrbits.get(planet);
        } while (planet != null);
        return orbits;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Map<String, String> directOrbits = new HashMap<>();
        GroupItem item = configGroup.get(0);

        for (ItemLine line : item) {
            directOrbits.put(line.getString(1), line.getString(0));
        }

        List<String> youOrbit = findOrbits(directOrbits, "YOU");
        List<String> santaOrbit = findOrbits(directOrbits, "SAN");

        for (int i = youOrbit.size() - 1; i >= 0; i--) {
            if (santaOrbit.contains(youOrbit.get(i))) {
                int santaDepth = santaOrbit.size() - 1  - santaOrbit.indexOf(youOrbit.get(i)) - 1;
                return (long) youOrbit.size() - i - 1 + santaDepth - 1;
            }
        }

        throw new RuntimeException("not found");
    }
}
