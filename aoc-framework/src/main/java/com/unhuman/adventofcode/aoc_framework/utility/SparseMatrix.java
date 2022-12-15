package com.unhuman.adventofcode.aoc_framework.utility;

import java.awt.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SparseMatrix<T> {
    Map<Point, T> matrix = new HashMap();
    T defaultValue = null;

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
        return defaultValueOf(priorValue);
    }

    /**
     * Removes a value
     * @param point
     * @return previous value (or default)
     */
    public T remove(Point point) {
        T value = matrix.remove(point);
        return defaultValueOf(value);
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

    public Point getTopLeft() {
        Point topLeft = null;
        for(Point checkPoint: matrix.keySet()) {
            if (checkPoint == null) {
                topLeft = new Point(checkPoint);
            } else {
                if (checkPoint.x < topLeft.x) {
                    topLeft.x = checkPoint.x;
                }
                if (checkPoint.y > topLeft.y) {
                    topLeft.y = checkPoint.y;
                }
            }
        }
        return topLeft;
    }

    public Point getBottomRight() {
        Point bottomRight = null;
        for(Point checkPoint: matrix.keySet()) {
            if (checkPoint == null) {
                bottomRight = new Point(checkPoint);
            } else {
                if (checkPoint.x > bottomRight.x) {
                    bottomRight.x = checkPoint.x;
                }
                if (checkPoint.y < bottomRight.y) {
                    bottomRight.y = checkPoint.y;
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
                matrix.keySet().stream().filter(point ->
                        matrix.get(point).equals(desiredValue)).collect(Collectors.toSet()));
    }

    private T defaultValueOf(T value) {
        return (value != null) ? value : defaultValue;
    }
}
