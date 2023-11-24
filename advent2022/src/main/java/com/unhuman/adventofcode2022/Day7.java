package com.unhuman.adventofcode2022;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.HashMap;

public class Day7 extends InputParser {
    private static final String regex1 = "^(\\$\\s+)?(\\w+)(\\s+.+)?";
    private static final String regex2 = null;

    private static final int FILE_SYSTEM_SIZE = 70000000;
    private static final int UPDATE_NEED_FREE_SIZE = 30000000;

    public Day7() {
        super(2022, 7, regex1, regex2);
    }

    public Day7(String filename) {
        super(filename, regex1, regex2);
    }

    @Override
    public Object processInput1(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        DirectoryElement root = parseDirectoryData(dataItems1);
        return getTotalSmallDirectorySize(root);
    }

    @Override
    public Object processInput2(ConfigGroup dataItems1, ConfigGroup dataItems2) {
        DirectoryElement root = parseDirectoryData(dataItems1);

        Integer usedSpace = root.getMyDirSize();
        Integer freeSpace = FILE_SYSTEM_SIZE - usedSpace;
        Integer neededSpace = UPDATE_NEED_FREE_SIZE - freeSpace;

        return findSmallestDeletionForCleanup(root, neededSpace, Integer.MAX_VALUE);
    }

    public DirectoryElement parseDirectoryData(ConfigGroup dataItems) {
        DirectoryElement root = new DirectoryElement(null);
        DirectoryElement cwd = root;
        for (GroupItem item : dataItems) {
            for (ItemLine line : item) {
                boolean isCommand = line.get(0) != null;
                if (isCommand) {
                    String command = line.get(1);
                    switch (command) {
                        case "cd":
                            String directory = line.get(2).trim();
                            if (directory.equals("..")) {
                                cwd = cwd.getParent();
                            } else {
                                cwd = cwd.getSubdir(directory);
                            }
                            break;
                        case "ls":
                            // do nothing b/c the other lines we parse are file data
                            break;

                    }
                } else {
                    // we are in ls - so process this data
                    switch (line.get(1)) {
                        case "dir":
                            cwd.addSubdir(line.get(2).trim());
                            break;
                        default: // file size
                            Integer size = Integer.parseInt(line.get(1));
                            cwd.addFile(line.get(2).trim(), size);
                    }
                }
            }
        }
        return root;
    }

    public int getTotalSmallDirectorySize(DirectoryElement directory) {
        int returnValue = 0;

        if (directory.getMyDirSize() <= 100000) {
            returnValue += directory.getMyDirSize();
        }

        for (Object check: directory.values()) {
            if (check instanceof DirectoryElement) {
                returnValue += getTotalSmallDirectorySize((DirectoryElement) check);
            }
        }

        return returnValue;
    }

    public int findSmallestDeletionForCleanup(DirectoryElement directory, int neededSpace, int currentMinFound) {
        int currentDirSize = directory.getMyDirSize();
        if ((currentDirSize > neededSpace) && (currentDirSize < currentMinFound)) {
            currentMinFound = currentDirSize;

            for (Object check : directory.values()) {
                if (check instanceof DirectoryElement) {
                    currentMinFound = findSmallestDeletionForCleanup(
                            (DirectoryElement) check, neededSpace, currentMinFound);
                }
            }
        }

        return currentMinFound;
    }


    public static class DirectoryElement extends HashMap<String, Object> {
        private DirectoryElement parent;
        public DirectoryElement(DirectoryElement parent) {
            if (parent == null) {
                this.parent = this;
            } else {
                this.parent = parent;
            }
        }
        public DirectoryElement getParent() {
            return parent;
        }
        public DirectoryElement getSubdir(String subdir) {
            // we only get the root when we're the root, otherwise, we'd need to navigate back
            if (subdir.equals("/") && this == parent) {
                return this;
            }

            // makes assumption we will pass a directory, only
            return (DirectoryElement) get(subdir);
        }
        public void addFile(String name, Integer size) {
            put(name, size);
        }
        public void addSubdir(String name) {
            put(name, new DirectoryElement(this));
        }
        public int getMyDirSize() {
            return values().stream().mapToInt(object -> {
                if (object instanceof Integer) {
                    return (Integer) object;
                } else if (object instanceof DirectoryElement) {
                    return ((DirectoryElement) object).getMyDirSize();
                }
                return 0;
            }).sum();
        }
    }
}
