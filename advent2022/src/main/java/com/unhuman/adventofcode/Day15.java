package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.SparseMatrix;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Day15 extends InputParser {
    private static final String regex1 = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)";
    private static final String regex2 = null;
    private int part1CheckRow = 2000000;
    private int part2Maximum = 4000000;

    public Day15(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    public void setPart1CheckRow(int row) {
        part1CheckRow = row;
    }

    public void setPart2Maximum(int maximum) {
        part2Maximum = maximum;
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        SparseMatrix<Character> matrix = new SparseMatrix<>();
        Map<Point, Point> sensorToBeacon = new LinkedHashMap<>();
        for (int groupItemIdx = 0; groupItemIdx < dataItems1.size(); groupItemIdx++) {
            GroupItem item = dataItems1.get(groupItemIdx);
            for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
                ItemLine line = item.get(lineIdx);
                Point sensor = new Point(Integer.parseInt(line.get(0)), Integer.parseInt(line.get(1)));
                Point beacon = new Point(Integer.parseInt(line.get(2)), Integer.parseInt(line.get(3)));
                matrix.put(sensor, 'S');
                matrix.put(beacon, 'B');
                sensorToBeacon.put(sensor, beacon);
            }
        }

        // Once everything is placed, plot things
        sensorToBeacon.forEach((sensor, beacon) -> {
            int manhattanDistance = Math.abs(beacon.x - sensor.x) + Math.abs(beacon.y - sensor.y);
            for (int y = -manhattanDistance; y <= manhattanDistance; y++) {
                // Optimization - only process desired row
                if (sensor.y + y != part1CheckRow) {
                    continue;
                }
                int horizontalDifference = manhattanDistance - (Math.abs(y));
                for (int x = -horizontalDifference; x <= horizontalDifference; x++) {
                    Point point = new Point(sensor.x + x, sensor.y + y);
                    // do not plot points outside of our existing placement
                    if (matrix.get(point) == null) {
                        matrix.put(point, '#');
                    }
                }
            }
        });

        Point topLeft = matrix.getTopLeft();
        Point bottomRight = matrix.getBottomRight();
        int counter = 0;
        for (int i = topLeft.x; i <= bottomRight.x; i++) {
            Point check = new Point(i, part1CheckRow);
            if (matrix.get(check) != null && matrix.get(check) != 'B') {
                counter++;
            }
        }
        System.out.println(matrix);
        return counter;
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        SparseMatrix<Character> matrix = new SparseMatrix<>();
        Map<Point, Point> sensorToBeacon = new LinkedHashMap<>();
        for (int groupItemIdx = 0; groupItemIdx < dataItems1.size(); groupItemIdx++) {
            GroupItem item = dataItems1.get(groupItemIdx);
            for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
                ItemLine line = item.get(lineIdx);
                Point sensor = new Point(Integer.parseInt(line.get(0)), Integer.parseInt(line.get(1)));
                Point beacon = new Point(Integer.parseInt(line.get(2)), Integer.parseInt(line.get(3)));
                matrix.put(sensor, 'S');
                matrix.put(beacon, 'B');
                sensorToBeacon.put(sensor, beacon);
            }
        }

        // Once everything is placed, plot things
        sensorToBeacon.forEach((sensor, beacon) -> {
            int manhattanDistance = Math.abs(beacon.x - sensor.x) + Math.abs(beacon.y - sensor.y);
            for (int yRange = -manhattanDistance; yRange <= manhattanDistance; yRange++) {
                int y = sensor.y + yRange;

                // optimization
                if (y < 0 || y > part2Maximum) {
                    continue;
                }

                int horizontalDifference = manhattanDistance - (Math.abs(yRange));
                for (int xRange = -horizontalDifference; xRange <= horizontalDifference; xRange++) {
                    int x = sensor.x + xRange;
                    // optimization
                    if (x < 0 || x > part2Maximum) {
                        continue;
                    }

                    Point point = new Point(x, y);
                    // do not plot points outside of our existing placement
                    if (matrix.get(point) == null) {
                        matrix.put(point, '#');
                    }
                }
            }
        });

        System.out.println(matrix);

        Point topLeft = matrix.getTopLeft();
        Point bottomRight = matrix.getBottomRight();
        for (int y = 0; y < part2Maximum; y++) {
            for (int x = 0; x < part2Maximum; x++) {
                if (matrix.get(new Point(x, y)) == null) {
                    return x * 4000000 + y;
                }
            }
        }

        throw new RuntimeException("Got too far");
    }
}
