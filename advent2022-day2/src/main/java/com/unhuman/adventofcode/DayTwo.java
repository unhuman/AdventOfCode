package com.unhuman.adventofcode;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

public class DayTwo extends InputParser {
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
    /**
     * Creates an InputParser that will process line-by-line
     *
     * @param args
     */
    public DayTwo(String[] args) {
        super(args, regex1, null);
    }

    public static void main(String [] args) {
        DayTwo parser = new DayTwo(args);
        parser.process();
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
        int score = p2.value;
        int win = p2.getWin(p1);
        switch (win) {
            case 0: score += 3;
                    break;
            case 1: score += 6;
                    break;
        }
        return score;
    }

    @Override
    protected void processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        int score = 0;
        for (GroupItem item: dataItems1) {
            for (ItemLine individual: item) {
                RPS p1 = convert(individual.get(0));
                RPS p2 = convert(individual.get(1));

                score += calculateScore(p1, p2);
            }
        }
        System.out.println(score);
    }

    @Override
    protected void processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
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
        System.out.println(score);
    }
}
