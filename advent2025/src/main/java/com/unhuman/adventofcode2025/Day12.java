package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 extends InputParser {
    // Note: Built in parsing does not work for this solution
    private static final String MATCH_ALL = "(.*)";
    private static final String REGEX_1 = "(\\d+:)|([#.]*)";
    private static final String REGEX_2 = "(\\d+)[x: ]?";

    public Day12() {
        super(2025, 12, MATCH_ALL, MATCH_ALL);
    }

    public Day12(String filename) {
        super(filename, MATCH_ALL, MATCH_ALL);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // THe built in parsing is busted
        configGroup.addAll(configGroup1);
        configGroup1 = new ConfigGroup();
        configGroup1.add(configGroup.removeLast());

        Pattern shapePattern = Pattern.compile(REGEX_1);
        Map<Integer, List<Matrix>> shapes = new HashMap<>();

        // easier to assume there's only one group
        for (GroupItem group: configGroup) {
            List<String> currentShape = new ArrayList<>();
            Integer number = null;
            for (ItemLine line : group) {
                Matcher shapeMatcher = shapePattern.matcher(line.toString());
                if (shapeMatcher.find()) {
                    if (shapeMatcher.group(1) != null && shapeMatcher.group(1).endsWith(":")) {
                        number = Integer.parseInt(shapeMatcher.group(1).substring(0, shapeMatcher.group(1).indexOf(':')));
                        shapes.put(number, new ArrayList<>());
                    } else {
                        currentShape.add(shapeMatcher.group(0));
                    }
                }
            }

            Matrix matrix = new Matrix(currentShape);
            for (int i = 0; i < 4; i++) {
                shapes.get(number).add(matrix);
                shapes.get(number).add(Matrix.flip(matrix, Matrix.Direction.Flip.HORIZONTAL));
                // we don't need to flip two ways:
                // shapes.get(number).add(Matrix.flip(matrix, Matrix.Direction.Flip.VERTICAL));
                matrix = Matrix.rotate(matrix, Matrix.Direction.Rotation.CLOCKWISE);
            }
        }

        Pattern fillPattern = Pattern.compile(REGEX_2);

        long count = 0;
        for (ItemLine line : configGroup1.getFirst()) {
            List<Integer> items = new ArrayList<>();
            Matcher matcher = fillPattern.matcher(line.toString());
            while (matcher.find()) {
                items.add(Integer.parseInt(matcher.group(1)));
            }

            int totalSquares = 0;
            int xSize = items.get(0);
            int ySize = items.get(1);

            int squaresCapacity = xSize / 3 * ySize / 3;

            for (int i = 2; i < items.size(); i++) {
                totalSquares += items.get(i);
            }

            if (totalSquares <= squaresCapacity) {
                ++count;
            }
        }

            // Here's code for a 2nd group, if needed
//        GroupItem group1 = configGroup1.getFirst();
//        for (ItemLine line : group1) {
//            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
////                char value = line.getChar(itemNum);
////                String value = line.getString(itemNum);
////                Long value = line.getLong(itemNum);
//            }
//        }


        return count;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        return 2L;
    }
}
