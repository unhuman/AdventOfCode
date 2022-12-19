package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.*;

public class Day19 extends InputParser {
    // Blueprint 13: Each ore robot costs 2 ore. Each clay robot costs 4 ore. Each obsidian robot costs 4 ore and 18 clay. Each geode robot costs 2 ore and 11 obsidian.
    private static final String regex1 = "Blueprint (\\d+):" +
            " Each ore robot costs (\\d+) ore." +
            " Each clay robot costs (\\d+) ore." +
            " Each obsidian robot costs (\\d+) ore and (\\d+) clay." +
            " Each geode robot costs (\\d+) ore and (\\d+) obsidian.";
    private static final String regex2 = null;

    private static int maxFound = 0;

    public Day19(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        List<Blueprint> blueprints = new ArrayList<>();
        for (int groupItemIdx = 0; groupItemIdx < dataItems1.size(); groupItemIdx++) {
            GroupItem item = dataItems1.get(groupItemIdx);
            for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
                ItemLine line = item.get(lineIdx);
                Integer number = Integer.parseInt(line.get(0));
                Robot oreRobotRecipe = new Robot(Integer.parseInt(line.get(1)), 0, 0);
                Robot clayRobotRecipe = new Robot(Integer.parseInt(line.get(2)), 0, 0);
                Robot obsidianRobotRecipe = new Robot(Integer.parseInt(line.get(3)), Integer.parseInt(line.get(4)), 0);
                Robot geodeRobotRecipe = new Robot(Integer.parseInt(line.get(5)), 0, Integer.parseInt(line.get(6)));
                Blueprint blueprint = new Blueprint(number, oreRobotRecipe, clayRobotRecipe, obsidianRobotRecipe, geodeRobotRecipe);
                assert(blueprints.size() == number - 1);
                blueprints.add(blueprint);
            }
        }

        int score = 0;
        for (int i = 0; i < blueprints.size(); i++) {
            maxFound = 0;
            State state = new State(blueprints.get(i));
            System.out.println("Processing blueprint " + state.blueprint.number);
            score += state.blueprint.number * process(state);
        }
        return score;
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        return 2;
    }

    public int process(State state) {
        if (state.time == 24) {
            if (state.geodes > maxFound) {
                System.out.println("Blueprint " + state.blueprint.number + " could have: " + state.geodes);
                maxFound = state.geodes;
            }
            return state.geodes;
        }

        // decide on what to do
        TreeMap<Integer, State> returnedValues = new TreeMap<>();
        if (state.canCreateOreRobot()) {
            State stateTry = new State(state);
            stateTry.payForOreRobot();
            stateTry.processTime();
            stateTry.createOreRobot();
            returnedValues.put(process(stateTry), stateTry);
        }
        if (state.canCreateClayRobot()) {
            State stateTry = new State(state);
            stateTry.payForClayRobot();
            stateTry.processTime();
            stateTry.createClayRobot();
            returnedValues.put(process(stateTry), stateTry);
        }
        if (state.canCreateObsidianRobot()) {
            State stateTry = new State(state);
            stateTry.payForObsidianRobot();
            stateTry.processTime();
            stateTry.createObsidianRobot();
            returnedValues.put(process(stateTry), stateTry);
        }
        if (state.canCreateGeodeRobot()) {
            State stateTry = new State(state);
            stateTry.payForGeodeRobot();
            stateTry.processTime();
            stateTry.createGeodeRobot();
            returnedValues.put(process(stateTry), stateTry);
        }

        // always do this one - keep processing
        {
            State stateTry = new State(state);
            // no creation here
            stateTry.processTime();
            returnedValues.put(process(stateTry), stateTry);
        }

        // find the best value and use that
        return returnedValues.lastKey();
    }

    record Blueprint(int number, Robot oreRobotRecipe, Robot clayRobotRecipe, Robot obsidianRobotRecipe, Robot geodeRobotRecipe) {
    }

    class State {
        Blueprint blueprint;

        int time = 0;

        int oreRobots = 1; // we always start with 1 ore robot
        int clayRobots = 0;
        int obsidianRobots = 0;
        int geodeRobots = 0;

        int ore = 0;
        int clay = 0;
        int obsidian = 0;
        int geodes = 0;

        public State(Blueprint blueprint) {
            this.blueprint = blueprint;
        }

        public State(State state) {
            this.time = state.time;

            this.blueprint = state.blueprint;
            this.oreRobots = state.oreRobots;
            this.clayRobots = state.clayRobots;
            this.obsidianRobots = state.obsidianRobots;
            this.geodeRobots = state.geodeRobots;

            this.ore = state.ore;
            this.clay = state.clay;
            this.obsidian = state.obsidian;
            this.geodes = state.geodes;
        }

        void processTime() {
            ++time;

            ore += oreRobots;
            clay += clayRobots;
            obsidian += obsidianRobots;
            geodes += geodeRobots;
        }

        // note recipes will only make a robot when one item exactly matches the amount needed
        // otherwise, we're saving for something else, so we should reduce decision points for recursion

        boolean canCreateOreRobot() {
            // require exact match as decision point
            return (ore >= blueprint.oreRobotRecipe.oreCost);
        }

        void payForOreRobot() {
            ore -= blueprint.oreRobotRecipe.oreCost;
        }

        void createOreRobot() {
            ++oreRobots;
        }

        boolean canCreateClayRobot() {
            // require exact match as decision point
            return (ore >= blueprint.clayRobotRecipe.oreCost);
        }

        void payForClayRobot() {
            ore -= blueprint.clayRobotRecipe.oreCost;
        }

        void createClayRobot() {
            ++clayRobots;
        }

        boolean canCreateObsidianRobot() {
            // require at least one exact match as decision point
            return ((ore >= blueprint.obsidianRobotRecipe.oreCost
                    && clay >= blueprint.obsidianRobotRecipe.clayCost) ||
                    (ore >= blueprint.obsidianRobotRecipe.oreCost
                            && clay >= blueprint.obsidianRobotRecipe.clayCost));
        }

        void payForObsidianRobot() {
            ore -= blueprint.obsidianRobotRecipe.oreCost;
            clay -= blueprint.obsidianRobotRecipe.clayCost;
        }

        void createObsidianRobot() {
            ++obsidianRobots;
        }

        boolean canCreateGeodeRobot() {
            // require at least one exact match as decision point
            return ((ore >= blueprint.geodeRobotRecipe.oreCost
                    && obsidian >= blueprint.geodeRobotRecipe.obsidianCost) ||
                    (ore >= blueprint.geodeRobotRecipe.oreCost
                            && obsidian >= blueprint.geodeRobotRecipe.obsidianCost));
        }

        void payForGeodeRobot() {
            ore -= blueprint.geodeRobotRecipe.oreCost;
            obsidian -= blueprint.geodeRobotRecipe.obsidianCost;
        }

        void createGeodeRobot() {
            ++geodeRobots;
        }
    }

    record Robot(Integer oreCost, Integer clayCost, Integer obsidianCost) {
    }
}
