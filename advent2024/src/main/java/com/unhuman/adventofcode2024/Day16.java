package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;
import com.unhuman.adventofcode.aoc_framework.utility.PointHelper;

import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
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
    // 101496 too high

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix maze = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        Point start = maze.getCharacterLocations('S').get(0);
        Point end = maze.getCharacterLocations('E').get(0);

        shortCircuits = new HashSet<>();
        return findShortestPathDijikstra(maze, start, Matrix.Direction.RIGHT, end, '#');
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

    public record VisitInfo(Point point, Matrix.Direction direction, Integer distance) {
    }

    /** based off Matrix.findShortestPathDijikstra */
    public int findShortestPathDijikstra(Matrix maze, Point start, Matrix.Direction direction, Point finish, Character wallChar) {
        // 2D arrays representing the shortest distances and visited cells
        int[][] dist = new int[maze.getWidth()][maze.getHeight()];
        HashSet<Point> visited = new HashSet<>();

        // initialize all distances to infinity except the starting cell
        for (int i = 0; i < maze.getWidth(); i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[start.x][start.y] = 0;

        // create a priority queue for storing cells to visit
        PriorityQueue<VisitInfo> pq = new PriorityQueue<>(Comparator.comparingInt(VisitInfo::distance));
        pq.offer(new VisitInfo(start, direction, 0));

        // iterate while there are cells to visit
        while (!pq.isEmpty()) {
            // remove the cell with the smallest distance from the priority queue
            VisitInfo cell = pq.poll();
            Point currentPoint = cell.point();
            direction = cell.direction;
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
            for (Matrix.Direction checkDirection: maze.getNextNavigation(currentPoint, false, wallChar)) {
                Point nextPoint = PointHelper.addPoints(currentPoint, checkDirection.getDirection());
                if (!visited.contains(nextPoint)) {
                    int newDistance = distance + 1;
                    if (!checkDirection.equals(direction)) {
                        newDistance += 1000;
                        // check for u-turns
                        if (checkDirection.getDirection().x == -direction.getDirection().x
                                || checkDirection.getDirection().y == -direction.getDirection().y) {
                            newDistance += 1000;
                        }
                    }

                    if (newDistance < dist[nextPoint.x][nextPoint.y]) {
                        dist[nextPoint.x][nextPoint.y] = newDistance;
                        pq.offer(new VisitInfo(nextPoint, checkDirection, newDistance));
                    }
                }
            }
        }

        // if we couldn't reach the end cell, return -1
        return -1;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        shortCircuits = new HashSet<>();
        return 2;
    }
}
