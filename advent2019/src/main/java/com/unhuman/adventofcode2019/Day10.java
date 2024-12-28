package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;
import com.unhuman.adventofcode.aoc_framework.utility.PointHelper;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class Day10 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    private Point forceBase = null;
    private int shots = 200;

    public Day10() {
        super(2019, 10, regex1, regex2);
    }

    public Day10(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        Pair<Point, Long> results = getBestBaseStation(matrix);

        return results.getRight();
    }

    private Pair<Point, Long> getBestBaseStation(Matrix matrix) {
        long maxCounts = 0L;
        Point bestPoint = null;

        List<Point> asteroidLocations = matrix.getCharacterLocations('#');
        for (Point baseLocation: asteroidLocations) {
            Set<Point> otherAsteroids = new HashSet<>(asteroidLocations);
            otherAsteroids.remove(baseLocation);
            for (Point checkOther: asteroidLocations) {
                // skip self
                if (checkOther == baseLocation) {
                    continue;
                }

                // calculate slope
                Point slopePoint = PointHelper.getSlope(baseLocation, checkOther);

                for (Point checkOther2: asteroidLocations) {
                    // skip selves
                    if (checkOther2 == baseLocation || checkOther == checkOther2) {
                        continue;
                    }

                    Point slopePoint2 = PointHelper.getSlope(baseLocation, checkOther2);

                    // ensure we are both in the same direction away from base station
                    if (!(((slopePoint.x >= 0 && slopePoint2.x >= 0) || (slopePoint.x <= 0 && slopePoint2.x <= 0))
                            && ((slopePoint.y >= 0 && slopePoint2.y >= 0) || (slopePoint.y <= 0 && slopePoint2.y <= 0)))) {
                        continue;
                    }
                    if ((double) slopePoint.y / (double) slopePoint.x == (double) slopePoint2.y / (double) slopePoint2.x) {
                        double distance1 = PointHelper.getDistance(baseLocation, checkOther);
                        double distance2 = PointHelper.getDistance(baseLocation, checkOther2);
                        if (distance2 > distance1) {
                            otherAsteroids.remove(checkOther2);
                        }
                    }
                }
            }

            if (otherAsteroids.size() > maxCounts) {
                bestPoint = baseLocation;
                maxCounts = otherAsteroids.size();
            }
        }

        System.out.println("Best Base station: " + bestPoint + " count = " + maxCounts);
        return new Pair<Point, Long>(bestPoint, maxCounts);
    }

    public void forceBase(Point forceBase) {
        this.forceBase = forceBase;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        List<Point> asteroidLocations = matrix.getCharacterLocations('#');

        Point baseStation = (forceBase == null) ? getBestBaseStation(matrix).getLeft() : forceBase;

        SortedMap<Double, List<Point>> degreesToAsteroids = new TreeMap<>();

         for (Point asteroidLocation : asteroidLocations) {
            if (asteroidLocation.equals(baseStation)) {
                continue;
            }

            double degrees = PointHelper.getSlope(baseStation, asteroidLocation).getDegrees();

            // convert to start at 12 o'clock
             degrees = (450 - degrees) % 360;

            if (!degreesToAsteroids.containsKey(degrees)) {
                degreesToAsteroids.put(degrees, new ArrayList<>());
            }
            degreesToAsteroids.get(degrees).add(asteroidLocation);
        }

        int shotCounter = 0;
        while (true) {
            for (double degreeCheck : degreesToAsteroids.keySet()) {
                List<Point> asteroids = degreesToAsteroids.get(degreeCheck);
                if (!asteroids.isEmpty()) {
                    // find the closest asteroid to the baseStation
                    // and remove it

                    Pair<Point, Double> closestAsteroidDistance = null;
                    for (Point asteroid : asteroids) {
                        double distance = PointHelper.getDistance(baseStation, asteroid);
                        if (closestAsteroidDistance == null || distance < closestAsteroidDistance.getRight()) {
                            closestAsteroidDistance = new Pair<>(asteroid, distance);
                        }
                    }

                    if (closestAsteroidDistance != null) {
                        asteroids.remove(closestAsteroidDistance.getLeft());
                        System.out.println(closestAsteroidDistance.getLeft());
                        if (++shotCounter == shots) {
                            return closestAsteroidDistance.getLeft().x * 100 + closestAsteroidDistance.getLeft().y;
                        }
                    }
                }
            }
        }
    }
}
