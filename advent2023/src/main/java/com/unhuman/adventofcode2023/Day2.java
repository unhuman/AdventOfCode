package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 extends InputParser {
    private static final String regex1 = "([^;:]*)[;:]?";
    private static final String regex2 = null;

    private static final Pattern GAME_PATTERN = Pattern.compile("Game (\\d*)");
    private static final Pattern RED_PATTERN = Pattern.compile(".*?(\\d+) red.*");
    private static final Pattern GREEN_PATTERN = Pattern.compile(".*?(\\d+) green.*");
    private static final Pattern BLUE_PATTERN = Pattern.compile(".*?(\\d+) blue.*");

    public Day2() {
        super(2023, 2, regex1, regex2);
    }

    public Day2(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int allowedRed = 12;
        int allowedGreen = 13;
        int allowedBlue = 14;
        int gamesSum = 0;
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                Matcher gameMatcher = GAME_PATTERN.matcher(line.get(0));
                if (gameMatcher.matches()) {
                    int game = Integer.parseInt(gameMatcher.group(1));
                    boolean allAllowed = true;
                    for (int i = 1; i < line.size(); i++) {
                        String gameText = line.get(i);
                        int redCubes = getData(RED_PATTERN, gameText);
                        int greenCubes = getData(GREEN_PATTERN, gameText);
                        int blueCubes = getData(BLUE_PATTERN, gameText);
                        if (redCubes > allowedRed
                                || greenCubes > allowedGreen
                                || blueCubes > allowedBlue) {
//                            System.out.println("Game " + game + " denied: " + gameText);
                            allAllowed = false;
                            break;
                        }
                    }
                    if (allAllowed) {
                        gamesSum += game;
//                        System.out.println("Game " + game + " allowed - total: " + gamesSum);
                    }
                }
            }
        }

        return gamesSum;
    }

    private int getData(Pattern pattern, String data) {
        Matcher checkPattern = pattern.matcher(data);
        return (checkPattern.matches()) ? Integer.parseInt(checkPattern.group(1)) : 0;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int gamesSum = 0;
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                Matcher gameMatcher = GAME_PATTERN.matcher(line.get(0));
                if (gameMatcher.matches()) {
                    int game = Integer.parseInt(gameMatcher.group(1));
                    int minRed = 0;
                    int minGreen = 0;
                    int minBlue = 0;
                    boolean allAllowed = true;
                    for (int i = 1; i < line.size(); i++) {
                        String gameText = line.get(i);
                        int redCubes = getData(RED_PATTERN, gameText);
                        int greenCubes = getData(GREEN_PATTERN, gameText);
                        int blueCubes = getData(BLUE_PATTERN, gameText);
                        if (redCubes > minRed) {
                            minRed = redCubes;
                        }
                        if (greenCubes > minGreen) {
                            minGreen = greenCubes;
                        }
                        if (blueCubes > minBlue) {
                            minBlue = blueCubes;
                        }
                    }
                    gamesSum += (minRed * minGreen * minBlue);
//                    System.out.println("Game " + game + " allowed - total: " + gamesSum);
                }
            }
        }

        return gamesSum;
    }
}
