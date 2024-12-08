package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;
import com.unhuman.adventofcode.aoc_framework.utility.ScrollingMatrix;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

public class Day21 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    enum Parity { EVEN, ODD }

    enum TouchType { NONE, EVEN, ODD, BOTH }
    enum Corners { TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT }
    int count1 = 64;
    int count2 = 26501365;
//    int count2 = 131;

    static class TouchedPoint extends Point {
        TouchType touchType;

        TouchedPoint(Point point) {
            this.x = point.x;
            this.y = point.y;
            this.touchType = TouchType.NONE;
        }

        TouchType getTouchType() {
            return touchType;
        }

        void addTouch(int counter) {
            TouchType touch = (counter % 2 == 1) ? TouchType.ODD : TouchType.EVEN;
            if ((touchType != TouchType.BOTH) && (touchType != touch)) {
                if (touchType == TouchType.NONE) {
                    touchType = touch;
                } else {
                    touchType = TouchType.BOTH;
                }
            } // otherwise BOTH or match already - do nothing
        }
    }

    void setCount(int count) {
        this.count1 = count;
        this.count2 = count;
    }

    public Day21() {
        super(2023, 21, regex1, regex2);
    }

    public Day21(String filename) {
        super(filename, regex1, regex2);
    }



    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // Track if we want evens or odd
        LinkedHashMap<Point, TouchedPoint> touchedPoints = getTouchedPoints(configGroup, count1);

        return touchedPoints.values().stream().filter(x -> x.touchType != TouchType.NONE).count();
    }

    private LinkedHashMap<Point, TouchedPoint> getTouchedPoints(ConfigGroup configGroup, int countDesired) {
        Parity parity = (countDesired % 2 == 0) ? Parity.EVEN : Parity.ODD;

        LinkedHashMap<Point, TouchedPoint> touchedPoints = new LinkedHashMap<>();
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        List<Point> priorStepPoints = new ArrayList<>();
        Point startingPoint = matrix.findCharacterLocations('S').get(0);
        priorStepPoints.add(startingPoint);

        HashSet<Point> alreadyProcessedNeighbors = new HashSet<>();

        for (int step = 1; step <= countDesired; step++) {
//            System.out.println("Cycle: " + step);
            // we want to iterate through a copy so we don't double-process entities
            // then we build up a new list to process for the next cycle
            List<Point> processSteps = new ArrayList<>(priorStepPoints);
            priorStepPoints = new ArrayList<>();
            for (Point point: processSteps) {
                // Don't duplicate efforts
                if (alreadyProcessedNeighbors.contains(point)) {
                    continue;
                }
                alreadyProcessedNeighbors.add(point);

                for (Point adjacentPoint: matrix.getAdjacentPoints(point, false)) {
                    if (matrix.getValue(adjacentPoint).equals('#')) {
                        continue;
                    }

                    // we'll need to cycle this point next round
                    priorStepPoints.add(adjacentPoint);

                    // only attribute the even or odd steps (as needed)
                    if ((step % 2 == 0 && parity == Parity.EVEN) || (step % 2 == 1 && parity == Parity.ODD)) {
                        if (!touchedPoints.containsKey(adjacentPoint)) {
                            touchedPoints.put(adjacentPoint, new TouchedPoint(adjacentPoint));
                        }
                        touchedPoints.get(adjacentPoint).addTouch(step);
                    }
                }
            }
        }
        return touchedPoints;
    }

    public Object processInput2Original(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // Track if we want evens or odd
        boolean evens = count2 % 2 == 0;

        LinkedHashMap<Point, TouchedPoint> touchedPoints = new LinkedHashMap<>();
        Matrix matrix = new ScrollingMatrix(configGroup, Matrix.DataType.CHARACTER);


        List<Point> priorStepPoints = new ArrayList<>();
        Point startingPoint = matrix.findCharacterLocations('S').get(0);
        priorStepPoints.add(startingPoint);



        HashSet<Point> alreadyProcessedNeighbors = new HashSet<>();

        for (int step = 1; step <= count2; step++) {
//            System.out.println("Cycle: " + step);
            // we want to iterate through a copy so we don't double-process entities
            // then we build up a new list to process for the next cycle
            List<Point> processSteps = new ArrayList<>(priorStepPoints);
            priorStepPoints = new ArrayList<>();
            for (Point point: processSteps) {
                // Don't duplicate efforts
                if (alreadyProcessedNeighbors.contains(point)) {
                    continue;
                }
                alreadyProcessedNeighbors.add(point);

                for (Point adjacentPoint: matrix.getAdjacentPoints(point, false)) {
                    if (matrix.getValue(adjacentPoint).equals('#')) {
                        continue;
                    }

                    // we'll need to cycle this point next round
                    priorStepPoints.add(adjacentPoint);

                    // only attribute the even or odd steps (as needed)
                    if ((step % 2 == 0 && evens) || (step % 2 == 1 && !evens)) {
                        if (!touchedPoints.containsKey(adjacentPoint)) {
                            touchedPoints.put(adjacentPoint, new TouchedPoint(adjacentPoint));
                        }
                        touchedPoints.get(adjacentPoint).addTouch(step);
                    }
                }
            }
        }

        return touchedPoints.values().stream().filter(x -> x.touchType != TouchType.NONE).count();
    }
    // 598047175204413 - ??
    // 598047219103731 - ??
    // 2392173899891582 too high
    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // Track if we want evens or odd
        boolean evens = count2 % 2 == 0;

        LinkedHashMap<Point, TouchedPoint> touchedPoints = new LinkedHashMap<>();
        Matrix matrix = new ScrollingMatrix(configGroup, Matrix.DataType.CHARACTER);

        // calculate evens and odds
        if (matrix.getWidth() != matrix.getHeight()) {
            throw new RuntimeException("Bad expectation on non-square matrix");
        }
        if (matrix.getWidth() % 2 == 0) {
            throw new RuntimeException("Bad expectation on odd sized matrix");
        }
        count1 = matrix.getWidth(); // odds
        LinkedHashMap<Point, TouchedPoint> oddPointsInBase = getTouchedPoints(configGroup, count1);
        count1 = matrix.getWidth() + 1; // evens - +1 makes this evens
        LinkedHashMap<Point, TouchedPoint> evenPointsInBase = getTouchedPoints(configGroup, count1);

        // calculate how many exact squares we have
        long widthExactSquares = ((count2 - matrix.getWidth() / 2) / matrix.getWidth()) * 2 + 1; // we assume starting in the middle, we'll cross over

        if ((widthExactSquares - 1) % 2 == 1) {
            throw new RuntimeException("This algorithm prefers ending bits being odd");
        }

