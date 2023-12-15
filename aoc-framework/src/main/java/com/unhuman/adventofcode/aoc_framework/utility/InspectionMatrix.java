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
public class InspectionMatrix {
    protected List<List<Character>> matrix;

    protected InspectionMatrix(int width, int height) {
        this.matrix = new ArrayList<>(height);
        for (int i = 0; i < height; ++i) {
            List<Character> line = new ArrayList<>(width);
            for (int j = 0; j < width; j++) {
                line.add(null);
            }
            this.matrix.add(line);
        }
    }

    /**
     * Get a cloned matrix
     * This could be used for value inspection
     * The owned matrix should be the one mutated
     * @return
     */
    public InspectionMatrix getInspectionMatrix() {
        int height = getHeight();
        InspectionMatrix inspectionMatrix = new InspectionMatrix(matrix.get(0).size(), height);
        for (int y = 0; y < height; y++) {
            // TODO: this is probably wrong now
            inspectionMatrix.matrix.add(new ArrayList<>(matrix.get(y)));
        }
        return inspectionMatrix;
    }

    public int getWidth() {
        return matrix.get(0).size();
    }

    public int getHeight() {
        return matrix.size();
    }

    public Character getValue(int x, int y) {
        return getPointValue(new Point(x, y));
    }

    public Character getPointValue(Point point) {
        return (isValid(point)) ? matrix.get(point.y).get(point.x) : null;
    }

    public boolean isValid(Point point) {
        return (point.x >= 0 && point.x < getWidth() && point.y >= 0 && point.y < getHeight());
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

    public List<Point> findCharacterLocations(char lookFor) {
        List<Point> founds = new ArrayList<>();
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Point check = new Point(x, y);
                if (getPointValue(check) == lookFor) {
                    founds.add(check);
                }
            }
        }
        return founds;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getHeight() * getWidth());
        matrix.forEach(line -> { line.forEach(sb::append); sb.append('\n'); });
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (int row = 0; row < getHeight(); row++) {
            for (int col = 0; col < getWidth(); col++) {
                hash = hash * 7 + matrix.get(row).get(col).hashCode();
            }
        }
        return hash;
    }
}
