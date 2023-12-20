package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day20 extends InputParser {
    static long pressNum = 0L;
    private static final String regex1 = "([%&]?)(.*?) -> (.*)";
    private static final String regex2 = null;

    enum Pulse { HIGH, LOW }

    public Day20() {
        super(2023, 20, regex1, regex2);
    }

    public Day20(String filename) {
        super(filename, regex1, regex2);
    }

    record Message(Module source, String destination, Pulse pulse) {
        @Override
        public String toString() {
            return String.format("%s %s -> %s", source.name, pulse.name(), destination);
        }
    }

    static abstract class Module {
        static boolean trackLowSentToRX = true;
        static Long lowPulsesSent = 0L;
        static Long highPulsesSent = 0L;
        static Map<String, Module> sharedModuleMap = new HashMap<>();

        String name;
        List<String> destinations = new ArrayList<>();
        static List<Message> queue = new ArrayList<>();

        Module(String name, List<String> destinations) {
            this.name = name;
            this.destinations.addAll(destinations);
        }

        public static void setRXTrackLow(boolean trackLowSentToRX) {
            Module.trackLowSentToRX = trackLowSentToRX;
        }

        public static Long getLowPulsesSent() {
            return lowPulsesSent;
        }

        public static Long getHighPulsesSent() {
            return highPulsesSent;
        }

        static Map<String, Module> obtainModuleMap() {
            sharedModuleMap.clear();

            lowPulsesSent = 0L;
            highPulsesSent = 0L;

            return sharedModuleMap;
        }

        static void linkModules() {
            sharedModuleMap.forEach((moduleName, module) -> {
                module.destinations.forEach(destinationName -> {
                    Module destination = sharedModuleMap.get(destinationName);
                    if (destination != null) {
                        destination.notifyConnection(module);
                    }
                });
            });
        }

        void addDestination(String module) {
            this.destinations.add(module);
            notifyConnection(this);
        }

        void sendPulse(Pulse pulse) {
            // add everything to the queue
            for (String destination: destinations) {
                queue.add(new Message(this, destination, pulse));
                lowPulsesSent += (pulse == Pulse.LOW) ? 1 : 0;
                highPulsesSent += (pulse == Pulse.HIGH) ? 1 : 0;
            }
        }

        static boolean queueReady() {
            return (!queue.isEmpty());
        }

        static void processQueue() {
            // copy (and reset) the queue
            List<Message> useQueue = new ArrayList<>(queue);
            queue = new ArrayList<>();

            // process messages we had batched
            for (Message message: useQueue) {
//                System.out.println(message);
                Module destination = sharedModuleMap.get(message.destination);

//                if (destination == null && trackLowSentToRX)
//                {
//                    System.out.println(message + " " + ++rxSendCounter);
//                }
                if (destination != null || (message.pulse == Pulse.LOW && trackLowSentToRX)) {
                    destination.receivePulse(message.source, message.pulse);
                }
            }
        }

        abstract void receivePulse(Module source, Pulse pulse);

        void notifyConnection(Module source) { }
    }

    static class FlipFlop extends Module {
        enum State { ON, OFF }
        State state = State.OFF;

        FlipFlop(String name, List<String> destinations) {
            super(name, destinations);
        }

        @Override
        void receivePulse(Module source, Pulse pulse) {
            // HIGH is ignored
            if (pulse == Pulse.LOW) {
                state = (state == State.ON) ? State.OFF : State.ON;
                sendPulse ((state == State.ON) ? Pulse.HIGH : Pulse.LOW);
            }
        }
    }

    static class Conjunction extends Module {
        Map<Module, Pulse> recentPulses = new HashMap<>();
        Map<Module, Long> recentHighPulses = new HashMap<>();
        Map<Module, Long> caughtHighPulses = new HashMap<>();

        Conjunction(String name, List<String> destinations) {
            super(name, destinations);
        }

        @Override
        void notifyConnection(Module source) {
            recentPulses.put(source, Pulse.LOW);
            recentHighPulses.put(source, 0L);
        }

        @Override
        void receivePulse(Module source, Pulse pulse) {
            recentPulses.put(source, pulse);
            if (pulse == Pulse.HIGH && trackLowSentToRX) {
                if (!caughtHighPulses.containsKey(source)) {
                    caughtHighPulses.put(source, pressNum);
                    if (caughtHighPulses.size() == recentPulses.size() && destinations.size() == 1 && sharedModuleMap.get(destinations.get(0)) == null) {
                        throw new RuntimeException(caughtHighPulses.values().stream().reduce(1L, (a, b) -> (a * b)).toString());
                    }
//                    System.out.println(String.format("%s source %s cycle: %d, pressNum: %d ", name, source.name, pressNum, recentHighPulses.get(source)));
                }
            }


            // All high, send low pulse, else high pulse
            boolean allHighFlags = (recentPulses.values().stream().filter(level -> level == Pulse.HIGH).count() == recentPulses.size());
            sendPulse((allHighFlags) ? Pulse.LOW : Pulse.HIGH);
        }
    }

    static class Broadcast extends Module {
        Broadcast(String name, List<String> destinations) {
            super(name, destinations);
        }

        @Override
        void receivePulse(Module source, Pulse pulse) {
            sendPulse(pulse);
        }
    }

    static class Button {
        Broadcast broadcastModule;

        Button(Broadcast broadcastModule) {
            this.broadcastModule = broadcastModule;
        }

        void push() {
            broadcastModule.receivePulse(null, Pulse.LOW);
        }
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Module.setRXTrackLow(false);
        Map<String, Module> moduleMap = Module.obtainModuleMap();

        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        Button button = null;
        for (ItemLine line : item) {
            String moduleType = (line.get(0) == null) ? "B" : line.get(0);
            String moduleName = line.get(1);
            List<String> destinations = Arrays.asList(line.get(2).split(", "));

            Module module;
            switch (moduleType) {
                case "%":
                    module = new FlipFlop(moduleName, destinations);
                    break;
                case "&":
                    module = new Conjunction(moduleName, destinations);
                    break;
                case "B":
                    Broadcast broadcastModule = new Broadcast(moduleName, destinations);
                    button = new Button(broadcastModule);
                    module = broadcastModule;
                    break;
                default:
                    throw new RuntimeException("WHAT IS THIS??? " + moduleType);
            }
            moduleMap.put(moduleName, module);
        }

        // now perform linking of modules
        Module.linkModules();

        int buttonPressesLow = 1000;
        for (int i = 0; i < buttonPressesLow; i++) {
            button.push();
            while (Module.queueReady()) {
                Module.processQueue();
            }
        }

        long lowPulses = Module.getLowPulsesSent();
        long highPulses = Module.getHighPulsesSent();

        return (lowPulses + buttonPressesLow) * highPulses;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Module.setRXTrackLow(true);
        Map<String, Module> moduleMap = Module.obtainModuleMap();

        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        Button button = null;

        Module endingModule = null;

        for (ItemLine line : item) {
            String moduleType = (line.get(0) == null) ? "B" : line.get(0);
            String moduleName = line.get(1);
            List<String> destinations = Arrays.asList(line.get(2).split(", "));

            Module module;
            switch (moduleType) {
                case "%":
                    module = new FlipFlop(moduleName, destinations);
                    break;
                case "&":
                    module = new Conjunction(moduleName, destinations);
                    break;
                case "B":
                    Broadcast broadcastModule = new Broadcast(moduleName, destinations);
                    button = new Button(broadcastModule);
                    module = broadcastModule;
                    break;
                default:
                    throw new RuntimeException("WHAT IS THIS??? " + moduleType);
            }

            if (destinations.contains("nx")) {
                endingModule = module;
            }

            moduleMap.put(moduleName, module);
        }

        // now perform linking of modules
        Module.linkModules();

        while (true) {
            ++pressNum;
            button.push();
            while (Module.queueReady()) {
                try {
                    Module.processQueue();
                } catch (Exception e) {
                    System.out.println("Took " + pressNum + " button presses");
                    return Long.parseLong(e.getMessage());
                }
            }
        }
    }
}
