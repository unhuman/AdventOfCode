package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;

import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day10 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    public Day10() {
        super(2024, 10, regex1, regex2);
    }

    public Day10(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        Matrix mountain = new Matrix(configGroup, Matrix.DataType.CHARACTER);
        List<Point> trailheads = mountain.getCharacterLocations('0');

        long scores = 0;

        for (Point trailhead : trailheads) {
            scores += findTrailScore(mountain, trailhead);
        }

        return scores;
    }

    private long findTrailScore(Matrix mountain, Point trailhead) {
        return recurseMountain(mountain, trailhead, new HashSet<>());
    }

    private long recurseMountain(Matrix mountain, Point point, Set<Point> endpoints) {
        if (mountain.getValue(point) == '9') {
            // prevent this point from being counted again
            endpoints.add(point);
            return 1;
        }

        long score = 0;
        char value = mountain.getValue(point);
        char lookFor = (char) (value + 1);

        List<Point> checkPoints = mountain.getAdjacentPoints(point, false);
        for (Point checkPoint : checkPoints) {
            // Ensure we're always going up
            if (mountain.getValue(checkPoint) != lookFor || endpoints.contains(checkPoint)) {
                continue;
            }
            score += recurseMountain(mountain, checkPoint, endpoints);
        }

        return score;
    }

    private long findTrailScore2(Matrix mountain, Point trailhead) {
        return recurseMountain2(mountain, trailhead);
    }

    private long recurseMountain2(Matrix mountain, Point point) {
        if (mountain.getValue(point) == '9') {
            return 1;
        }

        long score = 0;
        char value = mountain.getValue(point);
        char lookFor = (char) (value + 1);

        List<Point> checkPoints = mountain.getAdjacentPoints(point, false);
        for (Point checkPoint : checkPoints) {
            // Ensure we're always going up
            if (mountain.getValue(checkPoint) != lookFor) {
                continue;
            }
            score += recurseMountain2(mountain, checkPoint);
        }

        return score;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {

        // easier to assume there's only one group
        Matrix mountain = new Matrix(configGroup, Matrix.DataType.CHARACTER);
        List<Point> trailheads = mountain.getCharacterLocations('0');

        long scores = 0;

        for (Point trailhead : trailheads) {
            scores += findTrailScore2(mountain, trailhead);
        }

        return scores;
    }
}
