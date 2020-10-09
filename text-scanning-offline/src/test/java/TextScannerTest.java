import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextScannerTest {
    private final TextScanner unit = new TextScanner();

    @Test
    public void should_match_123456789() {
        String input = "    _  _     _  _  _  _  _ \n" +
                       "  | _| _||_||_ |_   ||_||_|\n" +
                       "  ||_  _|  | _||_|  ||_| _|";

        assertEquals("123456789", unit.scan(input));
    }

    @Test
    public void should_match_987654321() {
        String input = " _  _  _  _  _     _  _    \n" +
                       "|_||_|  ||_ |_ |_| _| _|  |\n" +
                       " _||_|  ||_| _|  | _||_   |";

        assertEquals("987654321", unit.scan(input));
    }

    @Test
    public void should_match_000000000() {
        String input = " _  _  _  _  _  _  _  _  _ \n" +
                       "| || || || || || || || || |\n" +
                       "|_||_||_||_||_||_||_||_||_|";

        assertEquals("000000000", unit.scan(input));
    }

    @Test
    public void should_match_3_lines_of_123456789() {
        String input = "    _  _     _  _  _  _  _ \n" +
                       "  | _| _||_||_ |_   ||_||_|\n" +
                       "  ||_  _|  | _||_|  ||_| _|\n" +
                       "\n" +
                       "    _  _     _  _  _  _  _ \n" +
                       "  | _| _||_||_ |_   ||_||_|\n" +
                       "  ||_  _|  | _||_|  ||_| _|\n" +
                       "\n" +
                       "    _  _     _  _  _  _  _ \n" +
                       "  | _| _||_||_ |_   ||_||_|\n" +
                       "  ||_  _|  | _||_|  ||_| _|";

        String expected = "123456789\n" +
                          "123456789\n" +
                          "123456789";
        assertEquals(expected, unit.scan(input));
    }

    @Test
    public void should_match_3_lines_of_123456789_with_additional_lines() {
        String input = "\n" +
                       "    _  _     _  _  _  _  _ \n" +
                       "  | _| _||_||_ |_   ||_||_|\n" +
                       "  ||_  _|  | _||_|  ||_| _|\n" +
                       "\n" +
                       "    _  _     _  _  _  _  _ \n" +
                       "  | _| _||_||_ |_   ||_||_|\n" +
                       "  ||_  _|  | _||_|  ||_| _|\n" +
                       "\n" +
                       "\n" +
                       "    _  _     _  _  _  _  _ \n" +
                       "  | _| _||_||_ |_   ||_||_|\n" +
                       "  ||_  _|  | _||_|  ||_| _|\n" +
                       "\n";

        String expected = "123456789\n" +
                          "123456789\n" +
                          "123456789";
        assertEquals(expected, unit.scan(input));
    }

    @Test
    public void should_match_3_lines_of_123456789_with_additional_lines_at_start() {
        String input = "\n" +
                       "\n" +
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
                       "  ||_  _|  | _||_|  ||_| _|\n";

        String expected = "123456789\n" +
                          "123456789\n" +
                          "123456789";
        assertEquals(expected, unit.scan(input));
    }

    @Test
    public void should_match_3_lines_of_123456789_with_additional_lines_at_end() {
        String input = "    _  _     _  _  _  _  _ \n" +
                       "  | _| _||_||_ |_   ||_||_|\n" +
                       "  ||_  _|  | _||_|  ||_| _|\n" +
                       "\n" +
                       "    _  _     _  _  _  _  _ \n" +
                       "  | _| _||_||_ |_   ||_||_|\n" +
                       "  ||_  _|  | _||_|  ||_| _|\n" +
                       "\n" +
                       "    _  _     _  _  _  _  _ \n" +
                       "  | _| _||_||_ |_   ||_||_|\n" +
                       "  ||_  _|  | _||_|  ||_| _|\n" +
                       "\n" +
                       "\n" +
                       "\n" +
                       "\n" +
                       "\n";

        String expected = "123456789\n" +
                          "123456789\n" +
                          "123456789";
        assertEquals(expected, unit.scan(input));
    }

    @Test
    public void should_expect_exception_when_lines_of_different_length() {
        String input = " _  _  _  _  _  _  _  _   \n" +
                       "| || || || || || || || || |\n" +
                       "|_||_||_||_||_||_||_||_||_|";

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            unit.scan(input);
        });
        
        assertEquals("Different Line lengths detected [26, 27]", exception.getMessage());
    }

    @Test
    public void should_match_detect_illegal_row() {
        String input = "    _  _     _  _  _  _  _ \n" +
                       "  | _| _||_||_ |_   ||_||_|\n" +
                       "  ||_  _|  | _||_|  ||_| _|\n" +
                       "\n" +
                       "    _  _     _  _  _  _  _ \n" +
                       "  | _| _||_||_ |_   ||_||_|\n" +
                       "  ||_  _|  | _||_|| ||_| _|\n" +
                       " \n" +
                       "    _  _     _  _  _  _  _ \n" +
                       "  | _| _||_||_ |_   ||_||_|\n" +
                       "  ||_  _|  | _||_|  ||_| _|";

        String expected = "123456789\n" +
                          "123456?89ILL\n" +
                          "123456789";
        assertEquals(expected, unit.scan(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            "\n",
            "?",
            " ",
            "    _  _     _  _  _  _  _ \n",
            "    _  _     _  _  _  _  _ \n" +
            "  | _| _||_||_ |_   ||_||_|\n"
    })
    public void should_expect_exception_when_lines_are_less_than_expected(String value) {
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            unit.scan(value);
        });

        assertEquals("Input needs to have at least 3 lines", exception.getMessage());
    }

    @Test
    public void should_expect_exception_when_lines_width_not_in_multiples_of_mind_width() {
        String input = " _  _  _  _  _  _  _  _     \n" +
                       "| || || || || || || || || | \n" +
                       "|_||_||_||_||_||_||_||_||_| ";
        
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            unit.scan(input);
        });

        assertEquals("Line needs to be in multiples of " + TextScanner.minWidth, exception.getMessage());
    }

    @Test
    public void should_match_variable_height() {
        String input = " _  _  _  _  _  _  _  _  _ \n" +
                       "| || || || || || || || || |\n" +
                       "| || || || || || || || || |\n" +
                       "| || || || || || || || || |\n" +
                       "|_||_||_||_||_||_||_||_||_|";

        assertEquals("000000000", unit.scan(input));
    }

    @Test
    public void should_not_match_variable_height() {
        String input = " _  _  _  _  _  _  _  _  _ \n" +
                       "| || || || || || || || || |\n" +
                       "| || || || || || || || || |\n" +
                       "|_||_||_||_||_||_||_||_||_|";

        assertEquals("?????????ILL", unit.scan(input));
    }

    @Test
    public void should_match_variable_width() {
        String input = " __  __  __  __  __  __  __  __  __ \n" +
                       "|  ||  ||  ||  ||  ||  ||  ||  ||  |\n" +
                       "|__||__||__||__||__||__||__||__||__|";

        assertEquals("000000000", unit.scan(input, 4));
    }


}