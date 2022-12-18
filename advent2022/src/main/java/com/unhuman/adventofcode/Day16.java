package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.*;

public class Day16 extends InputParser {
    private static final String regex1 = "Valve (\\w+) has flow rate=(\\d+); tunnels? leads? to valves? (.*)";
    private static final String regex2 = null;

    public Day16(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        Map<String, ValveInfo> valves = new HashMap<>(); // all valves
        List<ValveInfo> valvesWithFlow = new ArrayList<>();
        for (int groupItemIdx = 0; groupItemIdx < dataItems1.size(); groupItemIdx++) {
            GroupItem item = dataItems1.get(groupItemIdx);
            for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
                ItemLine line = item.get(lineIdx);
                String name = line.get(0);
                int flow = Integer.parseInt(line.get(1));
                List<String> connections = Arrays.stream(line.get(2).split(", ")).toList();
                ValveInfo valveInfo = new ValveInfo(name, flow, connections);
                valves.put(name, valveInfo);
                if (flow > 0) {
                    valvesWithFlow.add(valveInfo);
                }
            }
        }

        // Generate all the permutations of items with flow
        List<List<ValveInfo>> valvesWithFlowPermutations = new ArrayList<>();
        int[] indexes = new int[valvesWithFlow.size()];
        for (int i = 0; i < valvesWithFlow.size(); i++) {
            indexes[i] = 0;
        }
        // Plant first occurrence in permutations
        List<ValveInfo> permutation = new ArrayList<>(valvesWithFlow);
        valvesWithFlowPermutations.add(permutation);

        // Generate other permutations
        for (int i = 0; i < valvesWithFlow.size(); /* no increment here */) {
            if (indexes[i] < i) {
                swap(valvesWithFlow, i % 2 == 0 ?  0: indexes[i], i);
                // add a permutation
                permutation = new ArrayList<>(valvesWithFlow);
                valvesWithFlowPermutations.add(permutation);

                indexes[i]++;
                i = 0;
            }
            else {
                indexes[i] = 0;
                i++;
            }
        }

        System.out.println("There are " + valvesWithFlowPermutations.size() + " permutations");

        int mostFlow = 0;
        for (int i = 0; i < valvesWithFlowPermutations.size(); i++) {
            List<ValveInfo> flowPermutation = valvesWithFlowPermutations.get(i);

            int totalFlow = processPermutation(valves, flowPermutation);

            mostFlow = (totalFlow > mostFlow) ? totalFlow : mostFlow;
        }

        return mostFlow;
    }

    private int processPermutation(Map<String, ValveInfo> valves, List<ValveInfo> flowPermutation) {
        ValveInfo startingPoint = valves.get("AA");
        int timer = 0;
        int totalFlow = 0;
        int flowTurnedOn = 0;
        int destinationIndex = 0;

        do {
            if (destinationIndex < flowPermutation.size()) {
                ValveInfo destination = flowPermutation.get(destinationIndex++);
                int navigationTime = navigate(valves, startingPoint, destination, new ArrayList<>());
                startingPoint = destination;
                // cap the navigation time so we can't go too far
                navigationTime = Math.min(navigationTime, 30 - timer);
                timer += navigationTime;

                totalFlow += flowTurnedOn * navigationTime;

                // open the valve - if it's too late, who cares
                if (timer <= 29) {
                    totalFlow += flowTurnedOn;
                    flowTurnedOn += destination.flow();
                }
            } else {
                totalFlow += flowTurnedOn;
            }
        } while (++timer < 30);
        return totalFlow;
    }

    Integer navigate(Map<String, ValveInfo> valves, ValveInfo from, ValveInfo to, List<String> seenValves) {
        if (from.destinations.contains(to.name)) {
            return 1;
        }

        if (seenValves.contains(to.name)) {
            return null;
        }

        // Update that we've seen this node
        seenValves = new ArrayList<>(seenValves);
        seenValves.add(from.name);

        Integer minNavigate = null;
        List<String> destinations = new ArrayList<>(from.destinations);
        destinations.removeAll(seenValves);
        for (String middler: destinations) {
            Integer navigationExpense = navigate(valves, valves.get(middler), to, seenValves);
            if ((navigationExpense != null) && (minNavigate == null || navigationExpense < minNavigate)) {
                minNavigate = navigationExpense + 1;
            }
        }

        return minNavigate;
    }

    private static void swap(List<ValveInfo> elements, int a, int b) {
        ValveInfo tmp = elements.get(a);
        elements.set(a, elements.get(b));
        elements.set(b, tmp);
    }
    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        return 2;
    }

    record ValveInfo(String name, int flow, List<String> destinations) {

    }
}
