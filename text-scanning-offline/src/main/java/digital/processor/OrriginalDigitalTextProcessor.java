package digital.processor;

import digital.config.DigitConfig;
import digital.config.VariableDigitConfig;
import digital.model.Block;
import digital.model.Digit;
import digital.model.Fill;
import digital.model.Line;
import digital.model.VariableDigit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static digital.DigitalTextScanner.MIN_WIDTH;
import static digital.util.LineUtils.matchBody;
import static digital.util.LineUtils.matchEdge;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.stripToNull;

@Slf4j
public class OrriginalDigitalTextProcessor {

    private static final String UNKNOWN = "?";
    private static final String ILLEGAL = "ILL";
    public static final String EMPTY = "";

    public String processLine(Block block, final int width) {
        int lineLength = lineLength(block.getLines());
            /* 
                _  _     _  _  _  _  _ 
              | _| _||_||_ |_   ||_||_| 
              ||_  _|  | _||_|  ||_| _|
              
              For the above n lines, read start to end in increments of width. We want to build images of 1, 2, 3 etc...
            */


        String result = IntStream.iterate(0, i -> i < lineLength, i -> i += width)
                                 .mapToObj(carrot -> read(block.getLines(), block.getStart(), block.getEnd(), carrot, width))
                                 .map(this::process)
                                 .collect(Collectors.joining());

        if (result.contains(UNKNOWN)) {
            result += ILLEGAL;
        }

        return result;
    }

    private List<String> read(final String[] lines, final int start, final int end, final int carrot, final int width) {
        return Arrays.stream(lines, start, end)
                     .map(e -> e.substring(carrot, carrot + width))
                     .collect(Collectors.toList());
    }

    private String process(final List<String> lines) {
        int numberOfLines = lines.size();
        int firstLinePos = 0;
        int middleLinePos = (int) Math.floor(((double) numberOfLines) / 2);
        int lastLinePos = lines.size() - 1;

        Digit digit = buildDigit(lines, firstLinePos, middleLinePos, lastLinePos);
        Integer value = DigitConfig.MAP.get(digit);
        Optional<VariableDigit> variableDigit = buildVariableDigit(lines, firstLinePos, middleLinePos, lastLinePos);

        if (nonNull(value) && variableDigit.isPresent()) {

            VariableDigit ref = VariableDigitConfig.MAP.get(value);
            VariableDigit current = variableDigit.get();

            boolean topEdgeMatch = matchEdge(current.getTop(), ref.getTop());

            boolean topBodyMatch = matchBody(current.getTop(), ref.getTop());

            boolean bottomEdgeMatch = matchEdge(current.getBottom(), ref.getBottom());

            boolean bottomBodyMatch = matchBody(current.getBottom(), ref.getBottom());

            final Integer finalValue = value;
            value = Stream.of(topEdgeMatch, topBodyMatch, bottomEdgeMatch, bottomBodyMatch)
                          .reduce(Boolean::logicalAnd)
                          .filter(e -> e == Boolean.TRUE)
                          .map(e -> finalValue)
                          .orElse(null);
        }

        return value == null ? UNKNOWN : value + EMPTY;
    }

    private int lineLength(final String[] lines) {
        Set<Integer> lineLengths = Arrays.stream(lines)
                                         .filter(e -> !e.trim().isEmpty())
                                         .map(String::length)
                                         .collect(Collectors.toSet());

        if (lineLengths.size() != 1) {
            log.error(Arrays.stream(lines).collect(Collectors.joining(System.lineSeparator())));
            throw new IllegalStateException("Different Line lengths detected " + lineLengths);
        }

        return lineLengths.stream()
                          .filter(e -> e % MIN_WIDTH == 0)
                          .findFirst()
                          .orElseThrow(() -> new IllegalStateException("Line needs to be in multiples of " + MIN_WIDTH));
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

    private Fill lineLeft(final String s) {
        return Fill.from(s.charAt(0) + EMPTY);
    }

    private Fill lineBody(final String s) {
        return Fill.from(Arrays.stream(s.substring(1, s.length() - 1).split(EMPTY))
                               .distinct()
                               .collect(Collectors.joining()));
    }

    private Fill lineRight(final String s) {
        return Fill.from(s.charAt(s.length() - 1) + EMPTY);
    }

    private Optional<VariableDigit> buildVariableDigit(final List<String> lines, final int firstLinePos, final int middleLinePos, final int lastLinePos) {
        Optional<Line> top = variableLine(firstLinePos + 1, middleLinePos, lines);
        Optional<Line> bottom = variableLine(middleLinePos + 1, lastLinePos, lines);

        return top.isEmpty() && bottom.isEmpty()
                ? Optional.empty()
                : Optional.of(VariableDigit.builder()
                                           .top(top.orElse(null))
                                           .bottom(bottom.orElse(null))
                                           .build());
    }

    private Optional<Line> variableLine(final int start, final int end, final List<String> lines) {
        String left = stripToNull(IntStream.range(start, end)
                                           .mapToObj(value -> lines.get(value).charAt(0))
                                           .map(String::valueOf)
                                           .distinct()
                                           .collect(Collectors.joining()));
        String body = stripToNull(IntStream.range(start, end)
                                           .mapToObj(value -> {
                                               String s = lines.get(value);
                                               return s.substring(1, s.length() - 2);
                                           })
                                           .map(String::valueOf)
                                           .distinct()
                                           .collect(Collectors.joining()));
        String right = stripToNull(IntStream.range(start, end)
                                            .mapToObj(value -> {
                                                String s = lines.get(value);
                                                return s.charAt(s.length() - 1);
                                            })
                                            .map(String::valueOf)
                                            .distinct()
                                            .collect(Collectors.joining()));

        return ObjectUtils.anyNotNull(left, body, right)
                ? Optional.of(Line.builder()
                                  .left(Fill.from(left))
                                  .body(Fill.from(body))
                                  .right(Fill.from(right))
                                  .build())
                : Optional.empty();
    }
}
