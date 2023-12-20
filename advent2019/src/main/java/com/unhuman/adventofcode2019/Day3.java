package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.SparseMatrix;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Day3 extends InputParser {
    private static final String regex1 = "([LRUD])(\\d+),?";
    private static final String regex2 = null;

    public Day3() {
        super(2019, 3, regex1, regex2);
    }

    public Day3(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Integer minDistance = null;
        SparseMatrix<Character> matrix = new SparseMatrix<>();
        Point port = new Point(0, 0);
        matrix.put(port, 'o');
        for (int row = 0; row < configGroup.get(0).size(); row++) {
            ItemLine line = configGroup.get(0).get(row);

            int currentX = 0;
            int currentY = 0;

            for (int i = 0; i < line.size(); i += 2) {
                char direction = line.get(i).charAt(0);
                int length = Integer.parseInt(line.get(i + 1));

                for (int run = 0; run < length; run++) {
                    switch (direction) {
                        case 'L' -> --currentX;
                        case 'R' -> ++currentX;
                        case 'U' -> --currentY;
                        case 'D' -> ++currentY;
                    }

                    Point check = new Point(currentX, currentY);
                    if (row != 0) {
                        Character examine = matrix.get(check);
                        if (examine != null) {
                            int crossingDistance = Math.abs(check.x - port.x) + Math.abs(check.y - port.y);
                            if ((minDistance == null) || (crossingDistance < minDistance)) {
                                minDistance = crossingDistance;
                            }
                        }
                    } else {
                        matrix.put(check, '*');
                    }
                }
            }
        }

        return minDistance;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Integer minDistance = null;

        List<LinkedHashMap<Point, Integer>> paths = new ArrayList<>();

        for (int row = 0; row < configGroup.get(0).size(); row++) {
            paths.add(new LinkedHashMap<>());
            ItemLine line = configGroup.get(0).get(row);

            int currentX = 0;
            int currentY = 0;
            int distance = 0;

            for (int i = 0; i < line.size(); i += 2) {
                char direction = line.get(i).charAt(0);
                int length = Integer.parseInt(line.get(i + 1));

                for (int run = 0; run < length; run++) {
                    ++distance;
                    switch (direction) {
                        case 'L' -> --currentX;
                        case 'R' -> ++currentX;
                        case 'U' -> --currentY;
                        case 'D' -> ++currentY;
                    }

                    Point check = new Point(currentX, currentY);

                    if (!paths.get(row).containsKey(check)) {
                        paths.get(row).put(check, distance);
                    }

                    if (row == 1) {
                        if (paths.get(0).containsKey(check)) {
                            int calculatedDistance = distance + paths.get(0).get(check);
                            if ((minDistance == null) || calculatedDistance < minDistance) {
                                minDistance = calculatedDistance;
                            }
                        }
                    }
                }
            }
        }

        return minDistance;
    }
}
