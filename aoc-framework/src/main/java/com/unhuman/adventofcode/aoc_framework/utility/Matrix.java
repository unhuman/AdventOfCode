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
public class Matrix<T> extends InspectionMatrix<T> {
    public Matrix(ConfigGroup configGroup, SupportedType supportedType) {
        super(configGroup.get(0).get(0).size(), configGroup.get(0).size(), supportedType);
        this.supportedType = supportedType;
        if (configGroup.size() != 1) {
            throw new RuntimeException("Can't create matrix - config group size is not 1: " + configGroup.size());
        }

        for (int y = 0; y < height; y++) {
            List<String> line = configGroup.get(0).get(y);
            for (int x = 0; x < width; x++) {
                Object object = (supportedType == SupportedType.INTEGER)
                        ? Integer.parseInt(line.get(x))
                        : line.get(x).charAt(0);
                T value = (T) object;
                matrix[y][x] = value;
            }
        }
    }

    public void setPointValue(Point point, T character) {
        matrix[point.y][point.x] = character;
    }
}
