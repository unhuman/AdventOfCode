package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day5 extends InputParser {
    private static final String regex1 = "(?:seeds: )?(\\d+) ?";
    private static final String regex2 = "(.* map:| *\\d+)";

    public Day5() {
        super(2023, 5, regex1, regex2);
    }

    public Day5(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Long> seeds = new ArrayList<>();
        for (ItemLine line : configGroup.get(0)) {
            for (String element : line) {
                seeds.add(Long.parseLong(element));
            }
        }

        Map<String, ConversionData> lookups = new HashMap<>();
        for (GroupItem item : configGroup1) {
            String[] conversion = item.get(0).get(0).split(" ")[0].split("-");
            String from = conversion[0];
            String to = conversion[2];

            List<List<String>> data = new ArrayList<>();
            for (int i = 1; i < item.size(); i++) {
                data.add(item.get(i));
            }
            ConversionData conversionData = new ConversionData(from, to, data);
            lookups.put(from, conversionData);
        }

        List<Long> values = new ArrayList<>();
        for (Long value: seeds) {
            String lookupName = "seed";
            while (true) {
                // if we don't find anything else to lookup, we're done with this item
                if (!lookups.containsKey(lookupName)) {
                    values.add(value);
                    break;
                }
                ConversionData currentConversion = lookups.get(lookupName);

                value = currentConversion.convert(value);

                // our next lookup is going to be
                lookupName = currentConversion.to;
            }
        }

        values.sort(Comparator.naturalOrder());
        return values.get(0);
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Long> seeds = new ArrayList<>();
        for (ItemLine line : configGroup.get(0)) {
            for (String element : line) {
                seeds.add(Long.parseLong(element));
            }
        }

        Map<String, ConversionData> lookups = new HashMap<>();
        for (GroupItem item : configGroup1) {
            String[] conversion = item.get(0).get(0).split(" ")[0].split("-");
            String from = conversion[0];
            String to = conversion[2];

            List<List<String>> data = new ArrayList<>();
            for (int i = 1; i < item.size(); i++) {
                data.add(item.get(i));
            }
            ConversionData conversionData = new ConversionData(from, to, data);
            lookups.put(from, conversionData);
        }

        Long smallest = Long.MAX_VALUE;
        for (int i = 0; i < seeds.size(); i += 2) {
            System.out.println("Processing " + i + " of seed starts " + seeds.size());
            Long startSeed = seeds.get(i);
            Long seedCount = seeds.get(i + 1);

            for (long seedNum = startSeed; seedNum < startSeed + seedCount; seedNum++) {
                long lookupNum = seedNum;
                String lookupName = "seed";
                while (true) {
                    // if we don't find anything else to lookup, we're done with this item
                    if (!lookups.containsKey(lookupName)) {
                        if (lookupNum < smallest) {
                            smallest = lookupNum;
                        }
                        break;
                    }
                    ConversionData currentConversion = lookups.get(lookupName);

                    lookupNum = currentConversion.convert(lookupNum);

                    // our next lookup is going to be
                    lookupName = currentConversion.to;
                }
            }
        }

        return smallest;
    }

    class ConversionData {
        String from;
        String to;
        List<List<Long>> conversions;
        // todo rules

        ConversionData(String from, String to, List<List<String>> data) {
            this.from = from;
            this.to = to;
            this.conversions = new ArrayList<>(data.size());
            for (List<String> dataList: data) {
                List<Long> converter = new ArrayList<>();
                for (String numText: dataList) {
                    converter.add(Long.parseLong(numText.trim()));
                }
                conversions.add(converter);
            }

        }

        Long convert(long value) {
            for (List<Long> conversion: conversions) {
                if (value >= conversion.get(1) && value <= conversion.get(1) + conversion.get(2)) {
                    long diff = value - conversion.get(1);
                    return conversion.get(0) + diff;
                }
            }

            // if we didn't find anything, return value
            return value;
        }
    }
}
