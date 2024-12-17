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
    Map<Character, Integer> registers = new HashMap<>();

    public Day17() {
        super(2024, 17, regex1, regex2);
    }

    public Day17(String filename) {
        super(filename, regex1, regex2);
    }

    public Integer getRegisterValue(char register) {
        return registers.get(register);
    }

    @Override
    public Object processInput1(ConfigGroup configGroup, ConfigGroup configGroup1) {
        registers = new HashMap<>();

        List<Integer> commandLine = new ArrayList<>();
        GroupItem group0 = configGroup.get(0);
        for (ItemLine line : group0) {
            registers.put(line.getChar(0), line.getInt(1));
        }

        // Here's code for a 2nd group, if needed
        GroupItem group1 = configGroup1.get(0);
        for (ItemLine line : group1) {
            for (int itemNum = 0; itemNum < line.size(); itemNum++) {
                commandLine.add(line.getInt(itemNum));
            }
        }

        String results = processComputer(registers, commandLine);
        return results;
    }

    public Integer getComboOperand(Integer comboNumber) {
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
                return comboNumber;
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

    public String processComputer(Map<Character, Integer> registers, List<Integer> commands) {
        List<Integer> output = new ArrayList<>();
        int commandIndex = 0;
        while (commandIndex < commands.size()) {
            switch (commands.get(commandIndex++)) {
                case ADV:
                    // The adv instruction (opcode 0) performs division. The numerator is the value in the A register.
                    // The denominator is found by raising 2 to the power of the instruction's combo operand.
                    // (So, an operand of 2 would divide A by 4 (2^2); an operand of 5 would divide A by 2^B.)
                    // The result of the division operation is truncated to an integer and then written to the A register.
                    int divisor = (int) Math.pow(2, getComboOperand(commands.get(commandIndex)));
                    registers.put('A', registers.get('A') / divisor);
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
                    output.add(getComboOperand(commands.get(commandIndex)) % 8);
                    commandIndex++;
                    break;
                case BDV:
                    // The bdv instruction (opcode 6) works exactly like the adv instruction except that the
                    // result is stored in the B register. (The numerator is still read from the A register.)
                    int divisorB = (int) Math.pow(2, getComboOperand(commands.get(commandIndex)));
                    registers.put('B', registers.get('A') / divisorB);
                    commandIndex++;
                    break;
                case CDV:
                    // The cdv instruction (opcode 7) works exactly like the adv instruction except that
                    // the result is stored in the C register. (The numerator is still read from the A register.)
                    int divisorC = (int) Math.pow(2, getComboOperand(commands.get(commandIndex)));
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
        registers = new HashMap<>();
        return 2;
    }
}
