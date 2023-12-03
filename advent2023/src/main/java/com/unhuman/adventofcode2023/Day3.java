package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day3 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    public Day3() {
        super(2023, 3, regex1, regex2);
    }

    public Day3(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int total = 0;
        for (GroupItem item : configGroup) {
            int lineCount = item.size();
            int lineLength = item.get(0).size();
            Map<Point, Character> data = new HashMap<>();
            for (int y = 0; y < lineCount; y++) {
                ItemLine line = item.get(y);
                for (int x = 0; x < lineLength; x++) {
                    Point p = new Point(x, y);
                    char element = line.get(x).charAt(0);
                    data.put(p, element);
                }
            }

            Set<Point> alreadyPartedPoints = new HashSet<>();
            for (int y = 0; y < lineCount; y++) {
                for (int x = 0; x < lineLength; x++) {
                    Point p = new Point(x, y);
                    List<Integer> partNumbers = findPartNumbers(data, lineLength, lineCount, p, alreadyPartedPoints);
                    for (Integer partNumber : partNumbers) {
                        total += partNumber;
                    }
                }
            }
        }

        return total;
    }

    boolean isCharInteger(char check) {
        return (check >= '0' && check <= '9');
    }

    List<Integer> findPartNumbers(Map<Point, Character> data, int dataWidth, int dataHeight, Point currentPoint, Set<Point> alreadyPartedPoints) {
        char check = data.get(currentPoint);
        // The point we have must be a symbol (non ., non-number)
        if (check == '.' || (check >= '0' && check <= '9')) {
            return Collections.emptyList();
        }

        List<Integer> results = new ArrayList<>();
        for (int y = Math.max(0, currentPoint.y - 1); y < Math.min(dataHeight, currentPoint.y + 2 ); y++) { // skip the middle
            for (int x = Math.max(0, currentPoint.x - 1); x < Math.min(dataWidth, currentPoint.x + 2 ); x += (y == currentPoint.y) ? 2 : 1) {

                Point checkPoint = new Point(x, y);

                // skip already processed data
                if (alreadyPartedPoints.contains(checkPoint)) {
                    continue;
                }
                alreadyPartedPoints.add(checkPoint);

                // skip not numbers
                char checkValue = data.get(checkPoint);
                if (!isCharInteger(checkValue)) {
                    continue;
                }

                // determine if we have a number
                int startX = x;
                int endX = x;
                for (int scanX = x - 1; scanX >= 0; scanX--) {
                    if (alreadyPartedPoints.contains(new Point(scanX, y))
                            || !isCharInteger(data.get(new Point(scanX, y)))) {
                        break;
                    }
                    startX = scanX;
                    alreadyPartedPoints.add(new Point(scanX, y));
                }
                for (int scanX = x + 1; scanX < dataWidth; scanX++) {
                    if (alreadyPartedPoints.contains(new Point(scanX, y))
                            || !isCharInteger(data.get(new Point(scanX, y)))) {
                        break;
                    }
                    endX = scanX;
                    alreadyPartedPoints.add(new Point(scanX, y));
                }

                Integer partNum = 0;
                for (int i = startX; i <= endX; i++) {
                    partNum = partNum * 10 + data.get(new Point(i, y)) - '0';
                }
                results.add(partNum);
            }

        }
        return results;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int total = 0;
        for (GroupItem item : configGroup) {
            int lineCount = item.size();
            int lineLength = item.get(0).size();
            Map<Point, Character> data = new HashMap<>();
            for (int y = 0; y < lineCount; y++) {
                ItemLine line = item.get(y);
                for (int x = 0; x < lineLength; x++) {
                    Point p = new Point(x, y);
                    char element = line.get(x).charAt(0);
                    data.put(p, element);
                }
            }

            Set<Point> alreadyPartedPoints = new HashSet<>();
            for (int y = 0; y < lineCount; y++) {
                for (int x = 0; x < lineLength; x++) {
                    Point p = new Point(x, y);
                    List<Integer> partNumbers = findGearRatios(data, lineLength, lineCount, p, alreadyPartedPoints);
                    for (Integer partNumber : partNumbers) {
                        total += partNumber;
                    }
                }
            }
        }

        return total;
    }

    List<Integer> findGearRatios(Map<Point, Character> data, int dataWidth, int dataHeight, Point currentPoint, Set<Point> alreadyPartedPoints) {
        char check = data.get(currentPoint);
        // Only process gears
        if (check != '*') {
            return Collections.emptyList();
        }

        List<Integer> results = new ArrayList<>();
        for (int y = Math.max(0, currentPoint.y - 1); y < Math.min(dataHeight, currentPoint.y + 2 ); y++) { // skip the middle
            for (int x = Math.max(0, currentPoint.x - 1); x < Math.min(dataWidth, currentPoint.x + 2 ); x += (y == currentPoint.y) ? 2 : 1) {

                Point checkPoint = new Point(x, y);

                // skip already processed data
                if (alreadyPartedPoints.contains(checkPoint)) {
                    continue;
                }
                alreadyPartedPoints.add(checkPoint);

                // skip not numbers
                char checkValue = data.get(checkPoint);
                if (!isCharInteger(checkValue)) {
                    continue;
                }

                // determine if we have a number
                int startX = x;
                int endX = x;
                for (int scanX = x - 1; scanX >= 0; scanX--) {
                    if (alreadyPartedPoints.contains(new Point(scanX, y))
                            || !isCharInteger(data.get(new Point(scanX, y)))) {
                        break;
                    }
                    startX = scanX;
                    alreadyPartedPoints.add(new Point(scanX, y));
                }
                for (int scanX = x + 1; scanX < dataWidth; scanX++) {
                    if (alreadyPartedPoints.contains(new Point(scanX, y))
                            || !isCharInteger(data.get(new Point(scanX, y)))) {
                        break;
                    }
                    endX = scanX;
                    alreadyPartedPoints.add(new Point(scanX, y));
                }

                Integer partNum = 0;
                for (int i = startX; i <= endX; i++) {
                    partNum = partNum * 10 + data.get(new Point(i, y)) - '0';
                }
                results.add(partNum);
            }

        }

        if (results.size() > 1) {
            return Collections.singletonList(results.stream().mapToInt(x->x).reduce(1, Math::multiplyExact));
        }

        return Collections.singletonList(0);
    }
}

