package com.unhuman.adventofcode2022;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day12 extends InputParser {
    private static final String regex1 = "(\\w)";
    private static final String regex2 = null;

    public Day12() {
        super(2022, 12, regex1, regex2);
    }

    public Day12(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        Point start = null;
        Point destination = null;
        for (GroupItem item : dataItems1) {
            MapElement[][] map = new MapElement[item.size()][item.get(0).size()];
            for (int y = 0; y < item.size(); y++) {
                ItemLine line = item.get(y);
                for (int x = 0; x < line.size(); x++) {
                    String element = line.get(x);
                    MapElement point = new MapElement(element);
                    map[y][x] = point;
                    if (point.elevation == 'S') {
                        start = new Point(x, y);
                    }
                    if (point.elevation == 'E') {
                        destination = new Point(x, y);
                    }
                }
            }

            return navigateStart(map, destination, start, 0);
        }
        return 1;
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        List<Point> starts = new ArrayList<>();
        Point destination = null;
        for (GroupItem item : dataItems1) {
            MapElement[][] map = new MapElement[item.size()][item.get(0).size()];
            for (int y = 0; y < item.size(); y++) {
                ItemLine line = item.get(y);
                for (int x = 0; x < line.size(); x++) {
                    String element = line.get(x);
                    MapElement point = new MapElement(element);
                    map[y][x] = point;
                    if (point.elevation == 'S' || point.elevation == 'a') {
                        starts.add(new Point(x, y));
                    }
                    if (point.elevation == 'E') {
                        destination = new Point(x, y);
                    }
                }
            }
            Integer[] scores = new Integer[starts.size()];
            int i = 0;
            for (Point start: starts) {
                scores[i++] = navigateStart(map, destination, start, 0);
            }
            Arrays.sort(scores);
            return scores[0];
        }

        return null;
    }

    public int navigateStart(MapElement[][] map, Point destination, Point from, int counter) {
        Integer[] directions = new Integer[4];
        for (int i = 0; i < 4; i++) {
            int xOffset = (i < 2) ? i * 2 - 1 : 0;
            int yOffset = (i >= 2) ? (i - 2) * 2 - 1 : 0;
            directions[i] = navigateStep(map, destination, from,
                    new Point(from.x + xOffset, from.y + yOffset), counter);
        }

        // pursue only the fastest route
        Arrays.sort(directions);
        return directions[0];
    }

    public int navigateStep(MapElement[][] map, Point destination, Point from, Point check, int counter) {
        Integer newCounter = canGo(map, from, check, counter);
        // Stop when we find what we are looking for
        if (newCounter != null && check.equals(destination)) {
            return newCounter;
        }
        // return ridiculously high value when we can't go somewhere
        return (newCounter != null) ? navigateStart(map, destination, check, newCounter) : Integer.MAX_VALUE;
    }

    /**
     * Attempt to go to a square
     * @param map
     * @param from
     * @param to
     * @param counter
     * @return distance if we could go there, else null
     */
    public Integer canGo(MapElement[][] map, Point from, Point to, int counter) {
        // we only return counter if we do something, so increment first thing
        counter++;

        // we can never go off the border
        if (to.x < 0 || to.x >= map[0].length || to.y < 0 || to.y >= map.length) {
            return null;
        }
        MapElement start = map[from.y][from.x];
        MapElement end = map[to.y][to.x];

        // We can never go back to start
        if (end.elevation == 'S') {
            return null;
        }

        // if we have another path that has already gotten us here quicker (or same), we don't pursue this path
        if (end.distance <= counter) {
            return null;
        }

        // if we got to the end or are either going down or only up at most 1
        if (end.elevation == 'a'
                || (end.elevation != 'E' && end.elevation - start.elevation <= 1)
                || (end.elevation == 'E' && start.elevation == 'y' || start.elevation == 'z')) {
            end.from = from;
            end.distance = counter;

            return counter;
        }

        return null;
    }

    class MapElement {
        public MapElement(String item) {
            this.elevation = item.charAt(0);
        }
        char elevation;

        Point from = null;
        Integer distance = Integer.MAX_VALUE;
    }
}
