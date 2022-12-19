package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.*;

public class Day16 extends InputParser {
    private static final String regex1 = "Valve (\\w+) has flow rate=(\\d+); tunnels? leads? to valves? (.*)";
    private static final String regex2 = null;
    public HashMap<String, Integer> memoizedDistances = new HashMap<>();

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

        // prepopulate distances between valves
        cacheDistances(valves);

        long iterations = 1;
        for (int i = 1; i <= valvesWithFlow.size(); i++) {
            iterations *= i;
        }
        System.out.println("Permutations to process: " + iterations);

        // Generate all the permutations of items with flow
        List<List<ValveInfo>> valvesWithFlowPermutations = new ArrayList<>();
        int[] indexes = new int[valvesWithFlow.size()];
        for (int i = 0; i < valvesWithFlow.size(); i++) {
            indexes[i] = 0;
        }
        // Plant first occurrence in permutations
        List<ValveInfo> permutation = new ArrayList<>(valvesWithFlow);
        int mostFlow = processPermutation(valves, permutation);
        long permutationsProcessed = 1;

        // Generate other permutations
        for (int i = 0; i < valvesWithFlow.size(); /* no increment here */) {
            if (indexes[i] < i) {
                swap(valvesWithFlow, i % 2 == 0 ?  0: indexes[i], i);
                // add a permutation
                permutation = new ArrayList<>(valvesWithFlow);
                int permutationFlow = processPermutation(valves, permutation);
                ++permutationsProcessed;

                if (permutationsProcessed % 10000000 == 0) {
                    String status = String.format("Processed: %d of %d permutations (%f%%)",
                            permutationsProcessed, iterations,
                            (float) permutationsProcessed / (float) iterations);
                    System.out.println(status);
                }

                mostFlow = Math.max(mostFlow, permutationFlow);

                indexes[i]++;
                i = 0;
            }
            else {
                indexes[i] = 0;
                i++;
            }
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
        if (from == to) {
            return null;
        }

        if (from.destinations.contains(to.name)) {
            return 1;
        }

        String cacheKey = from.name + ":" + to.name;
        Integer cachedDistance = memoizedDistances.get(cacheKey);
        if (cachedDistance != null) {
            return cachedDistance;
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

        memoizedDistances.put(cacheKey, minNavigate);

        // cache it the other way, too
        cacheKey = to.name + ":" + from.name;
        memoizedDistances.put(cacheKey, minNavigate);

        return minNavigate;
    }

    public void cacheDistances(Map<String, ValveInfo> valves) {
        for (ValveInfo valve: valves.values()) {
            for (ValveInfo valve2: valves.values()) {
                navigate(valves, valve, valve2, new ArrayList<>());
            }
        }
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
