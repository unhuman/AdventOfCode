package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;
import com.unhuman.adventofcode.aoc_framework.utility.PointHelper;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day15 extends InputParser {
    private static final String regex1 = "([#\\.O@])";
    private static final String regex2 = "([\\^v<>])";

    public Day15() {
        super(2024, 15, regex1, regex2);
    }

    public Day15(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);
        // easier to assume there's only one group

        List<Character> instructions = new ArrayList<>();
        GroupItem group0 = configGroup1.get(0);
        for (ItemLine line : group0) {
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
                char instruction = line.getChar(itemNum);
                instructions.add(instruction);
            }
        }

        Point robotLocation = matrix.getStartingCharacterLocations('@').get(0);
//        System.out.println(matrix);
        matrix.setValue(robotLocation, '.');
        for (Character instruction: instructions) {
            robotLocation = attemptMove1(matrix, robotLocation, instruction);
//            matrix.setValue(robotLocation, '@');
//            System.out.println(matrix);
//            matrix.setValue(robotLocation, '.');
        }

        return scoreMatrix(matrix, 'O');
    }

    Point attemptMove1(Matrix matrix, Point robotLocation, Character instruction) {
        Point velocity = Matrix.Direction.getDirectionVelocity(instruction);
        Point check = PointHelper.addPoints(robotLocation, velocity);
        while (matrix.isValidLocation(check) && !matrix.getValue(check).equals('#')) {
            Point priorLocation = robotLocation;
            if (matrix.getValue(check).equals('.')) {
                for (Point move = check; !move.equals(robotLocation); move = priorLocation) {
                    priorLocation = PointHelper.subtract(move, velocity);
                    matrix.setValue(move, matrix.getValue(priorLocation));
                }
                return PointHelper.addPoints(robotLocation, velocity);
            }
            check = PointHelper.addPoints(check, velocity);
        }
        // We didn't find anything, stay where we are.
        return robotLocation;
    }

    // Return null if blocked
    // return empty list if can move
    // return items if needs further inspection
    List<Set<Pair<Point, Character>>> canBump(Matrix matrix, Set<Pair<Point, Character>> lookAt, Point velocity) {
        // velocity will always be up / down
        Set<Pair<Point, Character>> nextRound = new HashSet<>();

        for (Pair<Point, Character> item: lookAt) {
            Point lookUp = PointHelper.addPoints(item.getLeft(), velocity);
            Character aboveCharacter = matrix.getValue(lookUp);
            if (aboveCharacter.equals('.')) {
                continue;
            } else if (aboveCharacter.equals('#')) {
                return null;
            } else if (aboveCharacter.equals('[')) {
                nextRound.add(new Pair(new Point(lookUp), aboveCharacter));
                nextRound.add(new Pair(new Point(PointHelper.addPoints(lookUp, Matrix.Direction.RIGHT.getDirection())), ']'));
            } else if (aboveCharacter.equals(']')) {
                nextRound.add(new Pair(new Point(lookUp), aboveCharacter));
                nextRound.add(new Pair(new Point(PointHelper.addPoints(lookUp, Matrix.Direction.LEFT.getDirection())), '['));
            }
        }

        if (nextRound.isEmpty()) {
            return Collections.emptyList();
        }

        List<Set<Pair<Point, Character>>> recursive = canBump(matrix, nextRound, velocity);
        if (recursive == null) {
            return null;
        }

        List<Set<Pair<Point, Character>>> returns = new ArrayList<>(recursive);
        returns.add(nextRound);

        return returns;
    }

    Point attemptMove2(Matrix matrix, Point robotLocation, Character instruction) {
        // we can always use the old rules to move left or right - or up when the character is going to be a .
        Point velocity = Matrix.Direction.getDirectionVelocity(instruction);
        if (instruction.equals('<') || instruction.equals('>')
                || matrix.getValue(PointHelper.addPoints(robotLocation, velocity)).equals('.')) {
            return attemptMove1(matrix, robotLocation, instruction);
        }

        // build up a stack of things that need to move
        Set<Pair<Point, Character>> check = new HashSet<>();
        check.add(new Pair<>(robotLocation, '@'));

        List<Set<Pair<Point, Character>>> operations = canBump(matrix, check, velocity);

        // We didn't find anything, stay where we are.
        if (operations == null) {
            return robotLocation;
        }

        operations.forEach(operation -> {
            operation.forEach(action -> {
                Point from = action.getLeft();
                Character character = action.getRight();

                Point destination = PointHelper.addPoints(from, velocity);
                matrix.setValue(destination, character);
                matrix.setValue(from, '.');
            });
        });

        return PointHelper.addPoints(robotLocation, velocity);
    }

    Long scoreMatrix(Matrix matrix, Character scoreCharacter) {
        long score = 0L;

        List<Point> points = matrix.getCharacterLocations(scoreCharacter);
        for (Point point: points) {
            score += point.x + point.y * 100L;
        }

        return score;
    }

    Matrix doubleWideMatrix(Matrix matrix) {
        Matrix newMatrix = new Matrix(matrix.getWidth() * 2, matrix.getHeight(), Matrix.DataType.CHARACTER);
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                Character fatten = matrix.getValue(x, y);
                switch(fatten) {
                    case '#':
                    case '.':
                        newMatrix.setValue(x * 2, y, fatten);
                        newMatrix.setValue(x * 2 + 1, y, fatten);
                        break;
                    case 'O':
                        newMatrix.setValue(x * 2, y, '[');
                        newMatrix.setValue(x * 2 + 1, y, ']');
                }
            }
        }
        return newMatrix;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        List<Character> instructions = new ArrayList<>();
        GroupItem group0 = configGroup1.get(0);
        for (ItemLine line : group0) {
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
                char instruction = line.getChar(itemNum);
                instructions.add(instruction);
            }
        }

        Point robotLocation = matrix.getStartingCharacterLocations('@').get(0);
        matrix.setValue(robotLocation, '.');

        // double wide the matrix and adjust the robot location
        matrix = doubleWideMatrix(matrix);
        robotLocation = new Point(robotLocation.x * 2, robotLocation.y);

//        matrix.setValue(robotLocation, '@');
//        System.out.println(matrix);
//        matrix.setValue(robotLocation, '.');

        for (Character instruction: instructions) {
            robotLocation = attemptMove2(matrix, robotLocation, instruction);

//            matrix.setValue(robotLocation, '@');
//            System.out.println(matrix);
//            matrix.setValue(robotLocation, '.');
        }

        return scoreMatrix(matrix, '[');
    }
}
