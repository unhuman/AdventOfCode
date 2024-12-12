package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day12 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;
    enum FenceLocation { ABOVE, BELOW, LEFT, RIGHT }

    public Day12() {
        super(2024, 12, regex1, regex2);
    }

    public Day12(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        long cost = 0L;

        Set<Character> fieldNames = matrix.getKnownCharacters();

        for (Character fieldName: fieldNames) {
            List<Point> locations = matrix.getCharacterLocations(fieldName);
            while (locations.size() > 0) {
                Point startLocation = locations.get(0);
                Set<Point> field = findAdjacentPositions(matrix, locations, startLocation, fieldName);

                int area = field.size();

                // determine fences
                int fences = 0;
                for (Point ground: field) {
                    fences += (field.contains(new Point(ground.x - 1, ground.y))) ? 0 : 1;
                    fences += (field.contains(new Point(ground.x + 1, ground.y))) ? 0 : 1;
                    fences += (field.contains(new Point(ground.x, ground.y - 1))) ? 0 : 1;
                    fences += (field.contains(new Point(ground.x, ground.y + 1))) ? 0 : 1;
                }

                cost += fences * area;

            }
        }

        return cost;
    }

    public Set<Point> findAdjacentPositions(Matrix matrix, List<Point> knownLocations, Point checkPosition,
                                            Character desiredCharacter) {
        if (!matrix.getValue(checkPosition).equals(desiredCharacter)) {
            return Collections.emptySet();
        }
        Set<Point> field = new HashSet<>();
        field.add(checkPosition);
        knownLocations.remove(checkPosition);
        List<Point> adjacents = matrix.getAdjacentPoints(checkPosition, false);
        for (Point check: adjacents) {
            if (knownLocations.contains(check)) {
                field.addAll(findAdjacentPositions(matrix, knownLocations, check, desiredCharacter));
            }
        }
        return field;
    }

    public void cleanAdjacentFences(List<Fence> fences, Fence fence) {
        if (fences.isEmpty()) {
            return;
        }

        for (int i = 0; i < fences.size(); ) {
            Fence checkFence = fences.get(i);
            if (new Fence(fence.x - 1, fence.y, fence.fenceLocation).equals(checkFence)
                    || new Fence(fence.x + 1, fence.y, fence.fenceLocation).equals(checkFence)
                    || new Fence(fence.x, fence.y - 1, fence.fenceLocation).equals(checkFence)
                    || new Fence(fence.x, fence.y + 1, fence.fenceLocation).equals(checkFence)) {
                fences.remove(checkFence);
                cleanAdjacentFences(fences, checkFence);
            } else {
                i++;
            }
        }
    }

    @Override
    // 910172 too high
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        Matrix matrix = new Matrix(configGroup, Matrix.DataType.CHARACTER);

        long cost = 0L;

        Set<Character> fieldNames = matrix.getKnownCharacters();

        for (Character fieldName: fieldNames) {
            List<Point> locations = matrix.getCharacterLocations(fieldName);
            while (locations.size() > 0) {
                Point startLocation = locations.get(0);
                Set<Point> field = findAdjacentPositions(matrix, locations, startLocation, fieldName);

                int area = field.size();

                // determine fences
                List<Fence> fences = new ArrayList<>();
                for (Point ground: field) {
                    if (!field.contains(new Point(ground.x - 1, ground.y))) {
                        fences.add(new Fence(ground.x - 1, ground.y, FenceLocation.LEFT));
                    }
                    if (!field.contains(new Point(ground.x + 1, ground.y))) {
                        fences.add(new Fence(ground.x + 1, ground.y, FenceLocation.RIGHT));
                    }
                    if (!field.contains(new Point(ground.x, ground.y - 1))) {
                        fences.add(new Fence(ground.x, ground.y - 1, FenceLocation.ABOVE));
                    }
                    if (!field.contains(new Point(ground.x, ground.y + 1))) {
                        fences.add(new Fence(ground.x, ground.y + 1, FenceLocation.BELOW));
                    }
                }

                long sides = 0;
                while (fences.size() > 0) {
                    Fence fence = fences.remove(0);
                    sides++;
                    cleanAdjacentFences(fences, fence);
                }
                cost += area * sides;
                System.out.println("Section: " + fieldName + " Area: " + area + " * sides " + sides + " = " + area * sides);
            }
        }

        return cost;
    }

    private class Fence extends Point {
        private FenceLocation fenceLocation;
        public Fence(Point point, FenceLocation fenceLocation) {
            super(point);
            this.fenceLocation = fenceLocation;
        }

        public Fence(int x, int y, FenceLocation fenceLocation) {
            super(new Point(x, y));
            this.fenceLocation = fenceLocation;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Fence{");
            sb.append("fenceLocation=").append(fenceLocation);
            sb.append(", x=").append(x);
            sb.append(", y=").append(y);
            sb.append('}');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Fence fence = (Fence) o;
            return fenceLocation == fence.fenceLocation;
        }

        @Override
        public int hashCode() {
            return Objects.hash(super.hashCode(), fenceLocation);
        }
    }
}
