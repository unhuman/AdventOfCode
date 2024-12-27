package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;

public class Day8 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    int width = 25;
    int height = 6;

    public void overrideDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Day8() {
        super(2019, 8, regex1, regex2);
    }

    public Day8(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group

        long fewestZeros = Long.MAX_VALUE;
        Long bestScore = 0L;

        GroupItem item = configGroup.get(0);

        int frame = 0;
        try {
            while (true) {
                long zeros = 0;
                long ones = 0;
                long twos = 0;
                for (int i = 0; i < width * height; i++) {
                    char character = item.get(0).getChar(frame * width * height + i);
                    switch (character) {
                        case '0':
                            zeros++;
                            break;
                        case '1':
                            ones++;
                            break;
                        case '2':
                            twos++;
                            break;
                    }
                }

                System.out.println("Layer " + frame + " has " + zeros + " zeros with " + ones + " ones and " + twos + " twos");
                if (zeros < fewestZeros) {
                    fewestZeros = zeros;
                    System.out.println("Layer " + frame + " is the current best");
                    bestScore = ones * twos;
                }
                frame++;
            }
        } catch (Exception e) {
            // do nothing, but we want to end
        }

        return bestScore;
    }

    static final char BLACK = '0';
    static final char WHITE = '1';
    static final char TRANSPARENT = '2';

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix matrix = new Matrix(width, height, Matrix.DataType.CHARACTER, ' ');
        GroupItem item = configGroup.get(0);
        int frame = -1;
        try {
            while (true) {
                frame++;
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        int i = x + y * width;
                        char character = item.get(0).getChar(frame * width * height + i);
                        if (matrix.getValue(x, y).equals(' '))
                        {
                            switch (character) {
                                case '0':
                                    matrix.setValue(x, y, '.');
                                    break;
                                case '1':
                                    matrix.setValue(x, y, '#');
                                    break;
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            // nothing to do here
        }
        return matrix.toString().trim();
    }
}
