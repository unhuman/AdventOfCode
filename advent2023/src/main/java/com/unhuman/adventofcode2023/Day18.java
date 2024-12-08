package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;
import com.unhuman.adventofcode.aoc_framework.utility.SparseMatrix;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Day18 extends InputParser {
    private static final String regex1 = "([UDLR]) (\\d+) \\(#(.*?)\\)";
    private static final String regex2 = null;

    public Day18() {
        super(2023, 18, regex1, regex2);
    }

    public Day18(String filename) {
        super(filename, regex1, regex2);
    }

    static class Data {
        String color;
        Data(String color) {
            this.color = color;
        }
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Pair<Long, Long>> pairs = new ArrayList<>();
        Pair<Long, Long> starting = new Pair(0L, 0L);
        pairs.add(starting);

        SparseMatrix<Data> dugout = new SparseMatrix<>();

        int x = 0;
        int y = 0;

        Point point = new Point(x, y);
        dugout.put(point, new Data("000000"));

        int populatedPoints2 = 0;

        for (ItemLine line : configGroup.get(0)) {
            char direction = line.get(0).charAt(0);
            int distance = Integer.parseInt(line.get(1));
            populatedPoints2 += distance;
            String hexColor = line.get(2);

            for (int i = 0; i < distance; i++) {
                switch (direction) {
                    case 'U' -> y--;
                    case 'D' -> y++;
                    case 'L' -> x--;
                    case 'R' -> x++;
                }
                point = new Point(x, y);
                dugout.put(point, new Data(hexColor));

            }
            long xLong = x;
            long yLong = y;
            Pair<Long, Long> addPair = new Pair<>(xLong, yLong);
            pairs.add(addPair);
        }
        pairs.add(new Pair<Long, Long>(0L, 0L));


        Point topLeft = dugout.getTopLeft();
        Point bottomRight = dugout.getBottomRight();

        int xOffset = 0 - topLeft.x;
        int yOffset = 0 - topLeft.y;

        // Copy all this data to a matrix
        int populatedPoints = 0;
        Matrix matrix = new Matrix(bottomRight.x + xOffset + 1, bottomRight.y + yOffset + 1, Matrix.DataType.CHARACTER);
        for (int row = topLeft.y; row <= bottomRight.y; row++) {
            for (int col = topLeft.x; col <= bottomRight.x; col++) {
                if (dugout.get(col, row) != null) {
                    populatedPoints++;
                    matrix.setValue(col + xOffset, row + yOffset, '#');
                }
            }
        }

        System.out.println("Populated: " + populatedPoints);
        System.out.println("Populated2: " + populatedPoints2);

        // fill the outer borders so we can calculate anything not filled as internal
        int filledPoints = 0;
        for (int col = 0; col < matrix.getWidth(); col++) {
            filledPoints += matrix.floodFill(col, 0, ' ', '#');
            filledPoints += matrix.floodFill(col, matrix.getHeight() - 1, ' ', '#');
        }
        for (int row = 0; row < matrix.getHeight(); row++) {
            filledPoints += matrix.floodFill(0, row, ' ', '#');
            filledPoints += matrix.floodFill(matrix.getWidth() -1, row, ' ', '#');
        }

        int totalDugout = matrix.getHeight() * matrix.getWidth() - filledPoints;

        System.out.println("Matrix size: " + matrix.getWidth() * matrix.getHeight());

        System.out.println("Outer Filled: " + filledPoints);
        System.out.println("Dugout Size " + totalDugout);

        long area = calculateArea(pairs);
        System.out.println("Calculated area: " + area);

        long picksNumber = picksTheorem(area, populatedPoints);
        System.out.println("Picks points: " + picksNumber);

        long totalArea = populatedPoints + picksNumber;
        System.out.println("Total calculated from border + picks points: " + totalArea);

        return totalDugout;
    }


    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        long populatedPoints = 0;
        List<Pair<Long, Long>> points = new ArrayList<>();
        Pair<Long, Long> recent = new Pair(0L, 0L);
        points.add(recent);
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                String hexValue = line.get(2);
                char checkDirection = hexValue.charAt(hexValue.length() - 1);
                long distance = Long.parseLong(hexValue.substring(0, hexValue.length() - 1), 16);

                populatedPoints += distance;

                char direction = ' ';
                switch (checkDirection) {
                    case '0' -> direction = 'R';
                    case '1' -> direction = 'D';
                    case '2' -> direction = 'L';
                    case '3' -> direction = 'U';
                }

                long x = recent.getLeft();
                long y = recent.getRight();
                switch (direction) {
                    case 'U' -> y -= distance;
                    case 'D' -> y += distance;
                    case 'L' -> x -= distance;
                    case 'R' -> x += distance;
                }
                Pair<Long, Long> next = new Pair<>(x, y);
                points.add(next);
                recent = next;
            }
//                points.add(new Pair<>(0L, 0L));
        }

        long area = calculateArea(points);
        long picksNumber = picksTheorem(area, populatedPoints);

        return populatedPoints + picksNumber;
    }

    // from: https://www.youtube.com/watch?v=0KjG8Pg6LGk&t=204s
    static long calculateArea(List<Pair<Long, Long>> points) {
        long total = 0;
        Pair<Long, Long> prior = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            long left = prior.getLeft();
            long right = prior.getRight();

            Pair<Long, Long> next = points.get(i);
            long nextLeft = next.getRight();
            long nextRight = next.getLeft();
            left *= nextLeft;
            right = - (right * nextRight);

            prior = next;

            total += left;
            total += right;
        }

        return total / 2;
    }

    long picksTheorem(long calculatedArea, long edgePoints) {
        // picks theorem
        // Well Pick Theorem states that: S = I + B / 2 - 1
        // Where
        // S — polygon area,
        // I — number of points strictly inside polygon
        // B — Number of points on boundary.
        // from: https://codeforces.com/blog/entry/65888
        return -((edgePoints / 2) - 1 - calculatedArea);
    }
}
