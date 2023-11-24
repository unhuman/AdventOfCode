package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Day25 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    public Day25() {
        super(2022, 25, regex1, regex2);
    }

    public Day25(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        long sum = 0;
        for (int groupItemIdx = 0; groupItemIdx < dataItems1.size(); groupItemIdx++) {
            GroupItem item = dataItems1.get(groupItemIdx);
            System.out.println("Processing " + item.size() + " lines");
            for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
                ItemLine line = item.get(lineIdx);
                String data = "";
                for (int elementIdx = 0; elementIdx < line.size(); elementIdx++) {
                    data += line.get(elementIdx);
                }
                System.out.println(data);
                long value = convertSnafu(data);
                sum += value;
            }
        }

        // now we need to convert the number back to SNAFU
        return getSnafu(sum);
    }


    public static long convertSnafu(String snafu) {
        int power = 0;
        long number = 0;

        for (int i = snafu.length() - 1; i >= 0; i--) {
            String element = snafu.substring(i, i + 1);
            long value;
            switch (element) {
                case "-" -> value = -1;
                case "=" -> value = -2;
                default -> value = Integer.parseInt(element);
            }
            number += value * Math.pow(5, power++);
        }
        return number;
    }

    public static String getSnafu(long sum) {
        String buildNumber = "";
        int carry = 0;
        while (sum > 0) {
            int digit = (int) (sum % 5);
            carry = 0;
            switch (digit) {
                case 0, 1, 2 -> buildNumber = digit + buildNumber;
                case 3 -> {
                    buildNumber = '=' + buildNumber;
                    carry = 5;
                }
                case 4 -> {
                    buildNumber = '-' + buildNumber;
                    carry = 5;
                }
            }
            sum = (sum + carry) / 5;
        }
        System.out.println("snafu value: " + buildNumber);

        return buildNumber;
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        return 2;
    }
}