//        // with the evens ending, we need to find the edges (and points)
//        long evenTopLeftCount = 0L;     //    / <- this area
//        for (int y = 0; y < matrix.getHeight(); y++) {
//            for (int x = matrix.getWidth() - 1; x >= matrix.getWidth() - 1 - y; x++) {
//                Point check = new Point(x, y);
//                evenTopLeftCount += (evenPointsInBase.containsKey(check)) ? 1 : 0;
//            }
//        }
//
//        long evenBottomRightCount = 0L; //         this area -> /
//        for (int y = 0; y < matrix.getHeight(); y++) {
//            for (int x = 0; x < matrix.getWidth() - y; x++) {
//                Point check = new Point(x, y);
//                evenBottomRightCount += (evenPointsInBase.containsKey(check)) ? 1 : 0;
//            }
//        }
//
//        long evenTopRightCount = 0L;    //         this area -> \
//        for (int y = 0; y < matrix.getHeight(); y++) {
//            for (int x = 0; x <= y; x++) {
//                Point check = new Point(x, y);
//                evenTopRightCount += (evenPointsInBase.containsKey(check)) ? 1 : 0;
//            }
//        }
//        long evenBottomLeftCount = 0L;  //    \ <- this area
//        for (int y = 0; y < matrix.getHeight(); y++) {
//            for (int x = matrix.getWidth() - 1; x >= matrix.getWidth() - 1 - y; x--) {
//                Point check = new Point(x, y);
//                evenBottomLeftCount += (evenPointsInBase.containsKey(check)) ? 1 : 0;
//            }
//        }
//
//        long evenTopPoint = 0L;
//        long evenLeftPoint = 0L;
//        long evenRightPoint = 0L;
//        long evenBottomPoint = 0L;
//        // Entire square, plus extra diagonals + extra center
        long evenDiagonal1 = 0L;
        long evenDiagonal2 = 0L;
        long evenCenterValue = 0L;
        for (int xy = 0; xy < matrix.getWidth(); xy++) {
            Point check = new Point(xy, xy);
            evenDiagonal1 += (evenPointsInBase.containsKey(check)) ? 1 : 0;

            Point check2 = new Point(matrix.getWidth() - 1 - xy, xy);
            evenDiagonal2 += (evenPointsInBase.containsKey(check2)) ? 1 : 0;

            if (matrix.getWidth() - 1 - xy == xy) {
                Point check3 = new Point(matrix.getWidth() - 1 - xy, xy);
                evenCenterValue += (evenPointsInBase.containsKey(check3)) ? 1 : 0;
            }
        }

        // Top / Right / Left / Bottom Points +
        long extraGoodness = evenPointsInBase.size();

        long mainArea = 0L;
        for (long i = widthExactSquares; i >= 1; i -= 2) {
            long oddItemsInRow = (i / 2 + 1);
            long evenItemsInRow = (i / 2);
            long pointsInRow = (oddItemsInRow * oddPointsInBase.size()) + evenItemsInRow * evenPointsInBase.size();
            mainArea += pointsInRow;
            // double everything but the middle line
            if (i != widthExactSquares) {
                mainArea += pointsInRow;
                // Add two values (top split + bottom split = two wholes)
                mainArea += evenPointsInBase.size();
                mainArea += evenPointsInBase.size();
                // Add the diagonals for the split values
                mainArea += evenDiagonal1;
                mainArea += evenDiagonal2;
            }
        }
        // add even caps (top, bottom, left, right)
