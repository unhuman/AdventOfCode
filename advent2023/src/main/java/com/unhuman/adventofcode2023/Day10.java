package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Day10 extends InputParser {
    enum Direction { RIGHT, LEFT, DOWN, UP }
    enum Skew { NONE, RIGHT, LEFT, DOWN, UP }
    private static final String regex1 = "(.)";
    private static final String regex2 = null;
    private Direction firstDir;

    public Day10() {
        super(2023, 10, regex1, regex2);
    }

    public Day10(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Pair<char[][], Point> mapAndStart = extractMap(configGroup);
        char[][] map = mapAndStart.getLeft();
        firstDir = null;
        Point findNext = mapAndStart.getRight();
        Point prior = findNext;
        Direction[] checkDirs = Direction.values();
        int counter = 0;
        while (true) {
            Pair<Point, Direction> nextNext = findNext(map, findNext, prior, checkDirs);
            ++counter;

            if (nextNext.getRight() == null) {
                break;
            }

            prior = findNext;
            findNext = nextNext.getLeft();
            checkDirs = new Direction[] { nextNext.getRight() };
        };

        return counter / 2;
    }

    /**
     *
     * @param map
     * @param lookup
     * @param cannotReturn
     * @param checkDirs
     * @return Pair<Point, Direction (next to check)> or null when down
     */
    Pair<Point, Direction> findNext(char[][] map, Point lookup, Point cannotReturn, Direction[] checkDirs) {
        for (Direction dir: checkDirs) {
            int x = lookup.x
                    - ((dir == Direction.LEFT) ? 1 : 0)
                    + ((dir == Direction.RIGHT) ? 1 : 0);
            int y = lookup.y
                    - ((dir == Direction.UP) ? 1 : 0)
                    + ((dir == Direction.DOWN) ? 1 : 0);

            // If we are off the map or trying to return where we came from, skip this
            if (x < 0 || y < 0 || y >= map.length || x >= map[0].length
                || (x == cannotReturn.x && y == cannotReturn.y)) {
                continue;
            }

            char checkChar = map[y][x];
            if (checkChar == 'S') {
                // replace S with dir and firstDir
                if (firstDir == Direction.RIGHT) {
                    switch (checkDirs[0]) {
                        case RIGHT:
                            map[y][x] = '-';
                            break;
                        case UP:
                            map[y][x] = 'F';
                            break;
                        case DOWN:
                            map[y][x] = 'L';
                            break;
                    }
                }
                if (firstDir == Direction.LEFT) {
                    switch (checkDirs[0]) {
                        case LEFT:
                            map[y][x] = '-';
                            break;
                        case UP:
                            map[y][x] = '7';
                            break;
                        case DOWN:
                            map[y][x] = 'J';
                            break;
                    }
                }
                if (firstDir == Direction.DOWN) {
                    switch (checkDirs[0]) {
                        case LEFT:
                            map[y][x] = 'L';
                            break;
                        case RIGHT:
                            map[y][x] = 'J';
                            break;
                        case DOWN:
                            map[y][x] = '|';
                            break;
                    }
                }
                if (firstDir == Direction.DOWN) {
                    switch (checkDirs[0]) {
                        case LEFT:
                            map[y][x] = 'F';
                            break;
                        case RIGHT:
                            map[y][x] = '7';
                            break;
                        case UP:
                            map[y][x] = '|';
                            break;
                    }
                }

                return new Pair<>(new Point(x, y), null);
            }
            switch (dir) {
                case LEFT:
                    switch (checkChar) {
                        case '-':
                            if (firstDir == null) {
                                firstDir = dir;
                            }
                            return new Pair<>(new Point(x, y), dir);
                        case 'L':
                            if (firstDir == null) {
                                firstDir = dir;
                            }
                            return new Pair<>(new Point(x, y), Direction.UP);
                        case 'F':
                            if (firstDir == null) {
                                firstDir = dir;
                            }
                            return new Pair<>(new Point(x, y), Direction.DOWN);
                    }
                    break;
                case RIGHT:
                    switch (checkChar) {
                        case '-':
                            if (firstDir == null) {
                                firstDir = dir;
                            }
                            return new Pair<>(new Point(x, y), dir);
                        case 'J':
                            if (firstDir == null) {
                                firstDir = dir;
                            }
                            return new Pair<>(new Point(x, y), Direction.UP);
                        case '7':
                            if (firstDir == null) {
                                firstDir = dir;
                            }
                            return new Pair<>(new Point(x, y), Direction.DOWN);
                    }
                    break;
                case UP:
                    switch (checkChar) {
                        case '|':
                            if (firstDir == null) {
                                firstDir = dir;
                            }
                            return new Pair<>(new Point(x, y), dir);
                        case '7':
                            if (firstDir == null) {
                                firstDir = dir;
                            }
                            return new Pair<>(new Point(x, y), Direction.LEFT);
                        case 'F':
                            if (firstDir == null) {
                                firstDir = dir;
                            }
                            return new Pair<>(new Point(x, y), Direction.RIGHT);
                    }
                    break;
                case DOWN:
                    switch (checkChar) {
                        case '|':
                            if (firstDir == null) {
                                firstDir = dir;
                            }
                            return new Pair<>(new Point(x, y), dir);
                        case 'J':
                            if (firstDir == null) {
                                firstDir = dir;
                            }
                            return new Pair<>(new Point(x, y), Direction.LEFT);
                        case 'L':
                            if (firstDir == null) {
                                firstDir = dir;
                            }
                            return new Pair<>(new Point(x, y), Direction.RIGHT);
                    }
                    break;
            }
        }

        throw new RuntimeException("Couldn't process " + lookup);
    }

    Pair<char[][], Point> extractMap(ConfigGroup configGroup) {
        char[][] map = new char[configGroup.get(0).size()][configGroup.get(0).get(0).size()];
        Point starting = null;
        for (int y = 0; y < configGroup.get(0).size(); y++) {
            ItemLine line = configGroup.get(0).get(y);
            for (int x = 0; x < line.size(); x++) {
                char character = line.get(x).charAt(0);
                map[y][x] = character;
                if (character == 'S') {
                    starting = new Point(x, y);
                }
            }
        }
        return new Pair(map, starting);
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Pair<char[][], Point> mapAndStart = extractMap(configGroup);
        char[][] map = mapAndStart.getLeft();
        firstDir = null;
        Point findNext = mapAndStart.getRight();
        Point prior = findNext;
        Direction[] checkDirs = Direction.values();
        Set<Point> borderPoints = new HashSet<>();
        while (true) {
            Pair<Point, Direction> nextNext = findNext(map, findNext, prior, checkDirs);
            borderPoints.add(findNext);

            if (nextNext.getRight() == null) {
                break;
            }

            prior = findNext;
            findNext = nextNext.getLeft();
            checkDirs = new Direction[] { nextNext.getRight() };
        }


        int internalFound = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                Point checkPoint = new Point(x, y);
                if (borderPoints.contains(checkPoint)) {
                    continue;
                }

                int crosses = 0;
                int directionChanges = 0;
                for (int x1 = x + 1; x1 < map[0].length; x1++) {
                    if (borderPoints.contains(new Point(x1, y))) {
                        if (map[y][x1] == '|') {
                            ++crosses;
                        }
                        if (/* map[y][x1] == '7' || map[y][x1] == 'F' || */ map[y][x1] == 'J' || map[y][x1] == 'L') {
                            // ++directionChanges;
                            ++crosses;
                        }
                    }
                }

                if (((crosses + (directionChanges % 2)) % 2) == 1) {
                    //map[y][x] = 'I';
                    System.out.println("Internal: " + checkPoint);
                    internalFound++;
                }
            }
        }

        printMap(map, borderPoints);

        return internalFound;

        //        return map.length * map[0].length - floodFilled - borderPoints.size() - externalFound;
    }

    public Object processInput2AbsolutelyHeinous(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Pair<char[][], Point> mapAndStart = extractMap(configGroup);
        char[][] map = mapAndStart.getLeft();
        firstDir = null;
        Point findNext = mapAndStart.getRight();
        Point prior = findNext;
        Direction[] checkDirs = Direction.values();
        Set<Point> borderPoints = new HashSet<>();
        while (true) {
            Pair<Point, Direction> nextNext = findNext(map, findNext, prior, checkDirs);
            borderPoints.add(findNext);

            if (nextNext.getRight() == null) {
                break;
            }

            prior = findNext;
            findNext = nextNext.getLeft();
            checkDirs = new Direction[] { nextNext.getRight() };
        };

        int floodFilled = 0;
        int width = map[0].length;
        for (int i = 0; i < map.length; i++) {
            floodFilled += floodFill(map, borderPoints, new Point(-1, i));
            floodFilled += floodFill(map, borderPoints, new Point(width, i));
        }
        for (int i = 0; i < width; i++) {
            floodFilled += floodFill(map, borderPoints, new Point(i, -1));
            floodFilled += floodFill(map, borderPoints, new Point(i, map.length));
        }

        int externalFound = 0;
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < width; x++) {
                Point checkPoint = new Point(x, y);
                if (map[y][x] == ' ' || borderPoints.contains(checkPoint)) {
                    continue;
                }

                if (isExternal(map, new HashSet<>(), checkPoint, Skew.NONE)) {
                    ++externalFound;
                } else {
                    System.out.println("Internal point: " + checkPoint);
                }
            }
        }

        printMap(map, borderPoints);

        return map.length * map[0].length - floodFilled - borderPoints.size() - externalFound;
    }

    boolean isExternal(char[][] map, Set<Point> seenPoints, Point checkPoint, Skew skew) {
        if (seenPoints.contains(checkPoint)) {
            return false;
        }

        if (checkPoint.x < 0 || checkPoint.y < 0 || checkPoint.y >= map.length || checkPoint.x >= map[0].length
                || (map[checkPoint.y][checkPoint.x] == ' ')) {
            return true;
        }

        seenPoints.add(checkPoint);

        boolean leftResults = false;
        boolean rightResults = false;
        boolean upResults = false;
        boolean downResults = false;

        boolean canCheckLeft = (checkPoint.x > 0) && skew != Skew.LEFT && skew != Skew.RIGHT;
        if (canCheckLeft) {
            Skew useSkew = skew;
            char nextLeft = map[checkPoint.y][checkPoint.x - 1];
            char nextLeftAbove = (checkPoint.y > 0) ? map[checkPoint.y - 1][checkPoint.x - 1] : ' ';
            char nextLeftBelow = (checkPoint.y < map.length - 1) ? map[checkPoint.y + 1][checkPoint.x - 1] : ' ';

            boolean process = false;
            if (nextLeft == '.' || nextLeft == ' ') {
                if (nextLeft == ' ') {
                    return true;
                }
                process = true;
                useSkew = Skew.NONE;
            } else if ((nextLeftAbove == 'J' || nextLeftAbove == '-' || nextLeftAbove == '.' || nextLeftAbove == ' ') && nextLeft == '7') {
                if (nextLeftAbove == ' ') {
                    return true;
                }
                process = true;
                useSkew = Skew.UP;
            } else if (nextLeft == 'J' && (nextLeftBelow == '-' || nextLeftBelow == '|' || nextLeftBelow == '.' || nextLeftBelow == ' ')) {
                if (nextLeftBelow == ' ') {
                    return true;
                }
                process = true;
                useSkew = Skew.DOWN;
            } else if (skew == Skew.UP && (nextLeftAbove == '-' || nextLeftAbove == '.' || nextLeftAbove == ' ') && nextLeft == '-') {
                if (nextLeftAbove == ' ') {
                    return true;
                }
                process = true;
                // no skew adjustment
            } else if (skew == Skew.DOWN && nextLeft == '-' && (nextLeftBelow == '-' || nextLeftBelow == '.' || nextLeftBelow == ' ')) {
                if (nextLeftBelow == ' ') {
                    return true;
                }
                process = true;
                // no skew adjustment
            } else if (skew == Skew.UP && nextLeftAbove == 'L' && nextLeft == 'F') {
                process = true;
                useSkew = Skew.NONE;
            } else if (skew == Skew.DOWN && nextLeft == 'L' && nextLeftBelow == 'F') {
                process = true;
                useSkew = Skew.NONE;
            }

            if (process) {
                Point leftPoint = new Point(checkPoint.x - 1, checkPoint.y);
                leftResults = isExternal(map, seenPoints, leftPoint, useSkew);
            }
        }

        boolean canCheckRight = (checkPoint.x < map[0].length - 1) && skew != Skew.LEFT && skew != Skew.RIGHT;
        if (canCheckRight) {
            Skew useSkew = skew;
            char nextRight = map[checkPoint.y][checkPoint.x + 1];
            char nextRightAbove = (checkPoint.y > 0) ? map[checkPoint.y - 1][checkPoint.x + 1] : ' ';
            char nextRightBelow = (checkPoint.y < map.length - 1) ? map[checkPoint.y + 1][checkPoint.x + 1] : ' ';

            boolean process = false;
            if (nextRight == '.' || nextRight == ' ') {
                if (nextRight == ' ') {
                    return true;
                }
                process = true;
                useSkew = Skew.NONE;
            } else if ((nextRightAbove == 'L' || nextRightAbove == '-' || nextRightAbove == '.' || nextRightAbove == ' ') && nextRight == 'F') {
                if (nextRightAbove == ' ') {
                    return true;
                }
                process = true;
                useSkew = Skew.UP;
            } else if (nextRight == 'L' && (nextRightBelow == 'F' || nextRightBelow == '-' || nextRightBelow == '.' || nextRightBelow == ' ')) {
                if (nextRightBelow == ' ') {
                    return true;
                }
                process = true;
                useSkew = Skew.DOWN;
            } else if (skew == Skew.UP && (nextRightAbove == '-' || nextRightAbove == '.' || nextRightAbove == ' ') && nextRight == '-') {
                if (nextRightAbove == ' ') {
                    return true;
                }
                process = true;
                // no skew adjustment
            } else if (skew == Skew.DOWN && nextRight == '-' && (nextRightBelow == '-' || nextRightBelow == '.' || nextRightBelow == ' ')) {
                if (nextRightBelow == ' ') {
                    return true;
                }
                process = true;
                // no skew adjustment
            } else if (skew == Skew.UP && nextRightAbove == 'J' && nextRight == '7') {
                process = true;
                useSkew = Skew.NONE;
            } else if (skew == Skew.DOWN && nextRight == 'J' && nextRightBelow == '7') {
                process = true;
                useSkew = Skew.NONE;
            }

            if (process) {
                Point rightPoint = new Point(checkPoint.x + 1, checkPoint.y);
                rightResults = isExternal(map, seenPoints, rightPoint, useSkew);
            }
        }

        boolean canCheckUp = (checkPoint.y > 0) && skew != Skew.UP && skew != Skew.DOWN;
        if (canCheckUp) {
            Skew useSkew = skew;
            char nextUp = map[checkPoint.y - 1][checkPoint.x];
            char nextUpLeft = (checkPoint.x > 0) ? map[checkPoint.y - 1][checkPoint.x - 1] : ' ';
            char nextUpRight = (checkPoint.x < map[0].length - 1) ? map[checkPoint.y - 1][checkPoint.x + 1] : ' ';

            boolean process = false;
            if (nextUp == '.' || nextUp == ' ') {
                if (nextUp == ' ') {
                    return true;
                }
                process = true;
                useSkew = Skew.NONE;
            } else if( (nextUpLeft == 'J' || nextUpLeft == '|' || nextUpLeft == '.' || nextUpLeft == ' ') && nextUp == 'L') {
                if (nextUpLeft == ' ') {
                    return true;
                }
                process = true;
                useSkew = Skew.LEFT;
            } else if (nextUp == 'J' && (nextUpRight == 'L' || nextUpRight == ' ' || nextUpRight == '.' || nextUpRight == '|')) {
                if (nextUpRight == ' ') {
                    return true;
                }
                process = true;
                useSkew = Skew.RIGHT;
            } else if (skew == Skew.LEFT && (nextUpLeft == '|' || nextUpLeft == '.' || nextUpLeft == ' ') && nextUp == '|') {
                if (nextUpLeft == ' ') {
                    return true;
                }
                process = true;
                // no skew adjustment
            } else if (skew == Skew.RIGHT && nextUp == '|' && (nextUpRight == '|' || nextUpRight == '.' || nextUpRight == ' ')) {
                if (nextUpRight == ' ') {
                    return true;
                }
                process = true;
                // no skew adjustment
            } else if (skew == Skew.LEFT && nextUpLeft == '7' && nextUp == 'F') {
                process = true;
                useSkew = Skew.NONE;
            } else if (skew == Skew.RIGHT && nextUp == '7' && nextUpRight == 'F') {
                process = true;
                useSkew = Skew.NONE;
            }

            if (process) {
                Point upPoint = new Point(checkPoint.x, checkPoint.y - 1);
                upResults = isExternal(map, seenPoints, upPoint, useSkew);
            }
        }

        boolean canCheckDown = (checkPoint.y < map.length - 1) && skew != Skew.UP && skew != Skew.DOWN;
        if (canCheckDown) {
            Skew useSkew = skew;
            char nextDown = map[checkPoint.y + 1][checkPoint.x];
            char nextDownLeft = (checkPoint.x > 0) ? map[checkPoint.y + 1][checkPoint.x - 1] : ' ';
            char nextDownRight = (checkPoint.x < map[0].length - 1) ? map[checkPoint.y + 1][checkPoint.x + 1] : ' ';

            boolean process = false;
            if (nextDown == '.' || nextDown == ' ') {
                if (nextDown == ' ') {
                    return true;
                }
                process = true;
                useSkew = Skew.NONE;
            } else if ((nextDownLeft == '7' || nextDownLeft == 'J' || nextDownLeft == '|' || nextDownLeft == '.' || nextDownLeft == ' ') && nextDown == 'F') {
                if (nextDownLeft == ' ') {
                    return true;
                }
                process = true;
                useSkew = Skew.LEFT;
            } else if (nextDown == '7' && (nextDownRight == 'F' || nextDownRight == 'L' || nextDownRight == ' ' || nextDownRight == '.' || nextDownRight == '|')) {
                if (nextDownRight == ' ') {
                    return true;
                }
                process = true;
                useSkew = Skew.RIGHT;
            } else if (skew == Skew.LEFT && (nextDownLeft == '|' || nextDownLeft == '.' || nextDownLeft == ' ') && nextDown == '|') {
                if (nextDownLeft == ' ') {
                    return true;
                }
                process = true;
                // no skew adjustment
            } else if (skew == Skew.RIGHT && (nextDown == '|' || nextDown == 'J') && (nextDownRight == '|' || nextDownRight == '.' || nextDownRight == ' ')) {
                if (nextDownRight == ' ') {
                    return true;
                }
                process = true;
                // no skew adjustment
            } else if (skew == Skew.LEFT && nextDownLeft == 'J' && nextDown == 'L') {
                process = true;
                useSkew = Skew.NONE;
            } else if (skew == Skew.RIGHT && nextDown == 'J' && nextDownRight == 'L') {
                process = true;
                useSkew = Skew.NONE;
            }

            if (process) {
                Point downPoint = new Point(checkPoint.x, checkPoint.y + 1);
                downResults = isExternal(map, seenPoints, downPoint, useSkew);
            }
        }

        return leftResults || rightResults || upResults || downResults;
    }

    int floodFill(char[][] map, Set<Point> borderPoints, Point startingPoint) {
        if (startingPoint.x == -1) {
            return floodFill(map, borderPoints, new Point(0, startingPoint.y));
        }
        if (startingPoint.x == map[0].length) {
            return floodFill(map, borderPoints, new Point(map[0].length - 1, startingPoint.y));
        }
        if (startingPoint.y == -1) {
            return floodFill(map, borderPoints, new Point(startingPoint.x, 0));
        }
        if (startingPoint.y == map.length) {
            return floodFill(map, borderPoints, new Point(startingPoint.x, map.length - 1));
        }

        if (borderPoints.contains(startingPoint) ) {
            return 0;
        }

        if (map[startingPoint.y][startingPoint.x] == ' ') {
            return 0;
        }

        map[startingPoint.y][startingPoint.x] = ' ';

        return 1
                + floodFill(map, borderPoints, new Point(startingPoint.x - 1, startingPoint.y))
                + floodFill(map, borderPoints, new Point(startingPoint.x + 1, startingPoint.y))
                + floodFill(map, borderPoints, new Point(startingPoint.x, startingPoint.y - 1))
                + floodFill(map, borderPoints, new Point(startingPoint.x, startingPoint.y + 1));
    }

    void printMap(char[][] map, Set<Point> clearPoints) {
//        if (clearPoints != null) {
//            for (Point clearPoint: clearPoints) {
//                map[clearPoint.y][clearPoint.x] = '*';
//            }
//        }

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }
}
