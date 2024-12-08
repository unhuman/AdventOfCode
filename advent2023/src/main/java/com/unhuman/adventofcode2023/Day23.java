package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day23 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    public Day23() {
        super(2023, 23, regex1, regex2);
    }

    public Day23(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);
        Point startingPoint = matrix.findCharacterLocationsInRow(0, '.').get(0);
        Point endingPoint = matrix.findCharacterLocationsInRow(matrix.getHeight() - 1, '.').get(0);

        long longestPath = findLongestPath(matrix, startingPoint, endingPoint, Collections.emptySet(), false);

        return longestPath;
    }

    long getSetHashCode(Set<Point> set) {
        long hashCode = 0L;
        for (Point point: set) {
            hashCode = hashCode * 31 + point.hashCode();
        }
        return hashCode;
    }

    Map<Long, Long> cache = new HashMap<>();
    Long findLongestPath(Matrix matrix, Point currentPoint, Point endingPoint, Set<Point> pathTravelled,
                     boolean ignoreSlopes) {
//        long hash = currentPoint.hashCode() * 31L * 31L * 31L + endingPoint.hashCode() * 31L * 31L + pathTravelled.hashCode() * 31L + Boolean.hashCode(ignoreSlopes);
//        if (cache.containsKey(hash)) {
//            return cache.get(hash);
//        }

        Long longestPath = findLongestPathInternal(matrix, currentPoint, endingPoint, pathTravelled, ignoreSlopes);
//        cache.put(hash, longestPath);
        return longestPath;
    }

    // map of point + nextPoint to count to get to point
    Map<Pair<Point, Point>, Pair<Integer, Point>> distanceCache = new HashMap<>();
    void prepopulateDistanceCache(Matrix matrix, boolean ignoreSlopes) {
        if (!ignoreSlopes) {
            return;
        }
        for (int x = 1; x < matrix.getWidth() - 1; x++) {
            for (int y = 1; y < matrix.getHeight(); y++) {
                Point start = new Point(x, y);
                if (matrix.getValue(start) == '#') {
                    continue;
                }
                List<Point> options = matrix.getAdjacentPoints(start, false).stream()
                        .filter(destinationPoint -> canNavigate(matrix, start, destinationPoint, ignoreSlopes))
                        .toList();
                if (options.size() > 2) {
                    for (Point option: options) {
                        Set<Point> trackedPoints = new HashSet<>();
                        trackedPoints.add(start);

                        int counter = 1;
                        Point internalStart = option;
                        Pair<Point, Point> key = new Pair<>(start, option);
                        trackedPoints.add(option);

                        do {
                            counter++;

                            Point filterStart = internalStart;
                            option = internalStart;

// Used for debugging purposes
//                            List<Point> adjacentPoints = matrix.getAdjacentPoints(option, false);
//                            adjacentPoints = adjacentPoints.stream().filter(destinationPoint -> !trackedPoints.contains(destinationPoint)).toList();
//                            adjacentPoints = adjacentPoints.stream().filter(destinationPoint -> canNavigate(matrix, filterStart, destinationPoint, ignoreSlopes)).toList();

                            options = matrix.getAdjacentPoints(option, false).stream()
                                    .filter(destinationPoint -> !trackedPoints.contains(destinationPoint))
                                    .filter(destinationPoint -> canNavigate(matrix, filterStart, destinationPoint, ignoreSlopes))
                                    .toList();
                            if (options.size() != 1) {
                                break;
                            }
                            trackedPoints.add(options.get(0));
                            internalStart = options.get(0);
                        } while (true);
                        if (counter > 0) {
                            distanceCache.put(key, new Pair<>(counter, internalStart));
                        }
                    }
                }
            }
        }
    }

    Long findLongestPathInternal(Matrix matrix, Point currentPoint, Point endingPoint, Set<Point> pathTravelled,
                         boolean ignoreSlopes) {
        if (currentPoint.equals(endingPoint)) {
            return 1L;
        }

        Set<Point> newPathTravelled = new LinkedHashSet<>(pathTravelled);
        newPathTravelled.add(currentPoint);

        long counter = 0L;
        final Point checkCurrentPoint = currentPoint;
        List<Point> options = matrix.getAdjacentPoints(checkCurrentPoint, false).stream()
                .filter(destinationPoint -> !newPathTravelled.contains(destinationPoint))
                .filter(destinationPoint -> canNavigate(matrix, checkCurrentPoint, destinationPoint, ignoreSlopes))
                .toList();
        while (options.size() == 1) {
            ++counter;
            final Point checkCurrentPoint2 = options.get(0);

            if (checkCurrentPoint2.equals(endingPoint)) {
                return counter;
            }

            newPathTravelled.add(checkCurrentPoint2);
            options = matrix.getAdjacentPoints(checkCurrentPoint2, false).stream()
                    .filter(destinationPoint -> !newPathTravelled.contains(destinationPoint))
                    .filter(destinationPoint -> canNavigate(matrix, checkCurrentPoint2, destinationPoint, ignoreSlopes))
                    .toList();
        }

        // this is a short circuit, but the flow below would capture this, too.
        if (options.size() == 0) {
            return null;
        }

        ++counter;
        List<Long> routes = new ArrayList<>();
        // System.out.println("Decision point: " + currentPoint +  " " + options.size() + " options " + options.toString());
        for (Point option: options) {
            Set<Point> anotherNewPathTravelled = new LinkedHashSet<>(newPathTravelled);
            anotherNewPathTravelled.add(option);
            Long furtherPath = findLongestPath(matrix, option, endingPoint, anotherNewPathTravelled, ignoreSlopes);
            if (furtherPath != null) {
                routes.add(counter + furtherPath);
            }
        }
        routes.sort(Comparator.reverseOrder());

        return routes.size() > 0 ? routes.get(0) : null;

    }

    boolean canNavigate(Matrix matrix, Point currentPoint, Point destinationPoint, boolean ignoreSlopes) {
        char destinationChar = matrix.getValue(destinationPoint);

        if (destinationChar == '#') {
            return false;
        }

        int xdiff = destinationPoint.x - currentPoint.x;
        int ydiff = destinationPoint.y - currentPoint.y;

        if (!ignoreSlopes) {
            if ((xdiff == -1 && destinationChar == '>')          // going left cannot go into a >
                    || (xdiff == 1 && destinationChar == '<')    // going right cannot go into a <
                    || (ydiff == -1 && destinationChar == 'v')   // going up cannot go into a v
                    || (ydiff == 1 && destinationChar == '^')) { // going down cannot go into a ^
                return false;
            }
        }
        return true;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        prepopulateDistanceCache(matrix, true);

        Point startingPoint = matrix.findCharacterLocationsInRow(0, '.').get(0);
        Point endingPoint = matrix.findCharacterLocationsInRow(matrix.getHeight() - 1, '.').get(0);

        long longestPath = findLongestPath(matrix, startingPoint, endingPoint, Collections.emptySet(), true);

        return longestPath;
    }
}
