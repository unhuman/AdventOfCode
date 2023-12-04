package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day9 extends InputParser {
    private static final String regex1 = "(\\d)";
    private static final String regex2 = null;

    public Day9() {
        super(2021, 9, regex1, regex2);
    }

    public Day9(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int total = 0;
        for (GroupItem item : configGroup) {
            int rows = item.size();
            int cols = item.get(0).size();
            int matrix[][] = new int[rows][cols];
            for (int y = 0; y < rows; y++) {
                List<String> line = item.get(y);
                for (int x = 0; x < cols; x++) {
                    matrix[y][x] = Integer.parseInt(line.get(x));
                }
            }

            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    Integer lowPoint = checkLowPoint(matrix, x, y);
                    if (lowPoint != null) {
                        total += 1 + lowPoint;
                    }
                }
            }
        }

        return total;
    }

    Integer checkLowPoint(int[][] matrix, int x, int y) {
        int height = matrix.length;
        int width = matrix[0].length;

        int desiredValue = matrix[y][x];

        for (int checkX = x - 1; checkX <= Math.min(x + 1, width - 1); checkX += 2) {
            if (checkX < 0) {
                continue;
            }

            if (matrix[y][checkX] <= desiredValue) {
                return null;
            }
        }
        for (int checkY = y - 1; checkY <= Math.min(y + 1, height - 1); checkY += 2) {
            if (checkY < 0) {
                continue;
            }

            if (matrix[checkY][x] <= desiredValue) {
                return null;
            }
        }

        return desiredValue;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Integer> basins = new ArrayList<>();
        for (GroupItem item : configGroup) {
            int rows = item.size();
            int cols = item.get(0).size();
            int matrix[][] = new int[rows][cols];
            for (int y = 0; y < rows; y++) {
                List<String> line = item.get(y);
                for (int x = 0; x < cols; x++) {
                    matrix[y][x] = Integer.parseInt(line.get(x));
                }
            }

            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < cols; x++) {
                    Integer lowPoint = checkLowPoint(matrix, x, y);
                    if (lowPoint != null) {
                        basins.add(findBasin(matrix, x, y, lowPoint));
                    }
                }
            }
        }

        basins.sort(Collections.reverseOrder());
        basins = basins.subList(0, 3);

        return basins.stream().reduce(1, (a, b) -> a * b);
    }

    int findBasin(int[][] matrix, int x, int y, int expectedValue) {
        return findBasin(matrix, x, y, expectedValue, new HashSet<>());
    }

    int findBasin(int[][] matrix, int x, int y, int expectedValue, Set<Point> seenPoints) {
        if (expectedValue == 9) {
            return 0;
        }

        int height = matrix.length;
        int width = matrix[0].length;

        if (x < 0 || y < 0 || x >= width || y >= height) {
            return 0;
        }

        int foundValue = matrix[y][x];
        if (foundValue < expectedValue || foundValue == 9) {
            return 0;
        }

        Point checkPoint = new Point(x, y);
        if (seenPoints.contains(checkPoint)) {
            return 0;
        }
        seenPoints.add(checkPoint);

        return 1 + findBasin(matrix, x - 1, y, expectedValue + 1, seenPoints)
                + findBasin(matrix, x + 1, y, expectedValue + 1, seenPoints)
                + findBasin(matrix, x, y - 1, expectedValue + 1, seenPoints)
                + findBasin(matrix, x, y + 1, expectedValue + 1, seenPoints);

        // 605070 too low
    }
}
