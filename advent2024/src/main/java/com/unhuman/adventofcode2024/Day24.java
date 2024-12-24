package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Day24 extends InputParser {
    private static final String regex1 = "([a-z]+)([\\d]*): ([\\d])";
    private static final String regex2 = "([a-z]+)([\\d]*) (AND|OR|XOR) ([a-z]+)([\\d]*) -> ([a-z]+)([\\d]*)";

    public Day24() {
        super(2024, 24, regex1, regex2);
    }

    public Day24(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);
        Map<String, Long> variables = new HashMap<>();
        for (ItemLine line : group0) {
            String varName = line.getString(0);
            Long varBit = line.getLong(1);
            Long varValue = line.getLong(2);

            if (varBit == null) {
                variables.put(varName, varValue);
            }
            else {
                if (!variables.containsKey(varName)) {
                    variables.put(varName, 0L);
                }
                variables.put(varName, variables.get(varName) | varValue << varBit);
            }
        }

        variables.put("z", 0L);
        List<ItemLine> lines = new ArrayList<>(configGroup1.get(0));
        // Here's code for a 2nd group, if needed
        GroupItem group1 = configGroup1.get(0);
        while (!group1.isEmpty()) {
            Iterator<ItemLine> iter = group1.iterator();
            while (iter.hasNext()) {
                ItemLine line = iter.next();

                String var1Name = line.getString(0);
                Long var1Bit = line.getLong(1);

                String operation = line.getString(2);

                String var2Name = line.getString(3);
                Long var2Bit = line.getLong(4);

                String destName = line.getString(5);
                Long destBit = line.getLong(6);

                if (!variables.containsKey(var1Name)) {
                    continue;
                }
                if (!variables.containsKey(var2Name)) {
                    continue;
                }

                // remove this line b/c we can process it.
                iter.remove();

                Long value1 = variables.get(var1Name);
                value1 = (var1Bit == null) ? value1 : (value1 & (1L << var1Bit)) >> var1Bit;

                Long value2 = variables.get(var2Name);
                value2 = (var2Bit == null) ? value2 : (value2 & (1L << var2Bit)) >> var2Bit;

                Long result = 0L;
                switch (operation) {
                    case "AND":
                        result = value1 & value2;
                        break;
                    case "OR":
                        result = value1 | value2;
                        break;
                    case "XOR":
                        result = value1 ^ value2;
                        break;
                }

                if (destBit == null) {
                    variables.put(destName, result);
                } else {
                    if (!variables.containsKey(destName)) {
                        variables.put(destName, 0L);
                    }
                    Long currentValue = variables.get(destName);

                    if (result == 0) {
                        currentValue &= ~(1L << destBit);
                    } else {
                        currentValue |= (1L << destBit);
                    }

                    variables.put(destName, currentValue);
                }
            }
        }

        return variables.get("z");
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        return 2;
    }
}
