package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;

import java.awt.Point;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class Day4 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    public Day4() {
        super(2025, 4, regex1, regex2);
    }

    public Day4(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        AtomicLong count = new AtomicLong();

        List<Point> squares = matrix.getCharacterLocations('@');
        squares.forEach(point -> {
            List<Point> adjacentRolls = matrix.getAdjacentPointsAvoidChar(point, true, '.');
            if (adjacentRolls.size() < 4) {
                count.addAndGet(1L);
            }
        });

        return count.get();
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        AtomicLong count = new AtomicLong();
        long priorCount;

        do {
            priorCount = count.get();
            List<Point> squares = matrix.getCharacterLocations('@');
            squares.forEach(point -> {
                List<Point> adjacentRolls = matrix.getAdjacentPointsAvoidChar(point, true, '.');
                if (adjacentRolls.size() < 4) {
                    count.addAndGet(1L);
                    matrix.setValue(point, '.');
                }
            });
        } while (priorCount != count.get());
        return count.get();
    }
}
