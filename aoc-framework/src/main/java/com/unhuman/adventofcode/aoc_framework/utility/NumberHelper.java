package com.unhuman.adventofcode.aoc_framework.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberHelper {
    static final Pattern NUMBER_PARSER_PATTERN = Pattern.compile("[^\\d]*?(\\d+)");
    public static List<Integer> parseNumbers(String data) {
        List<Integer> numbers = new ArrayList<>();
        Matcher matcher = NUMBER_PARSER_PATTERN.matcher(data);
        while (true) {
            if (matcher.find()) {
                if (matcher.groupCount() == 1) {
                    numbers.add(Integer.parseInt(matcher.group(1)));
                    // allow continuation for duplicate matchers on a line
                    data = data.substring(matcher.group(0).length());
                    // if we advance to the end of the string and it's done, then we process next line
                    if (data.isEmpty()) {
                        break;
                    }
                    matcher = NUMBER_PARSER_PATTERN.matcher(data);
                } else {
                    throw new RuntimeException("Found no numbers (" + matcher.groupCount()
                            + ") in line: " + data + " with regex: " +
                            NUMBER_PARSER_PATTERN.pattern());
                }
            } else {
                break;
            }
        }
        return numbers;
    }
}
