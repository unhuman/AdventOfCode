package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Pair;

import java.awt.Button;

public class Day13 extends InputParser {
    private static final String regex1 = "(Button|Prize)\\s*([AB]?): X[+=](\\d+), Y[+=](\\d+)";
    private static final String regex2 = null;

    private static final long BUTTON_A_COST = 3;
    private static final long BUTTON_B_COST = 1;

    public Day13() {
        super(2024, 13, regex1, regex2);
    }

    public Day13(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Long totalMinimumCost = 0L;
        for (GroupItem group : configGroup) {
            ItemLine line = group.get(0);
            assert ("Button".equals(line.getString(0)));
            Button button1 = new Button(line.getChar(1), line.getLong(2), line.getLong(3));

            line = group.get(1);
            assert ("Button".equals(line.getString(0)));
            Button button2 = new Button(line.getChar(1), line.getLong(2), line.getLong(3));

            line = group.get(2);
            assert ("Prize".equals(line.getString(0)));
            Prize prize = new Prize(line.getLong(2), line.getLong(3));

            try {
                totalMinimumCost += calculateMinimumCost(button1, button2, prize);
            } catch (Exception e) {
                // ignore
            }
        }
        return totalMinimumCost;
    }

    long calculateMinimumCost(Button buttonA, Button buttonB, Prize prize) {
        Long minValue = null;
        long buttonAMax1 = prize.xLocation / buttonA.xDelta;
        long buttonAMax2 = prize.yLocation / buttonA.yDelta;
        long buttonAMax = Math.min(buttonAMax1, buttonAMax2);

        for (long i = 0; i <= buttonAMax; i++) {
            long xLocation = prize.xLocation - i * buttonA.xDelta;
            long yLocation = prize.yLocation - i * buttonA.yDelta;
            if (xLocation >= 0 && yLocation >= 0) {
                long buttonBMax1 = xLocation / buttonB.xDelta;
                long buttonBMax2 = yLocation / buttonB.yDelta;
                long buttonBMax = Math.min(buttonBMax1, buttonBMax2);

                if (i * buttonA.xDelta + buttonBMax * buttonB.xDelta == prize.xLocation &&
                    i * buttonA.yDelta + buttonBMax * buttonB.yDelta == prize.yLocation) {
                    long cost = i * BUTTON_A_COST + buttonBMax * BUTTON_B_COST;
                    if (minValue == null || cost < minValue) {
//                        System.out.println("a = " + i + " b = " + buttonBMax);
                        minValue = cost;
                    }
                }
            }
        }
        if (minValue != null) {
            return minValue;
        } else {
            throw new RuntimeException("No solution found");
        }
    }

    Long calculateMinimumCost2(Button buttonA, Button buttonB, Prize prize) {
        // reduce to solve for x

//        Button A: X+94, Y+34
//        Button B: X+22, Y+67
//        Prize: X=8400, Y=5400

        // Equations
        // A * buttonA.xDelta + B * buttonB.xDelta = Prize.X
        // A * buttonA.yDelta + B * buttonB.yDelta = Prize.Y

        // inverse co-efficients to eliminate first variable
        // A * buttonA.xDelta * buttonA.yDelta + B * buttonB.xDelta * ButtonA.yDelta = Prize.X * buttonA.yDelta
        // A * buttonA.yDelta * buttonA.xDelta + B * buttonB.yDelta * buttonA.xDelta = Prize.Y * buttonA.xDelta
        // subtract equations
        // B * (buttonB.xDelta * ButtonA.yDelta) - B * (buttonB.yDelta * buttonA.xDelta)
        //                = Prize.X * buttonA.yDelta - Prize.Y * buttonA.xDelta


        long diffEquations = buttonB.xDelta * buttonA.yDelta - buttonB.yDelta * buttonA.xDelta;
        long diffTotals = prize.xLocation * buttonA.yDelta - prize.yLocation * buttonA.xDelta;

        // calculate b button hits
        if (diffTotals % diffEquations != 0) {
            throw new RuntimeException("Decimal");
        }
        long b = diffTotals / diffEquations;

        // then solve for a button hits
        // A * buttonA.xDelta + B * buttonB.xDelta = Prize.X
        if (((prize.xLocation - b * buttonB.xDelta) % buttonA.xDelta) != 0) {
            throw new RuntimeException("Decimal");
        }
        long a = (prize.xLocation - b * buttonB.xDelta) / buttonA.xDelta;

        return a * BUTTON_A_COST + b * BUTTON_B_COST;
    }

    // This is expensive.  I'm sure there's a better way to do this.
//    long calculateMinimumCost(Button buttonA, Button buttonB, Prize prize) {
//        if (prize.xLocation < 0 || prize.yLocation < 0) {
//            throw new RuntimeException("No solution found");
//        }
//
//        if (buttonA.xDelta == prize.xLocation && buttonA.yDelta == prize.yLocation) {
//            return BUTTON_A_COST;
//        }
//        if (buttonB.xDelta == prize.xLocation && buttonB.yDelta == prize.yLocation) {
//            return BUTTON_B_COST;
//        }
//
//        int caught = 0;
//        Long costA = Long.MAX_VALUE;
//        Long costB = Long.MAX_VALUE;
//        try {
//            costA = BUTTON_A_COST + calculateMinimumCost(buttonA, buttonB,
//                    new Prize(prize.xLocation - buttonA.xDelta, prize.yLocation - buttonA.yDelta));
//        } catch (Exception e) {
//            caught++;
//        }
//        try {
//            costB = BUTTON_B_COST + calculateMinimumCost(buttonA, buttonB,
//                    new Prize(prize.xLocation - buttonB.xDelta, prize.yLocation - buttonB.yDelta));
//        } catch (Exception e) {
//            caught++;
//        }
//
//        if (caught == 2) {
//            throw new RuntimeException("No solution found");
//        }
//
//        return Math.min(costA, costB);
//    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        Long totalMinimumCost = 0L;
        final long ENGLARGER = 10000000000000L;

        for (GroupItem group : configGroup) {
            ItemLine line = group.get(0);
            assert ("Button".equals(line.getString(0)));
            Button button1 = new Button(line.getChar(1), line.getLong(2), line.getLong(3));

            line = group.get(1);
            assert ("Button".equals(line.getString(0)));
            Button button2 = new Button(line.getChar(1), line.getLong(2), line.getLong(3));

            line = group.get(2);
            assert ("Prize".equals(line.getString(0)));
            Prize prize = new Prize(line.getLong(2) + ENGLARGER, line.getLong(3) + ENGLARGER);

            try {
                totalMinimumCost += calculateMinimumCost2(button1, button2, prize);
            } catch (Exception e) {
                // ignore
            }
        }
        return totalMinimumCost;
    }

    record Prize(long xLocation, long yLocation) {
    }
    record Button(char name, long xDelta, long yDelta) {
    }
}
