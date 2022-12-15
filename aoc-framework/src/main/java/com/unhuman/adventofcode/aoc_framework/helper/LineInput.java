package com.unhuman.adventofcode.aoc_framework.helper;

import java.util.List;

public class LineInput {
    private final List<String> lines;
    private int currentLine;

    public LineInput(List<String> lines) {
        this.lines = lines;
        this.currentLine = 0;
    }

    public String peekLine() {
        if (currentLine >= lines.size()) {
            return null;
        }
        return lines.get(currentLine);
    }

    public String readLine() {
        String line = peekLine();
        if (peekLine() != null) {
            currentLine++;
        }
        return line;
    }
}
