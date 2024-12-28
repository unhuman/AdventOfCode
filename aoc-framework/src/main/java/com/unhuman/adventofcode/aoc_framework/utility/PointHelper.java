package com.unhuman.adventofcode.aoc_framework.utility;

import java.awt.Point;

public class PointHelper {
    public static Point addPoints(Point p1, Point p2) {
        return (new Point(p1.x + p2.x, p1.y + p2.y));
    }

    public static Point subtract(Point p1, Point p2) {
        return (new Point(p1.x - p2.x, p1.y - p2.y));
    }

    public static Slope getSlope(Point p1, Point p2) {
        return (new Slope(p2.x - p1.x, p2.y - p1.y));
    }

    public static Point addSlope(Point p1, Point slope) {
        return new Point(p1.x + slope.x, p1.y + slope.y);
    }

    public static Point subtrackSlope(Point p1, Point slope) {
        return new Point(p1.x - slope.x, p1.y - slope.y);
    }

    public static double getDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
    }

    public static class Slope extends Point {
        enum SlopeType { NONE, DEFINED, ABOVE, BELOW }

        private final SlopeType slopeType;

        public Slope(Point point) {
            this(point.x, point.y);
        }

        public Slope(int x, int y) {
            this.x = x;
            this.y = y;

            if (x != 0) {
                slopeType = SlopeType.DEFINED;
            } else {
                if (y == 0) {
                    slopeType = SlopeType.NONE;
                } else if (y < 0) {
                    slopeType = SlopeType.ABOVE;
                } else {
                    slopeType = SlopeType.BELOW;
                }
            }
        }

        public java.lang.Double getSlope() {
            return (slopeType == SlopeType.DEFINED) ? (double) y / (double) x : null;
        }

        public java.lang.Double getDegrees() {
//            System.out.println("Slope " + this);
            // We need to invert Y to convert from rows to cartesian coordinate behavior
            double useSlope = (double) -y / (double) x;
//            System.out.println("Slope: " + useSlope);
            double radians = Math.atan(useSlope) + ((x < 0) ? Math.PI : 0.0);
//            System.out.println("Radians: " + radians);
            double degrees = Math.toDegrees(radians) % 360.0;
//            System.out.println("Degrees: " + degrees);
            if (degrees < 0) {
                degrees = 360 + degrees;
            }
            return degrees;
        }

        @Override
        public void setLocation(Point point) {
            throw new RuntimeException("Cannot update location on Slope");
        }

        @Override
        public void setLocation(int x, int y) {
            throw new RuntimeException("Cannot update location on Slope");
        }

        @Override
        public void setLocation(double x, double y) {
            throw new RuntimeException("Cannot update location on Slope");
        }
    }
}
