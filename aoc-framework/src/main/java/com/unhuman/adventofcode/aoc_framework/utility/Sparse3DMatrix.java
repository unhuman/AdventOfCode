package com.unhuman.adventofcode.aoc_framework.utility;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Sparse3DMatrix<T> {
    private Map<Point3D, T> matrix = new HashMap();
    private T defaultValue = null;

    public Sparse3DMatrix() {
        this(null);
    }

    public Sparse3DMatrix(T defaultValue) {
        this.matrix = new HashMap();
        this.defaultValue = defaultValue;
    }

    private Point3D minCorner = null;
    private Point3D maxCorner = null;

    /**
     * Puts a value -
     * @param point
     * @param value
     * @return previous value, if any
     */
    public T put(Point3D point, T value) {
        T priorValue;
        if (value != null) {
            priorValue = matrix.put(point, value);
        } else {
            priorValue = remove(point);
        }

        updateMinMaxCorner(point);

        return defaultValueOf(priorValue);
    }

    public T put(int x, int y, int z, T value) {
        return put(new Point3D(x, y, z), value);
    }

    /**
     * Removes a value
     * @param point
     * @return previous value (or default)
     */
    public T remove(Point3D point) {
        T value = matrix.remove(point);
        minCorner = null;
        maxCorner = null;
        return defaultValueOf(value);
    }

    public T remove(int x, int y, int z) {
        return remove(new Point3D(x, y, z));
    }

    /**
     *
     * @param point
     * @return value at point, else null
     */
    public T get(Point3D point) {
        T value = matrix.get(point);
        return defaultValueOf(value);
    }

    public T get(int x, int y, int z) {
        return get(new Point3D(x, y, z));
    }

    public Set<Point3D> getAllPopulatedPoints() {
        return Collections.unmodifiableSet(matrix.keySet());
    }

    private Set<Point3D> getPopulatedPoints(T desiredValue) {
        return Collections.unmodifiableSet(
                matrix.keySet().stream().filter
                        (point -> matrix.get(point).equals(desiredValue)).collect(Collectors.toSet()));
    }

    private T defaultValueOf(T value) {
        return (value != null) ? value : defaultValue;
    }

    public Point3D getMinCorner() {
        if (minCorner != null) {
            return minCorner;
        }

        for (Point3D checkPoint: matrix.keySet()) {
            updateMinMaxCorner(checkPoint);
        }
        return minCorner;
    }

    public Point3D getMaxCorner() {
        if (maxCorner != null) {
            return maxCorner;
        }

        for (Point3D checkPoint: matrix.keySet()) {
            updateMinMaxCorner(checkPoint);
        }
        return maxCorner;
    }

    private void updateMinMaxCorner(Point3D point) {
        if (minCorner == null) {
            minCorner = new Point3D(point.x, point.y, point.z);
        } else {
            minCorner = new Point3D(Math.min(point.x, minCorner.x),
                    Math.min(point.y, minCorner.y),
                    Math.min(point.z, minCorner.z));
        }

        if (maxCorner == null) {
            maxCorner = new Point3D(point.x, point.y, point.z);
        } else {
            maxCorner = new Point3D(Math.max(point.x, maxCorner.x),
                    Math.max(point.y, maxCorner.y),
                    Math.max(point.z, maxCorner.z));
        }

    }

    public record Point3D(int x, int y, int z) {
        @Override
        public boolean equals(Object o) {
            Point3D other = (Point3D) o;
            return (x == other.x && y == other.y && z == other.z);
        }

        public boolean areAdjacent(Point3D other) {
            return ((x == other.x && y == other.y && Math.abs(z - other.z) == 1)
                    || (x == other.x && Math.abs(y - other.y) == 1 && z == other.z)
                    || (Math.abs(x - other.x) == 1 && y == other.y && z == other.z));
        }
    }
}
