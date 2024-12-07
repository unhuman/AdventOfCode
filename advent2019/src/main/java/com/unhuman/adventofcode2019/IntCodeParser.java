package com.unhuman.adventofcode2019;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IntCodeParser {
    private List<Integer> memory;

    private List<String> input = new ArrayList<>();
    private String output;

    enum ParameterMode { POSITION, IMMEDIATE }

    public IntCodeParser(List<String> code) {
        this.memory = new ArrayList<>(code.stream().map(Integer::parseInt).toList());
        this.output = "";
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
                }
            }

            try {
                switch (command) {
                    case 1 -> instructionPointer = processOpcode1(instructionPointer, parameterModes);
                    case 2 -> instructionPointer = processOpcode2(instructionPointer, parameterModes);
                    case 3 -> instructionPointer = processOpcode3(instructionPointer, parameterModes);
                    case 4 -> instructionPointer = processOpcode4(instructionPointer, parameterModes);
                    case 5 -> instructionPointer = processOpcode5(instructionPointer, parameterModes);
                    case 6 -> instructionPointer = processOpcode6(instructionPointer, parameterModes);
                    case 7 -> instructionPointer = processOpcode7(instructionPointer, parameterModes);
                    case 8 -> instructionPointer = processOpcode8(instructionPointer, parameterModes);
                    case 99 -> {
                        return;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(String.format("Problem performing operation %s at instruction: %d",
                        operationInfo, instructionPointer), e);
            }
        }
    }

    ParameterMode getParameterMode(Map<Integer, ParameterMode> parameterModes, int item) {
        return (parameterModes.getOrDefault(item, ParameterMode.POSITION));

    }

    /**
     * Opcode1 = Addition
     * Adds together numbers read from two positions and stores the result in a third position.
     * @param instructionPointer
     * @return
     */
    public int processOpcode1(int instructionPointer, Map<Integer, ParameterMode> parameterModes) {
        int leftValue = getParameterMode(parameterModes, 1) == ParameterMode.POSITION
                ? memory.get(memory.get(instructionPointer++))
                : memory.get(instructionPointer++);
        int rightValue = getParameterMode(parameterModes, 2) == ParameterMode.POSITION
                ? memory.get(memory.get(instructionPointer++))
                : memory.get(instructionPointer++);
        int sum = leftValue + rightValue;
        // Storage is always to a memory location
        int storageLocation = memory.get(instructionPointer++);
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
        int leftValue = getParameterMode(parameterModes, 1) == ParameterMode.POSITION
                ? memory.get(memory.get(instructionPointer++))
                : memory.get(instructionPointer++);
        int rightValue = getParameterMode(parameterModes, 2) == ParameterMode.POSITION
                ? memory.get(memory.get(instructionPointer++))
                : memory.get(instructionPointer++);
        int product = leftValue * rightValue;
        // Storage is always to a memory location
        int storageLocation = memory.get(instructionPointer++);
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
        int value = Integer.parseInt(consumeInput());
        int storageLocation = memory.get(instructionPointer++);
        // Storage is always to a memory location
        memory.set(storageLocation, value);
        return instructionPointer;
    }

    /**
     * Opcode3 = Write value to output
     * outputs the value of its only parameter.
     * For example, the instruction 4,50 would output the value at address 50.
     *
     * @param instructionPointer
     * @return
     */
    public int processOpcode4(int instructionPointer, Map<Integer, ParameterMode> parameterModes) {
        int data = getParameterMode(parameterModes, 1) == ParameterMode.POSITION
                ? memory.get(memory.get(instructionPointer++))
                : memory.get(instructionPointer++);
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
        int check = getParameterMode(parameterModes, 1) == ParameterMode.POSITION
                ? memory.get(memory.get(instructionPointer++))
                : memory.get(instructionPointer++);
        int jumpLine = getParameterMode(parameterModes, 2) == ParameterMode.POSITION
                ? memory.get(memory.get(instructionPointer++))
                : memory.get(instructionPointer++);
        return (check != 0) ? jumpLine : instructionPointer;
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
        int check = getParameterMode(parameterModes, 1) == ParameterMode.POSITION
                ? memory.get(memory.get(instructionPointer++))
                : memory.get(instructionPointer++);
        int jumpLine = getParameterMode(parameterModes, 2) == ParameterMode.POSITION
                ? memory.get(memory.get(instructionPointer++))
                : memory.get(instructionPointer++);
        return (check == 0) ? jumpLine : instructionPointer;
    }

    /**
     * Opcode7 = less than:
     * if the first parameter is less than the second parameter, it stores 1 in the position given by the third parameter.
     * Otherwise, it stores 0.
     * @param instructionPointer
     * @return
     */
    public int processOpcode7(int instructionPointer, Map<Integer, ParameterMode> parameterModes) {
        int leftValue = getParameterMode(parameterModes, 1) == ParameterMode.POSITION
                ? memory.get(memory.get(instructionPointer++))
                : memory.get(instructionPointer++);
        int rightValue = getParameterMode(parameterModes, 2) == ParameterMode.POSITION
                ? memory.get(memory.get(instructionPointer++))
                : memory.get(instructionPointer++);
        int value = (leftValue < rightValue) ? 1 : 0;
        // Storage is always to a memory location
        int storageLocation = memory.get(instructionPointer++);
        memory.set(storageLocation, value);
        return instructionPointer;
    }

    /**
     * Opcode7 = less than:
     * if the first parameter is less than the second parameter, it stores 1 in the position given by the third parameter.
     * Otherwise, it stores 0.
     * @param instructionPointer
     * @return
     */
    public int processOpcode8(int instructionPointer, Map<Integer, ParameterMode> parameterModes) {
        int leftValue = getParameterMode(parameterModes, 1) == ParameterMode.POSITION
                ? memory.get(memory.get(instructionPointer++))
                : memory.get(instructionPointer++);
        int rightValue = getParameterMode(parameterModes, 2) == ParameterMode.POSITION
                ? memory.get(memory.get(instructionPointer++))
                : memory.get(instructionPointer++);
        int value = (leftValue == rightValue) ? 1 : 0;
        // Storage is always to a memory location
        int storageLocation = memory.get(instructionPointer++);
        memory.set(storageLocation, value);
        return instructionPointer;
    }

    String consumeInput() {
        System.out.println("Consuming input: ....");
        return input.remove(0);
    }

    void setInput(String input) {
        this.input.add(input);
    }

    void appendOutput(int data, int instructionPointer) {
        System.out.println("IP: " + instructionPointer + ": Appending output: " + data);
        output += (!output.equals("")) ? "\n" + data : data;
    }

    String getOutput() {
        return output;
    }

}
