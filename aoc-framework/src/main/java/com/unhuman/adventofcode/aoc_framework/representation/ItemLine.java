package com.unhuman.adventofcode.aoc_framework.representation;

import java.util.ArrayList;

public class ItemLine extends ArrayList<String> {
    public ItemLine(String originalLine) {
        super();
        this.originalLine = originalLine;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.forEach(sb::append);
        return sb.toString();
    }

    private final String originalLine;

    public String getOriginalLine() {
        return originalLine;
    }

    public char getChar(int index) {
        return get(index).charAt(0);
    }

    public String getString(int index) {
        return get(index);
    }

    public Integer getInt(int index) {
        return get(index) != null ? Integer.parseInt(get(index).trim()) : null;
    }

    public Long getLong(int index) {
        return get(index) != null ? Long.parseLong(get(index).trim()) : null;
    }
}
