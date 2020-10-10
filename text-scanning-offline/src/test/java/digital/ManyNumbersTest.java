package digital;

import digital.config.DigitConfig;
import digital.model.Digit;
import digital.model.Line;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManyNumbersTest {

    private static final int TOTAL_NUMBERS = 400;

    @Test
    public void should_match_generated_numbers_test() {
        //Let's randomly generate the total number of numbers we want
        List<String> expected = new Random()
                .ints(0, 999999999)
                .limit(TOTAL_NUMBERS)
                .mapToObj(value -> {
                    StringBuilder s = new StringBuilder(value + "");
                    int fillRequired = 9 - s.length();

                    for (int i = 0; i < fillRequired; i++) {
                        s.insert(0, "0");
                    }

                    return s.toString();
                })
                .collect(Collectors.toList());

        //Now we digitise these numbers into one massive String
        String generated = expected.stream()
                                  .map(e -> generate(e) + System.lineSeparator())
                                  .collect(Collectors.joining());

        //Print it for illustrative purposes only
        System.out.println(generated);
        
        //Kick of the recognition process and assert we match what we generated at the start
        DigitalNumberScanner unit = new DigitalNumberScanner();
        assertEquals(expected.stream().collect(Collectors.joining(System.lineSeparator())), unit.scan(generated));
    }

    private String generate(String s) {
        List<Digit> digits = Arrays.stream(s.split(""))
                                   .map(e1 -> DigitConfig.MAP.entrySet()
                                                             .stream()
                                                             .filter(e -> e.getValue() == Integer.parseInt(e1))
                                                             .map(Map.Entry::getKey)
                                                             .findFirst()
                                                             .get())
                                   .collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();
        for (Digit n : digits) {
            printLine(n.getTop(), builder);
        }

        builder.append(System.lineSeparator());

        for (Digit n : digits) {
            printLine(n.getMiddle(), builder);
        }

        builder.append(System.lineSeparator());

        for (Digit n : digits) {
            printLine(n.getBottom(), builder);
        }

        builder.append(System.lineSeparator());
        return builder.toString();
    }


    private void printLine(Line line, StringBuilder builder) {
        builder.append(line.getLeft().getValue());
        builder.append(line.getBody().getValue());
        builder.append(line.getRight().getValue());
    }

}