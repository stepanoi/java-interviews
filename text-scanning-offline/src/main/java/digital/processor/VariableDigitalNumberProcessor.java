package digital.processor;

import digital.config.VariableDigitConfig;
import digital.model.Fill;
import digital.model.Line;
import digital.model.VariableDigit;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static digital.util.LineUtils.matchBody;
import static digital.util.LineUtils.matchEdge;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.stripToNull;

/*
    This class handles processing of digits that have variable width and height. It serves as an extension to the initial requirement.
    It can be activated by specifying Task.variableMode = true
 */
public final class VariableDigitalNumberProcessor extends AbstractDigitalNumberProcessor {

    @Override
    public Integer recognise(final List<String> lines, final int firstLinePos, final int middleLinePos, final int lastLinePos) {
        Integer value = super.read(lines, firstLinePos, middleLinePos, lastLinePos);
        
        if (nonNull(value)) {
            VariableDigit current = buildVariableDigit(lines, firstLinePos, middleLinePos, lastLinePos);
            
            VariableDigit ref = VariableDigitConfig.MAP.get(value);

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

        return value;
    }

    private VariableDigit buildVariableDigit(final List<String> lines, final int firstLinePos, final int middleLinePos, final int lastLinePos) {
        Line top = variableLine(firstLinePos + 1, middleLinePos, lines);
        Line bottom = variableLine(middleLinePos + 1, lastLinePos, lines);

        return VariableDigit.builder()
                            .top(top)
                            .bottom(bottom)
                            .build();
    }

    private Line variableLine(final int start, final int end, final List<String> lines) {
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

        return Line.builder()
                   .left(Fill.from(left))
                   .body(Fill.from(body))
                   .right(Fill.from(right))
                   .build();
    }
}