//        mainArea += evenTopPoint + evenLeftPoint + evenRightPoint + evenBottomPoint;
//        mainArea += extraGoodness;
        mainArea += evenPointsInBase.size();
        // add 2 diagonals and center point
        mainArea += evenDiagonal1;
        mainArea += evenDiagonal2;
        mainArea += evenCenterValue;
        return mainArea;
//
//
//        List<Point> priorStepPoints = new ArrayList<>();
//        Point startingPoint = matrix.findCharacterLocations('S').get(0);
//        priorStepPoints.add(startingPoint);
//
//
//
//        HashSet<Point> alreadyProcessedNeighbors = new HashSet<>();
//
//        for (int step = 1; step <= count2; step++) {
////            System.out.println("Cycle: " + step);
//            // we want to iterate through a copy so we don't double-process entities
//            // then we build up a new list to process for the next cycle
//            List<Point> processSteps = new ArrayList<>(priorStepPoints);
//            priorStepPoints = new ArrayList<>();
//            for (Point point: processSteps) {
//                // Don't duplicate efforts
//                if (alreadyProcessedNeighbors.contains(point)) {
//                    continue;
//                }
//                alreadyProcessedNeighbors.add(point);
//
//                for (Point adjacentPoint: matrix.getAdjacentPoints(point, false)) {
//                    if (matrix.getValue(adjacentPoint).equals('#')) {
//                        continue;
//                    }
//
//                    // we'll need to cycle this point next round
//                    priorStepPoints.add(adjacentPoint);
//
//                    // only attribute the even or odd steps (as needed)
//                    if ((step % 2 == 0 && evens) || (step % 2 == 1 && !evens)) {
//                        if (!touchedPoints.containsKey(adjacentPoint)) {
//                            touchedPoints.put(adjacentPoint, new TouchedPoint(adjacentPoint));
//                        }
//                        touchedPoints.get(adjacentPoint).addTouch(step);
//                    }
//                }
//            }
//        }
//
//        return touchedPoints.values().stream().filter(x -> x.touchType != TouchType.NONE).count();
    }
}
