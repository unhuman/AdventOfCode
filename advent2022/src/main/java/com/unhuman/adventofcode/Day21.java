package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 extends InputParser {
    private static final String regex1 = "(\\w+): ([^\\s]+)( . )?(\\w*)";
    private static final String regex2 = null;

    public Day21() {
        super(2022, 21, regex1, regex2);
    }

    public Day21(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        Map<String, Data> allData = new HashMap<>();
        Subscriptions subscriptions = new Subscriptions();

        for (int groupItemIdx = 0; groupItemIdx < dataItems1.size(); groupItemIdx++) {
            GroupItem item = dataItems1.get(groupItemIdx);
            for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
                ItemLine line = item.get(lineIdx);
                Data dataItem;
                String name = line.get(0);
                if (line.get(2) == null) {
                    dataItem = new Data(name, Long.parseLong(line.get(1)));
                } else {
                    dataItem = new Data(name, line.get(1), line.get(2).charAt(1), line.get(3));
                }
                allData.put(name, dataItem);
            }
        }

        allData.forEach((key, value) -> {
            if (value.requiresReference()) {
                subscriptions.subscribe(value.reference1, key);
                subscriptions.subscribe(value.reference2, key);
            }
        });

        while (true) {
            for (Data data: allData.values()) {
                if (data.operation == null) {
                    // monkey yells the number
                    if (data.name.equals("root")) {
                        return data.value1;
                    }

                    // process subscriptions
                    List<String> subs = subscriptions.get(data.name);
                    if (subs == null) {
                        continue;
                    }

                    for (String subName: subs) {
                        Data subscriber = allData.get(subName);
                        if (subscriber.reference1 != null && subscriber.reference1.equals(data.name)) {
                            subscriber.value1 = data.value1;
                            subscriber.reference1 = null;
                        }
                        if (subscriber.reference2 != null && subscriber.reference2.equals(data.name)) {
                            subscriber.value2 = data.value1;
                            subscriber.reference2 = null;
                        }
                    }
                    subscriptions.remove(data.name);

                    continue;
                }

                if (data.value1 == null || data.value2 == null) {
                    continue;
                }

                switch (data.operation) {
                    case '+':
                        data.value1 = data.value1 + data.value2;
                        break;
                    case '-':
                        data.value1 = data.value1 - data.value2;
                        break;
                    case '*':
                        data.value1 = data.value1 * data.value2;
                        break;
                    case '/':
                        data.value1 = data.value1 / data.value2;
                        break;
                }
                data.operation = null;
                data.value2 = null;
            }
        }
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        Map<String, Data> allData = new HashMap<>();
        Subscriptions subscriptions = new Subscriptions();

        for (int groupItemIdx = 0; groupItemIdx < dataItems1.size(); groupItemIdx++) {
            GroupItem item = dataItems1.get(groupItemIdx);
            for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
                ItemLine line = item.get(lineIdx);
                Data dataItem;
                String name = line.get(0);

                // Special case for humn
                if (name.equals("humn")) {
                    dataItem = new Data(name, null, null, null);
                } else if (line.get(2) == null) {
                    dataItem = new Data(name, Long.parseLong(line.get(1)));
                } else {
                    char operation = line.get(2).charAt(1);
                    if (name.equals("root")) {
                        operation = '=';
                    }
                    dataItem = new Data(name, line.get(1), operation, line.get(3));
                }
                allData.put(name, dataItem);
            }
        }

        allData.forEach((key, value) -> {
            if (value.requiresReference()) {
                subscriptions.subscribe(value.reference1, key);
                subscriptions.subscribe(value.reference2, key);
            }
        });

        boolean dataResolved;
        do {
            dataResolved = false;
            for (Data data: allData.values()) {
                if (data.operation == null) {
                    // process subscriptions
                    List<String> subs = subscriptions.get(data.name);
                    if (subs == null) {
                        continue;
                    }

                    for (String subName: subs) {
                        Data subscriber = allData.get(subName);
                        if (subscriber.reference1 != null && subscriber.reference1.equals(data.name)) {
                            subscriber.value1 = data.value1;
                            subscriber.reference1 = null;
                        }
                        if (subscriber.reference2 != null && subscriber.reference2.equals(data.name)) {
                            subscriber.value2 = data.value1;
                            subscriber.reference2 = null;
                        }
                    }
                    subscriptions.remove(data.name);
                    dataResolved = true;

                    continue;
                }

                if (data.value1 == null || data.value2 == null) {
                    continue;
                }

                switch (data.operation) {
                    case '+':
                        data.value1 = data.value1 + data.value2;
                        break;
                    case '-':
                        data.value1 = data.value1 - data.value2;
                        break;
                    case '*':
                        data.value1 = data.value1 * data.value2;
                        break;
                    case '/':
                        data.value1 = data.value1 / data.value2;
                        break;
                    case '=':
                        if (data.value1 != data.value2) {
                            System.out.println("Warning Data " + data.backupReference1 + " value " + data.value1 + " != "
                                    + data.backupReference2 + " value " + data.value2);
                        } else {
                            System.out.println("Data " + data.backupReference1 + " value " + data.value1 + " != "
                                    + data.backupReference2 + " value " + data.value2);
                        }

                        break;

                }
                dataResolved = true;
                data.operation = null;
                data.value2 = null;
            }
        } while (dataResolved);

        // try to resolve
        Data root = allData.get("root");
        long matchingValue = (root.value1 != null) ? root.value1 : root.value2;
        String matchingData = (root.reference1 != null) ? root.reference1 : root.reference2;

        long result = SolveFor(allData, matchingValue, matchingData);

        return result;
    }

    long SolveFor(Map<String, Data> allData, long desiredValue, String valueResolve) {
        Data element = allData.get(valueResolve);
        Character operation = element.operation;
        boolean valueIsLeft = (element.value1 != null);
        long value = (valueIsLeft) ? element.value1 : element.value2;
        String otherResolve = (valueIsLeft) ? element.reference2 : element.reference1;

        // this is it!!!
        if (otherResolve == null) {
            switch (operation) {
                case '+':
                    return desiredValue - value;
                case '-':
                    if (valueIsLeft) {
                        return -1 * (desiredValue - value);
                    } else { // value is on the right
                        return desiredValue + value;
                    }
                case '*':
                    return desiredValue / value;
                case '/':
                    if (valueIsLeft) {
                        return value / desiredValue;
                    } else { // value is on the right
                        return desiredValue * value;
                    }
            }
        }

        switch (operation) {
            case '+':
                return SolveFor(allData, desiredValue - value, otherResolve);
            case '-':
                if (valueIsLeft) {
                    return SolveFor(allData, -1 * (desiredValue - value), otherResolve);
                } else { // value is on the right
                    return SolveFor(allData, desiredValue + value, otherResolve);
                }
            case '*':
                return SolveFor(allData, desiredValue / value, otherResolve);
            case '/':
                if (valueIsLeft) {
                    return SolveFor(allData, value / desiredValue, otherResolve);
                } else { // value is on the right
                    return SolveFor(allData, desiredValue * value, otherResolve);
                }
        }
        throw new RuntimeException("You don't get to be here");
    }

    record Equation(Character operation, String lhs, String rhs) {
    }

    class Data {
        String name;
        Long value1 = null;
        Long value2 = null;
        Character operation = null;

        String reference1 = null;
        String reference2 = null;

        String backupReference1 = null;
        String backupReference2 = null;

        Data(String name, long value) {
            this.name = name;
            this.value1 = value;
        }

        Data(String name, String reference1, Character operation, String reference2) {
            this.name = name;
            this.reference1 = reference1;
            this.backupReference1 = reference1;
            this.operation = operation;
            this.reference2 = reference2;
            this.backupReference2 = reference2;
        }

        boolean requiresReference() {
            return this.reference1 != null && this.reference2 != null;
        }
    }

    class Subscriptions extends HashMap<String, ArrayList<String>> {
        void subscribe(String item, String listener) {
            if (get(item) == null) {
                put(item, new ArrayList<>());
            }
            get(item).add(listener);
        }
    }
}
