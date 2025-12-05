package com.unhuman.adventofcode2025;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class Day5 extends InputParser {
    private static final String regex1 = "(\\d+)-(\\d+)";
    private static final String regex2 = ("(\\d+)");

    public Day5() {
        super(2025, 5, regex1, regex2);
    }

    public Day5(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);
        List<Pair<Long, Long>> freshRanges = new ArrayList<>();
        for (ItemLine line : group0) {
            Long startRange = line.getLong(0);
            Long endRange = line.getLong(1);
            freshRanges.add(new Pair<>(startRange, endRange));
        }

        // Here's code for a 2nd group, if needed
        long count = 0L;
        GroupItem group1 = configGroup1.get(0);
        for (ItemLine line : group1) {
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
                Long value = line.getLong(itemNum);

                if (freshRanges.stream().filter(
                        range ->
                                (value >= range.getLeft() & value <= range.getRight())).findFirst().isPresent()) {
                    count++;
                    break;
                };
            }
        }


        return count;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        GroupItem group0 = configGroup.get(0);

        List<Pair<Long, Long>> freshRanges = new ArrayList<>();

        for (ItemLine line : group0) {
            final java.util.concurrent.atomic.AtomicLong startRange = new AtomicLong(line.getLong(0));
            final java.util.concurrent.atomic.AtomicLong endRange = new AtomicLong(line.getLong(1));

            do {
                Optional<Pair<Long, Long>> overlapRange = freshRanges.stream().filter(
                        range ->
                                ((startRange.get() < range.getLeft() && endRange.get() >= range.getLeft()
                                        || (startRange.get() <= range.getRight() && endRange.get() > range.getRight())))).findFirst();

                boolean overlaps = overlapRange.isPresent();
                // Deal with overlaps (remove + readd), else add a new range
                if (overlaps) {
                    // remove the overlapping ranges
                    freshRanges.remove(overlapRange.get());
                    freshRanges.remove(new Pair<>(startRange.get(), endRange.get()));

                    // reset start/end range so we can keep finding more overlaps
                    startRange.set(Math.min(overlapRange.get().getLeft(), startRange.get()));
                    endRange.set(Math.max(overlapRange.get().getRight(), endRange.get()));

                    // add our combined range
                    freshRanges.add(new Pair<>(startRange.get(), endRange.get()));
                } else {
                    // No overlap: Just add our range (if it doesn't exist) and bail out
                    Pair<Long, Long> checkToAdd = new Pair<>(startRange.get(), endRange.get());
                    if (!freshRanges.contains(checkToAdd))
                    {
                        freshRanges.add(checkToAdd);
                    }
                    break;
                }
            } while (true);
        }

        // The above algorithm, I thought, would've covered all the overlaps, but it didn't,
        // so, here we go, loop until we resolve them all
        while (true) {
            boolean overlap = false;
            for (int i = 0; i < freshRanges.size() - 1; i++) {
                for (int j = 0; j < freshRanges.size(); j++) {
                    Pair<Long, Long> check = freshRanges.get(i);
                    Pair<Long, Long> compare = freshRanges.get(j);

                    if ((check.getLeft() < compare.getLeft() && check.getRight() >= compare.getLeft()
                            || (check.getLeft() <= compare.getRight() && check.getRight() > compare.getRight()))) {
                        freshRanges.remove(check);
                        freshRanges.remove(compare);
                        Long startRange = Math.min(check.getLeft(), compare.getLeft());
                        Long endRange = Math.max(check.getRight(), compare.getRight());
                        freshRanges.add(new Pair<>(startRange, endRange));
                        overlap = true;
                    }
                }
            }
            if (!overlap) {
                break;
            }
        }

        long count = 0L;
        for (Pair<Long, Long> range: freshRanges) {
            count += range.getRight() - range.getLeft() + 1;
        }
        return count;
    }
}
