package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.SparseMatrix;

import java.awt.Point;

public class Day13 extends InputParser {
    private static final String regex1 = "(-?[\\d]*),?";
    private static final String regex2 = null;

    public Day13() {
        super(2019, 13, regex1, regex2);
    }

    public Day13(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        SparseMatrix<Character> matrix = new SparseMatrix<>(' ');

        // easier to assume there's only one group
        ItemLine line = configGroup.get(0).get(0);

        IntCodeParser parser = new IntCodeParser(line);
        GameState gameState = new GameState(0, null, null);
        while (!parser.hasHalted()) {
            parser.process();
            String output = parser.getOutput();
            processOutput(gameState, matrix, output);
        }

        return matrix.getPopulatedPoints('+').size();
    }

    public class GameState {
        public int score;
        public Point ball;
        public Point paddle;
        GameState(int score, Point ball, Point paddle) {
            this.score = score;
            this.ball = ball;
            this.paddle = paddle;
        }
    }

    public boolean processOutput(GameState gameState, SparseMatrix<Character> matrix, String input) {

        String[] instructions = input.split(",");
        for (int i = 0; i < instructions.length; i += 3) {
            int x = Integer.parseInt(instructions[i]);
            int y = Integer.parseInt(instructions[i + 1]);
            int value = Integer.parseInt(instructions[i + 2]);

            if (x == -1 && y == 0) {
                gameState.score = value;
                System.out.println("Score: " + gameState.score);
                return true;
            }

            Character character = ' ';
            switch (value) {
                case 0:
                    character = ' ';
                    break;
                case 1:
                    character = '#';
                    break;
                case 2:
                    character = '+';
                    break;
                case 3:
                    character = '_';
                    gameState.paddle = new Point(x, y);
                    break;
                case 4:
                    character = '*';
                    gameState.ball = new Point(x, y);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + value);
            };
            matrix.put(x, y, character);
        }
        return false;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        SparseMatrix<Character> matrix = new SparseMatrix<>(' ');

        // easier to assume there's only one group
        ItemLine line = configGroup.get(0).get(0);

        IntCodeParser parser = new IntCodeParser(line);
        parser.setReturnOnOutput(true);
        parser.setInput("2");
        parser.setInput("1");
        parser.setInput("1");
        parser.setInput("1");
        GameState gameState = new GameState(0, null, null);

        Integer nextInput = null;

        while (!parser.hasHalted()) {
            if (nextInput != null) {
                parser.setInput(nextInput.toString());
                nextInput = null;
            }
            parser.process();
            if (parser.hasHalted()) {
                System.out.println("HALT");
            }
            parser.process();
            parser.process();

            String output = parser.getOutput();
            System.out.println(output);
            boolean updatedScore = processOutput(gameState, matrix, output);
            if (updatedScore) {
                nextInput = Integer.compare(gameState.ball.x, gameState.paddle.x);
            }
        }
        System.out.println(matrix);

        return gameState.score;
    }
}
