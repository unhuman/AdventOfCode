package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;
import com.unhuman.adventofcode.aoc_framework.utility.PointHelper;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day8 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    public Day8() {
        super(2024, 8, regex1, regex2);
    }

    public Day8(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        List<Character> antennaNames = new ArrayList<>(matrix.getStartingKnownCharacters());
        antennaNames.remove(Character.valueOf('.'));

        HashSet<Point> antinodes = new HashSet<>();

        for (Character antennaName: antennaNames) {
            List <Point> antennaLocations = matrix.getStartingCharacterLocations(antennaName);
            for (int i = 0; i < antennaLocations.size() - 1; i++) {
                for (int j = i + 1; j < antennaLocations.size(); j++) {
                    Point p1 = antennaLocations.get(i);
                    Point p2 = antennaLocations.get(j);
                    Point slope = PointHelper.getSlope(p1, p2);
                    Point check = PointHelper.subtrackSlope(p1, slope);
                    if (matrix.isValidLocation(check)) {
                        antinodes.add(check);
                    }
                    check = PointHelper.addSlope(p2, slope);
                    if (matrix.isValidLocation(check)) {
                        antinodes.add(check);
                    }
                }
            }
        }

        return antinodes.size();
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        List<Character> antennaNames = new ArrayList<>(matrix.getStartingKnownCharacters());
        antennaNames.remove(Character.valueOf('.'));

        HashSet<Point> antinodes = new HashSet<>();

        for (Character antennaName: antennaNames) {
            List <Point> antennaLocations = matrix.getStartingCharacterLocations(antennaName);
            for (int i = 0; i < antennaLocations.size() - 1; i++) {
                for (int j = i + 1; j < antennaLocations.size(); j++) {
                    Point p1 = antennaLocations.get(i);
                    Point p2 = antennaLocations.get(j);
                    Point slope = PointHelper.getSlope(p1, p2);
                    Point check = PointHelper.subtrackSlope(p1, slope);
                    antinodes.add(p1);
                    antinodes.add(p2);
                    while (matrix.isValidLocation(check)) {
                        antinodes.add(check);
                        check = PointHelper.subtrackSlope(check, slope);
                    }
                    check = PointHelper.addSlope(p2, slope);
                    while (matrix.isValidLocation(check)) {
                        antinodes.add(check);
                        check = PointHelper.addSlope(check, slope);
                    }
                }
            }
        }

        return antinodes.size();
    }
}
