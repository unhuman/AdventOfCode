package com.unhuman.adventofcode2019;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.SparseMatrix;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

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

    public boolean processOutput(GameState gameState, SparseMatrix<Character> matrix, List<String> input) {
        int x = Integer.parseInt(input.get(0));
        int y = Integer.parseInt(input.get(1));
        int value = Integer.parseInt(input.get(2));

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
        return false;
    }


    public boolean processOutput(GameState gameState, SparseMatrix<Character> matrix, String input) {
        String[] instructions = input.split(",");
        List<String> inputs3 = new ArrayList<>();
        for (int i = 0; i < instructions.length; i += 3) {
            inputs3.clear();
            inputs3.add(instructions[i]);
            inputs3.add(instructions[i + 1]);
            inputs3.add(instructions[i + 2]);
            processOutput(gameState, matrix, inputs3);
        }
        return true;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        SparseMatrix<Character> matrix = new SparseMatrix<>(' ');

        // easier to assume there's only one group
        ItemLine line = configGroup.get(0).get(0);

        IntCodeParser parser = new IntCodeParser(line);
        parser.setReturnOnOutput(true);
        parser.poke(0, 2L);
        GameState gameState = new GameState(0, null, null);

        int nextInput = 0;

        List<String> outputs = new ArrayList<>();
        while (!parser.hasHalted()) {
            parser.setInput(Integer.toString(nextInput));

            // do 3 processes because every output is in groups of 3
            parser.step();
            if (parser.hasOutput()) {
                outputs.add(parser.getOutput());
                if (outputs.size() == 3) {
                    boolean updatedScore = processOutput(gameState, matrix, outputs);
                    outputs.clear();
                }
            }
            if (gameState.ball != null && gameState.paddle != null) {
                nextInput = Integer.compare(gameState.ball.x, gameState.paddle.x);
//                System.out.println(matrix);
            }
        }

        return gameState.score;
    }
}
