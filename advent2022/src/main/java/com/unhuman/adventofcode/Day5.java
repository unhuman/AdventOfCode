package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class Day5 extends InputParser {
    private static final String regex1 = "^(\\[[\\w]\\]|\\s\\s\\s)\\s?";
    private static final String regex2 = "move (\\d+) from (\\d+) to (\\d+)";

    public Day5() {
        super(2022, 5, regex1, regex2);
    }

    public Day5(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        List<Stack<String>> stacks = new ArrayList<>();
        for (GroupItem item : dataItems1) {
            for (int i = item.size() - 1; i >= 0; --i) {
                ItemLine line = item.get(i);
                for (int j = 0; j < line.size(); j++) {
                    Stack<String> column;
                    // Create a new or add to existing stack
                    if (i == item.size() - 1) {
                        column = new Stack<>();
                        stacks.add(column);
                    } else {
                        column = stacks.get(j);
                    }

                    if (!line.get(j).isBlank()) {
                        column.add(line.get(j));
                    }
                }
            }
        }

        for (GroupItem item : dataItems2) {
            for (ItemLine line: item) {
                int count = Integer.parseInt(line.get(0));
                int from = Integer.parseInt(line.get(1)) - 1;
                int to = Integer.parseInt(line.get(2)) - 1;
                for (int i = 0; i < count; i++) {
                    String crate = stacks.get(from).pop();
                    stacks.get(to).push(crate);
                }
            }
        }

        String answer = "";
        for (int i = 0; i < stacks.size(); i++) {
            String crate = stacks.get(i).peek().substring(1, 2);
            answer += crate;
        }

        return answer;
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        List<Stack<String>> stacks = new ArrayList<>();
        for (GroupItem item : dataItems1) {
            for (int i = item.size() - 1; i >= 0; --i) {
                ItemLine line = item.get(i);
                for (int j = 0; j < line.size(); j++) {
                    Stack<String> column;
                    // Create a new or add to existing stack
                    if (i == item.size() - 1) {
                        column = new Stack<>();
                        stacks.add(column);
                    } else {
                        column = stacks.get(j);
                    }

                    if (!line.get(j).isBlank()) {
                        column.add(line.get(j));
                    }
                }
            }
        }

        for (GroupItem item : dataItems2) {
            for (ItemLine line: item) {
                int count = Integer.parseInt(line.get(0));
                int from = Integer.parseInt(line.get(1)) - 1;
                int to = Integer.parseInt(line.get(2)) - 1;

                Stack<String> tempStack = new Stack<>();
                for (int i = 0; i < count; i++) {
                    try {
                        String crate = stacks.get(from).pop();
                        tempStack.push(crate);
                    } catch(EmptyStackException ese) {
                        break;
                    }
                }

                while (tempStack.size() > 0) {
                    String crate = tempStack.pop();
                    stacks.get(to).push(crate);
                }
            }
        }

        String answer = "";
        for (int i = 0; i < stacks.size(); i++) {
            try {
                String crate = stacks.get(i).peek().substring(1, 2);
                answer += crate;
            } catch (EmptyStackException ese) {
                answer += " ";
            }
        }

        return answer;
    }
}
