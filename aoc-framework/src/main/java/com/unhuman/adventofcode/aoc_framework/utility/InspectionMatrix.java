package com.unhuman.adventofcode.aoc_framework.utility;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * InspectionMatrix class can be used to parse input easily
 *
 * Usage:
 *         int width = matrix.getWidth();
 *         int height = matrix.getHeight();
 *
 * All access is through using Point
 *
 * getAdjacentPoints() would be useful to find valid, adjacent points
 */
public class InspectionMatrix<T> {
    public enum SupportedType { INTEGER, CHARACTER }

    protected T[][] matrix;
    protected int width;
    protected int height;
    protected SupportedType supportedType;

    protected InspectionMatrix(int width, int height, SupportedType supportedType) {
        this.width = width;
        this.height = height;
        this.matrix = (T[][]) new Object[height][width];
        this.supportedType = supportedType;
    }

    /**
     * Get a cloned matrix
     * This could be used for value inspection
     * The owned matrix should be the one mutated
     * @return
     */
    public InspectionMatrix<T> getInspectionMatrix() {
        InspectionMatrix<T> inspectionMatrix = new InspectionMatrix<>(width, height, supportedType);
        for (int y = 0; y < height; y++) {
            inspectionMatrix.matrix[y] = matrix[y].clone();
        }
        return inspectionMatrix;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public T getPointValue(Point point) {
        return (isValid(point)) ? matrix[point.y][point.x] : null;
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
