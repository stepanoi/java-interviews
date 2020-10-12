package com.digitalnumber.scanner.util;

import com.digitalnumber.scanner.model.Line;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.digitalnumber.scanner.model.Fill.BAR;
import static com.digitalnumber.scanner.model.Fill.BLANK;
import static com.digitalnumber.scanner.model.Fill.LINE;

class LineUtilsTest {
    static Stream<Arguments> should_match_edge() {
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
    void should_match_edge(Line current, Line ref, Boolean expected) {
        Assertions.assertEquals(expected, LineUtils.matchEdge(current, ref));
    }

    static Stream<Arguments> should_match_body() {
        return Stream.of(
                Arguments.of(Line.builder().build(), Line.builder().build(), true),
                Arguments.of(Line.builder().body(BAR).build(), Line.builder().body(BAR).build(), true),
                Arguments.of(Line.builder().body(BAR).build(), Line.builder().body(LINE).build(), false),
                
                Arguments.of(Line.builder().build(), Line.builder().body(BAR).build(), true),
                Arguments.of(Line.builder().body(BAR).build(), Line.builder().build(), false),
                
                Arguments.of(Line.builder().body(BAR).build(), null, false),
                Arguments.of(Line.builder().build(), null, false),
                Arguments.of(null, Line.builder().body(BAR).build(), false),
                Arguments.of(null, Line.builder().build(), false),
                
                Arguments.of(null, null, false)
        );
    }

    @ParameterizedTest
    @MethodSource
    void should_match_body(Line current, Line ref, Boolean expected) {
        Assertions.assertEquals(expected, LineUtils.matchBody(current, ref));
    }

    static Stream<Arguments> should_create_line_as_expected() {
        return Stream.of(
                Arguments.of("   ", Line.builder().left(BLANK).body(BLANK).right(BLANK).build()),
                
                Arguments.of("_  ", Line.builder().left(BAR).body(BLANK).right(BLANK).build()),
                Arguments.of(" _ ", Line.builder().left(BLANK).body(BAR).right(BLANK).build()),
                Arguments.of("  _", Line.builder().left(BLANK).body(BLANK).right(BAR).build()),
                Arguments.of("__ ", Line.builder().left(BAR).body(BAR).right(BLANK).build()),
                Arguments.of(" __", Line.builder().left(BLANK).body(BAR).right(BAR).build()),
                Arguments.of("___", Line.builder().left(BAR).body(BAR).right(BAR).build()),
                
                Arguments.of("|  ", Line.builder().left(LINE).body(BLANK).right(BLANK).build()),
                Arguments.of(" | ", Line.builder().left(BLANK).body(LINE).right(BLANK).build()),
                Arguments.of("  |", Line.builder().left(BLANK).body(BLANK).right(LINE).build()),
                Arguments.of("|| ", Line.builder().left(LINE).body(LINE).right(BLANK).build()),
                Arguments.of(" ||", Line.builder().left(BLANK).body(LINE).right(LINE).build()),
                Arguments.of("|||", Line.builder().left(LINE).body(LINE).right(LINE).build()),

                Arguments.of("*  ", Line.builder().left(null).body(BLANK).right(BLANK).build()),
                Arguments.of(" * ", Line.builder().left(BLANK).body(null).right(BLANK).build()),
                Arguments.of("  *", Line.builder().left(BLANK).body(BLANK).right(null).build()),
                Arguments.of("** ", Line.builder().left(null).body(null).right(BLANK).build()),
                Arguments.of(" **", Line.builder().left(BLANK).body(null).right(null).build()),
                Arguments.of("***", Line.builder().left(null).body(null).right(null).build())
        );
    }

    @ParameterizedTest
    @MethodSource
    void should_create_line_as_expected(String string, Line expected) {
        Assertions.assertEquals(expected, LineUtils.line(string));
    }


}