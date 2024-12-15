package com.unhuman.adventofcode.aoc_framework.utility;

import java.awt.Point;

public class PointHelper {
    public static Point addPoints(Point p1, Point p2) {
        return (new Point(p1.x + p2.x, p1.y + p2.y));
    }

    public static Point subtract(Point p1, Point p2) {
        return (new Point(p1.x - p2.x, p1.y - p2.y));
    }

    public static Point getSlope(Point p1, Point p2) {
        return (new Point(p2.x - p1.x, p2.y - p1.y));
    }

    public static Point addSlope(Point p1, Point slope) {
        return new Point(p1.x + slope.x, p1.y + slope.y);
    }

    public static Point subtrackSlope(Point p1, Point slope) {
        return new Point(p1.x - slope.x, p1.y - slope.y);
    }
}
