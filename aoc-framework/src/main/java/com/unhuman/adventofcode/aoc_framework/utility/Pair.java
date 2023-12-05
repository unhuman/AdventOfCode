package com.unhuman.adventofcode.aoc_framework.utility;

public class Pair <T, T2> {
    T left;
    T2 right;

    public Pair(T left, T2 right) {
        this.left = left;
        this.right = right;
    }

    public T getLeft() {
        return left;
    }

    public T2 getRight() {
        return right;
    }
}
