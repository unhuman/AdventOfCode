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

    int timeLeft;

    public Day16(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        timeLeft = 30;

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

        int flow = prioritizedProcessing(valvesWithFlow, "AA", timeLeft, 0, "");

        return flow;
    }

    int prioritizedProcessing(List<ValveInfo> valvesWithFlow, String currentValve, int timeLeft, int currentFlow, String path) {
        if (valvesWithFlow.size() <= 0) {
            // we're done - we need to return the flow.
            return timeLeft * currentFlow;
        }

        valvesWithFlow.sort(new Comparator<ValveInfo>() {
            @Override
            public int compare(ValveInfo v1, ValveInfo v2) {
                int score1 = assignValveScore(v1, currentValve, timeLeft);
                int score2 = assignValveScore(v2, currentValve, timeLeft);

                // TODO: Do we need to recurse to resolve ties?

                return score2 - score1; // we want highest first
            }
        });

        int maxValue = 0;
        for (int i = 0; i < valvesWithFlow.size(); i++) {
            List<ValveInfo> nestedValveData = new ArrayList<>(valvesWithFlow);
            ValveInfo chosenValve = nestedValveData.remove(i);
            if (assignValveScore(chosenValve, currentValve, timeLeft) > 0) {
                int distance = memoizedDistances.get(chosenValve.name + ':' + currentValve);
                int moveAndOpenTime = distance + 1;
                String usepath = path + ':' + chosenValve.name;
                int value = (currentFlow * moveAndOpenTime) + prioritizedProcessing(nestedValveData, chosenValve.name, timeLeft - moveAndOpenTime,
                        currentFlow + chosenValve.flow, usepath);
                if (timeLeft == 30) {
                    System.out.println("Potential score: " + value);
                }
                maxValue = Math.max(maxValue, value);
            }
        }

        if (maxValue == 0) {
            return timeLeft * currentFlow;
        }

        return maxValue;
    }

    int assignValveScore(ValveInfo valveInfo, String currentValve, int timeLeft) {
        if (valveInfo.name.equals(currentValve)) {
            return 0;
        }
        int distance = memoizedDistances.get(valveInfo.name + ":" + currentValve);
        int score = valveInfo.flow * (timeLeft - (distance + 1)); // +1 = open valve time
        return score > 0 ? score : 0;
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

        String cacheKey = from.name + ":" + to.name;

        if (from.destinations.contains(to.name)) {
            memoizedDistances.put(cacheKey, 1);

            // cache it the other way, too
            cacheKey = to.name + ":" + from.name;
            memoizedDistances.put(cacheKey, 1);
            return 1;
        }

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

    public class ValveList extends ArrayList<ValveInfo> {

    }

    public record ValveInfo(String name, int flow, List<String> destinations) implements Comparable {
        @Override
        public int compareTo(Object o) {
            ValveInfo other = (ValveInfo) o;
            return other.flow - this.flow;
        }
    }
}
