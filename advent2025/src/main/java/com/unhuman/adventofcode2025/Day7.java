package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;

import java.awt.Point;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day7 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    public Day7() {
        super(2025, 7, regex1, regex2);
    }

    public Day7(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);
        Set<Integer> items = new HashSet<>();
        long splits = 0L;

        items.add(matrix.getStartingCharacterLocations('S').getFirst().x);

        for (int y = matrix.getStartingCharacterLocations('S').getFirst().y + 1; y < matrix.getHeight(); y++) {
            Set<Integer> nextSet = new HashSet<>();

            for (Integer item : items) {
                if (matrix.getValue(item, y) == '^') {
                    ++splits;
                    nextSet.add(item - 1);
                    nextSet.add(item + 1);
                } else {
                    nextSet.add(item);
                }
            }

            items = nextSet;
        }

        return splits;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);
        return processDownstreams(matrix, matrix.getStartingCharacterLocations('S').getFirst());
    }

    static final Map<Point, Long> knownPoints = new HashMap<>();
    long processDownstreams(Matrix matrix, Point point) {
        if (point.y >= matrix.getHeight()) {
            return 1L;
        }
        if (knownPoints.containsKey(point)) {
            return knownPoints.get(point);
        }

        Long returnValue;
        if (matrix.getValue(point) == '^') {
            returnValue = processDownstreams(matrix, new Point(point.x - 1, point.y + 1))
                    + processDownstreams(matrix, new Point(point.x + 1, point.y + 1));
        } else {
            returnValue = processDownstreams(matrix, new Point(point.x, point.y + 1));
        }

        knownPoints.put(point, returnValue);

        return returnValue;
    }
}
