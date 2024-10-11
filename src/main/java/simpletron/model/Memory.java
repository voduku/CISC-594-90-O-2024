package simpletron.model;

import java.util.Arrays;

import static simpletron.Utils.validateWord;

public record Memory(int value, int memoryLocation) {


    public static boolean isOpCode(String word) {
        validateWord(word);
        return Arrays.stream(OperationCode.values())
                .anyMatch(opcode -> opcode.getCode() == Integer.parseInt(word.substring(1, 3)));
    }
}
