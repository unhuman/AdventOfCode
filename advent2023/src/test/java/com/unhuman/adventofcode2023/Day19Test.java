package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.unhuman.adventofcode2023.Day19.findPathsToAcceptance;
import static com.unhuman.adventofcode2023.Day19.getScore;
import static com.unhuman.adventofcode2023.Day19.getStringWorkflowMap;
import static com.unhuman.adventofcode2023.Day19.regex1a;

public class Day19Test {
    // data must be at least 2 lines - add \n for single line data
    private static final String DATA =
                    "px{a<2006:qkq,m>2090:A,rfg}\n" +
                    "pv{a>1716:R,A}\n" +
                    "lnx{m>1548:A,A}\n" +
                    "rfg{s<537:gd,x>2440:R,A}\n" +
                    "qs{s>3448:A,lnx}\n" +
                    "qkq{x<1416:A,crn}\n" +
                    "crn{x>2662:A,R}\n" +
                    "in{s<1351:px,qqz}\n" +
                    "qqz{s>2770:qs,m<1801:hdj,R}\n" +
                    "gd{a>3333:R,R}\n" +
                    "hdj{m>838:A,pv}\n" +
                    "\n" +
                    "{x=787,m=2655,a=1222,s=2876}\n" +
                    "{x=1679,m=44,a=2067,s=496}\n" +
                    "{x=2036,m=264,a=79,s=2244}\n" +
                    "{x=2461,m=1339,a=466,s=291}\n" +
                    "{x=2127,m=1623,a=2188,s=1013}\n";

    static InputParser getDay(String data) {
         return new Day19(data);
    }

    @Test
    public void test1() {
        InputParser day = getDay(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(19114, day.processInput1(groups[0], groups[1]));
    }

    @Test
    public void test2() {
        InputParser day = getDay(DATA);
        ConfigGroup[] groups = day.parseFiles();
        Assertions.assertEquals(167409079868000L, day.processInput2(groups[0], groups[1]));
    }

    @Test
    void testOptimization0a() {
        Pattern workflowPattern = Pattern.compile(regex1a);
        GroupItem items = new GroupItem();
        ItemLine itemLine = new ItemLine();
        itemLine.add("in");
        itemLine.add("s<1:A,");
        itemLine.add("R");
        items.add(itemLine);
        Map<String, Day19.Workflow> workflows = getStringWorkflowMap(items, workflowPattern);

        List <Day19.OptimizedRule> pathsToAcceptance = new ArrayList<>();
        Day19.OptimizedRule optimizedRule = new Day19.OptimizedRule();
        findPathsToAcceptance(pathsToAcceptance, workflows, optimizedRule, "in");
        long score = getScore(pathsToAcceptance);
        Assertions.assertEquals(0L, score);
    }

    @Test
    void testOptimization0b() {
        Pattern workflowPattern = Pattern.compile(regex1a);
        GroupItem items = new GroupItem();
        ItemLine itemLine = new ItemLine();
        itemLine.add("in");
        itemLine.add("s>4000:A,");
        itemLine.add("R");
        items.add(itemLine);
        Map<String, Day19.Workflow> workflows = getStringWorkflowMap(items, workflowPattern);

        List <Day19.OptimizedRule> pathsToAcceptance = new ArrayList<>();
        Day19.OptimizedRule optimizedRule = new Day19.OptimizedRule();
        findPathsToAcceptance(pathsToAcceptance, workflows, optimizedRule, "in");
        long score = getScore(pathsToAcceptance);
        Assertions.assertEquals(0L, score);
    }

    @Test
    void testOptimization1a() {
        Pattern workflowPattern = Pattern.compile(regex1a);
        GroupItem items = new GroupItem();
        ItemLine itemLine = new ItemLine();
        itemLine.add("in");
        itemLine.add("s<2:A,");
        itemLine.add("R");
        items.add(itemLine);
        Map<String, Day19.Workflow> workflows = getStringWorkflowMap(items, workflowPattern);

        List <Day19.OptimizedRule> pathsToAcceptance = new ArrayList<>();
        Day19.OptimizedRule optimizedRule = new Day19.OptimizedRule();
        findPathsToAcceptance(pathsToAcceptance, workflows, optimizedRule, "in");
        long score = getScore(pathsToAcceptance);
        Assertions.assertEquals(64000000000L, score);
    }

    @Test
    void testOptimization1b() {
        Pattern workflowPattern = Pattern.compile(regex1a);
        GroupItem items = new GroupItem();
        ItemLine itemLine = new ItemLine();
        itemLine.add("in");
        itemLine.add("s>3999:A,");
        itemLine.add("R");
        items.add(itemLine);
        Map<String, Day19.Workflow> workflows = getStringWorkflowMap(items, workflowPattern);

        List <Day19.OptimizedRule> pathsToAcceptance = new ArrayList<>();
        Day19.OptimizedRule optimizedRule = new Day19.OptimizedRule();
        findPathsToAcceptance(pathsToAcceptance, workflows, optimizedRule, "in");
        long score = getScore(pathsToAcceptance);
        Assertions.assertEquals(64000000000L, score);
    }
}
