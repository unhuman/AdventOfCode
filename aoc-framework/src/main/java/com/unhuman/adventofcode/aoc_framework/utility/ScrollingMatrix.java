package com.unhuman.adventofcode.aoc_framework.utility;

import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;

import java.awt.Point;

public class ScrollingMatrix extends Matrix {
    public ScrollingMatrix(ConfigGroup configGroup, DataType dataType) {
        super(configGroup, dataType);
    }

    public ScrollingMatrix(int width, int height, DataType dataType) {
        super(width, height, dataType);
    }

    @Override
    public Character getValue(Point point) {
        int x = point.x % getWidth();
        x = x >= 0 ? x : getWidth() + x;
        int y = point.y % getWidth();
        y = y >= 0 ? y : getWidth() + y;
        point = new Point(x, y);
        Character charValue = (isValid(point)) ? matrix.get(point.y).get(point.x) : null;
        return (dataType == DataType.DIGIT) ? (char) (charValue - '0') : charValue;
    }

    @Override
    public boolean isValid(Point point) {
        return true;
    }
}
