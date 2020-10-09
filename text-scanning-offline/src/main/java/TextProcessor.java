import config.NumberConfig;
import config.VariableNumberConfig;
import lombok.extern.slf4j.Slf4j;
import model.Fill;
import model.Image;
import model.Line;
import model.Number;
import model.VariableNumber;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.stripToNull;
import static util.LineUtils.matchBody;
import static util.LineUtils.matchEdge;

@Slf4j
public class TextProcessor {

    private static final String UNKNOWN = "?";
    private static final String ILLEGAL = "ILL";

    public String processLine(final String[] lines, final int start, final int end, final int width) {

        int lineLength = lineLength(lines);
            /* 
                _  _     _  _  _  _  _ 
              | _| _||_||_ |_   ||_||_| 
              ||_  _|  | _||_|  ||_| _|
              
              For the above n lines, read start to end in increments of width. We want to build images of 1, 2, 3 etc...
            */


        String result = IntStream.iterate(0, i -> i < lineLength, i -> i += width)
                                 .mapToObj(carrot -> snapShot(lines, start, end, carrot, width))
                                 .map(this::process)
                                 .collect(Collectors.joining());

        if (result.contains(UNKNOWN)) {
            result += ILLEGAL;
        }

        return result;
    }

    private Image snapShot(final String[] lines, final int start, final int end, final int carrot, final int width) {
        return Image.builder()
                    .lines(Arrays.stream(lines, start, end)
                                 .map(e -> e.substring(carrot, carrot + width))
                                 .collect(Collectors.toList()))
                    .build();
    }

    private String process(final Image image) {
        int numberOfLines = image.getLines().size();
        int firstLinePos = 0;
        int middleLinePos = (int) Math.floor(((double) numberOfLines) / 2);
        int lastLinePos = image.getLines().size() - 1;

        Number number = buildNumber(image, firstLinePos, middleLinePos, lastLinePos);
        Integer value = NumberConfig.NUMBER_MAP.get(number);
        Optional<VariableNumber> variableNumber = buildVariableNumber(image, firstLinePos, middleLinePos, lastLinePos);

        if (value != null && variableNumber.isPresent()) {

            VariableNumber ref = VariableNumberConfig.variableNumberMap.get(value);
            VariableNumber current = variableNumber.get();

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

        return value == null ? UNKNOWN : value + "";
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
                          .filter(e -> e % TextScanner.minWidth == 0)
                          .findFirst()
                          .orElseThrow(() -> new IllegalStateException("Line needs to be in multiples of " + TextScanner.minWidth));
    }

    private Number buildNumber(final Image image, final int firstLinePos, final int middleLinePos, final int lastLinePos) {
        return Number.builder()
                     .top(Line.builder()
                              .left(lineLeft(image.getLines().get(firstLinePos)))
                              .body(lineBody(image.getLines().get(firstLinePos)))
                              .right(lineRight(image.getLines().get(firstLinePos)))
                              .build())
                     .middle(Line.builder()
                                 .left(lineLeft(image.getLines().get(middleLinePos)))
                                 .body(lineBody(image.getLines().get(middleLinePos)))
                                 .right(lineRight(image.getLines().get(middleLinePos)))
                                 .build())
                     .bottom(Line.builder()
                                 .left(lineLeft(image.getLines().get(lastLinePos)))
                                 .body(lineBody(image.getLines().get(lastLinePos)))
                                 .right(lineRight(image.getLines().get(lastLinePos)))
                                 .build())
                     .build();
    }

    private Optional<VariableNumber> buildVariableNumber(final Image image, final int firstLinePos, final int middleLinePos, final int lastLinePos) {
        Optional<Line> top = variableLine(firstLinePos + 1, middleLinePos, image.getLines());
        Optional<Line> bottom = variableLine(middleLinePos + 1, lastLinePos, image.getLines());

        return top.isEmpty() && bottom.isEmpty()
                ? Optional.empty()
                : Optional.of(VariableNumber.builder()
                                            .top(top.orElse(null))
                                            .bottom(bottom.orElse(null))
                                            .build());
    }

    private Fill lineLeft(final String s) {
        return Fill.from(s.charAt(0) + "");
    }

    private Fill lineBody(final String s) {
        return Fill.from(Arrays.stream(s.substring(1, s.length() - 1).split(""))
                               .distinct()
                               .collect(Collectors.joining()));
    }

    private Fill lineRight(final String s) {
        return Fill.from(s.charAt(s.length() - 1) + "");
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
