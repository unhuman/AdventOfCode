package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.SparseMatrix;

import java.awt.*;
import java.math.BigInteger;
import java.util.HashMap;
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


    public Day17(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
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
        return processInputInternal(dataItems1, BigInteger.valueOf(TURNS));
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        return processInputInternal(dataItems1, BigInteger.valueOf(1000000000000L));
    }

    public Object processInputInternal(ConfigGroup dataItems1, BigInteger turns) {
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

        int rotator = 0;
        BigInteger pieces = BigInteger.valueOf(0L);
        int instructionIndex = 0;
        Shape currentShape;
        while (pieces.compareTo(turns) < 0) {
            // get the next shape
            Shape shape = Shape.values()[(rotator++) % Shape.values().length];
            pieces = pieces.add(BigInteger.ONE);

            int x = X_START_OFFSET;
            int y = sparseMatrix.getTopLeft().y - Y_START_OFFSET - 1; // bad instructions?

            while (true) {
                // Get the next instruction
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
        }

        return -sparseMatrix.getTopLeft().y;

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
