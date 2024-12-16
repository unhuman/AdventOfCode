package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;
import com.unhuman.adventofcode.aoc_framework.utility.PointHelper;

import java.awt.Point;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class Day16 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    Set<Pair<Point, Point>> shortCircuits = new HashSet<>();

    public Day16() {
        super(2024, 16, regex1, regex2);
    }

    public Day16(String filename) {
        super(filename, regex1, regex2);
    }

    // 2094 too low
    // 134438 too high
    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix maze = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        Point start = maze.getCharacterLocations('S').get(0);
        Point end = maze.getCharacterLocations('E').get(0);

        shortCircuits = new HashSet<>();
        return findPath(maze, start, end);
    }

    Long findPath(Matrix maze, Point start, Point end, Matrix.Direction currentDirection,
                  Set<Point> visitedPoints, final long currentScore, AtomicLong lowestScoreSoFar) {
//        if (shortCircuits.contains(new Pair<>(start, end))) {
//            return null;
//        }

        // No sense going deeper if we've already found a better path
        if (currentScore >= lowestScoreSoFar.get()) {
            return null;
        }

        // don't crash into walls (don't need this check since we're avoiding walls in the recursion
//        if (maze.getValue(start).equals('#')) {
//            return null;
//        }

        // prevent looping
        if (visitedPoints.contains(start)) {
            return null;
        }

        // We found the end
        if (start.equals(end)) {
            lowestScoreSoFar.set(currentScore);
            return currentScore;
        }

        AtomicLong bestScore = new AtomicLong(Long.MAX_VALUE);
        maze.getNextNavigation(start, false, '#').forEach(nextNav -> {
            // update state for recursion
            Set<Point> nextVisitedPoints = new HashSet<>(visitedPoints);
            nextVisitedPoints.add(start);
            long nextScore = currentScore + (nextNav.equals(currentDirection) ? 1L : 1001L);

            Point next = PointHelper.addPoints(start, nextNav.getDirection());
            Long score = findPath(maze, next, end, nextNav, nextVisitedPoints, nextScore, lowestScoreSoFar);
//            if (score == null) {
//                shortCircuits.add(new Pair<>(start, next));
//            }
            if (score != null && score < bestScore.get()) {
                System.out.println("found lower score: " + score);
                bestScore.set(score);
            }
        });
        return bestScore.get();
    }

    private long findPath(Matrix maze, Point start, Point end) {
        maze.eliminateDeadEnds('.', '#');
        Set<Point> visitedPoints = new HashSet<>();
        return findPath(maze, start, end, Matrix.Direction.RIGHT, visitedPoints, 0L, new AtomicLong(Long.MAX_VALUE));
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        shortCircuits = new HashSet<>();
        return 2;
    }
}
