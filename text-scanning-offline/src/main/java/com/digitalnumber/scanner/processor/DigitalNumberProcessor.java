package com.digitalnumber.scanner.processor;

import com.digitalnumber.scanner.config.DigitConfig;
import com.digitalnumber.scanner.container.DigitalNumberContainer;
import com.digitalnumber.scanner.model.Digit;
import com.digitalnumber.scanner.model.Fill;
import com.digitalnumber.scanner.model.Line;
import com.digitalnumber.scanner.model.Number;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.digitalnumber.scanner.container.DigitalNumberContainer.*;


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
        Digit   digit = buildDigit(lines, firstLinePos, middleLinePos, lastLinePos);
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

    private Digit buildDigit(final List<String> lines, final int firstLinePos, final int middleLinePos, final int lastLinePos) {
        return Digit.builder()
                    .top(Line.builder()
                             .left(lineLeft(lines.get(firstLinePos)))
                             .body(lineBody(lines.get(firstLinePos)))
                             .right(lineRight(lines.get(firstLinePos)))
                             .build())
                    .middle(Line.builder()
                                .left(lineLeft(lines.get(middleLinePos)))
                                .body(lineBody(lines.get(middleLinePos)))
                                .right(lineRight(lines.get(middleLinePos)))
                                .build())
                    .bottom(Line.builder()
                                .left(lineLeft(lines.get(lastLinePos)))
                                .body(lineBody(lines.get(lastLinePos)))
                                .right(lineRight(lines.get(lastLinePos)))
                                .build())
                    .build();
    }

    private Fill lineLeft(final String line) {
        return Fill.from(StringUtils.left(line, 1));
    }

    private Fill lineBody(final String line) {
        return Fill.from(Arrays.stream(line.substring(1, line.length() - 1).split(EMPTY))
                               .distinct()
                               .collect(Collectors.joining()));
    }

    private Fill lineRight(final String line) {
        return Fill.from(StringUtils.right(line, 1));
    }
}
