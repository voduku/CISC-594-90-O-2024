package simpletron.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum OperationCode {

    READ(10),
    WRITE(11),
    LOAD(20),
    STORE(21),
    ADD(30),
    SUBTRACT(31),
    MULTIPLY(32),
    DIVIDE(33),
    BRANCH(40),
    BRANCHNEG(41),
    BRANCHZERO(42),
    HALT(43);

    private final int code;

    public static OperationCode fromCode(int code) {
        return Arrays.stream(values())
                .filter(v -> v.getCode() == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid operation code: " + code));
    }
}
