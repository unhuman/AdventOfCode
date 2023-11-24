package com.unhuman.adventofcode2022;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Day23 extends InputParser {
    private static final String regex1 = "(.)";
    private static final String regex2 = null;

    enum Directions { ALL, NORTH, SOUTH, WEST, EAST }

    public Day23() {
        super(2022, 23, regex1, regex2);
    }

    public Day23(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        LinkedHashSet<Point> elfLocations = new LinkedHashSet<>();
        for (int groupItemIdx = 0; groupItemIdx < dataItems1.size(); groupItemIdx++) {
            GroupItem item = dataItems1.get(groupItemIdx);
            for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
                ItemLine line = item.get(lineIdx);
                for (int elementIdx = 0; elementIdx < line.size(); elementIdx++) {
                    String element = line.get(elementIdx);
                    if (element.equals("#")) {
                        elfLocations.add(new Point(elementIdx, lineIdx));
                    }
                }
            }
        }
        renderBoard(0, elfLocations);

        ArrayList<Directions> directionOrdering =
                new ArrayList<>(Arrays.asList(Directions.NORTH, Directions.SOUTH, Directions.WEST, Directions.EAST));

        for (int i = 0; i < 10; i++) {
            LinkedHashMap<Point, Point> proposals = getElfProposals(elfLocations, directionOrdering);

            // remove collisions from the proposals
            Set<Point> duplicatesFound = new HashSet<>();
            for (int j = 0; j < proposals.size() - 1; j++) {
                Point point1 = (Point) proposals.keySet().toArray()[j];
                Point point1Proposal = proposals.get(point1);
                for (int k = j + 1; k < proposals.size(); k++) {
                    Point point2 = (Point) proposals.keySet().toArray()[k];
                    Point point2Proposal = proposals.get(point2);
                    if (point1Proposal.equals(point2Proposal)) {
                        duplicatesFound.add(point1);
                        duplicatesFound.add(point2);
                    }
                }
            }
            for (Point duplicatePoint: duplicatesFound) {
                proposals.remove(duplicatePoint);
            }

            // move accepted proposals
            proposals.forEach((point, proposal) -> {
                elfLocations.remove(point);
                elfLocations.add(proposal);
            });

            // At the end of the round, rotate the directions
            directionOrdering.add(directionOrdering.remove(0));

            renderBoard(i + 1, elfLocations);
        }

        Point topLeft = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Point bottomRight = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);

        for (Point elf: elfLocations) {
            topLeft.x = Math.min(topLeft.x, elf.x);
            topLeft.y = Math.min(topLeft.y, elf.y);
            bottomRight.x = Math.max(bottomRight.x, elf.x);
            bottomRight.y = Math.max(bottomRight.y, elf.y);
        }

        return (bottomRight.x - topLeft.x + 1) * (bottomRight.y - topLeft.y + 1) - elfLocations.size();
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        LinkedHashSet<Point> elfLocations = new LinkedHashSet<>();
        for (int groupItemIdx = 0; groupItemIdx < dataItems1.size(); groupItemIdx++) {
            GroupItem item = dataItems1.get(groupItemIdx);
            for (int lineIdx = 0; lineIdx < item.size(); lineIdx++) {
                ItemLine line = item.get(lineIdx);
                for (int elementIdx = 0; elementIdx < line.size(); elementIdx++) {
                    String element = line.get(elementIdx);
                    if (element.equals("#")) {
                        elfLocations.add(new Point(elementIdx, lineIdx));
                    }
                }
            }
        }
        renderBoard(0, elfLocations);

        ArrayList<Directions> directionOrdering =
                new ArrayList<>(Arrays.asList(Directions.NORTH, Directions.SOUTH, Directions.WEST, Directions.EAST));

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            LinkedHashMap<Point, Point> proposals = getElfProposals(elfLocations, directionOrdering);

            // remove collisions from the proposals
            Set<Point> duplicatesFound = new HashSet<>();
            for (int j = 0; j < proposals.size() - 1; j++) {
                Point point1 = (Point) proposals.keySet().toArray()[j];
                Point point1Proposal = proposals.get(point1);
                for (int k = j + 1; k < proposals.size(); k++) {
                    Point point2 = (Point) proposals.keySet().toArray()[k];
                    Point point2Proposal = proposals.get(point2);
                    if (point1Proposal.equals(point2Proposal)) {
                        duplicatesFound.add(point1);
                        duplicatesFound.add(point2);
                    }
                }
            }
            for (Point duplicatePoint: duplicatesFound) {
                proposals.remove(duplicatePoint);
            }

            // move accepted proposals
            proposals.forEach((point, proposal) -> {
                elfLocations.remove(point);
                elfLocations.add(proposal);
            });

            // At the end of the round, rotate the directions
            directionOrdering.add(directionOrdering.remove(0));

