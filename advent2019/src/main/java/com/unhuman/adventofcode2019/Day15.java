package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;
import com.unhuman.adventofcode.aoc_framework.utility.PointHelper;
import com.unhuman.adventofcode.aoc_framework.utility.SparseMatrix;

import java.awt.Point;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Day15 extends InputParser {
    private static final String regex1 = "(-?\\d+),?";
    private static final String regex2 = null;

    enum Direction {
        NORTH(1),
        SOUTH(2),
        WEST(3),
        EAST(4);

        private final int value;
        Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public Day15() {
        super(2019, 15, regex1, regex2);
    }

    public Day15(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        IntCodeParser parser = new IntCodeParser(item.get(0));

        SparseMatrix<Character> matrix = new SparseMatrix<>();

        return navigate(parser, matrix, new Point(0, 0), Collections.emptySet());
    }

    int navigate(IntCodeParser parser, SparseMatrix<Character> matrix,
                 Point position, Set<Point> visitedPoints) {
        int bestScore = Integer.MAX_VALUE - 1;

//        System.out.println(position);

        // Prevent cycles
        if (visitedPoints.contains(position)) {
            return bestScore;
        }
        visitedPoints = new HashSet<>(visitedPoints);
        visitedPoints.add(position);

        // this place is guaranteed to be open
        matrix.put(position, '.');
//        System.out.println(matrix.toString());

        for (Direction direction: Direction.values()) {
            Pair<Point, Direction> nextCheckInfo = getNextPositionAndBacktrackInfo(position, direction);

            // don't recurse into a point we've seen
            if (visitedPoints.contains(nextCheckInfo.getLeft())) {
                continue;
            }

            parser.setInput(direction.value);
            parser.stepUntilOutput();

            switch (Integer.parseInt(parser.getOutput())) {
                case 0:
                    visitedPoints.add(nextCheckInfo.getLeft());
                    // wall
                    matrix.put(nextCheckInfo.getLeft(), '#');
                    // backtrack is not necessary (position is unchanged)
                    continue;
                case 1:
                    // recurse
                    int checkScore = navigate(parser, matrix, nextCheckInfo.getLeft(), visitedPoints);

                    if (checkScore + 1 < bestScore) {
                        bestScore = checkScore + 1;
                    }
                    break;
                case 2:
                    // found it
                    matrix.put(nextCheckInfo.getLeft(), '$');

                    return 1;
            }

            // backtrack
            parser.setInput(nextCheckInfo.getRight().value);
            parser.stepUntilOutput();
        }
        return bestScore;
    }

    Pair<Point, Direction> getNextPositionAndBacktrackInfo(Point point, Direction direction) {
        return switch (direction) {
            case NORTH ->
                    new Pair<>(PointHelper.addPoints(point, Matrix.Direction.UP.getDirection()), Direction.SOUTH);
            case SOUTH ->
                    new Pair<>(PointHelper.addPoints(point, Matrix.Direction.DOWN.getDirection()), Direction.NORTH);
            case WEST ->
                    new Pair<>(PointHelper.addPoints(point, Matrix.Direction.LEFT.getDirection()), Direction.EAST);
            case EAST ->
                    new Pair<>(PointHelper.addPoints(point, Matrix.Direction.RIGHT.getDirection()), Direction.WEST);
        };
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        return 2;
    }
}
