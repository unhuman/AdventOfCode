package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Matrix;

import java.util.ArrayList;
import java.util.List;

public class Day25 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    public Day25() {
        super(2024, 25, regex1, regex2);
    }

    public Day25(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        // easier to assume there's only one group
        List<List<Long>> locks = new ArrayList<>();
        List<List<Long>> keys = new ArrayList<>();
        for (GroupItem group : configGroup) {
            Matrix matrix = new Matrix(group, Matrix.DataType.CHARACTER);
            boolean isLock = matrix.getValue(0, 0) == '#';
            List<Long> lockOrKey = new ArrayList<>();
            for (int x = 0; x < matrix.getWidth(); x++) {
                lockOrKey.add(0L);
                for (int i = 1; i < matrix.getHeight() - 1; i++) {
                    int y = (isLock) ? i : matrix.getHeight() - 1 - i;
                    if (matrix.getValue(x, y) == '#') {
                        lockOrKey.set(x, lockOrKey.get(x) + 1);
                    }
                }
            }
            if (isLock) {
                locks.add(lockOrKey);
            } else {
                keys.add((lockOrKey));
            }
        }

        long count = 0L;
        for (List<Long> lock: locks) {
            for (List<Long> key: keys) {
                boolean match = true;
                for (int i = 0; i < lock.size(); i ++) {
                    if (lock.get(i) + key.get(i) > 5) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        return 2;
    }
}
