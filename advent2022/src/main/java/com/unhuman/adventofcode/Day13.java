package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 extends InputParser {
    private static final String regex1 = "(.*)";
    private static final String regex2 = null;

    private static final Pattern numberFinder = Pattern.compile("(\\d+)(.*)");

    public Day13() {
        super(2022, 13, regex1, regex2);
    }

    public Day13(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        int total = 0;

        for (int i = 0; i < dataItems1.size(); i++) {
            GroupItem item = dataItems1.get(i);
            String leftText = item.get(0).get(0);
            String rightText = item.get(1).get(0);

            Representation left = new Representation(leftText);
            Representation right = new Representation(rightText);

            if (left.compareTo(right) < 1) {
                total += i + 1;
            }
        }
        return total;
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        List<Representation> allData = new ArrayList<>();

        for (int i = 0; i < dataItems1.size(); i++) {
            GroupItem item = dataItems1.get(i);
            for (int j = 0; j< item.size(); j++) {
                ItemLine itemLine = item.get(j);
                String text = itemLine.get(0);

                Representation representation = new Representation(text);
                allData.add(representation);
            }
        }

        // add the required dividers
        Representation divider1 = new Representation("[[2]]");
        Representation divider2 = new Representation("[[6]]");
        allData.add(divider1);
        allData.add(divider2);

        // Sort the data
        allData.sort(new Comparator<Representation>() {
            @Override
            public int compare(Representation o1, Representation o2) {
                return o1.compareTo(o2);
            }
        });

        // Find the dividers
        int total = 0;
        for (int i = 0; i < allData.size(); i++) {
            if (allData.get(i) == divider1 || allData.get(i) == divider2) {
                total = (total == 0) ? (i + 1) : total * (i + 1);
            }
        }

        return total;
    }

    class Representation implements Comparable {
        enum DataType { VALUE, LIST }
        private Object value;

        Representation(String string) {
            this(string, null);
        }

        private Representation(String text, Representation parent) {
            if (text.startsWith("[")) {
                this.value = new ArrayList<>();
                List<Representation> contents = new ArrayList<>();
                int closeArray = findCloseArray(text);
                String data = text.substring(1, closeArray);
                while (data.length() > 0) {
                    // find next item in the string
                    Matcher numberMatcher = numberFinder.matcher(data);
                    if (numberMatcher.matches()) {
                        contents.add(new Representation(numberMatcher.group(1), this));
                        data = numberMatcher.group(2);
                    } else {
                        // we have a group
                        int endGroup = findCloseArray(data);
                        String group = data.substring(0, endGroup + 1);
                        contents.add(new Representation(group, this));
                        data = data.substring(endGroup + 1);
                    }

                    // skip any trailing comma
                    if (data.startsWith(",")) {
                        data = data.substring(1);
                    }
                }
                value = contents;
            } else {
                value = Integer.parseInt(text);
                parent.addValue(this);
            }

        }

        private int findCloseArray(String text) {
            int opens = 0;
            for (int i = 0; i < text.length(); i++) {
                switch(text.charAt(i)) {
                    case '[':
                        ++opens;
                        break;
                    case ']':
                        if (--opens == 0) {
                            return i;
                        }
                        break;
                }
            }
            throw new RuntimeException("Did not find closing ] in " + text);
        }

        // we should only add items to lists....
        private void addValue(Representation item) {
            ((List<Representation>) value).add(item);
        }

        @Override
        public int compareTo(Object o) {
            Representation other = (Representation) o;

            if ((this.value instanceof Integer) && (other.value instanceof Integer)) {
                return ((Integer)value).compareTo(((Integer)other.value));
            }

            // convert a single value to a list for comparison
            Representation leftCompare = (this.value instanceof List) ? this : new Representation("[" + this.value + "]");
            Representation rightCompare = (other.value instanceof List) ? other : new Representation("[" + other.value + "]");;

            if ((leftCompare.value instanceof List) && (rightCompare.value instanceof List)) {
                List<Representation> left = (List<Representation>) leftCompare.value;
                List<Representation> right = (List<Representation>) rightCompare.value;
                for (int i = 0; i < left.size(); i++) {
                    if (right.size() < i + 1) {
                        return 1;
                    }
                    Representation leftElem = left.get(i);
                    Representation rightElem = right.get(i);
                    int diff = leftElem.compareTo(rightElem);
                    if (diff != 0) {
                        return diff;
                    }
                }
                if (right.size() > left.size()) {
                    return -1;
                }
            }

            return 0;
        }
    }
}
