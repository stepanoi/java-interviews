package com.digitalnumber.scanner;

import com.digitalnumber.scanner.config.DigitConfig;
import com.digitalnumber.scanner.model.Digit;
import com.digitalnumber.scanner.model.Line;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManyNumbersTest {

    private static final int TOTAL_NUMBERS = 1000;

    @Test
    void should_match_generated_numbers_test() {
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
                                  .map(number -> generate(number) + System.lineSeparator())
                                  .collect(Collectors.joining());

        //Print it for illustrative purposes only
        System.out.println(generated);
        
        //Kick off the recognition process and assert we match what we generated at the start
        DigitalNumberScanner unit = new DigitalNumberScanner();
        assertEquals(expected.stream().collect(Collectors.joining(System.lineSeparator())), unit.scan(generated));
    }

    private String generate(String number) {
        List<Digit> digits = Arrays.stream(number.split(""))
                                   .map(textDigit -> DigitConfig.MAP.entrySet()
                                                             .stream()
                                                             .filter(e -> e.getValue() == Integer.parseInt(textDigit))
                                                             .map(Map.Entry::getKey)
                                                             .findFirst()
                                                             .orElse(null))
                                   .collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();

        digits.forEach(n -> printLine(n.getTop(), builder));

        builder.append(System.lineSeparator());

        digits.forEach(n -> printLine(n.getMiddle(), builder));

        builder.append(System.lineSeparator());

        digits.forEach(n -> printLine(n.getBottom(), builder));
 
        builder.append(System.lineSeparator());
        return builder.toString();
    }


    private void printLine(Line line, StringBuilder builder) {
        builder.append(line.getLeft().getValue());
        builder.append(line.getBody().getValue());
        builder.append(line.getRight().getValue());
    }

}