package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;
import com.unhuman.adventofcode.aoc_framework.utility.Sparse3DMatrix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Day8 extends InputParser {
    private static final String regex1 = "(\\d+)";
    private static final String regex2 = null;

    public Day8() {
        super(2025, 8, regex1, regex2);
    }

    public Day8(String filename) {
        super(filename, regex1, regex2);
    }

    int junctions = 1000;

    public void setJunctions(int count) {
        junctions = count;
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.getFirst();

        ArrayList<HashSet<Sparse3DMatrix.Point3D>> circuits = new ArrayList<>();
        for (ItemLine line : group0) {
            Sparse3DMatrix.Point3D point = new Sparse3DMatrix.Point3D(line.getLong(0),
                    line.getLong(1), line.getLong(2));
            circuits.add(new HashSet<>(Collections.singleton(point)));
        }

        Map<Double, Pair<Sparse3DMatrix.Point3D, Sparse3DMatrix.Point3D>> pointDistances = new TreeMap<>();
        for (int i = 0; i < circuits.size() - 1; i++) {
            Sparse3DMatrix.Point3D point1 = circuits.get(i).stream().findFirst().get();
            for (int j = i + 1; j < circuits.size(); j++) {
                Sparse3DMatrix.Point3D point2 = circuits.get(j).stream().findFirst().get();
                double distance = point1.distance(point2);
                pointDistances.put(distance, new Pair<>(point1, point2));
            }
        }

        List<Double> shortestDistances = new ArrayList<>(pointDistances.keySet());
        for (int i = 0; i < junctions; i++) {
            Double shortestDistance = shortestDistances.get(i);
            Pair<Sparse3DMatrix.Point3D, Sparse3DMatrix.Point3D> closest = pointDistances.get(shortestDistance);
            combineCircuit(circuits, closest);
        }

        List<Integer> circuitSizes = new ArrayList<>();
        circuitSizes = circuits.stream().map(circuit -> circuit.size()).sorted().toList();

        long score = circuitSizes.get(circuitSizes.size() - 1);
        int currentCircuitCount = 1;
        for (int i = circuitSizes.size() - 2; i >= 0 && currentCircuitCount++ < 3; i--) {
            score *= circuitSizes.get(i);
        }

        return score;
    }

    void combineCircuit(ArrayList<HashSet<Sparse3DMatrix.Point3D>> circuits,
                        Pair<Sparse3DMatrix.Point3D, Sparse3DMatrix.Point3D> point) {
        for (HashSet<Sparse3DMatrix.Point3D> circuit: circuits) {
            if (circuit.contains(point.getLeft()) && !circuit.contains(point.getRight())) {
                for (HashSet<Sparse3DMatrix.Point3D> circuitCheck: circuits) {
                    if (circuitCheck.contains(point.getRight())) {
                        // combine circuits and remove the match
                        circuit.addAll(circuitCheck);
                        circuits.remove(circuitCheck);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.getFirst();

        ArrayList<HashSet<Sparse3DMatrix.Point3D>> circuits = new ArrayList<>();
        for (ItemLine line : group0) {
            Sparse3DMatrix.Point3D point = new Sparse3DMatrix.Point3D(line.getLong(0),
                    line.getLong(1), line.getLong(2));
            circuits.add(new HashSet<>(Collections.singleton(point)));
        }

        Map<Double, Pair<Sparse3DMatrix.Point3D, Sparse3DMatrix.Point3D>> pointDistances = new TreeMap<>();
        for (int i = 0; i < circuits.size() - 1; i++) {
            Sparse3DMatrix.Point3D point1 = circuits.get(i).stream().findFirst().get();
            for (int j = i + 1; j < circuits.size(); j++) {
                Sparse3DMatrix.Point3D point2 = circuits.get(j).stream().findFirst().get();
                double distance = point1.distance(point2);
                pointDistances.put(distance, new Pair<>(point1, point2));
            }
        }

        List<Double> shortestDistances = new ArrayList<>(pointDistances.keySet());
        for (int i = 0; ; i++) {
            Double shortestDistance = shortestDistances.get(i);
            Pair<Sparse3DMatrix.Point3D, Sparse3DMatrix.Point3D> closest = pointDistances.get(shortestDistance);
            combineCircuit(circuits, closest);

            if (circuits.size() == 1) {
                return closest.getLeft().x() * closest.getRight().x();
            }
        }
    }
}
