package digital.processor;

import digital.config.VariableDigitConfig;
import digital.model.Fill;
import digital.model.Line;
import digital.model.VariableDigit;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static digital.util.LineUtils.matchBody;
import static digital.util.LineUtils.matchEdge;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.stripToNull;

public class VariableDigitalTextProcessor extends AbstractDigitalTextProcessor {
    
    @Override
    public Integer value(final List<String> lines, final int firstLinePos, final int middleLinePos, final int lastLinePos) {
        Integer value = super.handle(lines, firstLinePos, middleLinePos, lastLinePos);

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
        
        return value;
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
