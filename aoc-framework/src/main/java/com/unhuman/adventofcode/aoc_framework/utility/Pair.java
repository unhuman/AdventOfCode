package com.unhuman.adventofcode.aoc_framework.utility;

import java.util.Objects;

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

    public String toString() {
        return "[" + this.left.toString() + ":" + this.right.toString() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(left, pair.left) && Objects.equals(right, pair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
