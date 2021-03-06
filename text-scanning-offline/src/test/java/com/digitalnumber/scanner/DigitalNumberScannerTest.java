package com.digitalnumber.scanner;

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

class DigitalNumberScannerTest {
    private final DigitalNumberScanner unit = new DigitalNumberScanner();

    static Stream<Arguments> should_match_single_number() {
        return Stream.of(
                //123456789
                Arguments.of("    _  _     _  _  _  _  _ \n" +
                             "  | _| _||_||_ |_   ||_||_|\n" +
                             "  ||_  _|  | _||_|  ||_| _|",  "123456789"),
                //987654321
                Arguments.of(" _  _  _  _  _     _  _    \n" +
                             "|_||_|  ||_ |_ |_| _| _|  |\n" +
                             " _||_|  ||_| _|  | _||_   |",  "987654321"),
                //000000000
                Arguments.of(" _  _  _  _  _  _  _  _  _ \n" +
                             "| || || || || || || || || |\n" +
                             "|_||_||_||_||_||_||_||_||_|",  "000000000")
        );
    }

    @ParameterizedTest
    @MethodSource
    void should_match_single_number(String input, String expected) {
        assertEquals(expected, unit.scan(input));
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
            
    })
    void should_match_multiple_numbers(String input) {
       assertEquals("123456789\n" +
                     "123456789\n" +
                     "123456789", unit.scan(input));
    }
    
    static Stream<Arguments> should_throw_exception_when_lines_of_different_length() {
        return Stream.of(
                //space at end
                Arguments.of(" _  _  _  _  _  _  _  _     \n" +
                             "| || || || || || || || || |\n" +
                             "|_||_||_||_||_||_||_||_||_|",  "[28, 27]"),
                //space at end
                Arguments.of(" _  _  _  _  _  _  _  _    \n" +
                             "| || || || || || || || || |\n" +
                             "|_||_||_||_||_||_||_||_||_| ",  "[27, 28]"),
                //space at start
                Arguments.of(" _  _  _  _  _  _  _  _  _ \n" +
                             " | || || || || || || || || |\n" +
                             "|_||_||_||_||_||_||_||_||_|",  "[27, 28]"),
                //space at start and end
                Arguments.of(" _  _  _  _  _  _  _  _    \n" +
                             " | || || || || || || || || | \n" +
                             "|_||_||_||_||_||_||_||_||_| ",  "[27, 29, 28]")
        );
    }

    @ParameterizedTest
    @MethodSource
    void should_throw_exception_when_lines_of_different_length(String input, String expected) {
        Exception exception = assertThrows(IllegalStateException.class, () -> unit.scan(input));
        
        assertEquals("Different Line lengths detected " + expected, exception.getMessage());
    }

    static Stream<Arguments> should_detect_illegal_row() {
        return Stream.of(
                //5 of first block is not well formed
                Arguments.of("    _  _     _  _  _  _  _ \n" +
                             "  | _| _||_||_ |_   ||_||_|\n" +
                             "  ||_  _|  |  ||_|  ||_| _|\n" +
                             "\n" +
                             "    _  _     _  _  _  _  _ \n" +
                             "  | _| _||_||_ |_   ||_||_|\n" +
                             "  ||_  _|  | _||_|  ||_| _|\n" +
                             " \n" +
                             "    _  _     _  _  _  _  _ \n" +
                             "  | _| _||_||_ |_   ||_||_|\n" +
                             "  ||_  _|  | _||_|  ||_| _|",

                        "1234?6789ILL\n" +
                        "123456789\n" +
                        "123456789"),
                
                //7 of second block is not well formed
                Arguments.of("    _  _     _  _  _  _  _ \n" +
                             "  | _| _||_||_ |_   ||_||_|\n" +
                             "  ||_  _|  | _||_|  ||_| _|\n" +
                             "\n" +
                             "    _  _     _  _  _  _  _ \n" +
                             "  | _| _||_||_ |_   ||_||_|\n" +
                             "  ||_  _|  | _||_|| ||_| _|\n" +
                             " \n" +
                             "    _  _     _  _  _  _  _ \n" +
                             "  | _| _||_||_ |_   ||_||_|\n" +
                             "  ||_  _|  | _||_|  ||_| _|",

                        "123456789\n" +
                        "123456?89ILL\n" +
                        "123456789"),
                
                //3 of third block is not well formed
                Arguments.of("    _  _     _  _  _  _  _ \n" +
                             "  | _| _||_||_ |_   ||_||_|\n" +
                             "  ||_  _|  | _||_|  ||_| _|\n" +
                             "\n" +
                             "    _  _     _  _  _  _  _ \n" +
                             "  | _| _||_||_ |_   ||_||_|\n" +
                             "  ||_  _|  | _||_|  ||_| _|\n" +
                             " \n" +
                             "    _  _     _  _  _  _  _ \n" +
                             "  | _| _||_||_ |_   ||_||_|\n" +
                             "  ||_  _   | _||_|  ||_| _|",

                        "123456789\n" +
                        "123456789\n" +
                        "12?456789ILL"),
                
                //5 of first block is not well formed
                //7 of second block is not well formed
                //3 of third block is not well formed
                Arguments.of("    _  _     _  _  _  _  _ \n" +
                             "  | _| _||_||_ |_   ||_||_|\n" +
                             "  ||_  _|  |  ||_|  ||_| _|\n" +
                             "\n" +
                             "    _  _     _  _  _  _  _ \n" +
                             "  | _| _||_||_ |_   ||_||_|\n" +
                             "  ||_  _|  | _||_|| ||_| _|\n" +
                             " \n" +
                             "    _  _     _  _  _  _  _ \n" +
                             "  | _| _||_||_ |_   ||_||_|\n" +
                             "  ||_  _   | _||_|  ||_| _|",

                        "1234?6789ILL\n" +
                        "123456?89ILL\n" +
                        "12?456789ILL")
        );
    }

    @ParameterizedTest
    @MethodSource
    void should_detect_illegal_row(String input, String expected) {
        assertEquals(expected, unit.scan(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            //blank line
            "",
            
            //new line
            "\n",
            
            //line with on char only
            "?",
            
            //line with a blank
            " ",
            
            //one line only
            "    _  _     _  _  _  _  _ \n",

            //two lines only
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n",
    })
    void should_throw_exception_when_lines_are_less_than_expected(String value) {
        Exception exception = assertThrows(IllegalStateException.class, () -> unit.scan(value));

        assertEquals("Input needs to have at least 3 lines", exception.getMessage());
    }

    @Test
    void should_throw_exception_when_lines_height_is_not_min_height() {
        String input = " _  _  _  _  _  _  _  _  _ \n" +
                       "| || || || || || || || || |\n" +
                       "| || || || || || || || || |\n" +
                       "| || || || || || || || || |\n" +
                       "|_||_||_||_||_||_||_||_||_|";

        Exception exception = assertThrows(IllegalStateException.class, () -> unit.scan(input));

        assertEquals("Input needs to be exactly " + MIN_WIDTH  + " lines", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            //single digit
            " _ \n" +
            "|_|\n" +
            "|_|\n",
            
            //9 not well formed = 26 chars long
            "    _  _     _  _  _  _  _\n" +
            "  | _| _||_||_ |_   ||_||_\n" +
            "  ||_  _|  | _||_|  ||_| _\n",
            
            //1 not well formed = 26 chars long
            "   _  _     _  _  _  _  _\n" +
            " | _| _||_||_ |_   ||_||_\n" +
            " ||_  _|  | _||_|  ||_| _\n",
            
            //9 has additional space at end = 28 chars long
            "    _  _     _  _  _  _  _  \n" +
            "  | _| _||_||_ |_   ||_||_| \n" +
            "  ||_  _|  | _||_|  ||_| _| \n",
            
            //1 has additional space at start = 28 chars long 
            "     _  _     _  _  _  _  _ \n" +
            "   | _| _||_||_ |_   ||_||_|\n" +
            "   ||_  _|  | _||_|  ||_| _|\n",
            
            //1 and 9 has additional space at start / end = 30 chars long 
            "     _  _     _  _  _  _  _  \n" +
            "   | _| _||_||_ |_   ||_||_| \n" +
            "   ||_  _|  | _||_|  ||_| _| \n",
            
            //1 has additional space at end = 28 chars long  
            "     _  _     _  _  _  _  _ \n" +
            "  |  _| _||_||_ |_   ||_||_|\n" +
            "  | |_  _|  | _||_|  ||_| _|\n",
            
            //8 digits 3 chars long = 24 chars long
            " _  _     _  _  _  _  _ \n" +
            " _| _||_||_ |_   ||_||_|\n" +
            "|_  _|  | _||_|  ||_| _|\n",
            
            //10 digits 3 chars long = 30 chars long
            " _     _  _     _  _  _  _  _ \n" +
            "| |  | _| _||_||_ |_   ||_||_|\n" +
            "|_|  ||_  _|  | _||_|  ||_| _|\n",
            
            //9 digits 4 chars long = 36 chars long
            " __  __  __  __  __  __  __  __  __ \n" +
            "|  ||  ||  ||  ||  ||  ||  ||  ||  |\n" +
            "|__||__||__||__||__||__||__||__||__|"
    })
    void should_throw_exception_when_lines_length_is_not_exactly_product_of_min_width_and_max_digits_per_line(String input) {
        Exception exception = assertThrows(IllegalStateException.class, () -> unit.scan(input));

        assertEquals("Line needs to have exactly " + MAX_DIGITS_PER_LINE + " digits and " + MAX_DIGITS_PER_LINE * MIN_WIDTH  + " characters long", exception.getMessage());
    }
}