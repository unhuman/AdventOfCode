package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;
import com.unhuman.adventofcode.aoc_framework.utility.PointHelper;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day20 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    private enum JumpState {NOT_JUMPED, JUMPED}

    public Day20() {
        super(2024, 20, regex1, regex2);
    }

    public Day20(String filename) {
        super(filename, regex1, regex2);
    }

    long requiredSavings = 100;

    public void setRequiredSavings(long requiredSavings) {
        this.requiredSavings = requiredSavings;
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        Matrix maze = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        Map<Point, Integer> distances = getDistanceScores(maze);

        List<Integer> jumpScores = getJumpedDistances(maze, distances);

        return jumpScores.size();
    }

    Map<Point, Integer> getDistanceScores(Matrix maze) {
        Map<Point, Integer> distances = new HashMap<Point, Integer>();

        Point start = maze.getCharacterLocations('S').get(0);
        Point end = maze.getCharacterLocations('E').get(0);

        int distance = 0;
        do {
            distances.put(start, distance++);
            List<Point> checks = maze.getAdjacentPointsAvoidChar(start, false, '#');

            for (Point check : checks) {
                if (distances.containsKey(check)) {
                    continue;
                }
                Character item = maze.getValue(check);
                if (item == '.' || item == 'E' || item == ' ') {
                    start = check;
                    break;
                }
            }
        } while (!start.equals(end));
        distances.put(start, distance);

        return distances;
    }

    List<Integer> getJumpedDistances(Matrix maze, Map<Point, Integer> distances) {
        List<Integer> scores = new ArrayList<>();

        List<Point> jumpPoints = maze.getCharacterLocations('#', true);

        for (Point jumpPoint : jumpPoints) {
            Point above = PointHelper.addPoints(jumpPoint, Matrix.Direction.UP.getDirection());
            Point below = PointHelper.addPoints(jumpPoint, Matrix.Direction.DOWN.getDirection());
            Point left = PointHelper.addPoints(jumpPoint, Matrix.Direction.LEFT.getDirection());
            Point right = PointHelper.addPoints(jumpPoint, Matrix.Direction.RIGHT.getDirection());

            int diff = 0;
            if (distances.containsKey(above) && distances.containsKey(below)) {
                diff = Math.abs(distances.get(below) - distances.get(above)) - 1;
            } else if (distances.containsKey(left) && distances.containsKey(right)) {
                diff = Math.abs(distances.get(right) - distances.get(left)) - 1;
            }
            if (diff >= requiredSavings) {
                scores.add(diff);
            }
        }

        return scores;
    }

    Map<Pair<Point, Point>, Integer> getJumpedDistances2(Matrix maze, Map<Point, Integer> distances, int maxTime) {
        Map<Pair<Point, Point>, Integer> scores = new HashMap<>();

        List<Point> jumpPoints = maze.getCharacterLocations('#', true);

        for (Point jumpPoint : jumpPoints) {
            List<Point> checkPoints = maze.getAdjacentPoints(jumpPoint, false, true);
            for (Point checkStart : checkPoints) {
                if (!distances.containsKey(checkStart)) {
                    continue;
                }
                for (int xOffset = -maxTime; xOffset < maxTime; xOffset++) {
                    for (int yOffset = -(maxTime - Math.abs(xOffset)); yOffset < maxTime - Math.abs(xOffset); yOffset++) {
                        if (xOffset == 0 && yOffset == 0) {
                            continue;
                        }
                        // Ensure this point is in the maze
                        Point checkEnd = PointHelper.addPoints(checkStart, new Point(xOffset, yOffset));
                        if (!maze.isValid(checkEnd, true)) {
                            continue;
                        }
                        List<Point> exits = maze.getAdjacentPoints(checkEnd, false, true);
                        for (Point exit : exits) {
                            if (checkStart.equals(exit)) {
                                continue;
                            }
                            if (distances.containsKey(exit)) {
                                Point cheatDiff = PointHelper.subtract(checkStart, exit);

                                int diff = Math.abs(distances.get(checkStart) - distances.get(exit))
                                        - Math.abs(cheatDiff.x) - Math.abs(cheatDiff.y);

                                Pair<Point, Point> key = new Pair<>(checkStart, exit);

                                // the ordering of points in a pair should be consistent
                                Pair<Point, Point> keyFlip = new Pair<>(exit, checkStart);
                                if (scores.containsKey(keyFlip)) {
                                    key = keyFlip;
                                }

                                if (diff >= requiredSavings && (!scores.containsKey(key) || diff > scores.get(key))) {
                                    scores.put(key, diff);
                                }
                            }
                        }
                    }
                }
            }
        }
        return scores;
    }

    // 995024 too low
    // 1126275 too high
    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        Matrix maze = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        Map<Point, Integer> distances = getDistanceScores(maze);

        Map<Pair<Point, Point>, Integer> jumpScores = getJumpedDistances2(maze, distances, 20);

        System.out.println(jumpScores.values().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));

        return jumpScores.size();
    }
}