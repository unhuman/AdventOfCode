package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;

public class Day4 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    public Day4() {
        super(2024, 4, regex1, regex2);
    }

    public Day4(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        return findXMAS(matrix);
    }

    long findXMAS(Matrix matrix) {
        long count = 0;
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                if (matrix.getValue(x, y).equals('X')) {
                    for (int xDir = -1; xDir <= 1; xDir++) {
                        for (int yDir = -1; yDir <= 1; yDir++) {
                            if (xDir == 0 && yDir == 0) {
                                continue;
                            }
                            count += findMAS(matrix, x, y, xDir, yDir);
                        }
                    }
                }
            }
        }

        return count;
    }

    long findMAS(Matrix matrix, int x, int y, int xDir, int yDir) {
        for (char c: "MAS".toCharArray()) {
            x += xDir;
            y += yDir;
            Character check = matrix.getValue(x, y);
            if (check == null || !check.equals(c)) {
                return 0;
            }
        }
        return 1;
    }

    long findMASX(Matrix matrix) {
        long count = 0;
        for (int y = 1; y < matrix.getHeight() -1; y++) {
            for (int x = 1; x < matrix.getWidth() - 1; x++) {
                if (matrix.getValue(x, y).equals('A')) {
                    Character topLeft = matrix.getValue(x - 1 , y - 1);
                    Character bottomLeft = matrix.getValue(x - 1 , y + 1);
                    Character topRight = matrix.getValue(x + 1 , y - 1);
                    Character bottomRight = matrix.getValue(x + 1 , y + 1);
                    if (!(topLeft.equals('M') && bottomRight.equals('S') || topLeft.equals('S') && bottomRight.equals('M'))) {
                        continue;
                    }
                    if (!(bottomLeft.equals('M') && topRight.equals('S') || bottomLeft.equals('S') && topRight.equals('M'))) {
                        continue;
                    }

                    count++;
                }
            }
        }

        return count;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        return findMASX(matrix);
    }
}
