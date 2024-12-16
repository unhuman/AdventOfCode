package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day14 extends InputParser {
    private static final String regex1 = "p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)";
    private static final String regex2 = null;

    public Day14() {
        super(2024, 14, regex1, regex2);
    }

    public Day14(String filename) {
        super(filename, regex1, regex2);
    }
    public static int roomWidth = 101;
    public static int roomHeight = 103;

    public void setRoomWidth(int roomWidthUse) {
        roomWidth = roomWidthUse;
    }

    public void setRoomHeight(int roomHeightUse) {
        roomHeight = roomHeightUse;
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);
        List<Robot> robots = new ArrayList<>(group0.size());
        for (ItemLine line : group0) {
            Robot robot = new Robot(new Point(line.getInt(0), line.getInt(1)), new Point(line.getInt(2), line.getInt(3)));
            robots.add(robot);
        }

        for (int count = 0; count < 100; count++) {
            for (int i = 0; i < robots.size(); i++) {
                robots.get(i).move();
            }
        }

        int quadrantWidth = roomWidth / 2;
        int quadrantHeight = roomHeight / 2;

        long[] scores = {0L, 0L, 0L, 0L};
        for (int i = 0; i < robots.size(); i++) {
            Robot checkRobot = robots.get(i);
            int useQuadrant = -1;
            if (checkRobot.location.x < quadrantWidth) {
                useQuadrant = 0;
            } else if (checkRobot.location.x > quadrantWidth) {
                useQuadrant = 1;
            } else {
                continue;
            }

            if (checkRobot.location.y < quadrantHeight) {
                // do nothing
            } else if (checkRobot.location.y > quadrantHeight) {
                useQuadrant += 2;
            } else {
                continue;
            }

            scores[useQuadrant]++;
        }

        long totalScore = 1;
        for (int i = 0; i<scores.length; i++) {
            totalScore *= scores[i];
        }
        return totalScore;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);
        List<Robot> robots = new ArrayList<>(group0.size());
        for (ItemLine line : group0) {
            Robot robot = new Robot(new Point(line.getInt(0), line.getInt(1)), new Point(line.getInt(2), line.getInt(3)));
            robots.add(robot);
        }

        int iteration = 0;
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("lookForTree.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(true) {
            iteration++;
            Matrix matrix = new Matrix(roomWidth, roomHeight, Matrix.DataType.CHARACTER);
            for (int i = 0; i < robots.size(); i++) {
                Robot robot = robots.get(i);
                robot.move();
                matrix.setValue(robot.location.x, robot.location.y, '*');
            }
            try {
                writer.write(matrix.toString());
                writer.write("\n" + iteration + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

//        return 1L;
    }

    private record Robot(Point location, Point velocity) {
        public void move() {
            location.setLocation(new Point((location.x + velocity.x + roomWidth) % Day14.roomWidth, (location.y + velocity.y + roomHeight) % roomHeight));
        };
    }
}
