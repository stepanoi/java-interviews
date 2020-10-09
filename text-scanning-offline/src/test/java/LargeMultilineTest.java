import config.NumberConfig;
import model.Line;
import model.Number;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LargeMultilineTest {

    @Test
    public void should_match_generated_numbers_test() {
        List<String> strings = new Random()
                .ints(0, 999999999)
                .limit(400)
                .mapToObj(value -> {
                    StringBuilder s = new StringBuilder(value + "");
                    int fillRequired = 9 - s.length();

                    for (int i = 0; i < fillRequired; i++) {
                        s.insert(0, "0");
                    }

                    return s.toString();
                })
                .collect(Collectors.toList());

        String generated = strings.stream()
                                  .map(e -> generate(e) + System.lineSeparator())
                                  .collect(Collectors.joining());

        System.out.println(generated);
        TextScanner unit = new TextScanner();
        assertEquals(strings.stream().collect(Collectors.joining(System.lineSeparator())), unit.scan(generated));
    }

    private String generate(String s) {
        List<Number> numbers = Arrays.stream(s.split(""))
                                     .map(e1 -> NumberConfig.NUMBER_MAP.entrySet()
                                                                       .stream()
                                                                       .filter(e -> e.getValue() == Integer.parseInt(e1))
                                                                       .map(Map.Entry::getKey)
                                                                       .findFirst()
                                                                       .get())
                                     .collect(Collectors.toList());

        StringBuilder builder = new StringBuilder();
        for (Number n : numbers) {
            printLine(n.getTop(), builder);
        }

        builder.append(System.lineSeparator());

        for (Number n : numbers) {
            printLine(n.getMiddle(), builder);
        }

        builder.append(System.lineSeparator());

        for (Number n : numbers) {
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