//            renderBoard(i + 1, elfLocations);
            System.out.println("Round " + (i + 1) + " Proposals: " + proposals.size());

            if (proposals.isEmpty()) {
                return i + 1;
            }
        }
        throw new RuntimeException("Never get here");
    }

    LinkedHashMap<Point, Point> getElfProposals(LinkedHashSet<Point> elfLocations, List<Directions> directionOrdering) {
        LinkedHashMap<Point, Point> proposals = new LinkedHashMap<>();
        for (int i = 0; i < elfLocations.size(); i++) {
            Point elf = (Point) elfLocations.toArray()[i];

            // if nobody is near, don't move
            if (getElfProposal(elfLocations, elf, Directions.ALL) == null) { // null = no move
                continue;
            }

            for (Directions direction: directionOrdering) {
                Point proposal = getElfProposal(elfLocations, elf, direction);
                if (proposal != null) {
                    proposals.put(elf, proposal);
                    break;
                }
            }
        }
        return proposals;
    }

    /**
     * returns a Point if a change is recommended, else returns null to not make a proposal
     * @param elfLocations
     * @param elfCheck
     * @param direction
     * @return
     */
    Point getElfProposal(LinkedHashSet<Point> elfLocations, Point elfCheck, Directions direction) {
        switch (direction) {
            case ALL:
                for (int y = -1; y <= 1; y++) {
                    for (int x = -1; x <= 1; x++) {
                        if (x == 0 && y == 0) { // skip self
                            continue;
                        }
                        if (elfLocations.contains(new Point(elfCheck.x + x, elfCheck.y + y))) {
                            return elfCheck;
                        }
                    }
                }
                return null;
            case NORTH:
                for (int x = -1; x <= 1; x++) {
                    if (elfLocations.contains(new Point(elfCheck.x + x, elfCheck.y - 1))) {
                        return null;
                    }
                }
                return new Point(elfCheck.x, elfCheck.y - 1);
            case SOUTH:
                for (int x = -1; x <= 1; x++) {
                    if (elfLocations.contains(new Point(elfCheck.x + x, elfCheck.y + 1))) {
                        return null;
                    }
                }
                return new Point(elfCheck.x, elfCheck.y + 1);
            case EAST:
                for (int y = -1; y <= 1; y++) {
                    if (elfLocations.contains(new Point(elfCheck.x + 1, elfCheck.y + y))) {
                        return null;
                    }
                }
                return new Point(elfCheck.x + 1, elfCheck.y);
            case WEST:
                for (int y = -1; y <= 1; y++) {
                    if (elfLocations.contains(new Point(elfCheck.x - 1, elfCheck.y + y))) {
                        return null;
                    }
                }
                return new Point(elfCheck.x - 1, elfCheck.y);
        }
        return null;
    }

    void renderBoard(int round, LinkedHashSet<Point> elfLocations) {
        Point topLeft = new Point(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Point bottomRight = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
        elfLocations.forEach(location -> {
            topLeft.x = Math.min(topLeft.x, location.x);
            topLeft.y = Math.min(topLeft.y, location.y);
            bottomRight.x = Math.max(bottomRight.x, location.x);
            bottomRight.y = Math.max(bottomRight.y, location.y);
        });


        System.out.println("Round: " + round);
        for (int y = topLeft.y; y <= bottomRight.y; y++) {
            for (int x = topLeft.x; x <= bottomRight.x; x++) {
                System.out.print(elfLocations.contains(new Point(x, y)) ? '#' : '.');
            }
            System.out.println();
        }
        System.out.println();
    }
}
