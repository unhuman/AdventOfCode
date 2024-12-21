package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;
import com.unhuman.adventofcode.aoc_framework.utility.PointHelper;

import javax.swing.text.Position;
import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day21 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    public Day21() {
        super(2024, 21, regex1, regex2);
    }

    public Day21(String filename) {
        super(filename, regex1, regex2);
    }

    // 130788 too low
    // 134700 too high

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        KeyPad doorway = new KeyPad("789\n456\n123\n 0A");
        KeyPad robot1 = new KeyPad(" ^A\n<v>");
        KeyPad robot2 = new KeyPad(" ^A\n<v>");
        KeyPad human  = new KeyPad(" ^A\n<v>");

        List<KeyPad> keypads = Arrays.asList(doorway, robot1, robot2);

        // easier to assume there's only one group
        Long result = 0L;
        GroupItem group0 = configGroup.get(0);
        for (ItemLine line : group0) {
            for (KeyPad keypad: keypads) {
                keypad.resetFinger();
            }
            String command = "";
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
                command = command + line.getChar(itemNum);
            }

            String data = command;
            for (KeyPad keypad: keypads) {
                data = keypad.processCommand(data);
            }

            long commandValue = Long.parseLong(command.substring(0, command.length() - 1));
            System.out.println("Command: " + command + " Length: " + data.length() + " * value: " + commandValue +
                    " = " + data.length() * commandValue);
            result += data.length() * commandValue;
            System.out.println("Running total : " + result);
        }

        return result;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        return 2;
    }

    public static class KeyPad {
        Matrix matrix;
        Point finger;
        Point deadSpot;

        KeyPad(String buttons) {
            String[] lines = buttons.split("\\n");
            matrix = new Matrix(lines[0].length(), lines.length, Matrix.DataType.CHARACTER);
            for (int y = 0; y < lines.length; y++) {
                for (int x = 0; x < lines[y].length(); x++) {
                    Character button = lines[y].charAt(x);
                    matrix.setValue(x, y, button);
                    if (button.equals('A')) {
                        finger = new Point(x, y);
                    }
                }
            }
            deadSpot = matrix.getCharacterLocations(' ').get(0);
        }

        void resetFinger() {
            finger = matrix.getCharacterLocations('A').get(0);
        }

        boolean canWalk(Point start, Matrix.Direction direction, int steps) {
            if (steps == 0) {
                return false;
            }
            Point testStep = start;
            for (int i = 0; i < steps; i++) {
                testStep = PointHelper.addPoints(testStep, direction.getDirection());
                if (deadSpot.equals(testStep)) {
                    return false;
                }
            }
            return true;
        }

        protected String moveTo(Character desiredButton) {
            Point buttonLocation = matrix.getCharacterLocations(desiredButton).get(0);
            Point delta = PointHelper.subtract(buttonLocation, finger);

            int leftSteps = (delta.x < 0) ? Math.abs(delta.x) : 0;
            int rightSteps = (delta.x > 0) ? delta.x : 0;

            int upSteps = (delta.y < 0) ? Math.abs(delta.y) : 0;
            int downSteps = (delta.y > 0) ? delta.y : 0;

            // navigate to the button
            StringBuilder sb = new StringBuilder();

            while (leftSteps + rightSteps + upSteps + downSteps > 0) {
                if (canWalk(finger, Matrix.Direction.LEFT, leftSteps)) {
                    for (int i = 0; i < leftSteps; i++) {
                        sb.append('<');
                        finger = PointHelper.addPoints(finger, Matrix.Direction.LEFT.getDirection());
                    }
                    leftSteps = 0;
                } else if (canWalk(finger, Matrix.Direction.RIGHT, rightSteps)) {
                    for (int i = 0; i < rightSteps; i++) {
                        sb.append('>');
                        finger = PointHelper.addPoints(finger, Matrix.Direction.RIGHT.getDirection());
                    }
                    rightSteps = 0;
                } else if (canWalk(finger, Matrix.Direction.UP, upSteps)) {
                    for (int i = 0; i < upSteps; i++) {
                        sb.append('^');
                        finger = PointHelper.addPoints(finger, Matrix.Direction.UP.getDirection());
                    }
                    upSteps = 0;
                } else if (canWalk(finger, Matrix.Direction.DOWN, downSteps)) {
                    for (int i = 0; i < downSteps; i++) {
                        sb.append('v');
                        finger = PointHelper.addPoints(finger, Matrix.Direction.DOWN.getDirection());
                    }
                    downSteps = 0;
                }
            }

            // press the button
            sb.append('A');

            return sb.toString();
        }

        String processCommand(String command) {
            resetFinger();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < command.length(); i++) {
                Character desiredButton = command.charAt(i);
                sb.append(moveTo(desiredButton));
            }
            System.out.println(sb.toString() + " " + sb.toString().length());
            return sb.toString();
        }

        String followDirections(String command) {
            resetFinger();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < command.length(); i++) {
                switch (command.charAt(i)) {
                    case '<':
                        finger = PointHelper.addPoints(finger, Matrix.Direction.LEFT.getDirection());
                        break;
                    case '>':
                        finger = PointHelper.addPoints(finger, Matrix.Direction.RIGHT.getDirection());
                        break;
                    case '^':
                        finger = PointHelper.addPoints(finger, Matrix.Direction.UP.getDirection());
                        break;
                    case 'v':
                        finger = PointHelper.addPoints(finger, Matrix.Direction.DOWN.getDirection());
                        break;
                    case 'A':
                        sb.append(matrix.getValue(finger));
                }

                if (matrix.getValue(finger) == ' ') {
                    System.out.println("PANIC at index" + i);
                }
            }
            return sb.toString();
        }
    }
}
