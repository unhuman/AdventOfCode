package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day6 extends InputParser {
    enum Direction { UP, DOWN, LEFT, RIGHT }
    private static final String regex1 = "(.)";
    private static final String regex2 = null;
    private static List<Pair<Point, Point>> corners = new ArrayList<>();

    private class LoopingException extends RuntimeException {

    }

    public Day6() {
        super(2024, 6, regex1, regex2);
    }

    public Day6(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        corners.clear();

        Matrix map = new Matrix(configGroup, Matrix.DataType.CHARACTER);
        Point direction = new Point(0, -1);

        Point location = map.findCharacterLocations('^').get(0);
        HashSet<Point> visited = new HashSet<>();
        while (location != null) {
            visited.add(location);
            location = findNextLocation(map, location, direction);
        }

        return (long) visited.size();
    }

    Point findNextLocation(Matrix map, Point location, Point direction) {
        while (true) {
            Point next = new Point(location.x + direction.x, location.y + direction.y);
            if (map.getValue(next) == null) {
                return null;
            } else if (map.getValue(next) == '#') {
                if (corners.contains(new Pair<>(location, next))) {
                    throw new LoopingException();
                }
                corners.add(new Pair<>(location, next));

                // rotate right
                if (direction.y == -1) {
                    direction.setLocation(1, 0);
                } else if (direction.x == 1) {
                    direction.setLocation(0, 1);
                } else if (direction.y == 1) {
                    direction.setLocation(-1, 0);
                } else if (direction.x == -1) {
                    direction.setLocation(0, -1);
                }
            } else {
                return next;
            }
        }
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix map = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        Point start = map.findCharacterLocations('^').get(0);

        long count = 0;

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                HashSet<Point> visited = new HashSet<>();
                corners.clear();
                Point location = new Point(start);
                Point direction = new Point(0, -1);

                if (!map.getValue(x, y).equals('.')) {
                    continue;
                }

                // set this value to a blocker
                map.setValue(x, y, '#');
                // process
                while (location != null) {
                    try {
                        visited.add(location);
                        location = findNextLocation(map, location, direction);
                    } catch (LoopingException le) {
                        ++count;
                        break;
                    }
                }

                // restore this value so we can do the loop again
                map.setValue(x, y, '.');
            }
        }

        return count;
    }
}
