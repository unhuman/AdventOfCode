package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day11 extends InputParser {
    private static final String REGEX_1 = "(\\w+)";
    private static final String REGEX_2 = null;

    public Day11() {
        super(2025, 11, REGEX_1, REGEX_2);
    }

    public Day11(String filename) {
        super(filename, REGEX_1, REGEX_2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.getFirst();
        Map<String, List<String>> deviceOutputs = new HashMap<>(group0.size());
        Map<String, List<String>> outputDevices = new HashMap<>(group0.size());
        for (ItemLine line : group0) {
            String device = line.get(0);
            List<String> outputs = new ArrayList<>(line.size());
            for (int itemNum = 1; itemNum < line.size(); itemNum++) {
                String output = line.get(itemNum);
                outputs.add(output);

                if (!outputDevices.containsKey(output)) {
                    outputDevices.put(output, new ArrayList<>());
                }
                outputDevices.get(output).add(device);
            }
            deviceOutputs.put(device, outputs);
        }

        List<String> currentProcess = new ArrayList<>();
        currentProcess.addAll(deviceOutputs.get("you"));

        long count = 0;
        while (!currentProcess.isEmpty()) {
            List<String> nextRoundProcess = new ArrayList<>();
            for (String device: currentProcess) {
                List<String> outputs = deviceOutputs.get(device);
                for (String output: outputs) {
                    if (output.equals("out")) {
                        ++count;
                    } else {
                        nextRoundProcess.add(output);
                    }
                }
            }

            currentProcess = nextRoundProcess;
        }


//        Set<String> connectedToOut = findConnected(outputDevices, "out");
//        long alternate = findPathsFromTo(deviceOutputs, connectedToOut,"you", "out");
//        System.out.println("Alternate value: " + alternate);

        return count;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.getFirst();
        Map<String, List<String>> deviceOutputs = new HashMap<>(group0.size());
        Map<String, List<String>> outputDevices = new HashMap<>(group0.size());
        for (ItemLine line : group0) {
            String device = line.get(0);
            List<String> outputs = new ArrayList<>(line.size());
            for (int itemNum = 1; itemNum < line.size(); itemNum++) {
                String output = line.get(itemNum);
                outputs.add(output);

                if (!outputDevices.containsKey(output)) {
                    outputDevices.put(output, new ArrayList<>());
                }
                outputDevices.get(output).add(device);
            }
            deviceOutputs.put(device, outputs);
        }

        Set<String> connectedToDac = findConnected(outputDevices, "dac");
        Set<String> connectedToFft = findConnected(outputDevices, "fft");
        Set<String> connectedToOut = findConnected(outputDevices, "out");

        // 39060 too low

        return findPathsFromTo(deviceOutputs, connectedToDac,"svr", "dac")
                    * findPathsFromTo(deviceOutputs, connectedToFft, "dac", "fft")
                    * findPathsFromTo(deviceOutputs, connectedToOut, "fft", "out")
                + findPathsFromTo(deviceOutputs, connectedToFft, "svr", "fft")
                    * findPathsFromTo(deviceOutputs, connectedToDac,"fft", "dac")
                    * findPathsFromTo(deviceOutputs, connectedToOut, "dac", "out");

    }

    long findPathsFromTo(Map<String, List<String>> deviceOutputs, Set<String> allowedNodes,
                         String deviceStart, String outputEnd) {

        System.out.println("Tracking " + deviceStart + " to " + outputEnd);

        long count = 0;
        Map<String, Integer> currentProcess = new HashMap();
        for (String deviceOutput: deviceOutputs.get(deviceStart)) {
            currentProcess.put(deviceOutput, 1);
        }


        while (!currentProcess.isEmpty()) {
            Map<String, Integer> nextRoundProcess = new HashMap<>();
            for (String device: currentProcess.keySet()) {
                int deviceCount = currentProcess.get(device);
                List<String> outputs = deviceOutputs.get(device);
                if (outputs != null) {
                    for (String output : outputs) {
                        int increment = currentProcess.get(device);
                        if (output.equals(outputEnd)) {
                            count += increment;
                        } else {
                            nextRoundProcess.put(output, (!nextRoundProcess.containsKey(output)) ? increment : nextRoundProcess.get(output) + increment);
                        }
                    }
                }
            }

            currentProcess = nextRoundProcess;
        }

        return count;
    }

    Set<String> findConnected(Map<String, List<String>> outputDevices, String desired) {
        Set<String> founds = new HashSet<>();

        List<String> looks = Collections.singletonList(desired);

        while (!looks.isEmpty()) {
            List<String> newLooks = new ArrayList<>();
            for (String look: looks) {
                List<String> devices = outputDevices.get(look);
                if (devices != null) {
                    for (String device : devices) {
                        if (!founds.contains(device)) {
                            founds.add(device);
                            newLooks.add(device);
                        }
                    }
                }
                looks = newLooks;
            }
        }

        return founds;
    }
}
