package com.digitalnumber.scanner.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VariableDigitConfigTest {
    @Test
    public void should_have_10_digits() {
        assertEquals(10, VariableDigitConfig.MAP.entrySet().size());
    }

    @Test
    public void should_have_config_for_0_to_9() {
        assertEquals(Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), VariableDigitConfig.MAP.keySet());
    }

    public static Stream<Arguments> should_have_same_config() {
        return Stream.of(
                Arguments.of(Set.of(0, 8)),
                Arguments.of(Set.of(1, 3, 7)),
                Arguments.of(Set.of(2)),
                Arguments.of(Set.of(4, 9)),
                Arguments.of(Set.of(5)),
                Arguments.of(Set.of(6))
                
        );
    }

    @ParameterizedTest
    @MethodSource
    public void should_have_same_config(Set<Integer> numbers) {
        assertEquals(1, numbers.stream().map(VariableDigitConfig.MAP::get).collect(Collectors.toSet()).size());
    }
}
