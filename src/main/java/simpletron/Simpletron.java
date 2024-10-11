package simpletron;

import lombok.Data;
import lombok.NonNull;
import lombok.SneakyThrows;
import simpletron.model.Memory;
import simpletron.model.OperationCode;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

import static simpletron.model.Memory.isOpCode;

@Data
public class Simpletron {
    private final Memory[] memory;
    private final Queue<Integer> cmdInputs;
    private int accumulator;
    private int reservedMemoryLastIndex;

    public Simpletron(@NonNull String filePath, Queue<Integer> cmdInputs) {
        this.memory = new Memory[100];
        this.accumulator = 0;
        this.cmdInputs = cmdInputs;
        loadInstructionsIntoMemory(filePath);
    }

    public void process() {
        loop:
        for (int i = 0; i <= reservedMemoryLastIndex; ) {
            Memory instruction = memory[i];
            int instMemLoc = instruction.memoryLocation();
            switch (OperationCode.fromCode(instruction.value())) {
                case READ -> {
                    System.out.println("Enter an integer: ");
                    memory[instMemLoc] = new Memory(cmdInputs.remove(), instMemLoc);
                    i++;
                }
                case WRITE -> {
                    System.out.println("Output: " + memory[instMemLoc].value());
                    i++;
                }
                case LOAD -> {
                    accumulator = memory[instMemLoc].value();
                    System.out.printf("Loaded %s into accumulator%n", accumulator);
                    i++;
                }
                case STORE -> {
                    memory[instMemLoc] = new Memory(accumulator, instMemLoc);
                    System.out.printf("Stored %s into memory location %d%n", accumulator, instMemLoc);
                    i++;
                }
                case ADD -> {
                    int value = memory[instMemLoc].value();
                    accumulator += value;
                    System.out.printf("Added %s into accumulator%n", value);
                    i++;
                }
                case SUBTRACT -> {
                    int value = memory[instMemLoc].value();
                    accumulator -= value;
                    System.out.printf("Subtracted %s from accumulator%n", value);
                    i++;
                }
                case DIVIDE -> {
                    int value = memory[instMemLoc].value();
                    accumulator /= value;
                    System.out.printf("Divided accumulator by %d%n", value);
                    i++;
                }
                case MULTIPLY -> {
                    int value = memory[instMemLoc].value();
                    accumulator *= value;
                    System.out.printf("Multiplied accumulator by %d%n", value);
                    i++;
                }
                case BRANCH -> {
                    if (accumulator > 0) i = instMemLoc;
                    System.out.printf("Branched to instruction at memory location: %d%n", instMemLoc);
                }
                case BRANCHNEG -> {
                    if (accumulator < 0) i = instMemLoc;
                    System.out.printf("Branched to instruction at memory location: %d%n", instMemLoc);
                }
                case BRANCHZERO -> {
                    if (accumulator == 0) i = instMemLoc;
                    System.out.printf("Branched to instruction at memory location: %d%n", instMemLoc);
                }
                case HALT -> {
                    System.out.printf("""
                            REGISTERS:
                            accumulator         %d
                            instructionCounter  %d
                            instructionRegister %d
                            operationCode       %d
                            operand             %d
                            
                            %s
                            """.formatted(accumulator, reservedMemoryLastIndex + 1, instMemLoc, instruction.value(), instMemLoc, memoryToString()));
                    break loop;
                }
            }
        }

    }

    @SneakyThrows
    private void loadInstructionsIntoMemory(String filePath) {
        List<String> instructions = Files.readAllLines(Paths.get(filePath));
        if (instructions.isEmpty()) throw new IllegalArgumentException("No instructions found");

        this.reservedMemoryLastIndex = instructions.size() - 1;
        boolean hasHalt = false;

        for (int i = 0; i < instructions.size(); i++) {
            String word = instructions.get(i);
            if (!isOpCode(word)) throw new IllegalArgumentException("Invalid instruction: " + word);

            int opcode = Integer.parseInt(word.substring(1, 3));
            int memLoc = Integer.parseInt(word.substring(3));
            if (0 <= memLoc && memLoc <= reservedMemoryLastIndex && OperationCode.HALT.getCode() != opcode)
                throw new IllegalArgumentException("Invalid operand (memory location): " + memLoc);

            if (OperationCode.HALT.getCode() == opcode) hasHalt = true;
            this.memory[i] = new Memory(opcode, memLoc);
        }

        if (!hasHalt) throw new IllegalArgumentException("Instructions don't contain HALT");
    }

    private String memoryToString() {
        Object[] memoryCopy = Arrays.stream(memory)
                .map(m -> m != null ? m : new Memory(0, 0))
                .map(Memory::toString)
                .toArray(Object[]::new);
        return """
                       0     1     2     3     4     5     6     7     8     9
                 0 %s %s %s %s %s %s %s %s %s %s
                10 %s %s %s %s %s %s %s %s %s %s
                20 %s %s %s %s %s %s %s %s %s %s
                30 %s %s %s %s %s %s %s %s %s %s
                40 %s %s %s %s %s %s %s %s %s %s
                50 %s %s %s %s %s %s %s %s %s %s
                60 %s %s %s %s %s %s %s %s %s %s
                70 %s %s %s %s %s %s %s %s %s %s
                80 %s %s %s %s %s %s %s %s %s %s
                90 %s %s %s %s %s %s %s %s %s %s
                """.formatted(memoryCopy);
    }
}