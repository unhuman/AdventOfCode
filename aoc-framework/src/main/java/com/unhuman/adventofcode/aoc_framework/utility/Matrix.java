package com.unhuman.adventofcode.aoc_framework.utility;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;

import java.awt.*;
import java.util.List;

/**
 * Matrix class can be used to parse input easily
 *
 * Usage:
 *         Matrix matrix = new Matrix(configGroup);
 *         int width = matrix.getWidth();
 *         int height = matrix.getHeight();
 *
 * All access is through using Point
 *
 * getClonedMatrix() could use for inspection that requires "paging" changes
 *
 * getAdjacentPoints() would be useful to find valid, adjacent points
 */
public class Matrix extends InspectionMatrix {
    public Matrix(ConfigGroup configGroup) {
        super(configGroup.get(0).get(0).size(), configGroup.get(0).size());
        if (configGroup.size() != 1) {
            throw new RuntimeException("Can't create matrix - config group size is not 1: " + configGroup.size());
        }

        for (int y = 0; y < getHeight(); y++) {
            List<String> line = configGroup.get(0).get(y);
            for (int x = 0; x < getWidth(); x++) {;
                matrix.get(y).set(x, line.get(x).charAt(0));
            }
        }
    }

    public void setValue(int x, int y, Character character) {
        setPointValue(new Point(x, y), character);
    }

    public void setPointValue(Point point, Character character) {
        matrix.get(point.y).set(point.x, character);
    }

    public List<List<Character>> getDirectAccess() {
        return matrix;
    }
}
