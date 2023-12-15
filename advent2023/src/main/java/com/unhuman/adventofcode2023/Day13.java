package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Day13 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    public Day13() {
        super(2023, 13, regex1, regex2);
    }

    public Day13(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        long total = 0;
        for (GroupItem item : configGroup) {

            List<Integer> rowHashes = new ArrayList<>();
            List<Integer> columnHashes = new ArrayList<>();
            List<List<Character>> columns = new ArrayList<>();

            for (int i = 0; i < item.size(); i++) {
                ItemLine line = item.get(i);
                rowHashes.add(line.toString().hashCode());

                for (int j = 0; j < line.size(); j++) {
                    Character element = line.get(j).charAt(0);
                    if (i == 0) {
                        columns.add(new ArrayList<>());
                    }
                    columns.get(j).add(element);
                    if (i == item.size() - 1) {
                        columnHashes.add(columns.get(j).toString().hashCode());
                    }
                }
            }

            int rowScore = findMirror(rowHashes) * 100;
            int columnScore = findMirror(columnHashes);

            total += rowScore + columnScore;
        }

        return total;
    }

    int findMirror(List<Integer> scores) {
        for (double mirrorPoint = 0.5; mirrorPoint < scores.size() - 1; mirrorPoint++) {
            boolean mirror = true;
            int counter = 0;
            double reflection = mirrorPoint;
            while (reflection - 0.5 - counter >= 0 && (reflection + 0.5 + counter < scores.size())) {
                if (!scores.get((int)(reflection - 0.5 - counter)).equals(scores.get((int)(reflection + 0.5 + counter)))) {
                    mirror = false;
                    break;
                }
                counter++;
            }
            if (mirror) {
                return (int) Math.floor(mirrorPoint) + 1;
            }
        }
        return 0;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        long total = 0;
        int testNumber = 0;
        for (GroupItem item : configGroup) {
            ++testNumber;
            List<String> rows = new ArrayList<>();
            List<String> columns = new ArrayList<>();

            for (int i = 0; i < item.size(); i++) {
                ItemLine line = item.get(i);

                String rowData = "";
                for (int j = 0; j < line.size(); j++) {
                    char element = line.get(j).charAt(0);

                    rowData = rowData + element;

                    if (i == 0) {
                        columns.add(new String());
                    }
                    columns.set(j, columns.get(j) + element);
                }
                rows.add(rowData);
            }

            int priorRow = findMirrorString(rows, null) * 100;
            int priorColumn = findMirrorString(columns, null);

            for (String score: rows) {
                System.out.println("Before Row: " + score);
            }
            System.out.println();

            for (String score: columns) {
                System.out.println("Before Column: " + score);
            }
            System.out.println();

            Point flipped = swapCharacter(rows);
            if (flipped != null) {
                System.out.println(testNumber + " Swap of rows - " + flipped + " ");
                String rowData = rows.get(flipped.y);
                char flipChar = (rowData.charAt(flipped.x) == '.') ? '#' : '.';
                rowData = rowData.substring(0, flipped.x) + flipChar + rowData.substring(flipped.x + 1);
                rows.set(flipped.y, rowData);

                String columnData = columns.get(flipped.x);
                columnData = columnData.substring(0, flipped.y) + flipChar + columnData.substring(flipped.y + 1);
                columns.set(flipped.x, columnData);
            } else {
                flipped = swapCharacter(columns);
                if (flipped != null) {
                    System.out.println();

                    System.out.println(testNumber + " Swap of columns - " + flipped + " ");
                    String columnData = columns.get(flipped.y);
                    char flipChar = (columnData.charAt(flipped.x) == '.') ? '#' : '.';
                    columnData = columnData.substring(0, flipped.x) + flipChar + columnData.substring(flipped.x + 1);
                    columns.set(flipped.y, columnData);

                    String rowData = rows.get(flipped.x);
                    rowData = rowData.substring(0, flipped.y) + flipChar + rowData.substring(flipped.y + 1);
                    rows.set(flipped.x, rowData);
                }
            }

            for (String score: rows) {
                System.out.println("After Row: " + score);
            }
            System.out.println();

            for (String score: columns) {
                System.out.println("After Column: " + score);
            }
            System.out.println();

            int currentRow = findMirrorString(rows, priorRow);
            if (priorRow != currentRow) {
                System.out.print("Row update");
                total += currentRow * 100;
            }
            int currentColumn = findMirrorString(columns, priorColumn);
            if (priorColumn != currentColumn) {
                System.out.print("Column update");
                total += currentColumn;
            }

            System.out.println();

            if (total == 0) {
                System.out.println("need to investigate unexpected 0");
            }
        }

        return total;
    }

    int findMirrorString(List<String> scores, Integer skipMirror) {
        for (double mirrorPoint = 0.5; mirrorPoint < scores.size() - 1; mirrorPoint++) {
            boolean mirror = true;
            int counter = 0;
            while (mirrorPoint - 0.5 - counter >= 0 && (mirrorPoint + 0.5 + counter < scores.size())) {
                if (!scores.get((int)(mirrorPoint - 0.5 - counter)).equals(scores.get((int)(mirrorPoint + 0.5 + counter)))) {
                    mirror = false;
                    break;
                }
                counter++;
            }
            if (mirror) {
                int check = (int) Math.floor(mirrorPoint) + 1;
                if (skipMirror != null && check == skipMirror) {
                    continue;
                }
                return check;
            }
        }
        return 0;
    }

    Point swapCharacter(List<String> scores) {
        System.out.println();

        Point swapPoint = null;

        for (double mirrorPoint = 0.5; mirrorPoint < scores.size() - 1; mirrorPoint++) {

            // copy all the arrays

            boolean mirror = true;
            int counter = 0;

            if (swapPoint != null) {
                char[] scoreLine = scores.get(swapPoint.y).toCharArray();
                scoreLine[swapPoint.x] = (scoreLine[swapPoint.x] == '.') ? '#' : '.';
                swapPoint = null;
            }

            while (mirrorPoint - 0.5 - counter >= 0 && (mirrorPoint + 0.5 + counter < scores.size())) {
                // figure out if there's a 1 char difference
                String leftScore = scores.get((int)(mirrorPoint - 0.5 - counter));
                String rightScore = scores.get((int)(mirrorPoint + 0.5 + counter));

                if (!leftScore.equals(rightScore)) {
                    if (swapPoint == null) {
                        Integer diffPoint = null;
                        for (int i = 0; i < leftScore.length(); i++) {
                            if (rightScore.charAt(i) != leftScore.charAt(i)) {
                                if (diffPoint == null) {
                                    diffPoint = i;
                                } else {
                                    diffPoint = null;
                                    break;
                                }
                            }
                        }
                        if (diffPoint != null) {
                            swapPoint = new Point(diffPoint, (int)(mirrorPoint - 0.5 - counter));
//                            System.out.println("Swap Point Check: " + swapPoint);
                        } else {
                            mirror = false;
                            break;
                        }
                    } else {
                        mirror = false;
                        break;
                    }
                }
                counter++;
            }
            if (mirror && swapPoint != null) {
                return swapPoint;
            }
        }
        return null;
    }
}
