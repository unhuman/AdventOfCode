package com.unhuman.adventofcode.aoc_framework.representation;

import java.util.ArrayList;

public class ItemLine extends ArrayList<String> {
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.forEach(sb::append);
        return sb.toString();
    }

    public char getChar(int index) {
        return get(index).charAt(0);
    }

    public String getString(int index) {
        return get(index);
    }

    public int getInt(int index) {
        return Integer.parseInt(get(index));
    }

    public long getLong(int index) {
        return Long.parseLong(get(index));
    }
}
