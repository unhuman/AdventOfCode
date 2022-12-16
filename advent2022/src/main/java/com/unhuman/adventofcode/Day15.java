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
    private int checkRow = 2000000;

    public Day15(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    public void setCheckRow(int row) {
        checkRow = row;
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
                if (sensor.y + y != checkRow) {
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
            Point check = new Point(i, checkRow);
            if (matrix.get(check) != null && matrix.get(check) != 'B') {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        return 2;
    }
}
