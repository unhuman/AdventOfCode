package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;
import com.unhuman.adventofcode.aoc_framework.utility.PointHelper;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Day20 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    private enum JumpState { NOT_JUMPED, JUMPED }

    public Day20() {
        super(2024, 20, regex1, regex2);
    }

    public Day20(String filename) {
        super(filename, regex1, regex2);
    }

    long savings = 100;

    public void setSavings(long savings) {
        this.savings = savings;
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        Matrix maze = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        System.out.println(maze.eliminateDeadEnds('.', '#'));

        long bestScore = maze.findShortestPathDijikstra('S', 'E', '#');

        List<Integer> bestScoresWithJump = findShortestPathsWithJump(maze, 'S', 'E', '#',
                bestScore - savings);

//        Long bestScoresWithJumpDijikstra = findShortestPathDijikstraWithJump(maze, 'S', 'E', '#');


        return (long) bestScoresWithJump.size();
    }

    Long totalSizes = 0L;
    private List<Integer> findPathsInternalWithJump(Matrix maze, Point startingLocation, Point endingLocation,
                                          Character wallChar, Set<Point> visitedLocations,
                                          long lowerThanScore, JumpState jumpState) {
        // if we've already found a better score - we're done here
        if (visitedLocations.size() >= lowerThanScore) {
            return Collections.emptyList();
        }

        // we found the end, so - we're good here
        if (startingLocation.equals(endingLocation)) {
            return (jumpState == JumpState.JUMPED) ? Collections.singletonList(visitedLocations.size()) : Collections.emptyList();
        }

        // get next locations to navigate
        List<Integer> scores = new ArrayList<>();
        List<Matrix.Direction> nextNavigations = maze.getNextNavigation(startingLocation, false,
                (jumpState == JumpState.JUMPED) ? '#' : '!'); // nonexistent wall

        // track that we visited this location
        visitedLocations.add(startingLocation);
        totalSizes += visitedLocations.size();
        System.out.println("Current depth: " + visitedLocations.size() + " TotalSize: " + totalSizes); // 9385278 9389611

        for (Matrix.Direction nextNavigation : nextNavigations) {
            Point nextLocation = PointHelper.addPoints(startingLocation, nextNavigation.getDirection());
            if (visitedLocations.contains(nextLocation)) {
                continue;
            }

            // if we've already jumped - we're done here.
            Character nextChar = maze.getValue(nextLocation);

            scores.addAll(findPathsInternalWithJump(maze, nextLocation, endingLocation, wallChar,
                    new HashSet<>(visitedLocations), lowerThanScore,
                    (nextChar.equals(wallChar)) ? JumpState.JUMPED : jumpState));
        }
        return scores;
    }


    public List<Integer> findShortestPathsWithJump(Matrix maze, Point startingLocation, Point endingLocation,
                                                Character wallChar, Long lowerThanScore) {
        return findPathsInternalWithJump(maze, startingLocation, endingLocation,
                wallChar, new HashSet<>(), lowerThanScore, JumpState.NOT_JUMPED);
    }

    public List<Integer> findShortestPathsWithJump(Matrix maze, Character startingChar, Character endingChar,
                                                Character wallChar, Long lowerThanScore) {
        Point startingLocation = maze.getCharacterLocations(startingChar).get(0);
        Point endingLocation = maze.getCharacterLocations(endingChar).get(0);

        return findShortestPathsWithJump(maze, startingLocation, endingLocation, wallChar, lowerThanScore);
    }



    public int findShortestPathDijikstraWithJump(Matrix maze, Point start, Point finish, Character wallChar) {
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
        pq.offer(new VisitInfo(start, 0, false));

        // iterate while there are cells to visit
        while (!pq.isEmpty()) {
            // remove the cell with the smallest distance from the priority queue
            VisitInfo cell = pq.poll();
            Point currentPoint = cell.point();
            int distance = cell.distance();
            boolean hasJumped = cell.hasJumped();

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
            for (Matrix.Direction direction: maze.getNextNavigation(currentPoint, false, (hasJumped) ? wallChar : '!')) {
                Point nextPoint = PointHelper.addPoints(currentPoint, direction.getDirection());
                if (!visited.contains(nextPoint)) {
                    int newDistance = distance + 1;
                    if (newDistance < dist[nextPoint.x][nextPoint.y]) {
                        dist[nextPoint.x][nextPoint.y] = newDistance;
                        pq.offer(new VisitInfo(nextPoint, newDistance, hasJumped || maze.getValue(nextPoint) == wallChar));
                    }
                }
            }
        }

        // if we couldn't reach the end cell, return -1
        return -1;
    }

    public long findShortestPathDijikstraWithJump(Matrix maze, Character startingChar, Character endingChar,
                                          Character wallChar) {
        Point startingLocation = maze.getCharacterLocations(startingChar).get(0);
        Point endingLocation = maze.getCharacterLocations(endingChar).get(0);

        return findShortestPathDijikstraWithJump(maze, startingLocation, endingLocation, wallChar);
    }


    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        return 2;
    }

    public record VisitInfo(Point point, Integer distance, boolean hasJumped) {
    }
}
