package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;
import com.unhuman.adventofcode.aoc_framework.utility.SparseMatrix;

import java.awt.Point;
import java.util.ArrayList;

public class Day9 extends InputParser {
    private static final String regex1 = "(\\d+),(\\d+)";
    private static final String regex2 = null;

    public Day9() {
        super(2025, 9, regex1, regex2);
    }

    public Day9(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.getFirst();
        ArrayList<Pair<Long, Long>> points = new ArrayList<>();
        for (ItemLine line : group0) {
            points.add(new Pair(line.getLong(0), line.getLong(1)));
        }

        long maxArea = 0L;
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {
                long area = (Math.abs(points.get(j).getLeft() - points.get(i).getLeft()) + 1)
                        * (Math.abs(points.get(j).getRight() - points.get(i).getRight()) + 1);
                if (area > maxArea) {
                    maxArea = area;
                }
            }
        }

        // Here's code for a 2nd group, if needed
//        GroupItem group1 = configGroup1.get(0);
//        for (ItemLine line : group1) {
//            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
////                char value = line.getChar(itemNum);
////                String value = line.getString(itemNum);
////                Long value = line.getLong(itemNum);
//            }
//        }

        return maxArea;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.getFirst();
        ArrayList<Point> points = new ArrayList<>();
        for (ItemLine line : group0) {
            points.add(new Point(line.getInt(0), line.getInt(1)));
        }

        SparseMatrix<Character> matrix = new SparseMatrix<>();
        for (int i = 0; i < points.size(); i++) {
            Point start = points.get(i);
            Point end = points.get((i + 1) % points.size());
            for (int x = Math.min(start.x, end.x); x <= Math.max(start.x, end.x); x++) {
                for (int y = Math.min(start.y, end.y); y <= Math.max(start.y, end.y); y++) {
                    matrix.put(x, y, 'X');
                }
            }
            matrix.put(start, '#');
            matrix.put(end, '#');
        }

        System.out.println(matrix);
        long maxArea = 0L;
        System.out.println(Math.pow(points.size(), 2));
        for (int i = 0; i < points.size() - 1; i++) {
            System.out.println("Round " + i + " of " + points.size());
            for (int j = i + 1; j < points.size(); j++) {
                Point pointOne = points.get(i);
                Point pointTwo = points.get(j);

                // check overall area before we go digging into the valid area
                Long checkArea = ((long) Math.abs(pointTwo.x - pointOne.x) + 1)
                        * ((long) Math.abs(pointTwo.y - pointOne.y) + 1);

                // Check the corners
                if (checkArea > maxArea) {
                    if (matrix.get(pointOne.x, pointOne.y) == null || matrix.get(pointTwo.x, pointTwo.y) == null
                            || (matrix.get(pointOne.x, pointTwo.y) == null && !matrix.isInternalPoint(pointOne.x, pointTwo.y))
                            || (matrix.get(pointTwo.x, pointOne.y) == null && !matrix.isInternalPoint(pointTwo.x, pointOne.y))) {
                        checkArea = 0L;
                    }
                }

                // Left
                if (checkArea > maxArea) {
                    boolean priorGood = false;
                    for (int y = Math.min(pointOne.y, pointTwo.y); y <= Math.max(pointOne.y, pointTwo.y); y++) {
                        int x = Math.min(pointOne.x, pointTwo.x);

                        // if we are going down the left side, if we aren't a marker, and there is a marker to the right of last
                        // then we have a bad placement
                        if (matrix.get(x, y) != null) {
                            priorGood = false;
                        } else { // null
                            Character edgeCheck = matrix.get(x + 1, y - 1);
                            if (edgeCheck != null && !priorGood) {
                                checkArea = 0L;
                                break;
                            } else {
                                priorGood = true;
                            }
                        }
                    }
                }

                // Top
                if (checkArea > maxArea) {
                    boolean priorGood = false;
                    for (int x = Math.min(pointOne.x, pointTwo.x); x <= Math.max(pointOne.x, pointTwo.x); x++) {
                        int y = Math.min(pointOne.y, pointTwo.y);

                        // if we are going across the top, if we aren't a marker, and there is a marker below last
                        // then we have a bad placement
                        if (matrix.get(x, y) != null) {
                            priorGood = false;
                        } else { // null
                            Character edgeCheck = matrix.get(x - 1, y + 1);
                            if (edgeCheck != null && !priorGood) {
                                checkArea = 0L;
                                break;
                            } else {
                                priorGood = true;
                            }
                        }
                    }
                }

                // Right
                if (checkArea > maxArea) {
                    boolean priorGood = false;
                    for (int y = Math.min(pointOne.y, pointTwo.y); y <= Math.max(pointOne.y, pointTwo.y); y++) {
                        int x = Math.max(pointOne.x, pointTwo.x);

                        // if we are going down the right side, if we aren't a marker, and there is a marker to the left of last
                        // then we have a bad placement
                        if (matrix.get(x, y) != null) {
                            priorGood = false;
                        } else { // null
                            Character edgeCheck = matrix.get(x - 1, y - 1);
                            if (edgeCheck != null && !priorGood) {
                                checkArea = 0L;
                                break;
                            } else {
                                priorGood = true;
                            }
                        }
                    }
                }

                // Bottom
                if (checkArea > maxArea) {
                    boolean priorGood = false;
                    for (int x = Math.min(pointOne.x, pointTwo.x); x <= Math.max(pointOne.x, pointTwo.x); x++) {
                        int y = Math.max(pointOne.y, pointTwo.y);

                        // if we are going across the bottom, if we aren't a marker, and there is a marker above last
                        // then we have a bad placement
                        if (matrix.get(x, y) != null) {
                            priorGood = false;
                        } else { // null
                            Character edgeCheck = matrix.get(x - 1, y - 1);
                            if (edgeCheck != null && !priorGood) {
                                checkArea = 0L;
                                break;
                            } else {
                                priorGood = true;
                            }
                        }
                    }
                }

// OLD WAY
//                if (checkArea > maxArea) {
//                    for (int x = Math.min(pointOne.x, pointTwo.x); x <= Math.max(pointOne.x, pointTwo.x); x++) {
//                        for (int y = Math.min(pointOne.y, pointTwo.y); y <= Math.max(pointOne.y, pointTwo.y); y++) {
//                            if (matrix.get(x, y) == null && !matrix.isInternalPoint(x, y)) {
//                                // this guy is bad.
//                                checkArea = 0L;
//                                x = Math.max(pointOne.x, pointTwo.x) + 1;
//                                y = Math.max(pointOne.y, pointTwo.y) + 1;
//                            }
//                        }
//                    }
//                }
//                System.out.println(" Contained area: " + checkArea);
                if (checkArea > maxArea) {
                    System.out.println("assigning Max: " + checkArea);
                    maxArea = checkArea;
                }
            }
        }


        return maxArea;
    }
}
