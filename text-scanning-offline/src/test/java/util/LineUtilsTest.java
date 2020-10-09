package util;

import model.Line;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static model.Fill.BAR;
import static model.Fill.LINE;

class LineUtilsTest {
    public static Stream<Arguments> should_match_edge() {
        return Stream.of(
                Arguments.of(Line.builder().left(BAR).build(), Line.builder().left(BAR).build(), true),
                Arguments.of(Line.builder().right(BAR).build(), Line.builder().right(BAR).build(), true),
                Arguments.of(Line.builder().build(), Line.builder().left(BAR).build(), false),
                Arguments.of(Line.builder().right(BAR).build(), Line.builder().build(), false),
                Arguments.of(Line.builder().left(BAR).build(), null, false),
                Arguments.of(null, Line.builder().right(BAR).build(), false),
                Arguments.of(null, null, false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void should_match_edge(Line current, Line ref, Boolean expected) {
        Assertions.assertEquals(expected, LineUtils.matchEdge(current, ref));
    }

    public static Stream<Arguments> should_match_body() {
        return Stream.of(
                Arguments.of(Line.builder().build(), Line.builder().build(), true),
                Arguments.of(Line.builder().body(BAR).build(), Line.builder().body(BAR).build(), true),
                Arguments.of(Line.builder().body(BAR).build(), Line.builder().body(LINE).build(), true),
                
                Arguments.of(Line.builder().build(), Line.builder().body(BAR).build(), true),
                Arguments.of(Line.builder().body(BAR).build(), Line.builder().build(), true),
                
                Arguments.of(Line.builder().body(BAR).build(), null, false),
                Arguments.of(Line.builder().build(), null, false),
                Arguments.of(null, Line.builder().body(BAR).build(), false),
                Arguments.of(null, Line.builder().build(), false),
                
                Arguments.of(null, null, false)
        );
    }

    @ParameterizedTest
    @MethodSource
    public void should_match_body(Line current, Line ref, Boolean expected) {
        Assertions.assertEquals(expected, LineUtils.matchEdge(current, ref));
    }
}