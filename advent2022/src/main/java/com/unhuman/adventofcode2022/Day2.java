package com.unhuman.adventofcode2022;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class Day2 extends InputParser {
    private enum RPS {
        ROCK(1),
        PAPER(2),
        SCISSORS(3);

        private final int value;

        RPS(int value) {
            this.value = value;
        }

        public int getWin(RPS other) {
            int delta = Math.abs(this.value - other.value);
            switch (delta) {
                case 0, 1: return this.value - other.value;
                default:
                    return (this == ROCK) ? 1 : -1;
            }
        }

        public RPS getBeats() {
            if (value == 1) {
                return SCISSORS;
            } else {
                return values()[(this.ordinal() - 1) % values().length];
            }
        }

        public RPS getLosesTo() {
            if (value == 3) {
                return ROCK;
            } else {
                return values()[(this.ordinal() + 1) % values().length];
            }
        }
    }

    private static final String regex1 = "(\\w) (\\w)";
    private static final String regex2 = null;
    /**
     * Creates an InputParser that will process line-by-line
     */
    public Day2() {
        super(2022, 2, regex1, regex2);
    }

    public Day2(String filename) {
        super(filename, regex1, regex2);
    }

    private RPS convert(String item) {
        switch(item) {
            case "A", "X": return RPS.ROCK;
            case "B", "Y": return RPS.PAPER;
            case "C", "Z": return RPS.SCISSORS;
        }
        throw new RuntimeException("Bad item: " + item);
    }

    private int calculateScore(RPS p1, RPS p2) {
        return p2.value + (p2.getWin(p1) + 1) * 3;
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        int score = 0;
        for (GroupItem item: dataItems1) {
            for (ItemLine individual: item) {
                RPS p1 = convert(individual.get(0));
                RPS p2 = convert(individual.get(1));

                score += calculateScore(p1, p2);
            }
        }
        return score;
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        int score = 0;
        for (GroupItem item: dataItems1) {
            for (ItemLine individual: item) {
                RPS p1 = convert(individual.get(0));
                RPS p2 = p1;
                switch (individual.get(1)) {
                    case "X": // lose
                        p2 = p1.getBeats();
                        break;
                    case "Y": // tie
                        break;
                    case "Z": // win
                        p2 = p1.getLosesTo();
                        break;
                }

                score += calculateScore(p1, p2);
            }
        }
        return score;
    }
}
