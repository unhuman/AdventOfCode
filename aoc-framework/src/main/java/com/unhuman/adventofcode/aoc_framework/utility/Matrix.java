package com.unhuman.adventofcode.aoc_framework.utility;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;

import java.awt.*;
import java.util.ArrayList;
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
public class Matrix<T> {
    public enum SupportedType { INTEGER, CHARACTER }

    private int width;
    private int height;
    private T[][] matrix;
    private SupportedType supportedType;

    public Matrix(ConfigGroup configGroup, SupportedType supportedType) {
        this.supportedType = supportedType;
        if (configGroup.size() != 1) {
            throw new RuntimeException("Can't create matrix - config group size is not 1: " + configGroup.size());
        }
        height = configGroup.get(0).size();
        width = configGroup.get(0).get(0).size();

        matrix = (T[][]) new Object[height][width];;

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Get a cloned matrix
     * This could be used for value inspection
     * The owned matrix should be the one mutated
     * @return
     */
    public T[][] getClonedMatrix() {
        // T[][] copiedMatrix = new T[height][width];
        T[][] copiedMatrix = (T[][]) new Object[height][width];
        for (int y = 0; y < height; y++) {
            copiedMatrix[y] = matrix[y].clone();
        }
        return copiedMatrix;
    }

    public T getPointValue(Point point) {
        return (isValid(point)) ? matrix[point.y][point.x] : null;
    }

    public void setPointValue(Point point, T character) {
        matrix[point.y][point.x] = character;
    }

    public boolean isValid(Point point) {
        return (point.x >= 0 && point.x < width && point.y >= 0 && point.y >= height);
    }

    List<Point> getAdjacentPoints(Point point, boolean includeDiagonals) {
        List<Point> adjacentPoints = new ArrayList<>();

        // verticals / rows
        for (int y = point.y - 1; y <= point.y + 1; y += 2) {
            Point checkPoint = new Point(point.x, y);
            if (isValid(checkPoint)) {
                adjacentPoints.add(checkPoint);
            }
        }

        // horizontal / columns
        for (int x = point.x - 1; x <= point.x + 1; x += 2) {
            Point checkPoint = new Point(x, point.y);
            if (isValid(checkPoint)) {
                adjacentPoints.add(checkPoint);
            }
        }

        // diagonals
        if (includeDiagonals) {
            for (int y = point.y - 1; y <= point.y + 1; y += 2) {
                for (int x = point.x - 1; x <= point.x + 1; x += 2) {
                    Point checkPoint = new Point(x, y);
                    if (isValid(checkPoint)) {
                        adjacentPoints.add(checkPoint);
                    }
                }
            }
        }
        return adjacentPoints;
    }
}
