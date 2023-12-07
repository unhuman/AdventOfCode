package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;

public class Day6 extends InputParser {
    private static final String regex1 = ".*: *(\\d.*)";
    private static final String regex2 = null;

    public Day6() {
        super(2023, 6, regex1, regex2);
    }

    public Day6(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int waysToWinTotal = 0;
        GroupItem data = configGroup.get(0);

        String[] times = data.get(0).get(0).split("\\s+");
        String[] records = data.get(1).get(0).split("\\s+");

        for (int i = 0; i < times.length; i++) {
            int time = Integer.parseInt(times[i]);
            int record = Integer.parseInt(records[i]);

            int wins = 0;
            for (int hold = 1; hold < time; hold++) {
                int remainingTime = time - hold;
                int distance = hold * remainingTime;

                if (distance > record) {
                    wins += 1;
                }
            }
            waysToWinTotal = (waysToWinTotal == 0) ? wins : waysToWinTotal * wins;
        }

        return waysToWinTotal;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        GroupItem data = configGroup.get(0);

        String[] times = data.get(0).get(0).split("\\s+");
        String[] records = data.get(1).get(0).split("\\s+");

        String timeStr = "";
        String recordStr = "";
        for (int i = 0; i < times.length; i++) {
            timeStr += times[i];
            recordStr += records[i];
        }
        Long time = Long.parseLong(timeStr);
        Long record = Long.parseLong(recordStr);

        long firstWin = 0;
        long minSpeed = record / time;
        for (long hold = minSpeed; hold < time; hold++) {
            long remainingTime = time - hold;
            long distance = hold * remainingTime;

            if (distance > record) {
                firstWin = hold;
                break;
            }
        }
        long lastWin = time - firstWin;
        return lastWin - firstWin + 1;
    }
}
