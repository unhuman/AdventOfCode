package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;
import com.unhuman.adventofcode.aoc_framework.utility.Sparse3DMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Day24 extends InputParser {
    private static final String regex1 = "(?:.*?)(-?\\d+)(?:[,^\\\\d-]*)";
    private static final String regex2 = null;

    long minPosition = 200000000000000L;
    long maxPosition = 400000000000000L;

    public Day24() {
        super(2023, 24, regex1, regex2);
    }

    public Day24(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        List<Pair<Sparse3DMatrix.Point3D, Sparse3DMatrix.Point3D>> field = new ArrayList<>();
        for (ItemLine line : item) {
            Sparse3DMatrix.Point3D point = new Sparse3DMatrix.Point3D(
                    Long.parseLong(line.get(0)),
                    Long.parseLong(line.get(1)),
                    Long.parseLong(line.get(2)));
            Sparse3DMatrix.Point3D trajectory = new Sparse3DMatrix.Point3D(
                    Long.parseLong(line.get(3)),
                    Long.parseLong(line.get(4)),
                    Long.parseLong(line.get(5)));
            Pair<Sparse3DMatrix.Point3D, Sparse3DMatrix.Point3D> pointTrajectory = new Pair(point, trajectory);
            field.add(pointTrajectory);
        }

        int count = 0;
        for (int i = 0; i < field.size(); i++) {
            Pair<Sparse3DMatrix.Point3D, Sparse3DMatrix.Point3D> item1 = field.get(i);
            double slope1 = calculateSlope(item1);
            double b1 = findB(item1.getLeft(), slope1);

            for (int j = i + 1; j < field.size(); j++) {
                Pair<Sparse3DMatrix.Point3D, Sparse3DMatrix.Point3D> item2 = field.get(j);
                double slope2 = calculateSlope(item2);
                double b2 = findB(item2.getLeft(), slope2);

                Optional<Pair<Double, Double>> intersection = calculateIntersectionPoint(slope1, b1, slope2, b2);
                if (intersection.isPresent()) {
                    Pair<Double, Double> intersectionPoint = intersection.get();
                    // System.out.println("comparing: " + item1 + " with " + item2 + " intersection " + intersectionPoint);
                    if (intersectionPoint.getLeft() >= minPosition && intersectionPoint.getLeft() <= maxPosition
                            && intersectionPoint.getRight() >= minPosition && intersectionPoint.getRight() <= maxPosition
                            && isPointFuture(intersectionPoint, item1)
                            && isPointFuture(intersectionPoint, item2)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    boolean isPointFuture(Pair<Double, Double> intersectionPoint, Pair<Sparse3DMatrix.Point3D, Sparse3DMatrix.Point3D> item) {
        Sparse3DMatrix.Point3D startingPoint = item.getLeft();
        Sparse3DMatrix.Point3D trajectory = item.getRight();
        long xPolarity = trajectory.x() / (Math.abs(trajectory.x()));
        long yPolarity = trajectory.y() / (Math.abs(trajectory.y()));
        if ((intersectionPoint.getLeft() > startingPoint.x() && xPolarity > 0 || intersectionPoint.getLeft() < startingPoint.x() && xPolarity < 0)
            && (intersectionPoint.getRight() > startingPoint.y() && yPolarity > 0 || intersectionPoint.getRight() < startingPoint.y() && yPolarity < 0)) {
            return true;
        }
        return false;
    }

    public void setMinMaxPositions(long min, long max) {
        this.minPosition = min;
        this.maxPosition = max;
    }

    public Optional<Pair<Double, Double>> calculateIntersectionPoint(
            double m1,
            double b1,
            double m2,
            double b2) {

        if (m1 == m2) {
            return Optional.empty();
        }

        double x = (b2 - b1) / (m1 - m2);
        double y = m1 * x + b1;

        Pair<Double, Double> point = new Pair<>(x, y);
        return Optional.of(point);
    }

    double calculateSlope(Pair<Sparse3DMatrix.Point3D, Sparse3DMatrix.Point3D> pointTrajectory) {
        return (double) pointTrajectory.getRight().y() / (double) pointTrajectory.getRight().x();
    }

    double findB(Sparse3DMatrix.Point3D point, double slope) {
        // y = mx + b
        // b = y - mx;
        return point.y() - slope * point.x();
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        return 2;
    }
}
