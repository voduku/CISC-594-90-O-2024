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

    @Test
    void testSimpletronRemainder() {
        var simpletron = new Simpletron(
                "C:\\Users\\dovu4\\OneDrive - Harrisburg University\\Software T&T\\CISC-594-90-O-2024\\src\\test\\resources\\remainder2num.txt",
                new ArrayDeque<>(List.of(3, 5)));
        simpletron.process();
    }

    @Test
    void testSimpletronExpo() {
        var simpletron = new Simpletron(
                "C:\\Users\\dovu4\\OneDrive - Harrisburg University\\Software T&T\\CISC-594-90-O-2024\\src\\test\\resources\\expo2num.txt",
                new ArrayDeque<>(List.of(2, 2)));
        simpletron.process();
    }

    @Test
    void testSimpletronNewLine() {
        var simpletron = new Simpletron(
                "C:\\Users\\dovu4\\OneDrive - Harrisburg University\\Software T&T\\CISC-594-90-O-2024\\src\\test\\resources\\newline.txt",
                new ArrayDeque<>(List.of(2, 2)));
        simpletron.process();
    }
}
