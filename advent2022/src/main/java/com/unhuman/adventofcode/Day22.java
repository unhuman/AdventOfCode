package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class Day22 extends InputParser {
    private static final String regex1 = "([ .#])";
    private static final String regex2 = "(\\d+)(\\w)?";

    enum Facing {RIGHT, DOWN, LEFT, UP}

    public Day22(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        GroupItem item = dataItems1.get(0);

        int maxLineSize = 0;
        for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
            ItemLine line = item.get(lineIdx);
            maxLineSize = (line.size() > maxLineSize) ? line.size() : maxLineSize;
        }

        Point currentLocation = null;

        char map[][] = new char[item.size()][maxLineSize];
        for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
            ItemLine line = item.get(lineIdx);
            for (int elementIdx = 0; elementIdx < maxLineSize; elementIdx++) {
                // fill up array, and put spaces where missing data
                char character = (elementIdx < line.size()) ? line.get(elementIdx).charAt(0) : ' ';
                map[lineIdx][elementIdx] = character;
                if (currentLocation == null && character != ' ') {
                    currentLocation = new Point(elementIdx, lineIdx);
                }
            }
        }

        item = dataItems2.get(0);
        Facing facing = Facing.RIGHT;
        for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
            ItemLine line = item.get(lineIdx);
            for (int elementIdx = 0; elementIdx < line.size(); elementIdx += 2) {
                Integer advancement = Integer.parseInt(line.get(elementIdx));
                for (int i = 0; i < advancement; i++) {
                    if (!advance(map, currentLocation, facing)) {
                        break;
                    }
                }

                Facing nextFacing = facing;
                if (line.get(elementIdx + 1) != null) {
                    char rotation = line.get(elementIdx + 1).charAt(0);
                    switch (rotation) {
                        case 'R':
                            nextFacing = Facing.values()[(facing.ordinal() + 1) % Facing.values().length];
                            break;
                        case 'L':
                            nextFacing = Facing.values()[(facing.ordinal() - 1) >= 0 ? facing.ordinal() - 1 : Facing.values().length - 1];
                            break;
                    }
                }

                facing = nextFacing;
            }
        }


        // rows and columns
        return 1000 * (currentLocation.y + 1) + 4 * (currentLocation.x + 1) + facing.ordinal();
    }

    /**
     * Returns true if we did advance, false if otherwise.
     *
     * @param map
     * @param currentLocation
     * @param facing
     * @return
     */
    boolean advance(char[][] map, Point currentLocation, Facing facing) {
        int xIncrement = 0;
        int yIncrement = 0;
        switch (facing) {
            case RIGHT:
                xIncrement = 1;
                break;
            case DOWN:
                yIncrement = 1;
                break;
            case LEFT:
                xIncrement = -1;
                break;
            case UP:
                yIncrement = -1;
                break;
        }

        Point checkPoint = new Point(currentLocation);
        while (true) {
            checkPoint = new Point(checkPoint.x + xIncrement, checkPoint.y + yIncrement);
            if (checkPoint.x < 0) {
                checkPoint.x = map[0].length - 1;
            }
            if (checkPoint.x == map[0].length) {
                checkPoint.x = 0;
            }
            if (checkPoint.y < 0) {
                checkPoint.y = map.length - 1;
            }
            if (checkPoint.y == map.length) {
                checkPoint.y = 0;
            }

            if (map[checkPoint.y][checkPoint.x] == '.') {
                currentLocation.setLocation(checkPoint);
                return true;
            }

            if (map[checkPoint.y][checkPoint.x] == '#') {
                return false;
            }

            // next iteration will find something
        }
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        GroupItem item = dataItems1.get(0);

        int mapWidth = 0;
        for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
            ItemLine line = item.get(lineIdx);
            mapWidth = (line.size() > mapWidth) ? line.size() : mapWidth;
        }

        Point currentLocation = null;

        // Save off the size
        int mapHeight = item.size();
        char map[][] = new char[mapHeight][mapWidth];

        for (int lineIdx = 0; lineIdx < mapHeight; lineIdx++) {
            ItemLine line = item.get(lineIdx);
            for (int elementIdx = 0; elementIdx < mapWidth; elementIdx++) {
                // fill up array, and put spaces where missing data
                char character = (elementIdx < line.size()) ? line.get(elementIdx).charAt(0) : ' ';
                map[lineIdx][elementIdx] = character;
                if (currentLocation == null && character != ' ') {
                    currentLocation = new Point(elementIdx, lineIdx);
                }
            }
        }

        item = dataItems2.get(0);
        AtomicReference<Facing> facing = new AtomicReference<>(Facing.RIGHT);
        for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
            ItemLine line = item.get(lineIdx);
            for (int elementIdx = 0; elementIdx < line.size(); elementIdx += 2) {
                Integer advancement = Integer.parseInt(line.get(elementIdx));

                for (int i = 0; i < advancement; i++) {
                    if (!advance2(map, currentLocation, facing)) {
                        break;
                    }
                }

                Facing nextFacing = facing.get();
                if (line.get(elementIdx + 1) != null) {
                    Character rotation = line.get(elementIdx + 1).charAt(0);
                    switch (rotation) {
                        case 'R':
                            nextFacing = Facing.values()[(facing.get().ordinal() + 1) % Facing.values().length];
                            break;
                        case 'L':
                            nextFacing = Facing.values()[(facing.get().ordinal() - 1) >= 0 ? facing.get().ordinal() - 1 : Facing.values().length - 1];
                            break;
                    }
                }

                facing.set(nextFacing);
            }
        }

        // rows and columns
        return 1000 * (currentLocation.y + 1) + 4 * (currentLocation.x + 1) + facing.get().ordinal();
    }

    /**
     * Returns true if we did advance, false if otherwise.
     *
     * @param map
     * @param currentLocation
     * @param facing
     * @return
     */
    boolean advance2(char[][] map, Point currentLocation, AtomicReference<Facing> facing) {
        int cubeSize = Math.abs(map.length - map[0].length);
        while (true) {
            Point checkPoint = new Point(currentLocation);
            AtomicReference<Facing> checkFacing = new AtomicReference<>(facing.get());
            int xIncrement = 0;
            int yIncrement = 0;
            switch (checkFacing.get()) {
                case RIGHT:
                    xIncrement = 1;
                    break;
                case DOWN:
                    yIncrement = 1;
                    break;
                case LEFT:
                    xIncrement = -1;
                    break;
                case UP:
                    yIncrement = -1;
                    break;
            }

            checkPoint = new Point(checkPoint.x + xIncrement, checkPoint.y + yIncrement);

            if (checkPoint.x < 0 || checkPoint.x == map[0].length
                    || checkPoint.y < 0 || checkPoint.y == map.length
                    || map[checkPoint.y][checkPoint.x] == ' ') {
                if (map.length < 100) {
                    handleTeleportForTest(cubeSize, checkPoint, checkFacing);
                } else {
                    handleTeleportForProblem(cubeSize, checkPoint, checkFacing);
                }
            }

            if (map[checkPoint.y][checkPoint.x] == '.') {
                currentLocation.setLocation(checkPoint);
                facing.set(checkFacing.get());
                System.out.println("Plotting: " + checkPoint.x + " / " + checkPoint.y + " Direction: " + facing.get());
                return true;
            }

            if (map[checkPoint.y][checkPoint.x] == '#') {
                return false;
            }

            // next iteration will find something
        }
    }

    void handleTeleportForTest(int cubeSize, Point checkPoint, AtomicReference<Facing> facing) {

        // Cube 3 (top) - move left to 6
        if (checkPoint.y >= 0 && checkPoint.y < cubeSize && checkPoint.x == cubeSize - 1 && facing.get() == Facing.LEFT) {
            facing.set(Facing.DOWN);
            checkPoint.x = cubeSize + checkPoint.y;
            checkPoint.y = cubeSize;
        }
        // Cube 3 (top) - move right to 12
        if (checkPoint.y >= 0 && checkPoint.y < cubeSize && checkPoint.x == cubeSize * 3 && facing.get() == Facing.RIGHT) {
            facing.set(Facing.LEFT);
            checkPoint.x = cubeSize * 3 - 1;
            checkPoint.y = cubeSize * 3 - 1 - checkPoint.y;
        }
        // Cube 3 (top) - move up to 5
        if (checkPoint.y < 0 && facing.get() == Facing.UP) {
            facing.set(Facing.DOWN);
            checkPoint.x = cubeSize * 3 - checkPoint.x;
            checkPoint.y = cubeSize;
        }

        // Cube 5 - left to 12
        if (checkPoint.x < 0 && facing.get() == Facing.LEFT) {
            facing.set(Facing.UP);
            checkPoint.x = cubeSize * 4 - 1 - checkPoint.y;
            checkPoint.y = cubeSize * 3 - 1;
        }
        // Cube 5 - move up to 3
        if (checkPoint.x < cubeSize && checkPoint.y < cubeSize && facing.get() == Facing.UP) {
            facing.set(Facing.DOWN);
            checkPoint.x = cubeSize * 3 - 1 - checkPoint.x;
            checkPoint.y = 0;
        }
        // Cube 5 - move down to 11
        if (checkPoint.y == cubeSize * 2 && facing.get() == Facing.DOWN) {
            facing.set(Facing.UP);
            checkPoint.x = cubeSize * 3 - 1 - checkPoint.x;
            checkPoint.y = cubeSize * 3 - 1;
        }

        // Cube 6 - move up to 3
        if (checkPoint.x >= cubeSize && checkPoint.x < cubeSize * 2 && checkPoint.y < cubeSize && facing.get() == Facing.UP) {
            facing.set(Facing.RIGHT);
            checkPoint.y = checkPoint.x - cubeSize;
            checkPoint.x = cubeSize * 2;
        }
        // Cube 6 - move down to 11
        if (checkPoint.x >= cubeSize && checkPoint.x < cubeSize * 2 && checkPoint.y == cubeSize * 2 && facing.get() == Facing.DOWN) {
            facing.set(Facing.RIGHT);
            checkPoint.y = cubeSize * 3 - 1 - checkPoint.x;
            checkPoint.x = cubeSize * 2;
        }

        // Cube 6 - move down to 11
        if (checkPoint.x >= cubeSize && checkPoint.x < cubeSize * 2 && checkPoint.y == cubeSize * 2 && facing.get() == Facing.DOWN) {
            facing.set(Facing.RIGHT);
            checkPoint.y = cubeSize * 3 - 1 - checkPoint.x;
            checkPoint.x = cubeSize * 2;
        }

        // Cube 7 - move right to 12
        if (checkPoint.x == cubeSize * 3&& checkPoint.y >= cubeSize && checkPoint.y < cubeSize * 2 && facing.get() == Facing.RIGHT) {
            facing.set(Facing.DOWN);
            checkPoint.x = cubeSize * 3 + (cubeSize * 2 - 1 - checkPoint.y);
            checkPoint.y = cubeSize * 2;
        }

        // Cube 11 (bottom) - left to 6
        if (checkPoint.y >= cubeSize * 2 && checkPoint.x == cubeSize * 2 - 1 && facing.get() == Facing.LEFT) {
            facing.set(Facing.UP);
            checkPoint.x = cubeSize * 2 - 1 - (checkPoint.y - cubeSize * 2);
            checkPoint.y = cubeSize * 2 - 1;
        }

        // Cube 11 (bottom) - down to 5
        if (checkPoint.y == cubeSize * 3 && checkPoint.x >= cubeSize * 2 && checkPoint.x < cubeSize * 3 && facing.get() == Facing.DOWN) {
            facing.set(Facing.UP);
            checkPoint.x = cubeSize * 3 - 1 - checkPoint.x;
            checkPoint.y = cubeSize * 2 - 1;
        }

        // Cube 12 - move up to 7
        if (checkPoint.x >= cubeSize * 3 && checkPoint.x < cubeSize * 4 && checkPoint.y == cubeSize * 2 - 1 && facing.get() == Facing.UP) {
            facing.set(Facing.LEFT);
            checkPoint.y = cubeSize * 2 - 1 - (cubeSize * 3 - 1 - checkPoint.x);
            checkPoint.x = cubeSize * 3 - 1;
        }
        // Cube 12 - move down to 5
        if (checkPoint.y == cubeSize * 3 && checkPoint.x >= cubeSize * 3 && checkPoint.x < cubeSize * 4 && facing.get() == Facing.UP) {
            facing.set(Facing.RIGHT);
            checkPoint.y = cubeSize + (cubeSize * 4 - 1 - checkPoint.x);
            checkPoint.x = 0;
        }
        // Cube 12 - move right to 3
        if (checkPoint.x == cubeSize * 4 && checkPoint.y >= cubeSize * 2 && facing.get() == Facing.RIGHT) {
            facing.set(Facing.LEFT);
            checkPoint.y = cubeSize * 3 - 1 - checkPoint.y;
            checkPoint.x = cubeSize * 3 - 1;
        }
    }

    void handleTeleportForProblem(int cubeSize, Point checkPoint, AtomicReference<Facing> facing) {
    }
}
