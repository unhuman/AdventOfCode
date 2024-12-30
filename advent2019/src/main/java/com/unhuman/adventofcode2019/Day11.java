package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;
import com.unhuman.adventofcode.aoc_framework.utility.PointHelper;
import com.unhuman.adventofcode.aoc_framework.utility.SparseMatrix;

import java.awt.Point;
import java.util.Set;

public class Day11 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    public Day11() {
        super(2019, 11, regex1, regex2);
    }

    public Day11(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        for (ItemLine line : item) {
            IntCodeParser parser = new IntCodeParser(line.toString());
            parser.setReturnOnOutput(true);
            Point robot = new Point(0, 0);
            Matrix.Direction direction = Matrix.Direction.UP;
            SparseMatrix<Character> matrix = new SparseMatrix<>(' ');
            while (!parser.hasHalted()) {
                // process to get paint
                char currentValue = matrix.get(robot);
                int input = (currentValue == ' ' || currentValue == '.') ? 0 : 1;
                parser.addInput(Integer.toString(input));
                parser.process();

                if (!parser.hasOutput()) {
                    Set<Point> points = matrix.getAllPopulatedPoints();
                    return points;
                }

                int paintColor = Integer.parseInt(parser.getOutput());
                matrix.put(robot, (paintColor) == 0 ? '.' : '#');

                // process to get rotation
                parser.process();
                Matrix.Direction.Rotation rotation = (Integer.parseInt(parser.getOutput()) == 0)
                        ? Matrix.Direction.Rotation.LEFT : Matrix.Direction.Rotation.RIGHT;
                direction = direction.rotate(rotation);
                robot = PointHelper.addPoints(robot, direction.getDirection());
            }
        }
        throw new RuntimeException("this is bad");
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        for (ItemLine line : item) {
            IntCodeParser parser = new IntCodeParser(line.toString());
            parser.setReturnOnOutput(true);
            Point robot = new Point(0, 0);
            Matrix.Direction direction = Matrix.Direction.UP;
            SparseMatrix<Character> matrix = new SparseMatrix<>(' ');
            boolean starting = true;
            while (!parser.hasHalted()) {
                // process to get paint
                char currentValue = matrix.get(robot);
                int input = (!starting && (currentValue == ' ' || currentValue == '.')) ? 0 : 1;
                starting = false;
                parser.addInput(Integer.toString(input));
                parser.process();

                if (!parser.hasOutput()) {
                    Set<Point> points = matrix.getAllPopulatedPoints();
                    System.out.println(matrix);
                    return points;
                }

                int paintColor = Integer.parseInt(parser.getOutput());
                matrix.put(robot, (paintColor) == 0 ? '.' : '#');

                // process to get rotation
                parser.process();
                Matrix.Direction.Rotation rotation = (Integer.parseInt(parser.getOutput()) == 0)
                        ? Matrix.Direction.Rotation.LEFT : Matrix.Direction.Rotation.RIGHT;
                direction = direction.rotate(rotation);
                robot = PointHelper.addPoints(robot, direction.getDirection());
            }
        }
        throw new RuntimeException("this is bad");
    }
}
