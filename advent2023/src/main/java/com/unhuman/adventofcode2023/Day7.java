package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Day7 extends InputParser {
    private static final String regex1 = "(\\w+)\\s+(\\d+)";
    private static final String regex2 = null;

    public Day7() {
        super(2023, 7, regex1, regex2);
    }

    public Day7(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        SortedSet<Hand> table = new TreeSet<>();
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                String handText = line.get(0);
                Long bid = Long.parseLong(line.get(1));
                Hand hand = new Hand(handText, bid, false);
                table.add(hand);
            }
        }

        long total = 0L;
        int rank = 0;
        for (Hand hand: table) {
            ++rank;
            total += hand.bid * rank;
        }

        return total;
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        SortedSet<Hand> table = new TreeSet<>();
        for (GroupItem item : configGroup) {
            for (ItemLine line : item) {
                String handText = line.get(0);
                Long bid = Long.parseLong(line.get(1));
                Hand hand = new Hand(handText, bid, true);
                table.add(hand);
            }
        }

        long total = 0L;
        int rank = 0;
        for (Hand hand: table) {
            ++rank;
            total += hand.bid * rank;
        }

        return total;
    }

    class Hand implements Comparable {
        enum Kind { HIGH_CARD, ONE_PAIR, TWO_PAIR, THREE_OF_A_KIND, FULL_HOUSE, FOUR_OF_A_KIND, FIVE_OF_A_KIND }
        boolean jokersActive;
        char[] hand;
        Long bid;
        Kind kind;

        public Hand(String hand, Long bid, boolean jokersActive) {
            this.hand = hand.toCharArray();
            this.bid = bid;
            Map<Character, Integer> cardCounts = new HashMap<>();

            for (int i = 0; i < this.hand.length; i++) {
                switch (this.hand[i]) {
                    // Convert T to lower value and K and Ace to higher value chars so that sorting works easily
                    // TJQKA -> :JRZA or - jokers active TJQKA -> T1RZA
                    case 'T':
                        this.hand[i] = ':';
                        break;
                    case 'J':
                        if (jokersActive) {
                            this.hand[i] = '1';
                        }
                        break;
                    case 'K':
                        this.hand[i] = 'R';
                        break;
                    case 'A':
                        this.hand[i] = 'Z';
                        break;
                }
                Integer cardCount = cardCounts.getOrDefault(this.hand[i], 0);
                cardCounts.put(this.hand[i], cardCount + 1);
            }

            if (jokersActive) {
                // determine if we have jokers and find the best group
                Integer jokerCount = cardCounts.remove('1');
                if (jokerCount != null) {
                    // remove the jokers, since we're going to move them
                    Character bestJokerReplacement = null;
                    int bestJokerCount = 0;
                    List<Character> cardsInHand = new ArrayList<>(cardCounts.keySet().stream().toList());
                    cardsInHand.sort(Comparator.reverseOrder());
                    for (int i = 0; i < cardsInHand.size(); i++) {
                        Character checkChar = cardsInHand.get(i);
                        if (cardCounts.get(checkChar) > bestJokerCount) {
                            bestJokerCount = cardCounts.get(checkChar);
                            bestJokerReplacement = checkChar;
                        }
                    }
                    cardCounts.put(bestJokerReplacement, bestJokerCount + jokerCount);
                }
            }

            switch (cardCounts.size()) {
                case 1:
                    this.kind = Kind.FIVE_OF_A_KIND;
                    break;
                case 2:
                    if (cardCounts.values().contains(4)) {
                        this.kind = Kind.FOUR_OF_A_KIND;
                    } else {
                        this.kind = Kind.FULL_HOUSE;
                    }
                    break;
                case 3:
                    if (cardCounts.values().contains(3)) {
                        this.kind = Kind.THREE_OF_A_KIND;
                    } else if (cardCounts.values().contains(2)) {
                        this.kind = Kind.TWO_PAIR;
                    }
                    break;
                case 4:
                    this.kind = Kind.ONE_PAIR;
                    break;
                case 5:
                    this.kind = Kind.HIGH_CARD;
            }
        }

        @Override
        public int compareTo(Object o) {
            Hand other = (Hand) o;

            if (this.kind != other.kind) {
                return this.kind.compareTo(other.kind);
            }

            for (int i = 0; i < hand.length; i++) {
                if (this.hand[i] != other.hand[i]) {
                    return (this.hand[i] < other.hand[i]) ? -1 : 1;
                }
            }
            return 0;
        }

        @Override
        public String toString() {
            char[] handCopy = hand.clone();
            for (int i = 0; i < handCopy.length; i++) {
                switch (handCopy[i]) {
                    // Convert higher value chars back to K and Ace for display
                    // JQRZ: -> JQKAT
                    case ':':
                        this.hand[i] = 'T';
                        break;
                    case '1':
                        // we know jokers are active
                        handCopy[i] = 'J';
                        break;
                    case 'R':
                        handCopy[i] = 'K';
                        break;
                    case 'Z':
                        handCopy[i] = 'A';
                        break;
                }
            }
            return "Hand{" +
                    "kind=" + kind +
                    ", hand=" + Arrays.toString(handCopy) +
                    ", bid=" + bid +
                    '}';
        }
    }
}
