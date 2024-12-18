package com.unhuman.adventofcode2024;

import com.unhuman.adventofcode.aoc_framework.InputParser;
import com.unhuman.adventofcode.aoc_framework.representation.ConfigGroup;
import com.unhuman.adventofcode.aoc_framework.representation.GroupItem;
import com.unhuman.adventofcode.aoc_framework.representation.ItemLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day17 extends InputParser {
    /// Register A: 729
    /// Register B: 0
    /// Register C: 0
    ///
    /// Program: 0,1,5,4,3,0
    private static final String regex1 = "Register (.): (\\d+)";
    private static final String regex2 = "([\\d]),?";

    private static final int ADV = 0;
    private static final int BXL = 1;
    private static final int BST = 2;
    private static final int JNZ = 3;
    private static final int BXC = 4;
    private static final int OUT = 5;
    private static final int BDV = 6;
    private static final int CDV = 7;

    // kept here so we can pull values in tests
    Map<Character, Long> registers = new HashMap<>();

    public Day17() {
        super(2024, 17, regex1, regex2);
    }

    public Day17(String filename) {
        super(filename, regex1, regex2);
    }

    public Long getRegisterValue(char register) {
        return registers.get(register);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        registers = new HashMap<>();

        List<Integer> commandLine = new ArrayList<>();
        GroupItem group0 = configGroup.get(0);
        for (ItemLine line : group0) {
            registers.put(line.getChar(0), line.getLong(1));
        }

        // Here's code for a 2nd group, if needed
        GroupItem group1 = configGroup1.get(0);
        for (ItemLine line : group1) {
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
                commandLine.add(line.getInt(itemNum));
            }
        }

        String results = processComputer(registers, commandLine, false);
        return results;
    }

    public Long getComboOperand(Integer comboNumber) {
//        Combo operands 0 through 3 represent literal values 0 through 3.
//        Combo operand 4 represents the value of register A.
//        Combo operand 5 represents the value of register B.
//        Combo operand 6 represents the value of register C.
//        Combo operand 7 is reserved and will not appear in valid programs.
        switch (comboNumber) {
            case 0:
            case 1:
            case 2:
            case 3:
                return (long) comboNumber;
            case 4:
                return registers.get('A');
            case 5:
                return registers.get('B');
            case 6:
                return registers.get('C');
            default:
                throw new RuntimeException("Invalid combo operand: " + comboNumber);
        }
    }

    public String processComputer(Map<Character, Long> registers, List<Integer> commands, boolean wantOutputMatching) {
        List<Long> output = new ArrayList<>();
        int commandIndex = 0;
        while (commandIndex < commands.size()) {
            switch (commands.get(commandIndex++)) {
                case ADV:
                    // The adv instruction (opcode 0) performs division. The numerator is the value in the A register.
                    // The denominator is found by raising 2 to the power of the instruction's combo operand.
                    // (So, an operand of 2 would divide A by 4 (2^2); an operand of 5 would divide A by 2^B.)
                    // The result of the division operation is truncated to an integer and then written to the A register.
                    int divisorA = (int) Math.pow(2, getComboOperand(commands.get(commandIndex)));
                    if (divisorA == 0) {
                        System.err.println("Error: A Divide by zero");
                        return "Error: A Divide by zero";
                    }
                    registers.put('A', registers.get('A') / divisorA);
                    commandIndex++;
                    break;
                case BXL:
                    // The bxl instruction (opcode 1) calculates the bitwise XOR of register B and the instruction's
                    // literal operand, then stores the result in register B.
                    registers.put('B', registers.get('B') ^ commands.get(commandIndex));
                    commandIndex++;
                    break;
                case BST:
                    // The bst instruction (opcode 2) calculates the value of its combo operand modulo 8
                    // (thereby keeping only its lowest 3 bits), then writes that value to the B register.
                    registers.put('B', getComboOperand(commands.get(commandIndex)) % 8);
                    commandIndex++;
                    break;
                case JNZ:
                    // The jnz instruction (opcode 3) does nothing if the A register is 0.
                    // However, if the A register is not zero, it jumps by setting the instruction pointer
                    // to the value of its literal operand;
                    // if this instruction jumps, the instruction pointer is not increased by 2 after this instruction.
                    if (registers.get('A') == 0) {
                        commandIndex++;
                    } else {
                        commandIndex = commands.get(commandIndex);
                    }
                    break;
                case BXC:
                    // The bxc instruction (opcode 4) calculates the bitwise XOR of register B and register C,
                    // then stores the result in register B. (For legacy reasons, this instruction reads an operand but ignores it.)
                    registers.put('B', registers.get('B') ^ registers.get('C'));
                    commandIndex++;
                    break;
                case OUT:
                    // The out instruction (opcode 5) calculates the value of its combo operand modulo 8,
                    // then outputs that value. (If a program outputs multiple values, they are separated by commas.)

                    // Skip the output if we're looking for matching output
                    long value = getComboOperand(commands.get(commandIndex)) % 8;
                    if (wantOutputMatching) {
                        if (commands.get(output.size()) != value) {
                            return "NOPE";
                        }
                    }

                    output.add(value);
                    commandIndex++;
                    break;
                case BDV:
                    // The bdv instruction (opcode 6) works exactly like the adv instruction except that the
                    // result is stored in the B register. (The numerator is still read from the A register.)
                    int divisorB = (int) Math.pow(2, getComboOperand(commands.get(commandIndex)));
                    if (divisorB == 0) {
                        System.err.println("Error: B Divide by zero");
                        return "Error: B Divide by zero";
                    }
                    registers.put('B', registers.get('A') / divisorB);
                    commandIndex++;
                    break;
                case CDV:
                    // The cdv instruction (opcode 7) works exactly like the adv instruction except that
                    // the result is stored in the C register. (The numerator is still read from the A register.)
                    int divisorC = (int) Math.pow(2, getComboOperand(commands.get(commandIndex)));
                    if (divisorC == 0) {
                        System.err.println("Error: C Divide by zero");
                        return "Error: C Divide by zero";
                    }
                    registers.put('C', registers.get('A') / divisorC);
                    commandIndex++;
                    break;
                default:
                    return "Unknown command: " + commands.get(commandIndex);
            }
        }

        // return output as a comma separated string using streams
        return output.stream().map(Object::toString).reduce((a, b) -> a + "," + b).orElse("");
    }

    @Override
    public Object processInput2(ConfigGroup configGroup, ConfigGroup configGroup1) {
        List<Integer> commandLine = new ArrayList<>();
        GroupItem group0 = configGroup.get(0);
        Map<Character, Long> originalRegisterState = new HashMap<>();
        for (ItemLine line : group0) {
            originalRegisterState.put(line.getChar(0), line.getLong(1));
        }

        // Here's code for a 2nd group, if needed
        GroupItem group1 = configGroup1.get(0);
        for (ItemLine line : group1) {
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
                commandLine.add(line.getInt(itemNum));
            }
        }

        String desiredOutput = commandLine.stream().map(Object::toString).reduce((a, b) -> a + "," + b).orElse("");

        long regAValue = 1;

        while (true) {
            registers = new HashMap<>(originalRegisterState);
            registers.put('A', (long) regAValue);
            String results = processComputer(registers, commandLine, true);
            if (results.equals(desiredOutput)) {
                return regAValue;
            }
            if (++regAValue % 1000000 == 0) {
                System.out.println("Checking " + regAValue);
            };
        }
    }
}
