package com.unhuman.adventofcode.aoc_framework.utility;

import java.awt.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SparseMatrix<T> {
    enum MatrixSystem { CARTESIAN, ROW }

    private Map<Point, T> matrix = new HashMap();
    private T defaultValue = null;
    private Point topLeft = null;
    private Point bottomRight = null;

    private MatrixSystem matrixSystem = MatrixSystem.ROW;

    public SparseMatrix() {
        this(null);
    }

    public SparseMatrix(T defaultValue) {
        this.matrix = new HashMap();
        this.defaultValue = defaultValue;
    }

    /**
     * Puts a value -
     * @param point
     * @param value
     * @return previous value, if any
     */
    public T put(Point point, T value) {
        T priorValue;
        if (value != null) {
            priorValue =  matrix.put(point, value);
        } else {
            priorValue = remove(point);
        }

        if (topLeft == null) {
            topLeft = new Point(point.x, point.y);
        } else {
            topLeft.x = Math.min(point.x, topLeft.x);
            topLeft.y = (matrixSystem == MatrixSystem.ROW)
                    ? Math.min(point.y, topLeft.y)
                    : Math.max(point.y, topLeft.y);
        }

        if (bottomRight == null) {
            bottomRight = new Point(point.x, point.y);
        } else {
            bottomRight.x = Math.max(point.x, topLeft.x);
            bottomRight.y = (matrixSystem == MatrixSystem.ROW)
                    ? Math.max(point.y, bottomRight.y) : Math.min(point.y, bottomRight.y);
        }

        return defaultValueOf(priorValue);
    }

    public T put(int x, int y, T value) {
        return put(new Point(x, y), value);
    }

    /**
     * Removes a value
     * @param point
     * @return previous value (or default)
     */
    public T remove(Point point) {
        topLeft = null;
        bottomRight = null;
        T value = matrix.remove(point);
        return defaultValueOf(value);
    }

    public T remove(int x, int y) {
        return remove(new Point(x, y));
    }

    /**
     *
     * @param point
     * @return value at point, else null
     */
    public T get(Point point) {
        T value = matrix.get(point);
        return defaultValueOf(value);
    }

    public T get(int x, int y) {
        return get(new Point(x, y));
    }

    public Point getTopLeft() {
        if (topLeft != null) {
            return topLeft;
        }

        for (Point checkPoint: matrix.keySet()) {
            if (topLeft == null) {
                topLeft = new Point(checkPoint);
            } else {
                topLeft.x = Math.min(checkPoint.x, topLeft.x);

                if (matrixSystem == MatrixSystem.ROW) {
                    topLeft.y = Math.min(checkPoint.y, topLeft.y);
                } else { // cartesian
                    topLeft.y = Math.max(checkPoint.y, topLeft.y);
                }
            }
        }
        return topLeft;
    }
    public Point getBottomRight() {
        if (bottomRight != null) {
            return bottomRight;
        }

        for (Point checkPoint: matrix.keySet()) {
            if (bottomRight == null) {
                bottomRight = new Point(checkPoint);
            } else {
                bottomRight.x = Math.max(checkPoint.x, bottomRight.x);

                if (matrixSystem == MatrixSystem.ROW) {
                    bottomRight.y = Math.max(checkPoint.y, bottomRight.y);
                } else { // cartesian
                    bottomRight.y = Math.min(checkPoint.y, bottomRight.y);
                }
            }
        }
        return bottomRight;
    }

    private Set<Point> getAllPopulatedPoints() {
        return Collections.unmodifiableSet(matrix.keySet());
    }

    private Set<Point> getPopulatedPoints(T desiredValue) {
        return Collections.unmodifiableSet(
                matrix.keySet().stream().filter
                        (point -> matrix.get(point).equals(desiredValue)).collect(Collectors.toSet()));
    }

    private T defaultValueOf(T value) {
        return (value != null) ? value : defaultValue;
    }

    @Override
    public String toString() {
        // Safety
        if (Math.abs(getTopLeft().x - getBottomRight().x) + Math.abs(getTopLeft().y - getBottomRight().y) > 100) {
            return "TOO BIG TO RENDER";
        }

        int startRow = getTopLeft().y;
        int endRow = getBottomRight().y;
        int increment = (startRow < endRow) ? 1 : -1;

        int startCol = getTopLeft().x;
        int endCol = getBottomRight().x;

        StringBuilder sb = new StringBuilder();
        for (int x = startCol; x <= endCol; x++) {
            sb.append((x == 0) ? '0' : ' ');
        }
        sb.append('\n');
        for (int y = startRow; (y >= startRow && y <= endRow) || (y <= startRow && y >= endRow); y += increment) {
            for (int x = startCol; x <= endCol; x++) {
                T ch = matrix.get(new Point(x, y));
                sb.append(ch != null ? ch : '.');
            }
            sb.append("  " + y);
            sb.append('\n');
        }
        return sb.toString();
    }
}
