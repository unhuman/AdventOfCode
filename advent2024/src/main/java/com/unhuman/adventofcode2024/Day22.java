package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

public class Day22 extends InputParser {
    private static final String regex1 = "(\\d+)";
    private static final String regex2 = null;

    public Day22() {
        super(2024, 22, regex1, regex2);
    }

    public Day22(String filename) {
        super(filename, regex1, regex2);
    }

    long evolutionCount = 2000;

    public void setEvolutionCount(long count) {
        evolutionCount = count;
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Long total = 0L;
        GroupItem group0 = configGroup.get(0);
        for (ItemLine line : group0) {
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
                Long secret = line.getLong(itemNum);

                for (int i = 0; i < evolutionCount; i++) {
                    secret = evolve(secret);
                }

                total += secret;
            }
        }

        return total;
    }

    public long evolve(Long secret) {
        secret = mix(secret, secret * 64);
        secret = prune(secret);
        secret = mix(secret, secret / 32);
        secret = prune(secret);
        secret = mix(secret, secret * 2048);
        secret = prune(secret);
        return secret;
    }

    public long mix(Long secret, Long number) {
        return number ^ secret;
    }

    public long prune(Long secret) {
        return secret % 16777216L;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // Map of sequences to map of buyer / prices
        Map<List<Long>, HashMap<Long, Long>> tracking = new HashMap<>();
        GroupItem group0 = configGroup.get(0);
        for (ItemLine line : group0) {
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
                Long originalSecret = line.getLong(itemNum);
                Long secret = originalSecret;
                Long price = secret % 10;

                Queue<Long> mostRecent4 = new LinkedList<>();
                for (int i = 0; i < evolutionCount; i++) {
                    Long priorPrice = price;
                    secret = evolve(secret);
                    price = secret % 10;
                    mostRecent4.add(price - priorPrice);
                    if (mostRecent4.size() == 4) {
                        if (!tracking.containsKey(mostRecent4)) {
                            List<Long> mostRecent4Copy = new ArrayList<>(mostRecent4);
                            tracking.put(mostRecent4Copy, new HashMap<>());
                        }
                        if (!tracking.get(mostRecent4).containsKey(originalSecret)) {
                            tracking.get(mostRecent4).put(originalSecret, price);
                        }
                        // remove the first thing so we can keep cycling
                        mostRecent4.remove();
                    }
                }
            }
        }

        AtomicLong most = new AtomicLong();

        Collection<HashMap<Long, Long>> results = tracking.values();
        results.forEach(result -> {
            long currentTotal = result.values().stream().mapToLong(Long::longValue).sum();
            if (currentTotal > most.get()) {
                most.set(currentTotal);
            }
        });

        return most.get();
    }
}
