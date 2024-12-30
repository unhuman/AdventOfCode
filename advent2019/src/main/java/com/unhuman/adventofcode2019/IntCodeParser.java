package com.unhuman.adventofcode2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IntCodeParser {
    private Memory memory;
    private List<String> input;
    private String output;
    private boolean hasHalted;
    int instructionPointer;
    int relativeBase;
    boolean returnOnOutput = false;

    public enum ParameterMode { POSITION, IMMEDIATE, RELATIVE }

    public IntCodeParser(List<String> code) {
        this.memory = new Memory(code.stream().map(String::trim).map(Long::parseLong).toList());
        this.input = new ArrayList<>();
        this.output = "";
        this.hasHalted = false;
        this.instructionPointer = 0;
        this.relativeBase = 0;
    }

    public IntCodeParser(String code) {
        this(Arrays.stream(code.split(",")).collect(Collectors.toList()));
    }

    /**
     * when opcode 4 occurs to log output, pause the VM and return the output
     * @param returnOnOutput
     */
    public void setReturnOnOutput(boolean returnOnOutput) {
        this.returnOnOutput = returnOnOutput;
    }

    public Memory getReadOnlyMemory() {
        return memory.getReadOnlyMemory();
    }

    public Long peek(int location) {
        return memory.get(location);
    }

    public Long poke(int location, Long value) {
        Long priorValue = peek(location);
        memory.set(location, value);
        return priorValue;
    }

    public boolean hasHalted() {
        return hasHalted;
    }

    public void process() {
        if (hasHalted) {
            throw new RuntimeException("This process has halted - cannot process");
        }

        while (!hasHalted) {
            step();
        }
    }

    public void step() {
        String operationInfo = memory.get(instructionPointer++).toString();
        int commandLocation = (operationInfo.length() > 2) ? operationInfo.length() - 2 : 0;
        int command = Integer.parseInt(operationInfo.substring(commandLocation));
        Map<Integer, ParameterMode> parameterModes = new HashMap<>();
        for (int i = 1; i <= 3 && commandLocation > 0; i++) {
            char value = (operationInfo.length() - 2 - i >= 0)
                    ? operationInfo.charAt(operationInfo.length() - 2 - i) : 0;
            switch (value) {
                case '0' -> parameterModes.put(i, ParameterMode.POSITION);
                case '1' -> parameterModes.put(i, ParameterMode.IMMEDIATE);
                case '2' -> parameterModes.put(i, ParameterMode.RELATIVE);
            }
        }

        try {
            switch (command) {
                case 1 -> instructionPointer = processOpcode1(instructionPointer, parameterModes);
                case 2 -> instructionPointer = processOpcode2(instructionPointer, parameterModes);
                case 3 -> instructionPointer = processOpcode3(instructionPointer, parameterModes);
                case 4 -> {
                    instructionPointer = processOpcode4(instructionPointer, parameterModes);
                    if (returnOnOutput) {
                        return;
                    }
                }
                case 5 -> instructionPointer = processOpcode5(instructionPointer, parameterModes);
                case 6 -> instructionPointer = processOpcode6(instructionPointer, parameterModes);
                case 7 -> instructionPointer = processOpcode7(instructionPointer, parameterModes);
                case 8 -> instructionPointer = processOpcode8(instructionPointer, parameterModes);
                case 9 -> instructionPointer = processOpcode9(instructionPointer, parameterModes);
                case 99 -> {
                    hasHalted = true;
                }
                default -> {
                    throw new RuntimeException("Invalid Opcode: " + command);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(String.format("Problem performing operation %s at instruction: %d",
                    operationInfo, instructionPointer), e);
        }
    }

    ParameterMode getParameterMode(Map<Integer, ParameterMode> parameterModes, int item) {
        return (parameterModes.getOrDefault(item, ParameterMode.POSITION));
    }

    protected int getMemoryLocation(Map<Integer, ParameterMode> parameterModes, int item, int instructionPointer) {
        ParameterMode parameterMode = getParameterMode(parameterModes, item);
        return switch (parameterMode) {
            case POSITION -> Math.toIntExact(memory.get(instructionPointer));
            case IMMEDIATE -> instructionPointer;
            case RELATIVE -> relativeBase + Math.toIntExact(memory.get(instructionPointer));
            default -> throw new RuntimeException("Unexpected parameter mode: " + parameterMode);
        };
    }

    protected long getParameter(Map<Integer, ParameterMode> parameterModes, int item, int instructionPointer) {
        return memory.get(getMemoryLocation(parameterModes, item, instructionPointer));
    }

    /**
     * Opcode1 = Addition
     * Adds together numbers read from two positions and stores the result in a third position.
     * @param instructionPointer
     * @return
     */
    public int processOpcode1(int instructionPointer, Map<Integer, ParameterMode> parameterModes) {
        long leftValue = getParameter(parameterModes, 1, instructionPointer++);
        long rightValue = getParameter(parameterModes, 2, instructionPointer++);
        long sum = leftValue + rightValue;
        int storageLocation = getMemoryLocation(parameterModes, 3, instructionPointer++);
        memory.set(storageLocation, sum);
        return instructionPointer;
    }

    /**
     * Opcode2 = Multiplication
     * Multiplies together numbers read from two positions and stores the result in a third position.
     * @param instructionPointer
     * @return
     */
    public int processOpcode2(int instructionPointer, Map<Integer, ParameterMode> parameterModes) {
        long leftValue = getParameter(parameterModes, 1, instructionPointer++);
        long rightValue = getParameter(parameterModes, 2, instructionPointer++);
        long product = leftValue * rightValue;
        // Storage is always to a memory location
        int storageLocation = getMemoryLocation(parameterModes, 3, instructionPointer++);
        memory.set(storageLocation, product);
        return instructionPointer;
    }

    /**
     * Opcode3 = Store Input
     * takes a single integer as input and saves it to the position given by its only parameter.
     * For example, the instruction 3,50 would take an input value and store it at address 50.
     * @param instructionPointer
     * @return
     */
    public int processOpcode3(int instructionPointer, Map<Integer, ParameterMode> parameterModes) {
        long value = Long.parseLong(consumeInput());
        int storageLocation = getMemoryLocation(parameterModes, 1, instructionPointer++);
        // Storage is always to a memory location
        memory.set(storageLocation, value);
        return instructionPointer;
    }

    /**
     * Opcode4 = Write value to output
     * outputs the value of its only parameter.
     * For example, the instruction 4,50 would output the value at address 50.
     *
     * @param instructionPointer
     * @return
     */
    public int processOpcode4(int instructionPointer, Map<Integer, ParameterMode> parameterModes) {
        long data = getParameter(parameterModes, 1, instructionPointer++);
        appendOutput(data, instructionPointer);
        return instructionPointer;
    }

    /**
     * Opcode5 jump-if-true
     * if the first parameter is non-zero, it sets the instruction pointer to the value from the second parameter.
     * Otherwise, it does nothing.
     * @param instructionPointer
     * @param parameterModes
     * @return
     */
    public int processOpcode5(int instructionPointer, Map<Integer, ParameterMode> parameterModes) {
        long check = getParameter(parameterModes, 1, instructionPointer++);
        long jumpLine = getParameter(parameterModes, 2, instructionPointer++);
        return (Math.toIntExact(check) != 0) ? Math.toIntExact(jumpLine) : instructionPointer;
    }

    /**
     * Opcode6 jump-if-false
     * if the first parameter is zero, it sets the instruction pointer to the value from the second parameter.
     * Otherwise, it does nothing.
     * @param instructionPointer
     * @param parameterModes
     * @return
     */
    public int processOpcode6(int instructionPointer, Map<Integer, ParameterMode> parameterModes) {
        long check = getParameter(parameterModes, 1, instructionPointer++);
        long jumpLine = getParameter(parameterModes, 2, instructionPointer++);
        return (check == 0) ? Math.toIntExact(jumpLine) : instructionPointer;
    }

    /**
     * Opcode7 = less than:
     * if the first parameter is less than the second parameter, it stores 1 in the position given by the third parameter.
     * Otherwise, it stores 0.
     * @param instructionPointer
     * @return
     */
    public int processOpcode7(int instructionPointer, Map<Integer, ParameterMode> parameterModes) {
        long leftValue = getParameter(parameterModes, 1, instructionPointer++);
        long rightValue = getParameter(parameterModes, 2, instructionPointer++);
        long value = (leftValue < rightValue) ? 1 : 0;
        // Storage is always to a memory location
        int storageLocation = getMemoryLocation(parameterModes, 3, instructionPointer++);
        memory.set(Math.toIntExact(storageLocation), value);
        return instructionPointer;
    }

    /**
     * Opcode8 = equals
     * if the first parameter is equal to the second parameter, it stores 1 in the position given by the third parameter.
     * Otherwise, it stores 0.
     * @param instructionPointer
     * @return
     */
    public int processOpcode8(int instructionPointer, Map<Integer, ParameterMode> parameterModes) {
        long leftValue = getParameter(parameterModes, 1, instructionPointer++);
        long rightValue = getParameter(parameterModes, 2, instructionPointer++);
        long value = (leftValue == rightValue) ? 1L : 0L;
        // Storage is always to a memory location
        int storageLocation = getMemoryLocation(parameterModes, 3, instructionPointer++);
        memory.set(storageLocation, value);
        return instructionPointer;
    }

    /**
     * Opcode 9 = Relative Base
     * The address a relative mode parameter refers to is itself plus the current relative base.
     * When the relative base is 0, relative mode parameters and position mode parameters with
     * the same value refer to the same address.
     * @param instructionPointer
     * @param parameterModes
     * @return
     */
    public int processOpcode9(int instructionPointer, Map<Integer, ParameterMode> parameterModes) {
        long relativeBaseOffset = getParameter(parameterModes, 1, instructionPointer++);
        relativeBase += Math.toIntExact(relativeBaseOffset);
        return instructionPointer;
    }

    String consumeInput() {
//        System.out.println("Consuming input: ....");
        if (input.isEmpty()) {
            throw new RuntimeException("Can not consume input - there was none");
        }
        return input.remove(0);
    }

    void resetInput() {
        this.input = new ArrayList<>();
    }

    void setInput(String input) {
        this.input.add(input);
    }

    void appendOutput(long data, int instructionPointer) {
//        System.out.println("IP: " + instructionPointer + ": Appending output: " + data);
        output += (!output.isEmpty()) ? "," + data : data;
    }

    boolean hasOutput() {
        return !output.isEmpty();
    }

    String getOutput() {
        try {
            return output;
        } finally {
            output = "";
        }
    }

    static class Memory extends ArrayList<Long> {
        Map<Integer, Long> extendedMemory = new HashMap<>();

        public Memory(List<Long> list) {
            super(list);
        }

        @Override
        public Long get(int index) {
            if (index >= this.size()) {
                return extendedMemory.getOrDefault(index, 0L);
            }
            // for better or worse, just get whatever was requested
            return super.get(index);
        }

        @Override
        public Long set(int index, Long value) {
            if (index < this.size()) {
                return super.set(index, value);
            }
            Long oldValue = extendedMemory.get(index);
            extendedMemory.put(index, value);
            return oldValue;
        }

        public Memory getReadOnlyMemory() {
            Memory copy = new Memory(Collections.unmodifiableList(this));
            copy.extendedMemory = Collections.unmodifiableMap(new HashMap<>(extendedMemory));
            return copy;
        }
    }
}
