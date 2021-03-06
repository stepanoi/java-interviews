package com.digitalnumber.scanner;

import com.digitalnumber.scanner.model.ScanConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static com.digitalnumber.scanner.container.DigitalNumberContainer.MAX_DIGITS_PER_LINE;
import static com.digitalnumber.scanner.container.DigitalNumberContainer.MIN_WIDTH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EnhancedDigitalNumberScannerTest {
    private final DigitalNumberScanner unit = new DigitalNumberScanner();

    static Stream<Arguments> should_throw_exception_when_lines_length_is_not_exactly_product_of_custom_width_and_max_digits_per_line() {
        return Stream.of(
                //space at end
                Arguments.of(" __  __  __  __  __  __  __  __  __  \n" +
                             "|  ||  ||  ||  ||  ||  ||  ||  ||  | \n" +
                             "|__||__||__||__||__||__||__||__||__| ", 4),

                //space at start
                Arguments.of("  __  __  __  __  __  __  __  __  __ \n" +
                             " |  ||  ||  ||  ||  ||  ||  ||  ||  |\n" +
                             " |__||__||__||__||__||__||__||__||__|", 4),

                //space at start and end
                Arguments.of("  __  __  __  __  __  __  __  __  __  \n" +
                             " |  ||  ||  ||  ||  ||  ||  ||  ||  | \n" +
                             " |__||__||__||__||__||__||__||__||__| ", 4)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            //no new line at start/end
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|",

            //new line at start
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|",

            //more new lines at start
            "\n" +
            "\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|",

            //new line at end
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|" +
            "\n",


            //more new lines at end
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|" +
            "\n" +
            "\n" +
            "\n",

            //new line at start/end
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|" +
            "\n",

            //more new lines at start/end
            "\n" +
            "\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|" +
            "\n" +
            "\n" +
            "\n",

            //new lines in middle
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|",

            //new lines at start, middle and end
            "\n" +
            "\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|\n" +
            "\n" +
            "\n" +
            "\n" +
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n" +
            "  ||_  _|  | _||_|  ||_| _|" +
            "\n" +
            "\n" +
            "\n",

    })
    void should_match_multiple_numbers_multi_line_separator(String input) {
        String expected = "123456789\n" +
                          "123456789\n" +
                          "123456789";
        assertEquals(expected, unit.scan(input, ScanConfig.builder()
                                                          .width(MIN_WIDTH)
                                                          .variableMode(false)
                                                          .singleEmptyLineSeparator(false)
                                                          .build()));
    }

    @Test
    void should_match_variable_height() {
        String input = " _  _  _  _  _  _  _  _  _ \n" +
                       "| || || || || || || || || |\n" +
                       "| || || || || || || || || |\n" +
                       "| || || || || || || || || |\n" +
                       "|_||_||_||_||_||_||_||_||_|";

        assertEquals("000000000", unit.scan(input, ScanConfig.builder()
                                                             .width(MIN_WIDTH)
                                                             .variableMode(true)
                                                             .singleEmptyLineSeparator(true)
                                                             .build()));
    }

    @Test
    void should_not_match_variable_number() {
        String input = "*_  _  _  _  _  _  _  _  _ \n" +
                       "* || || || || || || || || |\n" +
                       "| || || || || || || || || |\n" +
                       "| || || || || || || || || |\n" +
                       "|_||_||_||_||_||_||_||_||_|";

        assertEquals("?00000000ILL", unit.scan(input, ScanConfig.builder()
                                                                .width(MIN_WIDTH)
                                                                .variableMode(true)
                                                                .singleEmptyLineSeparator(true)
                                                                .build()));
    }

    @Test
    void should_not_match_variable_height() {
        String input = " _  _  _  _  _  _  _  _  _ \n" +
                       "| || || || || || || || || |\n" +
                       "| || || || || || || || || |\n" +
                       "|_||_||_||_||_||_||_||_||_|";

        assertEquals("?????????ILL", unit.scan(input, ScanConfig.builder()
                                                                .width(MIN_WIDTH)
                                                                .variableMode(true)
                                                                .singleEmptyLineSeparator(true)
                                                                .build()));
    }

    @Test
    void should_match_variable_width() {
        String input = " __  __  __  __  __  __  __  __  __ \n" +
                       "|  ||  ||  ||  ||  ||  ||  ||  ||  |\n" +
                       "|__||__||__||__||__||__||__||__||__|";

        assertEquals("000000000", unit.scan(input, ScanConfig.builder()
                                                             .width(4)
                                                             .variableMode(false)
                                                             .singleEmptyLineSeparator(true)
                                                             .build()));
    }

    @ParameterizedTest
    @MethodSource
    void should_throw_exception_when_lines_length_is_not_exactly_product_of_custom_width_and_max_digits_per_line(String input, int width) {
        Exception exception = assertThrows(IllegalStateException.class, () -> unit.scan(input, ScanConfig.builder()
                                                                                                         .width(width)
                                                                                                         .variableMode(true)
                                                                                                         .singleEmptyLineSeparator(true)
                                                                                                         .build()));

        assertEquals("Line needs to have exactly " + MAX_DIGITS_PER_LINE + " digits and " + MAX_DIGITS_PER_LINE * width + " characters long", exception.getMessage());
    }
}