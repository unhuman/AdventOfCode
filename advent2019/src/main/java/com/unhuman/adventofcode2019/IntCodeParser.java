package com.unhuman.adventofcode2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class IntCodeParser {
    private List<Integer> memory;

    public IntCodeParser(List<String> code) {
        this.memory = new ArrayList<>(code.stream().map(Integer::parseInt).toList());
    }

    public IntCodeParser(String code) {
        this(Arrays.stream(code.split(",")).collect(Collectors.toList()));
    }

    public List<Integer> getReadOnlyMemory() {
        return Collections.unmodifiableList(memory);
    }

    public Integer peek(int location) {
        return memory.get(location);
    }

    public Integer poke(int location, Integer value) {
        Integer priorValue = peek(location);
        memory.set(location, value);
        return priorValue;
    }

    public void process() {
        int instructionPointer = 0;

        while (true) {
            switch (memory.get(instructionPointer++)) {
                case 1 -> instructionPointer = processAddition(instructionPointer);
                case 2 -> instructionPointer = processMultiplication(instructionPointer);
                case 99 -> { return; }
            }
        }
    }

    public int processAddition(int instructionPointer) {
        int leftValue = memory.get(memory.get(instructionPointer++));
        int rightValue = memory.get(memory.get(instructionPointer++));
        int sum = leftValue + rightValue;
        int storageLocation = memory.get(instructionPointer++);
        memory.set(storageLocation, sum);
        return instructionPointer;
    }

    public int processMultiplication(int instructionPointer) {
        int leftValue = memory.get(memory.get(instructionPointer++));
        int rightValue = memory.get(memory.get(instructionPointer++));
        int product = leftValue * rightValue;
        int storageLocation = memory.get(instructionPointer++);
        memory.set(storageLocation, product);
        return instructionPointer;
    }

}
