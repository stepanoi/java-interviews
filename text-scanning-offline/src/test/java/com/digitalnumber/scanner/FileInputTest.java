package com.digitalnumber.scanner;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileInputTest {
    private final DigitalNumberScanner unit = new DigitalNumberScanner();
    
    static Stream<Arguments> should_read_from_file_and_match() {
        return Stream.of(
                Arguments.of("input/singleChunk", "000000000"),
                Arguments.of("input/multipleChunks", "123456789\n" +
                                                     "123456789\n" +
                                                     "123456789"),
                Arguments.of("input/multipleChunksWithIllegalRow", "123456789\n" +
                                                                   "123456?89ILL\n" +
                                                                   "123456789")
        );
    }

    @ParameterizedTest
    @MethodSource
    void should_read_from_file_and_match(Path path, String expected) throws IOException {
        assertEquals(expected, unit.scan(Files.readString(path)));
    }

}
