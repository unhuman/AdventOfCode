package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Sparse3DMatrix;

import java.util.Set;

public class Day18 extends InputParser {
    private static final String regex1 = "(\\d+),(\\d+),(\\d+)";
    private static final String regex2 = null;
    Sparse3DMatrix<Integer> matrix;
    Sparse3DMatrix.Point3D minPoint;
    Sparse3DMatrix.Point3D maxPoint;

    public Day18() {
        super(2022, 18, regex1, regex2);
    }

    public Day18(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        matrix = new Sparse3DMatrix<>();
        for (int groupItemIdx = 0; groupItemIdx < dataItems1.size(); groupItemIdx++) {
            GroupItem item = dataItems1.get(groupItemIdx);
            for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
                ItemLine line = item.get(lineIdx);
                matrix.put(Integer.parseInt(line.get(0)), Integer.parseInt(line.get(1)), Integer.parseInt(line.get(2)), 6);
            }
        }

        minPoint = matrix.getMinCorner();
        maxPoint = matrix.getMaxCorner();

        Set<Sparse3DMatrix.Point3D> points = matrix.getAllPopulatedPoints();
        for (Sparse3DMatrix.Point3D point : points) {
            for (Sparse3DMatrix.Point3D point2 : points) {
                if (point.areAdjacent(point2)) {
                    matrix.put(point, matrix.get(point).intValue() - 1);
                }
            }
        }

        int total = 0;
        for (Sparse3DMatrix.Point3D point : points) {
            total += matrix.get(point);
        }

        return total;
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        matrix = new Sparse3DMatrix<>();
        for (int groupItemIdx = 0; groupItemIdx < dataItems1.size(); groupItemIdx++) {
            GroupItem item = dataItems1.get(groupItemIdx);
            for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
                ItemLine line = item.get(lineIdx);
                matrix.put(Integer.parseInt(line.get(0)), Integer.parseInt(line.get(1)), Integer.parseInt(line.get(2)), 6);
            }
        }

        minPoint = matrix.getMinCorner();
        maxPoint = matrix.getMaxCorner();

        Set<Sparse3DMatrix.Point3D> points = matrix.getAllPopulatedPoints();
        for (Sparse3DMatrix.Point3D point : points) {
            for (Sparse3DMatrix.Point3D point2 : points) {
                if (point.areAdjacent(point2)) {
                    matrix.put(point, matrix.get(point).intValue() - 1);
                }
            }
        }

        // spread zeros from the edges
        for (int y = minPoint.y() + 1; y <= maxPoint.y() - 1; y++) {
            for (int z = minPoint.z() + 1; z <= maxPoint.z() - 1; z++) {
                spreadZeros(minPoint.x(), y, z);
                spreadZeros(maxPoint.x(), y, z);
            }
        }

        for (int x = minPoint.x() + 1; x <= maxPoint.x() - 1; x++) {
            for (int z = minPoint.z() + 1; z <= maxPoint.z() - 1; z++) {
                spreadZeros(x, minPoint.y(), z);
                spreadZeros(x, maxPoint.y(), z);
            }
        }

        for (int x = minPoint.x() + 1; x <= maxPoint.x() - 1; x++) {
            for (int y = minPoint.y() + 1; y <= maxPoint.y() - 1; y++) {
                spreadZeros(x, y, minPoint.z());
                spreadZeros(x, y, minPoint.z());
            }
        }

        // lower neighboring counters for items missing
        for (int x = minPoint.x(); x <= maxPoint.x() - 1; x++) {
            for (int y = minPoint.y() ; y <= maxPoint.y(); y++) {
                for (int z = minPoint.z(); z <= maxPoint.z(); z++) {
                    if (matrix.get(x, y, z) == null) {
                        lowerNeighboringMatrixCounts(matrix, x, y, z);
                    }
                }
            }
        }

        int total = 0;
        for (Sparse3DMatrix.Point3D point : points) {
            total += matrix.get(point);
        }
        return total;
    }

    public void spreadZeros(int x, int y, int z) {
        // if we're off the grid, ignore this
        if (x < minPoint.x() || y < minPoint.y() || z < minPoint.z()
            || x > maxPoint.x() || y > maxPoint.y() || z > maxPoint.z()) {
            return;
        }

        if (matrix.get(x, y, z) != null) {
            return;
        }

        matrix.put(x, y, z, 0);

        // process all neighboring points
        spreadZeros(x - 1, y, z);
        spreadZeros(x + 1, y, z);
        spreadZeros(x,y - 1, z);
        spreadZeros(x, y + 1, z);
        spreadZeros(x, y, z - 1);
        spreadZeros(x, y, z + 1);
    }

    public void lowerNeighboringMatrixCounts(Sparse3DMatrix<Integer> matrix, int x, int y, int z) {
        lowerMatrixCounter(matrix, x - 1, y, z);
        lowerMatrixCounter(matrix, x + 1, y, z);
        lowerMatrixCounter(matrix, x, y - 1, z);
        lowerMatrixCounter(matrix, x, y + 1, z);
        lowerMatrixCounter(matrix, x, y, z - 1);
        lowerMatrixCounter(matrix, x, y, z + 1);
    }

    public void lowerMatrixCounter(Sparse3DMatrix<Integer> matrix, int x, int y, int z) {
        // if we're off the grid, ignore this
        if (x < minPoint.x() || y < minPoint.y() || z < minPoint.z()
                || x > maxPoint.x() || y > maxPoint.y() || z > maxPoint.z()) {
            return;
        }

        if (matrix.get(x, y, z) != null && matrix.get(x, y, z) > 0) {
            matrix.put(x, y, z, matrix.get(x, y, z) - 1);
        }
    }
}
