package com.unhuman.adventofcode.aoc_framework.utility;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

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
    public enum Direction {
        UP(new Point(0, -1)),
        RIGHT(new Point(1, 0)),
        DOWN(new Point(0, 1)),
        LEFT(new Point (-1, 0));

        private final Point direction;

        Direction(Point point) {
            this.direction = point;
        }

        public Point getDirection() {
            return direction;
        }

        public static Direction getDirection(Character arrow) {
            return switch (arrow) {
                case '^' -> UP;
                case '>' -> RIGHT;
                case 'v' -> DOWN;
                case '<' -> LEFT;
                default -> throw new RuntimeException("Invalid direction: " + arrow);
            };
        }

        public static Point getDirectionVelocity(Character arrow) {
            return switch (arrow) {
                case '^' -> UP.getDirection();
                case '>' -> RIGHT.getDirection();
                case 'v' -> DOWN.getDirection();
                case '<' -> LEFT.getDirection();
                default -> throw new RuntimeException("Invalid direction: " + arrow);
            };
        }

        public static Direction getDirectionVelocity(Point value) {
            if (value.equals(Direction.UP.direction)) {
                return UP;
            }
            if (value.equals(Direction.RIGHT.direction)) {
                return RIGHT;
            }
            if (value.equals(Direction.DOWN.direction)) {
                return DOWN;
            }
            if (value.equals(Direction.LEFT.direction)) {
                return LEFT;
            }

            throw new RuntimeException("Invalid direction: " + value);
        }

    }
    protected List<List<Character>> matrix;
    protected DataType dataType;

    Map<Character, List<Point>> startingCharacterTracker = new HashMap<>();

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
                    if (!startingCharacterTracker.containsKey(character)) {
                        startingCharacterTracker.put(character, new ArrayList<>());
                    }
                    startingCharacterTracker.get(character).add(new Point(x, y));
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

    public Matrix(int width, int height, Matrix.DataType dataType, Character defaultChar) {
        this (width, height, dataType, Optional.empty());
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                setValue(x, y, defaultChar);
            }
        }
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

    public void setValue(Point point, Character character) {
        setValue(point.x, point.y, character);
    }

    public List<List<Character>> getDirectAccess() {
        return matrix;
    }

    public Set<Character> getStartingKnownCharacters() {
        return startingCharacterTracker.keySet();
    }

    /**
     * Returns list of the character locations that everything started at
     * Alternative is getCharacterLocations
     * @param character
     * @return
     */
    public List<Point> getStartingCharacterLocations(Character character) {
        return startingCharacterTracker.getOrDefault(character, Collections.emptyList());
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

    public List<Point> getAdjacentPointsAvoidChar(Point point, boolean includeDiagonals, Character avoidChar) {
        return getAdjacentPoints(point, includeDiagonals).stream().filter(p -> getValue(p) != avoidChar).toList();
    }

    public List<Direction> getNextNavigation(Point point, boolean includeDiagonals, Character avoidChar) {
        return getAdjacentPointsAvoidChar(point, includeDiagonals, avoidChar)
                .stream().map(p -> Direction.getDirectionVelocity(new Point(p.x - point.x, p.y - point.y)))
                .toList();
    }


    public List<Point> getCharacterLocations(char lookFor) {
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

    public boolean eliminateDeadEnds(Character openChar, Character wallChar) {
        boolean everChanged = false;
        boolean currentChange;
        do {
            currentChange = false;
            for (int y = 0; y < getHeight(); y++) {
                for (int x = 0; x < getWidth(); x++) {
                    Point check = new Point(x, y);
                    // only inspect open space characters
                    if (getValue(check) == openChar) {
                        List<Point> adjacentPoints = getAdjacentPoints(check, false);
                        int wallCount = 0;
                        for (Point adjacentPoint : adjacentPoints) {
                            if (getValue(adjacentPoint) == wallChar) {
                                wallCount++;
                            }
                        }
                        if (wallCount == 3) {
                            setValue(check, wallChar);
                            currentChange = true;

                            // Todo: navigate the path until there isn't any other dead ends
                        }
                    }
                }
            }
            everChanged = (everChanged) ? everChanged : currentChange;
        } while (currentChange);
        return everChanged;
    }

    private long findShortestPathInternal(Point startingLocation, Point endingLocation,
                                          Character wallChar, Set<Point> visitedLocations,
                                          long currentScore, AtomicLong lowestScoreSoFar) {
        // bail out if we've already navigated here
        if ((lowestScoreSoFar != null) && (currentScore >= lowestScoreSoFar.get())) {
            return lowestScoreSoFar.get();
        }

        // we found the end, so - we're good here
        if (startingLocation.equals(endingLocation)) {
            lowestScoreSoFar.set(currentScore);
            return currentScore;
        }

        // get next locations to navigate
        List<Direction> nextNavigations = getNextNavigation(startingLocation, false, wallChar);
        for (Direction nextNavigation : nextNavigations) {
            Point nextLocation = new Point(startingLocation.x + nextNavigation.getDirection().x,
                    startingLocation.y + nextNavigation.getDirection().y);
            if (visitedLocations.contains(nextLocation)) {
                continue;
            }

            // track that we visited this location
            visitedLocations = new HashSet<>(visitedLocations);
            visitedLocations.add(startingLocation);

            long score = findShortestPathInternal(nextLocation, endingLocation, wallChar, visitedLocations,
                    currentScore + 1, lowestScoreSoFar);
            if (score < lowestScoreSoFar.get()) {
                lowestScoreSoFar.set(score);
            }
        }
        return lowestScoreSoFar.get();
    }


    @Deprecated // Use findShortestPathDijikstra
    public long findShortestPath(Point startingLocation, Point endingLocation, Character wallChar) {
        return findShortestPathInternal(startingLocation, endingLocation,
                wallChar, new HashSet<>(), 0L, new AtomicLong(Long.MAX_VALUE));
    }

    @Deprecated // Use findShortestPathDijikstra
    public long findShortestPath(Character startingChar, Character endingChar, Character wallChar) {
        Point startingLocation = getCharacterLocations(startingChar).get(0);
        Point endingLocation = getCharacterLocations(endingChar).get(0);

        return findShortestPathInternal(startingLocation, endingLocation,
                wallChar, new HashSet<>(), 0L, new AtomicLong(Long.MAX_VALUE));
    }

    public int findShortestPathDijikstra(Point start, Point finish, Character wallChar) {
        // 2D arrays representing the shortest distances and visited cells
        int[][] dist = new int[getWidth()][getHeight()];
        HashSet<Point> visited = new HashSet<>();

        // initialize all distances to infinity except the starting cell
        for (int i = 0; i < getWidth(); i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[start.x][start.y] = 0;

        // create a priority queue for storing cells to visit
        PriorityQueue<VisitInfo> pq = new PriorityQueue<>(Comparator.comparingInt(VisitInfo::distance));
        pq.offer(new VisitInfo(start, 0));

        // iterate while there are cells to visit
        while (!pq.isEmpty()) {
            // remove the cell with the smallest distance from the priority queue
            VisitInfo cell = pq.poll();
            Point currentPoint = cell.point();
            int distance = cell.distance();

            // if the cell has already been visited, skip it
            if (visited.contains(currentPoint)) {
                continue;
            }

            // mark the cell as visited
            visited.add(currentPoint);

            // if we've reached the end cell, return the shortest distance
            if (currentPoint.equals(finish)) {
                return distance;
            }

            // iterate over the neighboring cells and update their distances if necessary
            for (Direction direction: getNextNavigation(currentPoint, false, wallChar)) {
                Point nextPoint = PointHelper.addPoints(currentPoint, direction.getDirection());
                if (!visited.contains(nextPoint)) {
                    int newDistance = distance + 1;
                    if (newDistance < dist[nextPoint.x][nextPoint.y]) {
                        dist[nextPoint.x][nextPoint.y] = newDistance;
                        pq.offer(new VisitInfo(nextPoint, newDistance));
                    }
                }
            }
        }

        // if we couldn't reach the end cell, return -1
        return -1;
    }

    public long findShortestPathDijikstra(Character startingChar, Character endingChar,
                                          Character wallChar) {
        Point startingLocation = getCharacterLocations(startingChar).get(0);
        Point endingLocation = getCharacterLocations(endingChar).get(0);

        return findShortestPathDijikstra(startingLocation, endingLocation,
                wallChar);
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

    public record VisitInfo(Point point, Integer distance) {
    }
}
