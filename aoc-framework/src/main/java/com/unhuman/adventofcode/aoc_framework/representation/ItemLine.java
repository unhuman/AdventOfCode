package com.unhuman.adventofcode.aoc_framework.representation;

import java.util.ArrayList;

public class ItemLine extends ArrayList<String> {
    public String toString() {
        StringBuilder sb = new StringBuilder();
        this.forEach(sb::append);
        return sb.toString();
    }
}
