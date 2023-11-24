package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day9 extends InputParser {
    private static final String regex1 = "(\\w) (\\d+)";
    private static final String regex2 = null;

    public Day9() {
        super(2022, 9, regex1, regex2);
    }

    public Day9(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        final int bodyUnits = 1;
        return processInput(dataItems1, bodyUnits);
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        final int bodyUnits = 9;
        return processInput(dataItems1, bodyUnits);
    }

    private int processInput(ConfigGroup dataItems1, int bodyUnits) {
        HashSet<Point> trailJourney = new HashSet<>();
        List<Point> bodyTrail = new ArrayList<>();
        Point head = new Point(0, 0);
        for (int i = 0; i < bodyUnits; i++) {
            bodyTrail.add(new Point(head));
        }

        for (GroupItem item : dataItems1) {
            for (ItemLine line : item) {
                String command = line.get(0);
                int distance = Integer.parseInt(line.get(1));

                for (int i = 0; i < distance; i++) {
                    switch (command) {
                        case "U":
                            head.y--;
                            break;
                        case "D":
                            head.y++;
                            break;
                        case "L":
                            head.x--;
                            break;
                        case "R":
                            head.x++;
                            break;
                    }

                    // track the prior segment for moving subsequent segments
                    Point priorSegment = head;

                    for (int j = 0; j < bodyTrail.size(); j++) {
                        Point currentSegment = bodyTrail.get(j);

                        // Calculate diff between where prior segment is to where this one is
                        int xDelta = priorSegment.x - currentSegment.x;
                        int yDelta = priorSegment.y - currentSegment.y;

                        // adjust the current location
                        if (Math.abs(xDelta) == 2 || Math.abs(yDelta) == 2) {
                            // we move prior segment to where the prior segment used to be
                            // Account for partial shifts
                            currentSegment.x += xDelta / 2 + ((Math.abs(xDelta) == 1) ? xDelta : 0);
                            currentSegment.y += yDelta / 2 + ((Math.abs(yDelta) == 1) ? yDelta : 0);
                        }

                        // track the last segment
                        priorSegment = currentSegment;
                    }

                    // Append the current tail location to the journey
                    trailJourney.add(new Point(priorSegment));
                }
            }
        }

        return trailJourney.size();
    }
}
