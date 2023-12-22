package com.unhuman.adventofcode2023;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;
import com.unhuman.adventofcode.aoc_framework.utility.Sparse3DMatrix;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day22 extends InputParser {
    private static final String regex1 = "([\\d]+),([\\d]+),([\\d]+)";
    private static final String regex2 = null;

    public Day22() {
        super(2023, 22, regex1, regex2);
    }

    public Day22(String filename) {
        super(filename, regex1, regex2);
    }

    static class Block {
        int num;
        List<Sparse3DMatrix.Point3D> points = new ArrayList<>();
        List<Block> supports = new ArrayList<>();
        List<Block> supporters = new ArrayList<>();

        Block(int num, Sparse3DMatrix.Point3D start, Sparse3DMatrix.Point3D end) {
            this.num = num;
            int x1 = start.x();
            int y1 = start.y();
            int z1 = start.z();
            int x2 = end.x();
            int y2 = end.y();
            int z2 = end.z();
            for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                    for (int z = Math.min(z1, z2); z <= Math.max(z1, z2); z++) {
                        points.add(new Sparse3DMatrix.Point3D(x, y, z));
                    }
                }
            }
        }

        void clearSupportInfrastructure() {
            this.supports = new ArrayList<>();
            this.supporters = new ArrayList<>();
        }

        void addSupports(Block other) {
            this.supports.add(other);
        }

        void addSupporter(Block other) {
            this.supporters.add(other);
        }

        boolean supports(Block other) {
            for (Sparse3DMatrix.Point3D myPoint: points) {
                for (Sparse3DMatrix.Point3D otherPoint: other.points) {
                    if (myPoint.x() == otherPoint.x()
                            && myPoint.y() == otherPoint.y()
                            && myPoint.z() - otherPoint.z() == -1) {
                        return true;
                    }
                }
            }
            return false;
        }

        boolean gravitate() {
            int minZ = points.stream().map(x -> x.z()).min((a, b) -> (a - b)).get();
            if (supporters.size() == 0 && minZ > 1) {
                for (int i = 0; i < points.size(); i++) {
                    Sparse3DMatrix.Point3D point = points.get(i);
                    points.set(i, new Sparse3DMatrix.Point3D(point.x(), point.y(), point.z() - 1));
                }
                return true;
            }
            return false;
        }
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Block> blocks = setupFindRemovalCandidates(configGroup);
        Set<Block> removalCandidates = findRemovalCandidates(blocks);
        return removalCandidates.size();
    }

    static List<Block> setupFindRemovalCandidates(ConfigGroup configGroup) {
        // easier to assume there's only one group
        GroupItem item = configGroup.get(0);
        List<Block> blocks = new ArrayList<>();
        for (int i = 0; i < item.size(); i++) {
            ItemLine line = item.get(i);
            Sparse3DMatrix.Point3D start = new Sparse3DMatrix.Point3D(
                    Integer.parseInt(line.get(0)),
                    Integer.parseInt(line.get(1)),
                    Integer.parseInt(line.get(2)));
            Sparse3DMatrix.Point3D end = new Sparse3DMatrix.Point3D(
                    Integer.parseInt(line.get(3)),
                    Integer.parseInt(line.get(4)),
                    Integer.parseInt(line.get(5)));
            Block block = new Block(i + 1, start, end);
            blocks.add(block);
        }

        boolean gravityAffected = true;
        // arrange supports
        generateSupportInfrastructure(blocks);
        while (gravityAffected) {
            gravityAffected = false;

            for (Block dropCandidate: blocks) {
                if (dropCandidate.gravitate()) {
                    gravityAffected = true;
                }
            }

            // Rearrange supports
            generateSupportInfrastructure(blocks);
        }

        return blocks;
    }

    private static Set<Block> findRemovalCandidates(List<Block> blocks) {
        Set<Block> removalCandidates = new HashSet<>();
        for (Block block: blocks) {
            boolean canRemove = true;

            List<Block> supportCandidates = block.supports;
            for (Block supported: supportCandidates) {
                if (supported.supporters.size() == 1) {
                    canRemove = false;
                    break;
                }
            }
            if (canRemove) {
                removalCandidates.add(block);
            }
        }
        return removalCandidates;
    }

    private static void generateSupportInfrastructure(List<Block> blocks) {
        // clear any existing support structure
        for (Block block : blocks) {
            block.clearSupportInfrastructure();
        }

        // (re)establish support basis
        for (Block block : blocks) {
            for (Block block2 : blocks) {
                if (block == block2) {
                    continue;
                }
                if (block.supports(block2)) {
                    block.addSupports(block2);
                    block2.addSupporter(block);
                }
            }
        }
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Block> blocks = setupFindRemovalCandidates(configGroup);
        int totalRemovalCount = 0;
        for (Block block: blocks) {
            List<Block> checkRowRemoves = new ArrayList<>(block.supports);

            while (checkRowRemoves.size() > 0) {
                List<Block> nextCheckRowRemoves = new ArrayList<>();
                for (Block rowItem: checkRowRemoves) {
                    if (rowItem.supporters.size() == 1) {
                        totalRemovalCount++;
                        nextCheckRowRemoves.addAll(rowItem.supports);
                    }
                }
                checkRowRemoves = nextCheckRowRemoves;
            }
        }


        return totalRemovalCount;
    }
}
