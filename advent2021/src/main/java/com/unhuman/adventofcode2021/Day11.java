package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.InspectionMatrix;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;

public class Day11 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    public Day11() {
        super(2021, 11, regex1, regex2);
    }

    public Day11(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix<Integer> matrix = new Matrix<>(configGroup, InspectionMatrix.SupportedType.INTEGER);

        return 1;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        return 2;
    }
}
