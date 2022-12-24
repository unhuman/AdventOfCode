package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Day24 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    public Day24(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        GroupItem item = dataItems1.get(0);
        WeatherMap map = new WeatherMap(item.get(0).size(), item.size());
        for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
            ItemLine line = item.get(lineIdx);
            for (int elementIdx = 0; elementIdx < line.size(); elementIdx++) {
                WeatherNode node = new WeatherNode(line.get(elementIdx).charAt(0));
                map.setNode(elementIdx, lineIdx, node);
            }
        }
        // System.out.println(map);

        LinkedHashSet<Point> permutations = new LinkedHashSet<>();
        permutations.add(new Point(1, 0));
        int round = 0;
        while(true) {
            round++;
            map = map.getNextCycle();
//            System.out.println(map);

            LinkedHashSet<Point> newPermutations = new LinkedHashSet<>();
            for (Point currentLocation: permutations) {
                // check left / right / up / down
                for (int xCheck = -1; xCheck <= 1; xCheck++) {
                    for (int yCheck = -1; yCheck <= 1; yCheck++) {
                        // don't allow diagonals
                        if (Math.abs(xCheck) + Math.abs(yCheck) == 2) {
                            continue;
                        }
                        Point checkPoint = new Point(currentLocation.x + xCheck, currentLocation.y + yCheck);
                        // do not allow going off the grid
                        if (checkPoint.y < 0) {
                            continue;
                        }
                        if (map.isOpen(checkPoint)) {
                            newPermutations.add(checkPoint);
                            if (checkPoint.y == map.getHeight() - 1) {
                                return round;
                            }
                        }
                    }
                }
            }
            // Cycle to next iteration of all place for next round
            permutations = newPermutations;
        }
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        GroupItem item = dataItems1.get(0);
        WeatherMap map = new WeatherMap(item.get(0).size(), item.size());
        for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
            ItemLine line = item.get(lineIdx);
            for (int elementIdx = 0; elementIdx < line.size(); elementIdx++) {
                WeatherNode node = new WeatherNode(line.get(elementIdx).charAt(0));
                map.setNode(elementIdx, lineIdx, node);
            }
        }
        // System.out.println(map);

        LinkedHashSet<Point> permutations = new LinkedHashSet<>();
        permutations.add(new Point(1, 0));
        int round = 0;
        ArrayList<Point> destinations = new ArrayList<>();
        destinations.add(new Point(map.getWidth() - 2, map.getHeight() - 1));
        destinations.add(new Point(1, 0));
        destinations.add(new Point(map.getWidth() - 2, map.getHeight() - 1));


        while(true) {
            round++;
            map = map.getNextCycle();
//            System.out.println(map);

            LinkedHashSet<Point> newPermutations = new LinkedHashSet<>();
            boolean foundDestination = false;
            for (Point currentLocation: permutations) {
                // check left / right / up / down
                for (int xCheck = -1; xCheck <= 1 && !foundDestination; xCheck++) {
                    for (int yCheck = -1; yCheck <= 1 && !foundDestination; yCheck++) {
                        // don't allow diagonals
                        if (Math.abs(xCheck) + Math.abs(yCheck) == 2) {
                            continue;
                        }
                        Point checkPoint = new Point(currentLocation.x + xCheck, currentLocation.y + yCheck);
                        // do not allow going off the grid
                        if (checkPoint.y < 0 || checkPoint.y >= map.getHeight()) {
                            continue;
                        }
                        if (map.isOpen(checkPoint)) {
                            if (checkPoint.equals(destinations.get(0))) {
                                System.out.println("Made it to destination: " + checkPoint + " at time: " + round);
                                newPermutations.clear();
                                destinations.remove(0);

                                if (destinations.size() == 0) {
                                    return round;
                                }

                                // break out of the loops
                                foundDestination = true;
                            }
                            newPermutations.add(checkPoint);
                        }
                    }
                }
            }
            // Cycle to next iteration of all place for next round
            permutations = newPermutations;
        }
    }

    class WeatherMap {
        WeatherNode[][] map;

        WeatherMap(int width, int height) {
            this.map = new WeatherNode[height][width];
        }

        void setNode(int x, int y, WeatherNode node) {
            map[y][x] = node;
        }

        int getHeight() {
            return map.length;
        }

        int getWidth() {
            return map[0].length;
        }

        WeatherMap getNextCycle() {
            int height = map.length;
            int width = map[0].length;
            WeatherMap newMap = new WeatherMap(width, height);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    WeatherNode newNode = new WeatherNode(' ');
                    newNode.setBehavior(findNextBehavior(x, y));
                    newMap.map[y][x] = newNode;
                }
            }
            return newMap;
        }

        boolean isOpen(Point point) {
            return (map[point.y][point.x].isOpen());
        }

        int findNextBehavior(int x, int y) {
            int height = map.length;
            int width = map[0].length;

            // if we are on the border, everything stays the same
            if (x == 0 || y == 0 || x == width - 1 || y == height -1) {
                return map[y][x].value;
            }

            int value = 0;
            if (((map[y][x-1].value & WeatherNode.RIGHT) == WeatherNode.RIGHT)
                    || (x == 1 && (map[y][width - 2].value & WeatherNode.RIGHT) == WeatherNode.RIGHT)) {
                value |= WeatherNode.RIGHT;
            }
            if (((map[y][x+1].value & WeatherNode.LEFT) == WeatherNode.LEFT)
                    || (x == width - 2 && (map[y][1].value & WeatherNode.LEFT) == WeatherNode.LEFT)) {
                value |= WeatherNode.LEFT;
            }
            if (((map[y-1][x].value & WeatherNode.DOWN) == WeatherNode.DOWN)
                    || (y == 1 && (map[height - 2][x].value & WeatherNode.DOWN) == WeatherNode.DOWN)) {
                value |= WeatherNode.DOWN;
            }
            if (((map[y+1][x].value & WeatherNode.UP) == WeatherNode.UP)
                    || (y == height - 2 && (map[1][x].value & WeatherNode.UP) == WeatherNode.UP)) {
                value |= WeatherNode.UP;
            }
            return value;
        }

        @Override
        public String toString() {
            if (map.length > 20) {
                return super.toString();
            }
            StringBuilder sb = new StringBuilder();
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[y].length; x++) {
                    WeatherNode node = map[y][x];
                    sb.append(node);
                }
                sb.append('\n');
            }
            return sb.toString();
        }
    }

    class WeatherNode {
        static final int BLOCKER = 1;
        static final int UP = 2;
        static final int DOWN = 4;
        static final int LEFT = 8;
        static final int RIGHT = 16;

        int value;

        WeatherNode(char item) {
            value = 0;
            addBehavior(item);
        }

        void setBehavior(int behavior) {
            value = behavior;
        }

        void addBehavior(char item) {
            switch(item) {
                case '#' -> value |= BLOCKER;
                case '^' -> value |= UP;
                case 'v' -> value |= DOWN;
                case '<' -> value |= LEFT;
                case '>' -> value |= RIGHT;
            }
        }

        boolean isOpen() {
            return (value == 0);
        }

        @Override
        public String toString() {
            if (value == 0) {
                return " ";
            }
            if (value == BLOCKER) {
                return "#";
            }

            int counter = 0;
            counter += (value & LEFT) == LEFT ? 1 : 0;
            counter += (value & RIGHT) == RIGHT ? 1 : 0;
            counter += (value & UP) == UP ? 1 : 0;
            counter += (value & DOWN) == DOWN ? 1 : 0;

            switch (counter) {
                case 2:
                    return "2";
                case 3:
                    return "3";
                case 4:
                    return "4";
            }

            if (value == LEFT) return "<";
            if (value == RIGHT) return ">";
            if (value == UP) return "^";
            if (value == DOWN) return "v";

            throw new RuntimeException("Unknown value: " + value);
        }
    }
}
