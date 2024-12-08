package com.unhuman.adventofcode.aoc_framework.utility;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
public abstract class InspectionMatrix {
    public enum DataType { CHARACTER, DIGIT}
    protected List<List<Character>> matrix;
    protected DataType dataType;
    Map<Character, List<Point>> characterTracker = new HashMap<>();

    protected InspectionMatrix(int width, int height, DataType dataType) {
        this.dataType = dataType;
        this.matrix = new ArrayList<>(height);
        for (int i = 0; i < height; ++i) {
            List<Character> line = new ArrayList<>(width);
            for (int j = 0; j < width; j++) {
                line.add(null);
            }
            this.matrix.add(line);
        }
    }

    /** Only to be used during setup */
    protected void initializeValue(int x, int y, Character character) {
        matrix.get(y).set(x, character);
        if (!characterTracker.containsKey(character)) {
            characterTracker.put(character, new ArrayList<>());
        }
        characterTracker.get(character).add(new Point(x, y));
    }

    /**
     * Get a cloned matrix
     * This could be used for value inspection
     * The owned matrix should be the one mutated
     * @return
     */
//    public InspectionMatrix getInspectionMatrix() {
//        int height = getHeight();
//        InspectionMatrix inspectionMatrix = new InspectionMatrix(matrix.get(0).size(), height, dataType);
//        for (int y = 0; y < height; y++) {
//            // TODO: this is probably wrong now
//            inspectionMatrix.matrix.add(new ArrayList<>(matrix.get(y)));
//        }
//        return inspectionMatrix;
//    }

    public int getWidth() {
        return matrix.get(0).size();
    }

    public int getHeight() {
        return matrix.size();
    }

    public Character getValue(int x, int y) {
        Character charValue = (isValid(x, y)) ? matrix.get(y).get(x) : null;
        if (charValue == null) {
            return null;
        }
        return (dataType == DataType.DIGIT) ? (char) (charValue - '0') : charValue;
    }

    public Character getValue(Point point) {
        return getValue(point.x, point.y);
    }

    public boolean isValid(int x, int y) {
        return (x >= 0 && x < getWidth() && y >= 0 && y < getHeight());
    }

    public boolean isValid(Point point) {
        return isValid(point.x, point.y);
    }

    public List<Point> getAdjacentPoints(Point point, boolean includeDiagonals) {
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
                if (getValue(check) == lookFor) {
                    founds.add(check);
                }
            }
        }
        return founds;
    }

    public List<Point> findCharacterLocationsInRow(int y, char lookFor) {
        List<Point> founds = new ArrayList<>();
        for (int x = 0; x < getWidth(); x++) {
            Point check = new Point(x, y);
            if (getValue(check) == lookFor) {
                founds.add(check);
            }
        }
        return founds;
    }

    public List<Point> findCharacterLocationsInColumn(int x, char lookFor) {
        List<Point> founds = new ArrayList<>();
        for (int y = 0; y < getHeight(); y++) {
            Point check = new Point(x, y);
            if (getValue(check) == lookFor) {
                founds.add(check);
            }
        }
        return founds;
    }

    public Set<Character> getKnownCharacters() {
        return characterTracker.keySet();
    }

    public List<Point> getCharacterLocations(Character character) {
        return characterTracker.getOrDefault(character, Collections.emptyList());
    }

    public boolean isValidLocation(Point point) {
        return isValidLocation(point.x, point.y);
    }

    public boolean isValidLocation(int x, int y) {
        return (x >= 0 && x < getWidth() && y >=0 && y < getHeight());
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
