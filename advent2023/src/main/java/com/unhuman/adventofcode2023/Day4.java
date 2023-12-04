package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.unhuman.adventofcode.aoc_framework.utility.NumberHelper.parseNumbers;

public class Day4 extends InputParser {
    private static final String regex1 = "Card\\s+(\\d+):([^\\|]*)\\s\\|\\s(.*)";
    private static final String regex2 = null;

    public Day4() {
        super(2023, 4, regex1, regex2);
    }

    public Day4(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int total = 0;
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                Integer card = Integer.parseInt(line.get(0));
                List<Integer> winningList = parseNumbers(line.get(1));
                List<Integer> cardList = parseNumbers(line.get(2));

                Set winning = new HashSet();
                winning.addAll(winningList);

                int cardScore = 0;
                for (int i = 0; i < cardList.size(); i++) {
                    if (winning.contains(cardList.get(i))) {
                        cardScore = (cardScore == 0) ? 1 : cardScore * 2;
                    }
                }
                total += cardScore;
            }
        }

        return total;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        int total = 0;
        Map<Integer, Integer> cardCounts = new HashMap<>();
        for (GroupItem item : configGroup) {
            for (int c = 0; c < item.size(); c++) {
                cardCounts.put(c, 1);
            }
            for (int c = 0; c < item.size(); c++) {
                ItemLine line = item.get(c);
                Integer card = Integer.parseInt(line.get(0));
                List<Integer> winningList = parseNumbers(line.get(1));
                List<Integer> cardList = parseNumbers(line.get(2));

                Set winning = new HashSet();
                winning.addAll(winningList);

                int wonCount = 0;
                for (int i = 0; i < cardList.size(); i++) {
                    if (winning.contains(cardList.get(i))) {
                        ++wonCount;
                    }
                }

                for (int j = c + 1; j <= c + wonCount; j++) {
                    cardCounts.put(j, cardCounts.get(j) + cardCounts.get(c));
                }
            }
        }

        return cardCounts.values().stream().reduce(0, Integer::sum);
    }
}
