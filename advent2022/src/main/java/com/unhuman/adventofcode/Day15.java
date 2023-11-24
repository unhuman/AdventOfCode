package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.SparseMatrix;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Day15 extends InputParser {
    private static final String regex1 = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)";
    private static final String regex2 = null;
    private int part1CheckRow = 2000000;
    private int part2Maximum = 4000000;

    public Day15() {
        super(2022, 15, regex1, regex2);
    }

    public Day15(String filename) {
        super(filename, regex1, regex2);
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
                if (sensor.y + y != part1CheckRow && part1CheckRow > 100) {
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

        List<BlockedRangeList> rows = new ArrayList<>(part2Maximum);
        for (int i = 0; i < part2Maximum; i++) {
            rows.add(new BlockedRangeList());
        }

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
                if (y < 0 || y >= part2Maximum) {
                    continue;
                }

                int horizontalDifference = manhattanDistance - (Math.abs(yRange));
                int xStart = Math.max(0, sensor.x - horizontalDifference);
                int xEnd = Math.min(part2Maximum, sensor.x + horizontalDifference);
                BlockedRange newRange = new BlockedRange(xStart, xEnd);
                rows.get(y).add(newRange);
            }
        });

        for (int y = 0; y < part2Maximum; y++) {
            if (rows.get(y).size() == 2) {
                Collections.sort(rows.get(y));
                System.out.println("row y " + y + " values: " + rows.get(y));
                return (rows.get(y).get(0).end + 1) * 4000000L + y;
            }
        }
        throw new RuntimeException("Got too far");
    }

    class BlockedRange implements Comparable {
        int start;
        int end;
        BlockedRange(int start, int end) {
            this.start = start;
            this.end = end;
        }

        boolean touches(BlockedRange other) {
            return ((other.start <= start && other.end >= start) || (other.start <= end && other.end >= end)
                    || (end == other.start - 1) || (other.end == start - 1)
                    || (other.start >= start && other.end <= end)
                    || (start >= other.start && end <= other.end));
        }

        @Override
        public int compareTo(Object o) {
            BlockedRange other = (BlockedRange) o;
            if (this.start < other.start) {
                return -1;
            }
            if (this.start > other.start) {
                return 1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return "[" + start + "-" + end + "]";
        }
    }

    class BlockedRangeList extends ArrayList<BlockedRange> {
        void add(int location) {
            add(new BlockedRange(location, location));
        }

        @Override
        public boolean add(BlockedRange range) {
            super.add(range);

            // Dedupe when overlaps - because could overlap multiple to combine
            for (int i = size() - 1; i > 0; i--) {
                for (int j = i - 1; j >= 0; j--) {
                    if (get(i).touches(get(j))) {
                        get(j).start = Math.min(get(j).start, get(i).start);
                        get(j).end = Math.max(get(j).end, get(i).end);
                        remove(i);
                        break;
                    }
                }
            }

            return true;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder(64);
            for (int i = 0; i < size(); i++) {
                sb.append(get(i));
                if (i < size() - 1) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }
    }
}
