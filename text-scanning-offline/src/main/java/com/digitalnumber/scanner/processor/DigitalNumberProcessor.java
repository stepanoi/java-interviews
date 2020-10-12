package com.digitalnumber.scanner.processor;

import com.digitalnumber.scanner.config.DigitConfig;
import com.digitalnumber.scanner.model.Digit;
import com.digitalnumber.scanner.model.Number;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.digitalnumber.scanner.container.DigitalNumberContainer.MAX_DIGITS_PER_LINE;
import static com.digitalnumber.scanner.container.DigitalNumberContainer.MIN_WIDTH;
import static com.digitalnumber.scanner.util.LineUtils.line;


@Slf4j
public class DigitalNumberProcessor implements Processor {
    private static final String UNKNOWN = "?";
    private static final String ILLEGAL = "ILL";
    private static final String EMPTY   = "";

    @Override
    public String process(final Number number, final int width) {
        int lineLength = lineLength(number.getLines(), width);
        
        /* 
             _  _     _  _  _  _  _ 
           | _| _||_||_ |_   ||_||_| 
           ||_  _|  | _||_|  ||_| _|
              
           For the above n lines, read start to end in increments of width. We want to build images of 1, 2, 3 etc...
         */

        String result = IntStream.iterate(0, i -> i < lineLength, i -> i += width)
                                 .mapToObj(carrot -> Arrays.stream(number.getLines(), number.getStart(), number.getEnd())
                                                           .map(e -> e.substring(carrot, carrot + width))
                                                           .collect(Collectors.toList()))
                                 .map(lines -> {
                                     int numberOfLines = lines.size();
                                     int firstLinePos  = 0;
                                     int middleLinePos = (int) Math.floor(((double) numberOfLines) / 2);
                                     int lastLinePos   = lines.size() - 1;

                                     Integer value = recognise(lines, firstLinePos, middleLinePos, lastLinePos);

                                     return value == null ? UNKNOWN : value + EMPTY;
                                 })
                                 .collect(Collectors.joining());

        if (result.contains(UNKNOWN)) {
            result += ILLEGAL;
        }

        return result;
    }

    @Override
    public Integer recognise(final List<String> lines, final int firstLinePos, final int middleLinePos, final int lastLinePos) {
        Digit digit = Digit.builder()
                           .top(line(lines.get(firstLinePos)))
                           .middle(line(lines.get(middleLinePos)))
                           .bottom(line(lines.get(lastLinePos)))
                           .build();

        Integer value = DigitConfig.MAP.get(digit);

        if (Objects.isNull(value)) {
            log.info("Could not recognise " + digit);
        }

        return value;
    }

    private int lineLength(final String[] lines, final int width) {
        List<Integer> lineLengths = Arrays.stream(lines)
                                          .filter(e -> !e.trim().isEmpty())
                                          .map(String::length)
                                          .distinct()
                                          .collect(Collectors.toList());

        if (lineLengths.size() != 1) {
            log.error(Arrays.stream(lines).collect(Collectors.joining(System.lineSeparator())));
            throw new IllegalStateException("Different Line lengths detected " + lineLengths);
        }

        Integer lineLength = lineLengths.get(0);

        if (width == MIN_WIDTH && lineLength != MAX_DIGITS_PER_LINE * MIN_WIDTH) {
            throw new IllegalStateException("Line needs to have exactly " + MAX_DIGITS_PER_LINE + " digits and " + MIN_WIDTH * MAX_DIGITS_PER_LINE + " characters long");
        }

        if (width != MIN_WIDTH && lineLength != MAX_DIGITS_PER_LINE * width) {
            throw new IllegalStateException("Line needs to have exactly " + MAX_DIGITS_PER_LINE + " digits and " + width * MAX_DIGITS_PER_LINE + " characters long");
        }

        return lineLength;
    }
}
