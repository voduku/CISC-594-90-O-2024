package simpletron;

import lombok.NonNull;

import java.util.regex.Pattern;

public class Utils {
    public static final Pattern WORD_REGEX = Pattern.compile("[+-]\\d{4}");

    public static void validateWord(@NonNull String word) {
        if (!WORD_REGEX.matcher(word).matches()) {
            throw new IllegalArgumentException("Word %s does not match pattern %s".formatted(word, WORD_REGEX.toString()));
        }
    }
}
