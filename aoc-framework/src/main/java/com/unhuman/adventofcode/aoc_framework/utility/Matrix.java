package com.unhuman.adventofcode.aoc_framework.utility;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;

import java.awt.Point;
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
 */
public class Matrix extends InspectionMatrix {
    public Matrix(ConfigGroup configGroup, DataType dataType) {
        super(configGroup.get(0).get(0).size(), configGroup.get(0).size(), dataType);
        if (configGroup.size() != 1) {
            throw new RuntimeException("Can't create matrix - config group size is not 1: " + configGroup.size());
        }

        for (int y = 0; y < getHeight(); y++) {
            List<String> line = configGroup.get(0).get(y);
            for (int x = 0; x < getWidth(); x++) {
                initializeValue(x, y, line.get(x).charAt(0));
            }
        }
    }

    public Matrix(int width, int height, DataType dataType) {
        super(width, height, dataType);

        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                initializeValue(x, y, ' ');
            }
        }
    }

    public void setValue(int x, int y, Character character) {
        setPointValue(new Point(x, y), character);
    }

    public void setPointValue(Point point, Character character) {
        character = (dataType == DataType.DIGIT && (character <= 9)) ? (char) (character + '0') : character;
        matrix.get(point.y).set(point.x, character);
    }

    public List<List<Character>> getDirectAccess() {
        return matrix;
    }

    public int floodFill(Point point, char match, char fillPattern) {
        return floodFill(point.x, point.y, match, fillPattern);
    }

    public int floodFill(int x, int y, char match, char fillPattern) {
        if (!isValidLocation(x, y)) {
            return 0;
        }
        if (matrix.get(y).get(x) != match) {
            return 0;
        }

        matrix.get(y).set(x, fillPattern);

        int count = 1
                + floodFill(x - 1, y, match, fillPattern)
                + floodFill(x + 1, y, match, fillPattern)
                + floodFill(x, y - 1, match, fillPattern)
                + floodFill(x, y + 1, match, fillPattern);
        return count;
    }

}
