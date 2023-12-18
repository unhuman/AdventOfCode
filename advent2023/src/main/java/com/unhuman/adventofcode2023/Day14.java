package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.InspectionMatrix;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;

import java.util.HashMap;
import java.util.Map;

public class Day14 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    public Day14() {
        super(2023, 14, regex1, regex2);
    }

    public Day14(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix matrix = new Matrix(configGroup, InspectionMatrix.DataType.CHARACTER);

        System.out.println("Before:\n" + matrix.toString());

        rollNorth(matrix);
        System.out.println("After N:\n" + matrix.toString());

        long score = getScore(matrix);
        return score;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix matrix = new Matrix(configGroup, InspectionMatrix.DataType.CHARACTER);

        System.out.println("Before:\n" + matrix.toString());

        Map<Integer, Integer> hashCache = new HashMap<>(20000);
        Integer cycles = null;
        for (int i = 0; i < 1000000000; i++) {
            rollNorth(matrix);
            rollWest(matrix);
            rollSouth(matrix);
            rollEast(matrix);

            Integer hashCode = matrix.hashCode();
            if (hashCache.containsKey(hashCode)) {
                if (cycles == null) {
                    System.out.println("iteration: " + i + " for code: " + hashCode + " found prior: hashCode diff = " + (i - hashCache.get(hashCode)));
                    cycles = i - hashCache.get(hashCode);
                    while (i + cycles < 1000000000) {
                        i += cycles;
                    }
                }
            } else {
                hashCache.put(hashCode, i);
            }
        }
        long score = getScore(matrix);
        return score;
    }

    private static long getScore(Matrix matrix) {
        long score = 0;
        for (int row = 0; row < matrix.getHeight(); row++) {
            for (int col = 0; col < matrix.getWidth(); col++) {
                if (matrix.getValue(col, row) == 'O') {
                    score += matrix.getHeight() - row;
                }
            }
        }
        return score;
    }

    private static void rollNorth(Matrix matrix) {
        for (int row = 0; row < matrix.getHeight(); row++) {
            for (int col = 0; col < matrix.getWidth(); col++) {
                char ch = matrix.getValue(col, row);
                if (ch == 'O') {
                    int rowSwap;
                    for (rowSwap = row - 1; rowSwap >= 0; --rowSwap) {
                        char check = matrix.getValue(col, rowSwap);
                        if (check == '.') {
                            matrix.setValue(col, rowSwap, ch);
                            matrix.setValue(col, rowSwap + 1, check);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }

    private static void rollEast(Matrix matrix) {
        for (int col = matrix.getWidth() - 1; col >= 0 ; col--) {
            for (int row = 0; row < matrix.getHeight(); row++) {
                char ch = matrix.getValue(col, row);
                if (ch == 'O') {
                    int colSwap;
                    for (colSwap = col + 1; colSwap < matrix.getWidth(); ++colSwap) {
                        char check = matrix.getValue(colSwap, row);
                        if (check == '.') {
                            matrix.setValue(colSwap, row, ch);
                            matrix.setValue(colSwap - 1, row, check);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }

    private static void rollSouth(Matrix matrix) {
        for (int row = matrix.getHeight() - 1; row >= 0; row--) {
            for (int col = 0; col < matrix.getWidth(); col++) {
                char ch = matrix.getValue(col, row);
                if (ch == 'O') {
                    int rowSwap;
                    for (rowSwap = row + 1; rowSwap < matrix.getHeight(); ++rowSwap) {
                        char check = matrix.getValue(col, rowSwap);
                        if (check == '.') {
                            matrix.setValue(col, rowSwap, ch);
                            matrix.setValue(col, rowSwap - 1, check);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }

    private static void rollWest(Matrix matrix) {
        for (int col = 0; col < matrix.getWidth(); col++) {
            for (int row = 0; row < matrix.getHeight(); row++) {
                char ch = matrix.getValue(col, row);
                if (ch == 'O') {
                    int colSwap;
                    for (colSwap = col - 1; colSwap >= 0; --colSwap) {
                        char check = matrix.getValue(colSwap, row);
                        if (check == '.') {
                            matrix.setValue(colSwap, row, ch);
                            matrix.setValue(colSwap + 1, row, check);
                        } else {
                            break;
                        }
                    }
                }
            }
        }
    }
}
