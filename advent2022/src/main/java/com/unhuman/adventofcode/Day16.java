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

    public Day16() {
        super(2022, 16, regex1, regex2);
    }

    public Day16(String filename) {
        super(filename, regex1, regex2);
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

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        timeLeft = 26;

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

        WorkerStates workerStates = new WorkerStates(2, valves.get("AA"));

        int flow = prioritizedProcessingPart2(valvesWithFlow, workerStates, timeLeft, 0, 0);

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

    int assignValveScore(ValveInfo destinationValveInfo, String currentValve, int timeLeft) {
        if (destinationValveInfo.name.equals(currentValve)) {
            return 0;
        }
        int distance = memoizedDistances.get(destinationValveInfo.name + ":" + currentValve);
        int score = destinationValveInfo.flow * (timeLeft - (distance + 1)); // +1 = open valve time
        return score > 0 ? score : 0;
    }

    int assignValveScore(ValveInfo destinationValveInfo, ValveInfo currentValve, int timeLeft) {
        return assignValveScore(destinationValveInfo, currentValve.name, timeLeft);
    }


    int prioritizedProcessingPart2(List<ValveInfo> valvesWithFlow, WorkerStates workers, int timeLeft, int currentFlow,
                                   int nextCycleStartingFlow) {

        currentFlow += nextCycleStartingFlow;

        if (timeLeft <= 0) {
            return currentFlow;
        }

        // All workers do their work (if any)
        nextCycleStartingFlow = 0;
//        int[] openedValveFlow = new int[2];
        int valveCounter = 0;
        for (WorkerState worker : workers) {
            worker.performWork();
            int newFlow = worker.consumeFlow();
//            openedValveFlow[valveCounter++] = newFlow;
            nextCycleStartingFlow += newFlow;
        }

//        System.out.println("Timeleft: " + timeLeft + " / Minute " + (26 - timeLeft) + " currentFlow: " + currentFlow);
//        System.out.println("     Human Worker 1: " + workers.get(0) + (openedValveFlow[0] != 0 ? " and opened valve " + openedValveFlow[0] : ""));
//        System.out.println("     Eleph Worker 2: " + workers.get(1) + (openedValveFlow[1] != 0 ? " and opened valve " + openedValveFlow[1] : ""));

        // If no valves left and both workers are available, we can just calculate what's left
        if (valvesWithFlow.size() <= 0) {
            // TODO: the following conditions below function the same after all workers done
            if (workers.findAvailableWorkers().size() == 2) {
                // we're done - we need to return the flow.
                return (timeLeft + 1) * currentFlow + timeLeft * nextCycleStartingFlow;
            } else {
                // we just need to let the workers finish their job
                return currentFlow + prioritizedProcessingPart2(valvesWithFlow, workers, timeLeft - 1,
                        currentFlow, nextCycleStartingFlow);
            }
        }

        // if no available workers, keep it simple, and just process next day
        if (workers.findAvailableWorkers().size() == 0) {
            return currentFlow + prioritizedProcessingPart2(valvesWithFlow, workers, timeLeft - 1, currentFlow,
                    nextCycleStartingFlow);
        }

        // if there's only one valve left, find the worker with the shortest distance to it
        if (valvesWithFlow.size() == 1) {
            // TODO: Skip allocating this valve if the distance for the other worker will be shorter

            ValveInfo lastValve = valvesWithFlow.remove(0);

            List<WorkerState> availableWorkers = workers.findAvailableWorkers();
            WorkerState chosenWorker = availableWorkers.get(0);
            Integer distance = memoizedDistances.get(chosenWorker.startingValve.name() + ':' + lastValve.name());
            for (int i = 1; i < availableWorkers.size(); i++) {
                WorkerState checkWorker = availableWorkers.get(i);
                int checkDistance = memoizedDistances.get(checkWorker.startingValve.name() + ':' + lastValve.name());
                if (checkDistance < distance) {
                    chosenWorker = checkWorker;
                    distance = checkDistance;
                }
            }
            chosenWorker.assignWork(lastValve, distance, false);
            return currentFlow + prioritizedProcessingPart2(valvesWithFlow, workers, timeLeft - 1,
                    currentFlow, nextCycleStartingFlow);
        }

        // TODO: State getting lost when picking workers (copies are bad)
        // we have work to do and at least one available worker, so let's cater the work for that one
        // and if there's another, we'll cater the alternatives for that one.

        int maxValue = 0;
        int depthCheckLimit = 9;
        // we use the list ^ as indexes because we will be adjusting the data for a second worker
        for (int i = 0; i < valvesWithFlow.size() && i < depthCheckLimit; i++) {
            ArrayList<ValveInfo> valvesWithFlowCopy = new ArrayList<>(valvesWithFlow); // copy; already sorted

            WorkerStates workersCopy = new WorkerStates(workers);
            List<WorkerState> availableWorkers = workersCopy.findAvailableWorkers();
            WorkerState firstWorker = availableWorkers.get(0);
            valvesWithFlowCopy.sort(new Comparator<ValveInfo>() {
                @Override
                public int compare(ValveInfo v1, ValveInfo v2) {
                    int score1 = assignValveScore(v1, firstWorker.startingValve, timeLeft);
                    int score2 = assignValveScore(v2, firstWorker.startingValve, timeLeft);
                    return score2 - score1; // we want highest first
                }
            });

            ValveInfo testValve1 = valvesWithFlowCopy.remove(i);

            WorkerState worker1 = availableWorkers.get(0);
            int valve1Distance = memoizedDistances.get(worker1.startingValve.name() + ':' + testValve1.name());
            worker1.assignWork(testValve1, valve1Distance, true); // we force to not use more memory

            // If there's another available worker, let's get them hooked up with work
            if (availableWorkers.size() > 1) {
                WorkerState worker2 = availableWorkers.get(1);
                valvesWithFlowCopy.sort(new Comparator<ValveInfo>() {
                    @Override
                    public int compare(ValveInfo v1, ValveInfo v2) {
                        int score1 = assignValveScore(v1, worker2.startingValve, timeLeft);
                        int score2 = assignValveScore(v2, worker2.startingValve, timeLeft);
                        return score2 - score1; // we want highest first
                    }
                });
                for (int j = 0; j < valvesWithFlowCopy.size() && j < depthCheckLimit; j++) {
                    ValveInfo testValve2 = valvesWithFlowCopy.remove(j);
                    Integer valve2Distance = memoizedDistances.get(worker2.startingValve.name() + ':' + testValve2.name());
                    worker2.assignWork(testValve2, valve2Distance, true); // force

                    // See if flipping the destinations is faster
                    Integer worker1ToValve2Distance =
                            memoizedDistances.get(worker1.startingValve.name() + ':' + testValve2.name());
                    Integer worker2ToValve1Distance =
                            memoizedDistances.get(worker2.startingValve.name() + ':' + testValve1.name());

                    if (worker1.timeToDestinationValve + worker2.timeToDestinationValve
                            > worker1ToValve2Distance + 1 + worker2ToValve1Distance + 1) { // account for open valve time
//                        System.out.println("     -- Swapping destinations!");
                        worker1.assignWork(testValve2, worker1ToValve2Distance, true); // force
                        worker2.assignWork(testValve1, worker2ToValve1Distance, true); // force
                    }

                    WorkerStates workersCopy2 = new WorkerStates(workersCopy);
                    List<ValveInfo> valvesWithFlowCopy2 = new ArrayList<>(valvesWithFlowCopy);

                    int checkValue = prioritizedProcessingPart2(valvesWithFlowCopy2, workersCopy2, timeLeft - 1,
                            currentFlow, nextCycleStartingFlow);
                    maxValue = Math.max(maxValue, checkValue);
                }
            } else {
                // this is just single worker by themselves
                WorkerStates workersCopy2 = new WorkerStates(workersCopy);
                List<ValveInfo> valvesWithFlowCopy2 = new ArrayList<>(valvesWithFlowCopy);

                int checkValue = prioritizedProcessingPart2(valvesWithFlowCopy2, workersCopy2, timeLeft - 1,
                        currentFlow, nextCycleStartingFlow);
                maxValue = Math.max(maxValue, checkValue);
            }
        }

        int result = currentFlow + ((maxValue != 0) ? maxValue : timeLeft * currentFlow);

        if (timeLeft == 26) {
            System.out.println(result);
        }

        return result;
    }

    class WorkerState {
        enum WorkState { AVAILABLE, PROCESSING, NEXT_ADD_FLOW }
        WorkState workState;
        ValveInfo startingValve;
        ValveInfo destinationValve;
        int timeToDestinationValve = 0;

        WorkerState(ValveInfo startingValve) {
            this.startingValve = startingValve;
            this.destinationValve = null;
            this.timeToDestinationValve = 0;
            this.workState = WorkState.AVAILABLE;
        }

        WorkerState(WorkerState other) {
            this.startingValve = other.startingValve;
            this.destinationValve = other.destinationValve;
            this.timeToDestinationValve = other.timeToDestinationValve;
            this.workState = other.workState;
        }

        void assignWork(ValveInfo destinationValve, int timeToDestinationValve, boolean force) {
            if (!force) {
                assert (this.destinationValve == null);
                assert (this.timeToDestinationValve == 0);
            }
            this.workState = WorkState.PROCESSING;
            this.destinationValve = destinationValve;
            this.timeToDestinationValve = timeToDestinationValve + 1; // include time to open valve
        }

        int consumeFlow() {
            if (workState == WorkState.NEXT_ADD_FLOW) {
                try {
                    this.workState = WorkState.AVAILABLE;
                    return destinationValve.flow();
                } finally {
                    startingValve = destinationValve;
                    destinationValve = null;
                }
            }
            return 0;
        }

        /**
         * perform work
         * @return true if the work is completed
         */
        WorkState performWork() {
            if (workState == WorkState.PROCESSING) {
                assert(timeToDestinationValve > 0);
                if (--timeToDestinationValve == 0) {
                    workState = WorkState.NEXT_ADD_FLOW;
                }
            } else {
                workState = WorkState.AVAILABLE;
            }
            return workState;
        }

        @Override
        public String toString() {
            return "from: " + startingValve.name + " to: " + ((destinationValve != null) ? destinationValve.name : "none") + " timeLeft: " + this.timeToDestinationValve;
        }
    }

    class WorkerStates extends ArrayList<WorkerState> {
        WorkerStates(int count, ValveInfo startingValve) {
            for (int i = 0; i < count; i++) {
                WorkerState workerState = new WorkerState(startingValve);
                this.add(workerState);
            }
        }

        WorkerStates(WorkerStates other) {
            for (int i = 0; i < other.size(); i++) {
                add(new WorkerState(other.get(i)));
            }
        }

        List<WorkerState> findAvailableWorkers() {
            List<WorkerState> availableWorkers = new ArrayList<>();
            for (WorkerState worker: this) {
                if (worker.workState == WorkerState.WorkState.AVAILABLE) {
                    availableWorkers.add(worker);
                }
            }
            return availableWorkers;
        }
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

    public record ValveInfo(String name, int flow, List<String> destinations) implements Comparable {
        @Override
        public int compareTo(Object o) {
            ValveInfo other = (ValveInfo) o;
            return other.flow - this.flow;
        }
    }
}
