package com.unhuman.adventofcode.aoc_framework.utility;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
public class Matrix {
    public enum DataType { CHARACTER, DIGIT }
    protected List<List<Character>> matrix;
    protected DataType dataType;

    Map<Character, List<Point>> characterTracker = new HashMap<>();

    private Matrix(int width, int height, Matrix.DataType dataType, Optional<GroupItem> optionalGroup) {
        if (width < 1 || height < 1) {
            throw new RuntimeException("Can't create matrix of size (" + width + ", " + height + ")");
        }

        this.dataType = dataType;
        this.matrix = new ArrayList<>(height);
        char emptyValue = (dataType == DataType.CHARACTER) ? ' ' : '0';
        for (int y = 0; y < height; ++y) {
            List<Character> line = new ArrayList<>(width);
            for (int x = 0; x < width; x++) {
                if (optionalGroup.isPresent()) {
                    Character character = optionalGroup.get().get(y).getChar(x);
                    if (!characterTracker.containsKey(character)) {
                        characterTracker.put(character, new ArrayList<>());
                    }
                    characterTracker.get(character).add(new Point(x, y));
                    line.add(character);
                } else {
                    line.add(emptyValue);
                }
            }
            this.matrix.add(line);
        }
    }

    public Matrix(int width, int height, Matrix.DataType dataType) {
        this (width, height, dataType, Optional.empty());
    }

    public Matrix(ConfigGroup configGroup, DataType dataType) {
        this(configGroup.get(0).get(0).size(), configGroup.get(0).size(), dataType, Optional.of(configGroup.get(0)));
    }

    /**
     * Get a cloned matrix
     * This could be used for value inspection
     * The owned matrix should be the one mutated
     * @return
     */

//        public Matrix getInspectionMatrix() {
//        int height = getHeight();
//        Matrix inspectionMatrix = new Matrix(matrix.get(0).size(), height, dataType);
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

    public void setValue(int x, int y, Character character) {
        character = (dataType == DataType.DIGIT && (character <= 9)) ? (char) (character + '0') : character;
        matrix.get(y).set(x, character);
    }

    public void setPointValue(Point point, Character character) {
        setValue(point.x, point.y, character);
    }

    public List<List<Character>> getDirectAccess() {
        return matrix;
    }

    public Set<Character> getKnownCharacters() {
        return characterTracker.keySet();
    }

    public List<Point> getCharacterLocations(Character character) {
        return characterTracker.getOrDefault(character, Collections.emptyList());
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
