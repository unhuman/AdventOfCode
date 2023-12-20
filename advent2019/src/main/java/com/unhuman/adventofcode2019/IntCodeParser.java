package com.unhuman.adventofcode2019;

import java.util.List;

public class IntCodeParser {
    List<Integer> memory;
    public IntCodeParser(List<Integer> code) {
        // Put his program into memory, directly.
        this.memory = code;
    }
}
