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

    enum Facing { RIGHT, DOWN, LEFT, UP }

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
                    currentLocation = new Point( elementIdx, lineIdx);
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
     * @param map
     * @param currentLocation
     * @param facing
     * @return
     */
    boolean advance(char[][] map, Point currentLocation, Facing facing) {
        int xIncrement = 0;
        int yIncrement = 0;
        switch(facing) {
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
                    currentLocation = new Point( elementIdx, lineIdx);
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
     * @param map
     * @param currentLocation
     * @param facing
     * @return
     */
    boolean advance2(char[][] map, Point currentLocation, AtomicReference<Facing> facing) {
        // Both the example and the real data have a 3/4 arrangement of cubes.
        // So, we can use that to figure out the size of each side
        int cubeSize = Math.abs(map.length - map[0].length);

        int xIncrement = 0;
        int yIncrement = 0;
        switch(facing.get()) {
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

            // TODO: Adjust how we determine edges of the cube and teleport and adjust facing

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

            // Check if we are out of bounds and teleport to another place
            if (map[checkPoint.y][checkPoint.x] == ' ') {

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
}
