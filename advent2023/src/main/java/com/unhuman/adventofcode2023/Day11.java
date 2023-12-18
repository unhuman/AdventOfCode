package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.InspectionMatrix;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day11 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    private long gapMultiplier = 1000000;

    public Day11() {
        super(2023, 11, regex1, regex2);
    }

    public Day11(String filename) {
        super(filename, regex1, regex2);
    }

    public void setGapMultiplier(long gapMultiplier) {
        this.gapMultiplier = gapMultiplier;
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix matrix = new Matrix(configGroup, InspectionMatrix.DataType.CHARACTER);

        List<List<Character>> directMatrixForExpansion = matrix.getDirectAccess();

        int width = matrix.getWidth();
        for (int y = directMatrixForExpansion.size() - 1; y >= 0; --y) {
            List<Character> line = directMatrixForExpansion.get(y);
            long columnCount = line.stream().filter(character -> character.equals('.')).count();
            if (columnCount == width) {
                directMatrixForExpansion.add(y, new ArrayList<>(line));
            }
        }

        int height = matrix.getHeight();
        for (int x = width - 1; x >= 0; --x) {
            int finalX = x;
            long lineCount = directMatrixForExpansion.stream().filter(line -> line.get(finalX).equals('.')).count();
            if (lineCount == height) {
                directMatrixForExpansion.forEach(line -> line.add(finalX, '.'));
            }
        }

        long total = 0L;
        List<Point> points = matrix.findCharacterLocations('#');
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i; j < points.size(); j++) {
                Point p1 = points.get(i);
                Point p2 = points.get(j);
                total += Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
            }
        }
        return total;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix matrix = new Matrix(configGroup, InspectionMatrix.DataType.CHARACTER);

        List<List<Character>> directMatrixForExpansion = matrix.getDirectAccess();

        Set<Integer> rowsToMulitply = new HashSet<>();
        Set<Integer> columnsToMulitply = new HashSet<>();

        int width = matrix.getWidth();
        for (int y = directMatrixForExpansion.size() - 1; y >= 0; --y) {
            List<Character> line = directMatrixForExpansion.get(y);
            long columnCount = line.stream().filter(character -> character.equals('.')).count();
            if (columnCount == width) {
                rowsToMulitply.add(y);
            }
        }

        int height = matrix.getHeight();
        for (int x = width - 1; x >= 0; --x) {
            int finalX = x;
            long lineCount = directMatrixForExpansion.stream().filter(line -> line.get(finalX).equals('.')).count();
            if (lineCount == height) {
                columnsToMulitply.add(x);
            }
        }

        long total = 0L;
        List<Point> points = matrix.findCharacterLocations('#');
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i; j < points.size(); j++) {
                Point p1 = points.get(i);
                Point p2 = points.get(j);
                for (int x = Math.min(p1.x, p2.x); x < Math.max(p1.x, p2.x); x++) {
                    total += (columnsToMulitply.contains(x)) ? gapMultiplier : 1;
                }
                for (int y = Math.min(p1.y, p2.y); y < Math.max(p1.y, p2.y); y++) {
                    total += (rowsToMulitply.contains(y)) ? gapMultiplier : 1;
                }
            }
        }
        return total;
    }
}
