package com.digitalnumber.scanner.config;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DigitConfigTest {
    
    @Test
    public void should_have_10_digits() {
        assertEquals(10, DigitConfig.MAP.entrySet().size());
    }

    @Test
    public void should_have_config_for_0_to_9() {
        assertEquals(Set.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), new HashSet<>(DigitConfig.MAP.values()));
    }
}