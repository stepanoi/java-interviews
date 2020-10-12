package com.digitalnumber.scanner.util;

import com.digitalnumber.scanner.model.Fill;
import com.digitalnumber.scanner.model.Line;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class LineUtils {
    private static final String EMPTY = "";

    public static Line line(final String line) {
        Fill lineLeft = Fill.from(StringUtils.left(line, 1));

        Fill lineBody = Fill.from(Arrays.stream(line.substring(1, line.length() - 1).split(EMPTY))
                                        .distinct()
                                        .collect(Collectors.joining()));

        Fill lineRight = Fill.from(StringUtils.right(line, 1));

        return Line.builder()
                   .left(lineLeft)
                   .body(lineBody)
                   .right(lineRight)
                   .build();
    }

    public static boolean matchEdge(final Line current, final Line ref) {
        return Objects.nonNull(current) &&
               Objects.nonNull(ref) &&
               current.getRight() == ref.getRight() &&
               current.getLeft() == ref.getLeft();
    }

    public static boolean matchBody(final Line current, final Line ref) {
        return Objects.nonNull(current) &&
               Objects.nonNull(ref) &&
               (current.getBody() == null || current.getBody() == ref.getBody());
    }
}
