package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day19 extends InputParser {
    //private static final String regex1 = "([^{]*)\\{(.*?),(\\w+)\\}";
    private static final String regex1 = "([^{]*)\\{(.*?)(\\w+)\\}";
    private static final String regex2 = "(\\w)=(\\d+)";

    private static final int MAX_COUNT = 4000;

    enum Operation { LESS_THAN, GREATER_THAN }

    static final String regex1a = "(\\w)([><])(\\d+):(\\w+),";

    public Day19() {
        super(2023, 19, regex1, regex2);
    }

    public Day19(String filename) {
        super(filename, regex1, regex2);
    }

    record Work(char category, Operation operation, Integer compValue, String matchAction) {
    }

    record Workflow(String name, List<Work> steps, String defaultBehavior) {
    }

    static class MinMax {
        Integer min = 1;
        Integer max = MAX_COUNT;

        MinMax() { }
        MinMax(MinMax other) {
            min = other.min;
            max = other.max;
        }

        void updateMin(int newValue) {
            min = Math.max(min, newValue);
        }

        void updateMax(int newValue) {
            max = Math.min(max, newValue);
        }

        int calcScore() {
            return (max - min >= 0) ? max - min + 1 : 0;
        }
    }

    static class OptimizedRule extends HashMap<Character, MinMax> {
        OptimizedRule() { }
        OptimizedRule(OptimizedRule other) {
            other.forEach((k, v) -> { put(k, new MinMax(v)); });
        }

        void applyStep(Work step) {
            Character category = step.category;
            if (!containsKey(category)) {
                put(category, new MinMax());
            }
            switch (step.operation) {
                case LESS_THAN -> get(category).updateMax(step.compValue - 1);
                case GREATER_THAN -> get(category).updateMin(step.compValue + 1);
            }
        }

        long calculateScore() {
            long score = 1L;
            score *= (containsKey('x') ? get('x').calcScore() : MAX_COUNT);
            score *= (containsKey('m') ? get('m').calcScore() : MAX_COUNT);
            score *= (containsKey('a') ? get('a').calcScore() : MAX_COUNT);
            score *= (containsKey('s') ? get('s').calcScore() : MAX_COUNT);
            return score;
        }
    }

    static class Part extends HashMap<Character, Integer> {
        @Override
        public String toString() {
            return this.values().stream().map(Object::toString).collect(Collectors.joining(","));
        }
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        GroupItem item = configGroup.get(0);

        Pattern workflowPattern = Pattern.compile(regex1a);
        Map<String, Workflow> workflows = getStringWorkflowMap(item, workflowPattern);

        List<Part> parts = new ArrayList<>();
        GroupItem item1 = configGroup1.get(0);
        for (ItemLine line : item1) {
            Part part = new Part();
            for (int i = 0; i < line.size(); i += 2) {
                Character category = line.get(i).charAt(0);
                Integer value = Integer.parseInt(line.get(i + 1));
                part.put(category, value);
            }
            parts.add(part);
        }

        List<Part> accepted = new ArrayList<>();
        List<Part> rejected = new ArrayList<>();

        for (Part part: parts) {
            //char results = processPart(workflows, part);
            char results = processPartRecursive(workflows, "in", part);
            switch (results) {
                case 'A' -> accepted.add(part);
                case 'R' -> rejected.add(part);
            }
        }

        // Sum of all the value in the accepted map
        return accepted.stream().map(
                x -> x.values().stream().mapToInt(Integer::intValue).sum()).mapToInt(Integer::intValue).sum();
    }

    static Map<String, Workflow> getStringWorkflowMap(GroupItem item, Pattern workflowPattern) {
        Map<String, Workflow> workflows = new LinkedHashMap<>();
        for (ItemLine line : item) {
            String workflowName = line.get(0);
            String workflowWork = line.get(1);
            String workflowDefault = line.get(2);
            Matcher workflowMatcher = workflowPattern.matcher(workflowWork);
            List<Work> workflowSteps = new ArrayList<>();
            while (workflowMatcher.find()) {
                char category = workflowMatcher.group(1).charAt(0);
                char operation = workflowMatcher.group(2).charAt(0);
                Integer compValue = Integer.parseInt(workflowMatcher.group(3));
                String matchAction = workflowMatcher.group(4);

                Work work = new Work(category,
                        operation == '<' ? Operation.LESS_THAN : Operation.GREATER_THAN,
                        compValue, matchAction);
                workflowSteps.add(work);
            }

            Workflow workflow = new Workflow(workflowName, workflowSteps, workflowDefault);
            workflows.put(workflowName, workflow);
        }
        return workflows;
    }

    char processPart(Map<String, Workflow> workflows, Part part) {
        Workflow current = workflows.get("in");
        while (true) {
            String conclusion = current.defaultBehavior;
            boolean result = false;
            for (Work step: current.steps) {
                char check = step.category;
                Operation operation = step.operation;
                Integer expectedValue = step.compValue;
                String matchAction = step.matchAction();
                result = (operation == Operation.GREATER_THAN)
                        ? part.get(check) > expectedValue : part.get(check) < expectedValue;
                if (result) {
                    switch (matchAction) {
                        case "A":
                        case "R":
                            return matchAction.charAt(0);
                        default:
                            current = workflows.get(matchAction);
                    }
                    break;
                }
            }
            if (!result) {
                switch (conclusion) {
                    case "A":
                    case "R":
                        return conclusion.charAt(0);
                    default:
                        current = workflows.get(conclusion);
                }
            }
        }
    }

    char processPartRecursive(Map<String, Workflow> workflows, String currentWorkflow, Part part) {
        Workflow current = workflows.get(currentWorkflow);
        String conclusion = current.defaultBehavior;

        for (Work step: current.steps) {
            char check = step.category;
            Operation operation = step.operation;
            Integer expectedValue = step.compValue;
            String matchAction = step.matchAction();
            boolean result = (operation == Operation.GREATER_THAN)
                    ? part.get(check) > expectedValue : part.get(check) < expectedValue;
            if (result) {
                switch (matchAction) {
                    case "A":
                    case "R":
                        return matchAction.charAt(0);
                    default:
                        return processPartRecursive(workflows, matchAction, part);
                }
            }
        }

        switch (conclusion) {
            case "A":
            case "R":
                return conclusion.charAt(0);
            default:
                return processPartRecursive(workflows, conclusion, part);
        }
    }

    static void findPathsToAcceptance(List<OptimizedRule> pathsToAcceptance, Map<String, Workflow> workFlow, OptimizedRule currentRules, String name) {
        // We find an endpoint
        if (name.equals("A")) {
            pathsToAcceptance.add(currentRules);
            return;
        }
        // nothing to do
        if (name.equals("R")) {
            return;
        }

        Workflow current = workFlow.get(name);

        // Now for whatever is left, we will need to apply the inverse
        OptimizedRule myCurrentRules = new OptimizedRule(currentRules);

        for (Work step: current.steps) {
            // If this step is a rejection - we only want the inverse moving forward
            if (!step.matchAction.equals("R")) {
                // try to apply this step - copy the rules for recursion
                OptimizedRule checkRules = new OptimizedRule(myCurrentRules);
                applyStepToRules(checkRules, step);
                findPathsToAcceptance(pathsToAcceptance, workFlow, checkRules, step.matchAction);
            }

            // Apply the inverse to the stack and continue
            Work inverseStep = createInverseStep(step);
            myCurrentRules.applyStep(inverseStep);
        }

        // myCurrentRules are now ready for default handling
        findPathsToAcceptance(pathsToAcceptance, workFlow, myCurrentRules, current.defaultBehavior);
    }

    static Work createInverseStep(Work step) {
        Operation newOperation = (step.operation == Operation.LESS_THAN) ? Operation.GREATER_THAN : Operation.LESS_THAN;
        int newValue = step.compValue + ((step.operation == Operation.LESS_THAN) ? -1 : 1);
        Work inverseStep = new Work(step.category, newOperation, newValue, null);
        return inverseStep;
    }

    static void applyStepToRules(OptimizedRule currentRules, Work step) {
        currentRules.applyStep(step);
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        GroupItem item = configGroup.get(0);

        Map<String, Workflow> workflows = new LinkedHashMap<>();
        List<Part> parts = new ArrayList<>();

        Pattern workflowPattern = Pattern.compile(regex1a);
        for (ItemLine line : item) {
            String workflowName = line.get(0);
            String workflowWork = line.get(1);
            String workflowDefault = line.get(2);
            Matcher workflowMatcher = workflowPattern.matcher(workflowWork);
            List<Work> workflowSteps = new ArrayList<>();
            while (workflowMatcher.find()) {
                char category = workflowMatcher.group(1).charAt(0);
                Operation operation = (workflowMatcher.group(2).charAt(0) == '>')
                        ? Operation.GREATER_THAN : Operation.LESS_THAN;
                Integer compValue = Integer.parseInt(workflowMatcher.group(3));
                String matchAction = workflowMatcher.group(4);

                Work work = new Work(category, operation, compValue, matchAction);
                workflowSteps.add(work);
            }

            Workflow workflow = new Workflow(workflowName, workflowSteps, workflowDefault);
            workflows.put(workflowName, workflow);
        }

        List<OptimizedRule> pathsToAcceptance = new ArrayList<>();
        findPathsToAcceptance(pathsToAcceptance, workflows, new OptimizedRule(), "in");

        // Sum of all the value in the accepted mapworkflows
        long score = getScore(pathsToAcceptance);
        return score;
    }

    static long getScore(List<OptimizedRule> pathsToAcceptance) {
        long score = 0;
        for (OptimizedRule pathToAcceptance: pathsToAcceptance) {
            score += pathToAcceptance.calculateScore();
        }
        return score;
    }
}
