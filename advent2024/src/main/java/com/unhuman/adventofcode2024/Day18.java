package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;

import java.awt.Point;

public class Day18 extends InputParser {
    private static final String regex1 = "(\\d+),(\\d+)";
    private static final String regex2 = null;

    public Day18() {
        super(2024, 18, regex1, regex2);
    }

    public Day18(String filename) {
        super(filename, regex1, regex2);
    }

    Matrix matrix = new Matrix(71, 71, Matrix.DataType.CHARACTER, '.');
    long limit = 1024;

    /** for Testing only */
    public void resetMatrix(int width, int height) {
        matrix = new Matrix(width, height, Matrix.DataType.CHARACTER, '.');
    }
    public void resetLimit(long limit) {
        this.limit = limit;
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);
        long counter = 0L;
        for (ItemLine line : group0) {
            if (counter++ == limit) {
                break;
            }
            int x = line.getInt(0);
            int y = line.getInt(1);
            matrix.setValue(x, y, '#');
        }

//        System.out.println(matrix);

        long result = matrix.findShortestPathDijikstra(new Point(0, 0),
                new Point(matrix.getWidth() - 1, matrix.getHeight() - 1), '#');

        return result;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);
        for (ItemLine line : group0) {
            int x = line.getInt(0);
            int y = line.getInt(1);
            matrix.setValue(x, y, '#');

//            System.out.println(x + "," + y);
//            System.out.println(matrix);

            long result = matrix.findShortestPathDijikstra(new Point(0, 0),
                    new Point(matrix.getWidth() - 1, matrix.getHeight() - 1), '#');

            if (result < 0) {
                return x + "," + y;
            }
        }

        return null;

    }
}
