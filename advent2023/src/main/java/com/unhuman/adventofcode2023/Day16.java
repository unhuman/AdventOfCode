package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Day16 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    enum Direction { UP, DOWN, LEFT, RIGHT }

    static class Beam {
        Point point;
        Direction direction;

        public Beam(Beam otherBeam) {
            this.point = new Point(otherBeam.point);
            this.direction = otherBeam.direction;
        }

        public Beam(int x, int y, Direction direction) {
            this.point = new java.awt.Point(x, y);
            this.direction = direction;
        }

        public void move() {
            switch (direction) {
                case UP: this.point.y--; break;
                case DOWN: this.point.y++; break;
                case LEFT: this.point.x--; break;
                case RIGHT: this.point.x++; break;
            }
        }

        public Point getPoint() {
            return point;
        }

        public Direction getDirection() {
            return direction;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        @Override
        public boolean equals(Object obj) {
            Beam other = (Beam) obj;
            return (this.direction == other.direction && this.point.equals(other.point));
        }

        @Override
        public int hashCode() {
            return this.point.hashCode() * 17 + this.direction.hashCode();
        }
    }

    public Day16() {
        super(2023, 16, regex1, regex2);
    }

    public Day16(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        Beam startingBeam = new Beam(-1, 0, Direction.RIGHT);

        return navigateField(matrix, startingBeam);
    }

    private int navigateField(Matrix matrix, Beam startingBeam) {
        java.util.List<Beam> beams = new ArrayList<>();
        beams.add(startingBeam);
        Set<Point> energized = new HashSet<>();
        Set<Beam> energizedDirections = new HashSet<>();
        while (beams.size() > 0) {
            for (int beamNum = beams.size() - 1; beamNum >= 0; beamNum--) {
                Beam beam = beams.get(beamNum);
                beam.move();

                // We are done if this is off matrix or re-energizes in the same direction
                if (!isValid(matrix, beam)
                        || energizedDirections.contains(beam)) {
                    beams.remove(beamNum);
                    continue;
                }
                energized.add(new Point(beam.getPoint()));
                energizedDirections.add(new Beam(beam));

                char place = matrix.getValue(beam.getPoint());
                Direction dir = beam.getDirection();

                // Handle just movement
                if (place == '.') {
                    continue;
                }

                // handle splits
                if (place == '-') {
                    if (dir == Direction.UP || dir == Direction.DOWN) {
                        beam.setDirection(Direction.RIGHT);
                        Beam beam2 = new Beam(beam);
                        beam2.setDirection(Direction.LEFT);
                        beams.add(beam2);
                    }
                    continue;
                }

                if (place == '|') {
                    if (dir == Direction.LEFT || dir == Direction.RIGHT) {
                        beam.setDirection(Direction.UP);
                        Beam beam2 = new Beam(beam);
                        beam2.setDirection(Direction.DOWN);
                        beams.add(beam2);
                    }
                    continue;
                }

                switch (place) {
                    case '/':
                        switch (dir) {
                            case UP:
                                beam.setDirection(Direction.RIGHT);
                                break;
                            case DOWN:
                                beam.setDirection(Direction.LEFT);
                                break;
                            case LEFT:
                                beam.setDirection(Direction.DOWN);
                                break;
                            case RIGHT:
                                beam.setDirection(Direction.UP);
                                break;
                        }
                        break;
                    case '\\':
                        switch (dir) {
                            case UP:
                                beam.setDirection(Direction.LEFT);
                                break;
                            case DOWN:
                                beam.setDirection(Direction.RIGHT);
                                break;
                            case LEFT:
                                beam.setDirection(Direction.UP);
                                break;
                            case RIGHT:
                                beam.setDirection(Direction.DOWN);
                                break;
                        }
                        break;
                }
            }
        }

        return energized.size();
    }

    boolean isValid(Matrix matrix, Beam beam) {
        Point point = beam.getPoint();
        return (point.x >= 0 && point.y >= 0 && point.x < matrix.getWidth() && point.y < matrix.getHeight());
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        int maxScore = 0;
        for (int i = 0; i < matrix.getWidth(); i++) {
            Beam startingBeam = new Beam(i, -1, Direction.DOWN);
            maxScore = Math.max(maxScore, navigateField(matrix, startingBeam));

            Beam startingBeam2 = new Beam(i, matrix.getHeight(), Direction.UP);
            maxScore = Math.max(maxScore, navigateField(matrix, startingBeam2));
        }

        for (int i = 0; i < matrix.getHeight(); i++) {
            Beam startingBeam = new Beam(-1, i, Direction.RIGHT);
            maxScore = Math.max(maxScore, navigateField(matrix, startingBeam));

            Beam startingBeam2 = new Beam(matrix.getWidth(), i, Direction.LEFT);
            maxScore = Math.max(maxScore, navigateField(matrix, startingBeam2));
        }

        return maxScore;
    }
}
