package com.unhuman.adventofcode2024;

import com.google.common.graph.Graph;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import com.sun.source.tree.Tree;
import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import org.apache.commons.collections4.SortedBag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Day23 extends InputParser {
    private static final String regex1 = "(.*)-(.*)";
    private static final String regex2 = null;

    public Day23() {
        super(2024, 23, regex1, regex2);
    }

    public Day23(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);

        Map<String, HashSet<String>> mappings = new HashMap<>();
        HashSet<String> knownTNodes = new HashSet<>();

        for (ItemLine line : group0) {
            String left = line.get(0);
            String right = line.get(1);
            if (!mappings.containsKey(left)) {
                mappings.put(left, new HashSet<>());
            }
            mappings.get(left).add(right);
            if (!mappings.containsKey(right)) {
                mappings.put(right, new HashSet<>());
            }
            mappings.get(right).add(left);

            if (left.startsWith("t")) {
                knownTNodes.add(left);
            }

            if (right.startsWith("t")) {
                knownTNodes.add(right);
            }
        }


        HashSet<HashSet<String>> completedArrangements = new HashSet<>();
        for (String knownTNode : knownTNodes) {
            HashSet<String> joiningNodes1 = new HashSet<>(mappings.get(knownTNode));
            for (String joiningNode1 : joiningNodes1) {
                HashSet<String> joiningNodes2 = new HashSet<>(mappings.get(joiningNode1));
                for (String joiningNode2 : joiningNodes2) {
                    if (joiningNode2.equals(knownTNode)) {
                        continue;
                    }
                    if (mappings.get(joiningNode2).contains(knownTNode)) {
                        HashSet<String> completedArrangement = new HashSet<>();
                        completedArrangement.add(knownTNode);
                        completedArrangement.add(joiningNode1);
                        completedArrangement.add(joiningNode2);
                        completedArrangements.add(completedArrangement);
                    }
                }
            }
        }
        return completedArrangements.size();
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);

        Map<String, TreeSet<String>> networks = new HashMap<>();
        for (ItemLine line : group0) {
            String left = line.get(0);
            String right = line.get(1);

            if (!networks.containsKey(left)) {
                networks.put(left, new TreeSet<>());
            }
            networks.get(left).add(right);
            if (!networks.containsKey(right)) {
                networks.put(right, new TreeSet<>());
            }
            networks.get(right).add(left);
        }

        TreeSet<String> bestOverall = new TreeSet<>();
        List<String> nodes = new ArrayList<>(networks.keySet());
        for (String node: nodes) {
            TreeSet<String> newSet = new TreeSet<>(networks.get(node));
            newSet.add(node);

            for (String checkup: networks.get(node)) {
                if (newSet.contains(checkup)) {
                    TreeSet<String> comparison = new TreeSet<>(networks.get(checkup));
                    comparison.add(checkup);
                    newSet.retainAll(comparison);
                }
            }

            if (newSet.size() > bestOverall.size()) {
                bestOverall = newSet;
            }
        }

        return String.join(",", bestOverall);
    }
}
