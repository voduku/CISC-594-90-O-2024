import org.junit.jupiter.api.Test;
import simpletron.Simpletron;

import java.util.ArrayDeque;
import java.util.List;

public class SimpletronTest {
    @Test
    void testSimpletron() {
        var simpletron = new Simpletron(
                "C:\\Users\\dovu4\\OneDrive - Harrisburg University\\Software T&T\\CISC-594-90-O-2024\\src\\test\\resources\\add2num.txt",
                new ArrayDeque<>(List.of(1, 1)));
        simpletron.process();
    }
}
