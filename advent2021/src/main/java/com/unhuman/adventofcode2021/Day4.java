package com.unhuman.adventofcode2021;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.List;

public class Day4 extends InputParser {
    private static final String regex1 = "(\\d+),?";
    private static final String regex2 = " *(\\d+) *";

    class Item {
        int value;
        boolean touched;
        
        public Item(int value) {
            this.value = value;
            this.touched = false;
        }

        public boolean isTouched() {
            return this.touched;
        }

        public void setTouched(int value) {
            if (this.value == value) {
                this.touched = true;
            }
        }

        public int getValueIfNotTouched() {
            return (!touched) ? value : 0;
        }
    }

    public Day4() {
        super(2021, 4, regex1, regex2);
    }

    public Day4(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // create boards
        List<Item[][]> boards = new ArrayList<>();

        // Initialize the board
        for (int i = 0; i < configGroup1.size(); i++) {
            GroupItem item = configGroup1.get(i);

            Item[][] board = new Item[item.size()][item.size()];
            for (int j = 0; j < item.size(); j++) {
                ItemLine line = item.get(j);

                for (int k = 0; k < line.size(); k++) {
                    int element = Integer.parseInt(line.get(k));
                    board[j][k] = new Item(element);
                }
            }
            boards.add(board);
        }

        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                for (String element : line) {
                    int called = Integer.parseInt(element);

                    for (int i = 0; i < boards.size(); i++) {
                        int sumUntouched = 0;

                        // Call out numbers & update board
                        Item[][] board = boards.get(i);
                        for (int j = 0; j < board.length; j++) {
                            for (int k = 0; k < board[j].length; k++) {
                                board[j][k].setTouched(called);
                                sumUntouched += board[j][k].getValueIfNotTouched();
                            }
                        }

                        for (int j = 0; j < board.length; j++) {
                            int columnCount = 0;
                            int rowCount = 0;
                            for (int k = 0; k < board[j].length; k++) {
                                if (board[j][k].isTouched()) {
                                    columnCount++;
                                }
                                if (board[k][j].isTouched()) {
                                    rowCount++;
                                }

                                if (rowCount == board.length || columnCount == board.length) {
                                    return sumUntouched * called;
                                }
                            }
                        }
                    }
                }
            }
        }

        return 1;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // create boards
        List<Item[][]> boards = new ArrayList<>();
        int lastboardWin = 0;

        // Initialize the board
        for (int i = 0; i < configGroup1.size(); i++) {
            GroupItem item = configGroup1.get(i);

            Item[][] board = new Item[item.size()][item.size()];
            for (int j = 0; j < item.size(); j++) {
                ItemLine line = item.get(j);

                for (int k = 0; k < line.size(); k++) {
                    int element = Integer.parseInt(line.get(k));
                    board[j][k] = new Item(element);
                }
            }
            boards.add(board);
        }

        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                for (String element : line) {
                    int called = Integer.parseInt(element);

                    for (int i = boards.size(); i > 0; i--) {
                        int sumUntouched = 0;

                        // Call out numbers & update board
                        Item[][] board = boards.get(i - 1);
                        for (int j = 0; j < board.length; j++) {
                            for (int k = 0; k < board[j].length; k++) {
                                board[j][k].setTouched(called);
                                sumUntouched += board[j][k].getValueIfNotTouched();
                            }
                        }

                        for (int j = 0; j < board.length; j++) {
                            int columnCount = 0;
                            int rowCount = 0;
                            for (int k = 0; k < board[j].length; k++) {
                                if (board[j][k].isTouched()) {
                                    columnCount++;
                                }
                                if (board[k][j].isTouched()) {
                                    rowCount++;
                                }

                                if (rowCount == board.length || columnCount == board.length) {
                                    lastboardWin = sumUntouched * called;
                                    boards.remove(i - 1);
                                }
                            }
                        }
                    }
                }
            }
        }

        return lastboardWin;
    }
}
