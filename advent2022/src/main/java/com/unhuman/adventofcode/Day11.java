package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 extends InputParser {
    final static String ID_MONKEY_DATA = " (\\d+):";
    final static String ID_ITEMS_DATA = ": (\\d+,? ?)+";
    final static String ID_OPERATION_DATA = ": (new = old) (.) (\\d+|old)";
    final static String ID_TEST_DATA = ": (divisible by) (\\d+)";
    final static String ID_CHECK_DATA = " (true|false): throw to monkey (\\d+)";

    private static final String regex1 = "(Monkey|Starting items|Operation|Test|If)"
            + "(" + ID_MONKEY_DATA
            + "|" + ID_ITEMS_DATA
            + "|" + ID_OPERATION_DATA
            + "|" + ID_TEST_DATA
            + "|" + ID_CHECK_DATA + ")";

    private static final String regex2 = null;

    public Day11(String[] filenameAndCookieInfo) {
        super(filenameAndCookieInfo, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        List<Monkey> monkeys = parseMonkeys(dataItems1);

        return processData(monkeys, 3, 20);
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        List<Monkey> monkeys = parseMonkeys(dataItems1);

        return processData(monkeys, 1, 10000);
    }

    private long processData(List<Monkey> monkeys, long stressReductionFactor, int rounds) {
        int lcm = 1;
        for (int i = 0; i < monkeys.size(); i++) {
            lcm *= monkeys.get(i).testDivisibleBy;
        }

        for (int round = 0; round < rounds; round++) {
            for (int i = 0; i < monkeys.size(); i++) {
                Monkey monkey = monkeys.get(i);
                while (monkey.worries.size() > 0) {

                    monkey.inspections++;

                    long worry = monkey.worries.remove(0);
                    long worryAdjustment = (monkey.operationBy.equals("old")
                            ? worry : Integer.parseInt(monkey.operationBy));
                    switch (monkey.operation) {
                        case "*":
                            worry = worry * worryAdjustment;
                            break;
                        case "+":
                            worry = worry + worryAdjustment;
                            break;
                    }

                    worry /= stressReductionFactor;

                    // throw the worry to another monkey
                    if (worry % monkey.testDivisibleBy == 0) {
                        monkeys.get(monkey.trueMonkey).worries.add(worry % lcm);
                    } else {
                        monkeys.get(monkey.falseMonkey).worries.add(worry % lcm);
                    }
                }
            }
        }
        Collections.sort(monkeys, Collections.reverseOrder());
        return monkeys.get(0).inspections * monkeys.get(1).inspections;
    }

    private List<Monkey> parseMonkeys(ConfigGroup dataItems1) {
        List<Monkey> monkeys = new ArrayList<>();
        for (GroupItem item : dataItems1) {
            Monkey monkey = new Monkey();
            for (ItemLine line : item) {
                switch (line.get(0)) {
                    case "Monkey":
                        // We don't need this
                        int currentMonkey = Integer.parseInt(line.get(2));
                        if (currentMonkey != monkeys.size()) {
                            throw new RuntimeException("Bad monkey found");
                        }
                        break;
                    case "Starting items":
                        monkey.worries = Arrays.stream(line.get(1).split("[^\\d]+")).toList()
                                .stream()
                                .filter(string -> !string.isEmpty())
                                .map(string -> Long.parseLong(string))
                                .collect(Collectors.toList());
                        break;
                    case "Operation":
                        monkey.operation = line.get(5);
                        monkey.operationBy = line.get(6);
                        break;
                    case "Test":
                        monkey.testDivisibleBy = Integer.parseInt(line.get(8));
                        break;
                    case "If":
                        if (Boolean.parseBoolean(line.get(9))) { // true or false condition
                            monkey.trueMonkey = Integer.parseInt(line.get(10));
                        } else {
                            monkey.falseMonkey = Integer.parseInt(line.get(10));
                        }
                        break;
                }
            }
            monkeys.add(monkey);
        }
        return monkeys;
    }

    public class Monkey implements Comparable{
        Long inspections = 0L;
        List<Long> worries = new ArrayList<>();
        Integer testDivisibleBy = null;
        String operation = "";
        String operationBy = null;
        Integer trueMonkey = null;
        Integer falseMonkey = null;

        @Override
        public int compareTo(Object o) {
            return this.inspections.compareTo(((Monkey) o).inspections);
        }
    }
}
