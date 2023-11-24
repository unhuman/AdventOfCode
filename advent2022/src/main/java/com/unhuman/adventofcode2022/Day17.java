package com.unhuman.adventofcode2022;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.SparseMatrix;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Day17 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    public enum Shape { HORIZ, PLUS, ELLE, VERT, BOX }
    Map<Shape, List<Point>> shapes = new HashMap<>();

    private final int FIELD_WIDTH = 7;
    private final int X_START_OFFSET = 2;
    private final int Y_START_OFFSET = 3;
    private final long TURNS = 2022;


    public Day17() {
        super(2022, 17, regex1, regex2);
        setupStuff();
    }

    public Day17(String filename) {
        super(filename, regex1, regex2);
        setupStuff();
    }

    private void setupStuff() {
        List<Point> horiz = List.of(new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0));
        shapes.put(Shape.HORIZ, horiz);
        List<Point> plus = List.of(new Point(1, -2), new Point(0, -1), new Point(1, -1), new Point(2, -1), new Point(1, 0));
        shapes.put(Shape.PLUS, plus);
        List<Point> elle = List.of(new Point(2, -2), new Point(2, -1), new Point(0, 0), new Point(1, 0), new Point(2, 0));
        shapes.put(Shape.ELLE, elle);
        List<Point> vert = List.of(new Point(0, 0), new Point(0, -1), new Point(0, -2), new Point(0, -3));
        shapes.put(Shape.VERT, vert);
        List<Point> box = List.of(new Point(0, -1), new Point(1, -1), new Point(0, 0), new Point(1, 0));
        shapes.put(Shape.BOX, box);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        return processInputInternal(dataItems1, TURNS);
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        return processInputInternal(dataItems1, 1000000000000L);
    }

    public Object processInputInternal(ConfigGroup dataItems1, Long turns) {
        String instructions = "";
        for (int groupItemIdx = 0; groupItemIdx < dataItems1.size(); groupItemIdx++) {
            GroupItem item = dataItems1.get(groupItemIdx);
            for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
                ItemLine line = item.get(lineIdx);
                for (int elementIdx = 0; elementIdx < line.size(); elementIdx++) {
                    instructions += line.get(elementIdx);
                }
            }
        }

        // set up the playing field
        SparseMatrix<Character> sparseMatrix = new SparseMatrix<>();
        for (int x = 0; x < 7; x++) {
            sparseMatrix.put(new Point(x, 0), '-');
        }

        long towerHeight = 0L;
        int rotator = 0;
        long pieces = 0L;
        int instructionIndex = 0;
        Shape currentShape;

        // Tracking data for optimizations
        HashSet<String> tracker = new HashSet<>();
        int trackerRound = 0;
        String trackerItem = null;
        Integer trackerStartHeight = null;
        Integer trackerEndHeight = null;
        long optimizationHeights = 0;
        Integer trackerPieces = 0;

        while (pieces < turns) {
            // get the next shape
            Shape shape = Shape.values()[(rotator++) % Shape.values().length];

            // This is an optimization to find extra data
            String trackerText = shape.toString() + instructionIndex;
            switch(trackerRound) {
                case 0:
                    if (!tracker.contains(trackerText)) {
                        tracker.add(trackerText);
                    } else {
                        ++trackerRound;
                    }
                    break;
                case 1:
                    if (tracker.contains(trackerText)) {
                        trackerItem = trackerText;
                        trackerStartHeight = sparseMatrix.getTopLeft().y;
                        ++trackerRound;
                    }
                    break;
                case 2:
                    ++trackerPieces;
                    if (trackerItem.equals(trackerText)) {
                        trackerEndHeight = sparseMatrix.getTopLeft().y;
                        ++trackerRound;

                        // calculate how many rounds are left
                        long roundsLeft = (turns - pieces) / trackerPieces;
                        pieces += trackerPieces * roundsLeft;
                        optimizationHeights = Math.abs(trackerEndHeight - trackerStartHeight) * roundsLeft;

// loop to process many items
//                        while (pieces + trackerPieces < turns) {
//                            pieces += trackerPieces;
//                            optimizationHeights += Math.abs(trackerEndHeight - trackerStartHeight);
//                        }
                    }
                    break;
            }

            ++pieces;

            int x = X_START_OFFSET;
            int y = sparseMatrix.getTopLeft().y - Y_START_OFFSET - 1; // bad instructions?

            while (true) {
                char instruction = instructions.charAt(instructionIndex++);
                if (instructionIndex >= instructions.length()) {
                    instructionIndex = 0;
                }

                switch(instruction) {
                    case '>':
                        if (canMove(sparseMatrix, x + 1, y, shape)) {
                            ++x;
                        }
                        break;
                    case '<':
                        if (canMove(sparseMatrix, x - 1, y, shape)) {
                            --x;
                        }
                        break;
                }

                // if we can't drop - we're done
                if (!canMove(sparseMatrix, x, y + 1, shape)) {
                    List<Point> piecePoints = shapes.get(shape);
                    for (Point point: piecePoints) {
                        sparseMatrix.put(new Point(x + point.x, y + point.y), '#');
                    }
//                    System.out.println(sparseMatrix);
                    break;
                }

                // gravity
                ++y;
            }

            // rebase the structure (find any contiguous horizontal piece
            // will need to keep everything above it
//            if (pieces % 1000 == 0) {
//                int checkLineY = sparseMatrix.getTopLeft().y;
//                boolean contiguous = false;
//                for (checkLineY = sparseMatrix.getTopLeft().y; checkLineY <= sparseMatrix.getBottomRight().y; checkLineY++) {
//                    contiguous = true;
//                    if (sparseMatrix.get(0, checkLineY) != null) {
//                        // keep track of the base where we need to reset
//                        int lowest = checkLineY;
//                        int currentHeight = lowest;
//                        for (int checkLineX = 1; checkLineX < 7; checkLineX++) {
//                            if (sparseMatrix.get(checkLineX, currentHeight - 1) != null) {
//                                --currentHeight;
//                                continue;
//                            } else if (sparseMatrix.get(checkLineX, currentHeight) != null) {
//                                continue;
//                            } else if (sparseMatrix.get(checkLineX, currentHeight + 1) != null) {
//                                ++currentHeight;
//                                lowest = Math.max(lowest, currentHeight);
//                                continue;
//                            } else {
//                                contiguous = false;
//                                break;
//                            }
//                        }
//
//                        if (contiguous) {
//                            System.out.println("found contiguous at line " + lowest);
//                            int offset = 0 - lowest;
//
//                            // Keep track of our offset for tower height
//                            towerHeight += offset;
//
//                            // recreate the sparse matrix from this point up
//                            SparseMatrix<Character> newMatrix = new SparseMatrix<>();
//                            int oldTop = sparseMatrix.getTopLeft().y;
//                            for (int copyY = lowest; copyY >= oldTop; copyY--) {
//                                for (int copyX = 0; copyX < 7; copyX++) {
//                                    newMatrix.put(copyX, copyY + offset, sparseMatrix.get(copyX, copyY));
//                                }
//                            }
//
//                            sparseMatrix = newMatrix;
//
//                            break;
//                        }
//                    }
//                }
//            }
        }
        return -sparseMatrix.getTopLeft().y + optimizationHeights;
    }




    boolean canMove(SparseMatrix matrix, int xLocation, int yLocation, Shape shape) {
        List<Point> shapePoints = shapes.get(shape);
        for (Point check: shapePoints) {
            check = new Point(check.x + xLocation, check.y + yLocation);
            // don't allow to move off sides of board
            if (check.x < 0 || check.x >= FIELD_WIDTH) {
                return false;
            }
            if (matrix.get(check) != null) {
                return false;
            }
        }
        return true;
    }
}
